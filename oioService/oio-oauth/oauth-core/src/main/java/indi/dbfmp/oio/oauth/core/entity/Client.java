package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接入客户端详情表
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Client extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统名
     */
    private String clientName;

    /**
     * 系统id
     */
    private String clientId;

    /**
     * 系统密钥
     */
    private String clientSecretKey;

    /**
     * 应用是否可调用,0:不可调用,1：可调用
     */
    private String accessFlag;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;


    public static final String CLIENT_NAME = "client_name";

    public static final String CLIENT_ID = "client_id";

    public static final String CLIENT_SECRET_KEY = "client_secret_key";

    public static final String ACCESS_FLAG = "access_flag";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
