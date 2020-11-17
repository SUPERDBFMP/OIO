package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分组-用户表
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GroupUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

}
