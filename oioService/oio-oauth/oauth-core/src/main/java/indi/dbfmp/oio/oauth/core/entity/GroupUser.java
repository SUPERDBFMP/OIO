package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
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
 * 分组-用户表
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-17
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GroupUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    @NotBlank(message = "分组ID不能为空",groups = AddGroup.class)
    private String groupId;

    /**
     * 分组名
     */
    @NotBlank(message = "分组名不能为空",groups = AddGroup.class)
    private String groupName;

    /**
     * 用户id
     */
    @NotBlank(message = "用户ID不能为空",groups = AddGroup.class)
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名名不能为空",groups = AddGroup.class)
    private String userName;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空",groups = AddGroup.class)
    private String orgName;

    /**
     * 组织id
     */
    @NotBlank(message = "组织ID不能为空",groups = AddGroup.class)
    private String orgId;


    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String ORG_NAME = "org_name";

    public static final String ORG_ID = "org_id";

}
