package inid.dbfmp.oauth.api.service;

import inid.dbfmp.oauth.api.dto.PermissionCheck;
import inid.dbfmp.oauth.api.dto.RoleCheck;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: DubboAuthService
 * @since 2020/10/22 10:19 下午
 */
public interface DubboAuthService {

    /**
     * 用户角色检查
     * @param roleCheck 检查对象
     * @return 是否拥有权限
     */
    boolean roleCheck(RoleCheck roleCheck);

    /**
     * 用户权限检查
     * @param permissionCheck 检查对象
     * @return 是否拥有权限
     */
    boolean permissionCheck(PermissionCheck permissionCheck);

}
