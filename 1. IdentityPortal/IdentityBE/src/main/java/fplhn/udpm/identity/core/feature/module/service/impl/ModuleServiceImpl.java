package fplhn.udpm.identity.core.feature.module.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.module.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModulePaginationRequest;
import fplhn.udpm.identity.core.feature.module.model.request.UpdateModuleRequest;
import fplhn.udpm.identity.core.feature.module.model.response.CreateModuleResponse;
import fplhn.udpm.identity.core.feature.module.model.response.UpdateModuleResponse;
import fplhn.udpm.identity.core.feature.module.repository.ClientEntryRepository;
import fplhn.udpm.identity.core.feature.module.repository.ModuleExtendRepository;
import fplhn.udpm.identity.core.feature.module.service.ModuleService;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.infrastructure.config.listener.ModuleInsertedEvent;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.GenerateClientUtils;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    @Value("${client.secret.length}")
    private int CLIENT_SECRET_LENGTH;

    private final ModuleExtendRepository moduleRepository;

    private final ClientEntryRepository clientEntryRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ResponseObject<?> getAllModule(ModulePaginationRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "moduleId");
            if (request.getListId() == null || request.getListId().length == 0) {
                return new ResponseObject<>(
                        PageableObject.of(moduleRepository.searchAllModule(pageable)),
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage()
                );
            }
            return new ResponseObject<>(
                    PageableObject.of(moduleRepository.searchModuleByListId(pageable, request)),
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
    public ResponseObject<?> createModule(CreateModuleRequest request) {
        try {
            String moduleName = request.getModuleName().replaceAll("\\s+", " ").trim();
            String moduleCode = request.getModuleCode().trim();
            String moduleUrl = request.getModuleUrl().trim();

            if (moduleRepository.existsByMa(moduleCode)) {
                return new ResponseObject<>(
                        null,
                        HttpStatus.BAD_REQUEST,
                        ResponseMessage.CODE_EXIST.getMessage()
                );
            }
            if (moduleRepository.existsByUrl(moduleUrl)) {
                return new ResponseObject<>(
                        null,
                        HttpStatus.BAD_REQUEST,
                        ResponseMessage.DUPLICATE.getMessage()
                );
            }
            if (moduleRepository.existsByTen(moduleName)) {
                return new ResponseObject<>(
                        null,
                        HttpStatus.BAD_REQUEST,
                        ResponseMessage.DUPLICATE.getMessage()
                );
            }

            Module module = new Module();
            module.setEntityStatus(EntityStatus.NOT_DELETED);
            module.setMa(moduleCode);
            module.setTen(moduleName);
            module.setUrl(moduleUrl);
            module.setRedirectRoute(request.getRedirectRoute());

            Module moduleSave = moduleRepository.save(module);

            String clientId = GenerateClientUtils.generateRandomClientID();
            String clientSecret = GenerateClientUtils.generateRandomClientSecret(CLIENT_SECRET_LENGTH);

            Client client = new Client();
            client.setClientId(clientId);
            client.setClientSecret(clientSecret);
            client.setSecretKey(GenerateClientUtils.generateJwtSecretKey(clientId, clientSecret));
            client.setModule(moduleSave);
            client.setEntityStatus(EntityStatus.NOT_DELETED);

            Client clientSave = clientEntryRepository.save(client);

            eventPublisher.publishEvent(new ModuleInsertedEvent(this));

            return new ResponseObject<>(
                    new CreateModuleResponse(
                            clientSave.getClientId(),
                            clientSave.getClientSecret()
                    ),
                    HttpStatus.CREATED,
                    ResponseMessage.CREATED.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> updateModule(UpdateModuleRequest request, Long id) {
        try {
            String trimmedModuleName = request.getModuleName().replaceAll("\\s+", " ").trim();
            String trimmedModuleCode = request.getModuleCode().trim();
            String trimmedModuleUrl = request.getModuleUrl().trim();

            Optional<Module> moduleOptional = moduleRepository.findById(id);
            if (moduleOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            Module module = moduleOptional.get();
            if (moduleRepository.existsByMa(trimmedModuleCode) && !module.getMa().trim().equalsIgnoreCase(trimmedModuleCode)) {
                return ResponseObject.errorForward(
                        ResponseMessage.CODE_EXIST.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (moduleRepository.existsByTen(trimmedModuleName) && !module.getTen().trim().equalsIgnoreCase(trimmedModuleName)) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (moduleRepository.existsByUrlAndId(trimmedModuleUrl, id) && !module.getUrl().trim().equalsIgnoreCase(trimmedModuleUrl)) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            module.setId(id);
            module.setTen(trimmedModuleName);
            module.setMa(trimmedModuleCode);
            module.setUrl(trimmedModuleUrl);
            module.setRedirectRoute(request.getRedirectRoute());

            moduleRepository.save(module);
            eventPublisher.publishEvent(new ModuleInsertedEvent(this));

            return new ResponseObject<>(
                    new UpdateModuleResponse(
                            module.getMa(),
                            module.getTen(), module.getUrl()
                    ),
                    HttpStatus.OK,
                    ResponseMessage.UPDATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> updateStatusModule(Long id) {
        Optional<Module> moduleOptional = moduleRepository.findById(id);

        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();
            if (module.getEntityStatus().equals(EntityStatus.NOT_DELETED)) {
                module.setEntityStatus(EntityStatus.DELETED);
            } else {
                module.setEntityStatus(EntityStatus.NOT_DELETED);
            }
            return new ResponseObject<>(
                    moduleRepository.save(module),
                    HttpStatus.OK,
                    ResponseMessage.UPDATED.getMessage());
        } else {
            return ResponseObject.errorForward(
                    ResponseMessage.NOT_FOUND.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseObject<?> getClientByModuleId(Long id) {
        try {
            return new ResponseObject<>(
                    clientEntryRepository.findByModuleId(id),
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
    public ResponseObject<?> getListModule() {
        try {
            return new ResponseObject<>(
                    moduleRepository.getAllModule(),
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
}
