package com.portalevent.core.common;//package com.portalevent.core.common;
//
//import com.portalevent.util.CallApiIdentity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author thangncph26123
// */
//@RestController
//@RequestMapping("/roles")
//
//public class RoleController {
//
//    @Autowired
//    private CallApiIdentity callApiIdentity;
//
//    @GetMapping
//    public ResponseObject getRolesUser(@RequestParam("idUser") String idUser) {
//        return new ResponseObject(callApiIdentity.handleCallApiGetRoleUserByIdUserAndModuleCode(idUser));
//    }
//
//}
