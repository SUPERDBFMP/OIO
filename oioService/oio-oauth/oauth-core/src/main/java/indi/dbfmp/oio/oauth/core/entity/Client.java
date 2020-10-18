package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
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


}
