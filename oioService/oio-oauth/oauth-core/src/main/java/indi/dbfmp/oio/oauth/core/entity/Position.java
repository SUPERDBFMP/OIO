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
 * 职位表
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
public class Position extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空",groups = AddGroup.class)
    private String positionName;

    /**
     * 职位编码
     */
    @NotBlank(message = "职位编码不能为空",groups = AddGroup.class)
    private String positionCode;

    /**
     * 上级id
     */
    private Integer superiorId;

    /**
     * 组织id
     */
    @NotBlank(message = "组织id不能为空",groups = AddGroup.class)
    private String orgId;

    /**
     * 组织名
     */
    @NotBlank(message = "组织名不能为空",groups = AddGroup.class)
    private String orgName;


    public static final String POSITION_NAME = "position_name";

    public static final String POSITION_CODE = "position_code";

    public static final String SUPERIOR_ID = "superior_id";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
