package indi.dbfmp.oio.core.entity;

import indi.dbfmp.oio.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dbfmp
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class App extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统名
     */
    private String appName;

    /**
     * 系统id
     */
    private String appId;

    /**
     * 系统密钥
     */
    private String appSecretKey;

    /**
     * 应用是否可调用,0:不可调用,1：可调用
     */
    private String accessFlag;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 分组名
     */
    private String groupName;


}
