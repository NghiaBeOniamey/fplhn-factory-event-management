package com.portalevent.core.system.service;

import com.portalevent.entity.SystemOption;

/**
 * @author SonPT
 */
public interface SystemService {

    SystemOption getSystemOption();

    SystemOption saveSystemOption(SystemOption systemOption);

}
