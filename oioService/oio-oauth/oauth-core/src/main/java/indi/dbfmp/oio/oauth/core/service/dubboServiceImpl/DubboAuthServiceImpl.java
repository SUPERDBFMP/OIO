package indi.dbfmp.oio.oauth.core.service.dubboServiceImpl;

import indi.dbfmp.oio.oauth.core.dto.PermissionCheckDto;
import indi.dbfmp.oio.oauth.core.dto.RoleCheckDto;
import indi.dbfmp.oio.oauth.core.service.impl.AuthService;
import inid.dbfmp.oauth.api.dto.PermissionCheck;
import inid.dbfmp.oauth.api.dto.RoleCheck;
import inid.dbfmp.oauth.api.service.DubboAuthService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: DubboAuthServiceImpl
 * @since 2020/10/22 10:21 下午
 */
@Component
@Service(group = "${spring.profiles.active}")
public class DubboAuthServiceImpl implements DubboAuthService {

    @Autowired
    private AuthService authService;

    /**
     * 用户角色检查
     *
     * @param roleCheck 检查对象
     * @return 是否拥有权限
     */
    @Override
    public boolean roleCheck(RoleCheck roleCheck) {
        RoleCheckDto roleCheckDto = RoleCheckDto.builder().userId(roleCheck.getUserId()).url(roleCheck.getUrl()).orgId(roleCheck.getOrgId()).build();
        return authService.roleCheck(roleCheckDto);
    }

    /**
     * 用户权限检查
     *
     * @param permissionCheck 检查对象
     * @return 是否拥有权限
     */
    @Override
    public boolean permissionCheck(PermissionCheck permissionCheck) {
        PermissionCheckDto permissionCheckDto = PermissionCheckDto.builder().userId(permissionCheck.getUserId()).url(permissionCheck.getUrl()).orgId(permissionCheck.getOrgId()).build();
        return authService.permissionCheck(permissionCheckDto);
    }
}
