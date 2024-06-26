package fplhn.udpm.identity.core.feature.role.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.role.model.request.ModifyRoleRequest;
import fplhn.udpm.identity.core.feature.role.model.request.RolePaginationRequest;
import fplhn.udpm.identity.core.feature.role.repository.RoleExtendRepository;
import fplhn.udpm.identity.core.feature.role.service.RoleService;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleExtendRepository roleExtendRepository;

    @Override
    public ResponseObject<?> findAllEntity(RolePaginationRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "id");
            return new ResponseObject<>(
                    PageableObject.of(roleExtendRepository.findAllRole(request, pageable)),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> create(ModifyRoleRequest request) {
        String ma = request.getRoleCode();

        Optional<Role> roleOptional = roleExtendRepository.findByCode(ma);
        if (roleOptional.isPresent()) {
            return new ResponseObject<>(
                    null,
                    HttpStatus.BAD_REQUEST,
                    ResponseMessage.CODE_EXIST.getMessage() + ": " + ma
            );
        }

        Role role = new Role();
        role.setCode(request.getRoleCode());
        role.setName(request.getRoleName());
        role.setEntityStatus(EntityStatus.NOT_DELETED);
        Role savedRole = roleExtendRepository.save(role);
        return new ResponseObject<>(savedRole, HttpStatus.OK, ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseObject<?> update(ModifyRoleRequest request, Long id) {
        Optional<Role> roleOptional = roleExtendRepository.findById(id);
        if (roleOptional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND.getMessage());
        }

        if (!roleOptional.get().getCode().trim().equalsIgnoreCase(request.getRoleCode().trim())) {
            if (roleExtendRepository.existsByCode(request.getRoleCode().trim())) {
                return new ResponseObject<>(
                        null,
                        HttpStatus.BAD_REQUEST,
                        ResponseMessage.CODE_EXIST.getMessage() + ": " + request.getRoleCode()
                );
            }
        }

        Role role = roleOptional.get();
        role.setCode(request.getRoleCode());
        role.setName(request.getRoleName());
        Role savedRole = roleExtendRepository.save(role);
        return new ResponseObject<>(savedRole, HttpStatus.OK, ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseObject<?> getRoleById(Long id) {
        try {
            Optional<Role> roleOptional = roleExtendRepository.findById(id);
            if (roleOptional.isEmpty()) {
                return new ResponseObject<>(
                        null,
                        HttpStatus.BAD_REQUEST,
                        ResponseMessage.NOT_FOUND.getMessage()
                );
            }
            return new ResponseObject<>(
                    roleExtendRepository.findDetailById(id),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> getAllRole() {
        try {
            return new ResponseObject<>(
                    roleExtendRepository.getAll(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> delete(Long id) {
        Optional<Role> roleOptional = roleExtendRepository.findById(id);
        if (roleOptional.isEmpty()) {
            return new ResponseObject<>(
                    null,
                    HttpStatus.BAD_REQUEST,
                    ResponseMessage.NOT_FOUND.getMessage()
            );
        }

        Role role = roleOptional.get();
        if (roleOptional.get().getEntityStatus().equals(EntityStatus.DELETED)) {
            role.setEntityStatus(EntityStatus.NOT_DELETED);
        } else {
            role.setEntityStatus(EntityStatus.DELETED);
        }
        roleExtendRepository.save(role);
        return new ResponseObject<>(
                null,
                HttpStatus.OK,
                ResponseMessage.SUCCESS.getMessage()
        );
    }

}
