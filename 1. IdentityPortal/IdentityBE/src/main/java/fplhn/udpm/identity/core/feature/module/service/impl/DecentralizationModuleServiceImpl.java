package fplhn.udpm.identity.core.feature.module.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffUpdateRequest;
import fplhn.udpm.identity.core.feature.module.model.response.ModuleRoleStaffResponse;
import fplhn.udpm.identity.core.feature.module.repository.AccessTokenDecentralizationRepository;
import fplhn.udpm.identity.core.feature.module.repository.ModuleDecentralizationRepository;
import fplhn.udpm.identity.core.feature.module.repository.RefreshTokenDecentralizationRepository;
import fplhn.udpm.identity.core.feature.module.repository.RoleDecentralizationRepository;
import fplhn.udpm.identity.core.feature.module.repository.StaffDecentralizationRepository;
import fplhn.udpm.identity.core.feature.module.repository.StaffModuleRoleAdminRepository;
import fplhn.udpm.identity.core.feature.module.service.DecentralizationModuleService;
import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.infrastructure.config.websocket.service.WebSocketService;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.constant.WebSocketCommand;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import fplhn.udpm.identity.util.DateTimeUtil;
import fplhn.udpm.identity.util.Helper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DecentralizationModuleServiceImpl implements DecentralizationModuleService {

    private final StaffModuleRoleAdminRepository staffModuleRoleAdminRepository;

    private final StaffDecentralizationRepository staffDecentralizationRepository;

    private final ModuleDecentralizationRepository moduleDecentralizationRepository;

    private final RoleDecentralizationRepository roleDecentralizationRepository;

    private final AccessTokenDecentralizationRepository accessTokenDecentralizationRepository;

    private final RefreshTokenDecentralizationRepository refreshTokenDecentralizationRepository;

    private final WebSocketService webSocketService;

    @Override
    public ResponseObject<?> getListRoleAvailable() {
        try {
            return ResponseObject.successForward(staffModuleRoleAdminRepository.getListRoleAvailable(), "Get list role available success");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> getStaffRoleModule(ModuleRoleStaffRequest request) {
        try {
            Long moduleId = request.getModuleId();
            String listStaffCodeString = request.getListStaffCode();
            Pageable pageable = Helper.createPageable(request, "staffId");
            if (listStaffCodeString == null || listStaffCodeString.isEmpty()) {
                PageableObject<List<ModuleRoleStaffResponse>> pageableObject = new PageableObject<>(
                        staffModuleRoleAdminRepository.getStaffRoleModuleByModuleIdNoSearch(moduleId, pageable)
                );
                return ResponseObject.successForward(pageableObject, "Get staff role by module id success");
            }
            PageableObject<List<ModuleRoleStaffResponse>> pageableObject = new PageableObject<>(
                    staffModuleRoleAdminRepository.getStaffRoleModuleByModuleId(moduleId, listStaffCodeString, pageable)
            );
            return ResponseObject.successForward(pageableObject, "Get staff role by module id success");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseObject<?> modifyStaffRoleModule(ModuleRoleStaffUpdateRequest request) {
        try {
            Long moduleId = request.getModuleId();
            List<ModuleRoleStaffUpdateRequest.StaffRoleRequest> staffs = request.getStaffs();

            for (ModuleRoleStaffUpdateRequest.StaffRoleRequest staff : staffs) {
                //! Get staff roles
                Long staffId = staff.getStaffId();
                String newRoles = staff.getRoles();
                List<String> newRoleList = newRoles != null ? Arrays.asList(newRoles.split(",")) : new ArrayList<>();

                //! Get old roles
                List<StaffRoleModule> oldStaffRoleModules = staffModuleRoleAdminRepository.findStaffRoleModuleByStaffIdAndModuleId(staffId, moduleId);
                List<String> oldRoleList = oldStaffRoleModules.stream().map(staffRoleModule -> staffRoleModule.getRole().getCode()).toList();

                //! Get roles to add and remove
                List<String> rolesToAdd = new ArrayList<>(newRoleList);
                rolesToAdd.removeAll(oldRoleList);
                List<String> rolesToRemove = new ArrayList<>(oldRoleList);
                rolesToRemove.removeAll(newRoleList);

                //! Send notification if role changed
                if (!rolesToAdd.isEmpty() || !rolesToRemove.isEmpty()) sendNotificationIfRoleChanged(staffId);

                //! Batch delete roles
                staffModuleRoleAdminRepository.deleteByStaffIdAndModuleIdAndRoleCodeIn(staffId, moduleId, rolesToRemove);

                //! Batch insert roles
                List<Role> roleEntities = roleDecentralizationRepository.findAllByCodeIn(rolesToAdd);
                List<StaffRoleModule> staffRoleModulesToAdd = roleEntities.stream().map(roleEntity -> {
                    StaffRoleModule staffRoleModule = new StaffRoleModule();
                    staffRoleModule.setStaff(staffDecentralizationRepository.findById(staffId).orElse(null));
                    staffRoleModule.setRole(roleEntity);
                    staffRoleModule.setModule(moduleDecentralizationRepository.findById(moduleId).orElse(null));
                    staffRoleModule.setEntityStatus(EntityStatus.NOT_DELETED);
                    return staffRoleModule;
                }).collect(Collectors.toList());
                staffModuleRoleAdminRepository.saveAll(staffRoleModulesToAdd);

                //! Revoke tokens if roles were modified
                if (!rolesToAdd.isEmpty() || !rolesToRemove.isEmpty()) {
                    Optional<AccessToken> accessToken = accessTokenDecentralizationRepository.findByUserIdAndUserType(staffId, UserType.CAN_BO);
                    Optional<RefreshToken> refreshToken = refreshTokenDecentralizationRepository.findByUserIdAndUserType(staffId, UserType.CAN_BO);
                    if (accessToken.isPresent() && refreshToken.isPresent()) {
                        accessToken.get().setRevokedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
                        refreshToken.get().setRevokedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
                        accessTokenDecentralizationRepository.save(accessToken.get());
                        refreshTokenDecentralizationRepository.save(refreshToken.get());
                    }
                }
            }
            return ResponseObject.successForward(null, "Modify staff role module success");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseObject<?> getListStaffInfo(String staffCode) {
        try {
            return ResponseObject.successForward(staffDecentralizationRepository.getStaffInfoByStaffCode(staffCode), "Get staff info success");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendNotificationIfRoleChanged(Long staffId) {
        //!CHECK IF USER'S ROLE IS CHANGED
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        if (userPrincipal.getId().equals(staffId)) {
            webSocketService.broadcastMessage(WebSocketCommand.getChangeRoleUser(staffId));
        }
    }

}
