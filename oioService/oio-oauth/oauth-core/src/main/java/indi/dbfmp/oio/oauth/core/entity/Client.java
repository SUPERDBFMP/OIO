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
 * 接入客户端详情表
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
public class Client extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统名
     */
    @NotBlank(message = "接入应用名不能为空",groups = AddGroup.class)
    private String clientName;

    /**
     * 系统id
     */
    @NotBlank(message = "接入应用文编码不能为空",groups = AddGroup.class)
    private String clientId;

    /**
     * 系统密钥
     */
    @NotBlank(message = "接入应用密钥不能为空",groups = AddGroup.class)
    private String clientSecretKey;

    /**
     * 应用是否可调用,0:不可调用,1：可调用
     */
    private Integer accessFlag;

    /**
     * 组织机构id
     */
    @NotBlank(message = "组织机构不能为空",groups = AddGroup.class)
    private String orgId;

    /**
     * 组织机构名称
     */
    @NotBlank(message = "组织机构名称不能为空",groups = AddGroup.class)
    private String orgName;


    public static final String CLIENT_NAME = "client_name";

    public static final String CLIENT_ID = "client_id";

    public static final String CLIENT_SECRET_KEY = "client_secret_key";

    public static final String ACCESS_FLAG = "access_flag";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
