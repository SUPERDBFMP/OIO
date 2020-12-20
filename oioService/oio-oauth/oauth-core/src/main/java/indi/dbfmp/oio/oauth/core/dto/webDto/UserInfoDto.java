package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import java.util.List;

/**
 * <p>
 *  用户详情
 * </p>
 *
 * @author dbfmp
 * @name: UserInfoDto
 * @since 2020/12/20 下午9:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserInfoDto {

    private String userId;

    private String name;

    private List<UserRoleDto> userRoleList;

    private List<UserPermissionDto> userPermissionList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class UserRoleDto{

        private String id;

        private String roleCode;

        private String roleName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class UserPermissionDto{

        private String id;

        private String permissionCode;

        private String permissionName;
    }

}
