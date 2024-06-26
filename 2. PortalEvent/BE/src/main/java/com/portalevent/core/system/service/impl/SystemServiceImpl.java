package com.portalevent.core.system.service.impl;

import com.portalevent.core.system.service.SystemService;
import com.portalevent.entity.SystemOption;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.repository.SystemOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author SonPT
 */

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    @Qualifier(SystemOptionRepository.NAME)
    private SystemOptionRepository systemOptionRepository;

    @Override
    public SystemOption getSystemOption() {
        Optional<SystemOption> systemOption = systemOptionRepository.findById(1L);
        if (systemOption.isPresent()) {
            return systemOption.get();
        } else {
            throw new RestApiException(Message.SYSTEM_OPTION_NOT_FOUND);
        }
    }

    @Override
    public SystemOption saveSystemOption(SystemOption systemOption) {
        if (systemOption.getId() != 1L) {
            throw new RestApiException(Message.UNDEFINED_SYSTEM_OPTION);
        }
        if (systemOption.getMandatoryApprovalDays() < 1 || systemOption.getMandatoryApprovalDays() >= 100) {
            throw new RestApiException(Message.INVALID_MANDATORY_APPROVAL_DAYS);
        }
        return systemOptionRepository.save(systemOption);
    }

}
