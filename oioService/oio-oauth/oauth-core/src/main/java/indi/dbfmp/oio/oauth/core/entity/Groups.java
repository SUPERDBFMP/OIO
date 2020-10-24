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
 * 分组详情表
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
public class Groups extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组名称
     */
    @NotBlank(message = "分组名不能为空",groups = AddGroup.class)
    private String groupName;

    /**
     * 组编码
     */
    @NotBlank(message = "分组编码不能为空",groups = AddGroup.class)
    private String groupCode;

    /**
     * 组织机构id
     */
    @NotBlank(message = "组织机构id不能为空",groups = AddGroup.class)
    private String orgId;


    public static final String GROUP_NAME = "group_name";

    public static final String GROUP_CODE = "group_code";

    public static final String ORG_ID = "org_id";

}
