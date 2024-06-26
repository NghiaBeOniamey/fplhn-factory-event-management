package fplhn.udpm.identity.core.feature.role.controller;

import fplhn.udpm.identity.core.feature.role.model.request.ModifyRoleRequest;
import fplhn.udpm.identity.core.feature.role.model.request.RolePaginationRequest;
import fplhn.udpm.identity.core.feature.role.service.RoleService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_ROLE_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public Object findAllRole(RolePaginationRequest roleDTO) {
        return Helper.createResponseEntity(roleService.findAllEntity(roleDTO));
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable("id") Long id) {
        return Helper.createResponseEntity(roleService.getRoleById(id));
    }

    @PostMapping
    public Object create(@RequestBody @Valid ModifyRoleRequest roleDTO) {
        return Helper.createResponseEntity(roleService.create(roleDTO));
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable("id") Long id, @RequestBody @Valid ModifyRoleRequest roleDTO) {
        return Helper.createResponseEntity(roleService.update(roleDTO, id));
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") Long id) {
        return Helper.createResponseEntity(roleService.delete(id));
    }

    @GetMapping("/all")
    public Object getAllRole() {
        return Helper.createResponseEntity(roleService.getAllRole());
    }

}
