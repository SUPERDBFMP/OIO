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
 * 角色详情表
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
public class Roles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色英文编码
     */
    @NotBlank(message = "角色英文编码不能为空",groups = AddGroup.class)
    private String roleCode;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空",groups = AddGroup.class)
    private String roleName;

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

    /**
     * 上级id
     */
    private String superiorId;


    public static final String ROLE_CODE = "role_code";

    public static final String ROLE_NAME = "role_name";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

    public static final String SUPERIOR_ID = "superior_id";

}
