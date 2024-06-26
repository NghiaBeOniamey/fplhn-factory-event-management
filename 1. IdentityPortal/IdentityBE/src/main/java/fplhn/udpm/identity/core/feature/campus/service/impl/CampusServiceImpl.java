package fplhn.udpm.identity.core.feature.campus.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.campus.model.request.CampusPaginationRequest;
import fplhn.udpm.identity.core.feature.campus.model.request.ModifyCampusRequest;
import fplhn.udpm.identity.core.feature.campus.repository.CampusExtendRepository;
import fplhn.udpm.identity.core.feature.campus.service.CampusService;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService {

    private final CampusExtendRepository campusExtendRepository;

    @Override
    public ResponseObject<?> getAllCampus(CampusPaginationRequest request) {
        try {
            Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
            if (request.getSearchValues() == null || request.getSearchValues().length == 0) {
                return new ResponseObject<>(
                        PageableObject.of(campusExtendRepository.search(pageable)),
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage()
                );
            }
            return new ResponseObject<>(
                    PageableObject.of(campusExtendRepository.search(pageable, request)),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseObject<?> createCampus(ModifyCampusRequest request) {
        if (request.getCampusName().length() > 255) {
            return ResponseObject.errorForward("Tên cơ sở vượt quá định dạng", HttpStatus.BAD_REQUEST);
        }
        request.setCampusName(request.getCampusName().replaceAll("\\s+", " "));
        boolean isExistsCampusCode = campusExtendRepository.existsByCode(request.getCampusCode().trim());

        if (isExistsCampusCode) {
            return ResponseObject.errorForward(
                    ResponseMessage.DUPLICATE.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } else {
            Campus campus = new Campus();
            campus.setName(request.getCampusName().trim());
            campus.setCode(request.getCampusCode().trim());
            campus.setEntityStatus(EntityStatus.NOT_DELETED);
            return new ResponseObject<>(
                    campusExtendRepository.save(campus),
                    HttpStatus.OK,
                    ResponseMessage.CREATED.getMessage()
            );
        }
    }

    @Override
    public ResponseObject<?> updateCampus(ModifyCampusRequest request, Long id) {
        if (request.getCampusName().length() > 255) {
            return ResponseObject.errorForward(ResponseMessage.STRING_TOO_LONG.getMessage(), HttpStatus.BAD_REQUEST);
        }
        request.setCampusName(request.getCampusName().replaceAll("\\s+", " "));

        Optional<Campus> campusOptional = campusExtendRepository.findById(id);

        if (campusOptional.isEmpty()) {
            return ResponseObject.errorForward(ResponseMessage.NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!campusOptional.get().getCode().trim().equalsIgnoreCase(request.getCampusCode().trim())) {
            if (campusExtendRepository.existsByCode(request.getCampusCode().trim())) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        campusOptional.get().setId(id);
        campusOptional.get().setName(request.getCampusName().trim());
        campusOptional.get().setCode(request.getCampusCode().trim());
        return new ResponseObject<>(
                campusExtendRepository.save(campusOptional.get()),
                HttpStatus.OK,
                ResponseMessage.UPDATED.getMessage()
        );
    }

    @Override
    public ResponseObject<?> updateCampusStatus(Long id) {
        Optional<Campus> coSoOptional = campusExtendRepository.findById(id);
        if (coSoOptional.isPresent()) {
            Campus campus = coSoOptional.get();
            if (campus.getEntityStatus().equals(EntityStatus.NOT_DELETED)) {
                campus.setEntityStatus(EntityStatus.DELETED);
            } else {
                campus.setEntityStatus(EntityStatus.NOT_DELETED);
            }
            return new ResponseObject<>(
                    campusExtendRepository.save(campus),
                    HttpStatus.OK,
                    ResponseMessage.UPDATED.getMessage()
            );
        } else {
            return ResponseObject.errorForward(ResponseMessage.NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseObject<?> getListCampus() {
        try {
            return new ResponseObject<>(
                    campusExtendRepository.getListAllCampus(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
