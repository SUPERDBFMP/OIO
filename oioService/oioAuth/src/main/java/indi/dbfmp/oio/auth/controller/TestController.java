package indi.dbfmp.oio.auth.controller;

import indi.dbfmp.oio.inner.entity.Roles;
import indi.dbfmp.oio.inner.service.RolesService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: TestController
 * @since 2020/6/15 8:55 下午
 */
@RestController
public class TestController {

    @Reference
    private RolesService rolesService;

    @RequestMapping("getRole")
    public Roles getRole() {
        return rolesService.getRoles();
    }

    /*@RequestMapping("getRole")
    public String getRole() {
        return "roles";
    }*/

}
