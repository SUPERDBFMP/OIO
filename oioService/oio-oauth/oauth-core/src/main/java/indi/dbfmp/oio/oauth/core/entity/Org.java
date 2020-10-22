package indi.dbfmp.oio.oauth.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空")
    private String orgName;

    /**
     * 组织编码
     */
    @NotBlank(message = "组织编码不能为空")
    private String orgCode;

    /**
     * 上级组织id
     */
    private Integer superiorId;

    /**
     * 组织机构类型
     */
    @NotBlank(message = "组织机构类型不能为空")
    private String orgType;


}
