package fplhn.udpm.identity.core.authentication.service;

import fplhn.udpm.identity.core.authentication.model.request.SwitchModuleRequest;
import org.apache.coyote.BadRequestException;


public interface ModuleSwitchService {

    String switchModule(SwitchModuleRequest request) throws BadRequestException;

}
