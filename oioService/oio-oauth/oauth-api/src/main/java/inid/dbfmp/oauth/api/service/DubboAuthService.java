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

    /**
     * 检查用户是否开启两步认证
     * @param userId
     * @return 是否开启两步认证
     */
    boolean checkOpenTowStepAuth(String userId);

    /**
     * 检查用户两步认证码是否正确
     * @param userId 用户ID
     * @param code 验证码
     */
    void codeValid(String userId,int code);

}
