package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.validator.core.group.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 权限详情表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-24
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限英文代码
     */
    @NotBlank(message = "权限英文代码不能为空",groups = AddGroup.class)
    private String permissionCode;

    /**
     * 授权名称
     */
    @NotBlank(message = "授权名称不能为空",groups = AddGroup.class)
    private String permissionName;

    /**
     * 组织机构id
     */
    @NotBlank(message = "组织机构id不能为空",groups = AddGroup.class)
    private String orgId;

    /**
     * 组织机构名称
     */
    @NotBlank(message = "组织机构名称不能为空",groups = AddGroup.class)
    private String orgName;


    public static final String PERMISSION_CODE = "permission_code";

    public static final String PERMISSION_NAME = "permission_name";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
