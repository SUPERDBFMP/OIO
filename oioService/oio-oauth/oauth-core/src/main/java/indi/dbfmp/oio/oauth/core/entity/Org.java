package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.validator.core.group.AddGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空",groups = AddGroup.class)
    private String orgName;

    /**
     * 组织编码
     */
    @NotBlank(message = "组织编码不能为空",groups = AddGroup.class)
    private String orgCode;

    /**
     * 上级组织id
     */
    private Integer superiorId;

    /**
     * 组织机构类型
     */
    @NotBlank(message = "组织机构类型不能为空",groups = AddGroup.class)
    private String orgType;


    public static final String ORG_NAME = "org_name";

    public static final String ORG_CODE = "org_code";

    public static final String SUPERIOR_ID = "superior_id";

    public static final String ORG_TYPE = "org_type";

}
