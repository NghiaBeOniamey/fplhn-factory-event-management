package fplhn.udpm.identity.core.authentication.controller;

import fplhn.udpm.identity.core.authentication.model.request.SwitchModuleRequest;
import fplhn.udpm.identity.core.authentication.service.ModuleSwitchService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static fplhn.udpm.identity.infrastructure.constant.MessageConstant.ERROR_REDIRECT_MODULE;

/**
 * This is a REST controller class for handling module switch operations.
 * It uses the ModuleSwitchService to perform the actual switching logic.
 * The controller is mapped to the API_MODULE_SWITCH_PREFIX path.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_MODULE_SWITCH_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class ModuleSwitchController {

    // The service used for module switching operations
    private final ModuleSwitchService moduleSwitchService;

    // The default target URL for identity
    @Value("${app.default-target-url-identity}")
    private String DEFAULT_TARGET_URL_IDENTITY;

    /**
     * This method handles the GET request to switch modules.
     * It uses the ModuleSwitchService to perform the switch and then redirects the response to the new module.
     * If an IOException occurs during the switch, it redirects to an error page.
     *
     * @param response The HttpServletResponse object to send the redirect
     * @param request  The SwitchModuleRequest object containing the details of the switch request
     * @throws IOException If an error occurs during the redirect
     */
    @GetMapping("/switch")
    public void switchModule(HttpServletResponse response, SwitchModuleRequest request) throws IOException {
        try {
            response.sendRedirect(moduleSwitchService.switchModule(request));
        } catch (IOException e) {
            response.sendRedirect(DEFAULT_TARGET_URL_IDENTITY + "/error?error=" + ERROR_REDIRECT_MODULE);
        }
    }

}