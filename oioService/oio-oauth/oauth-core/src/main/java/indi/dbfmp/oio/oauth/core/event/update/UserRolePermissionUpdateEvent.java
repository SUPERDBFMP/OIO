package indi.dbfmp.oio.oauth.core.event.update;

import indi.dbfmp.oio.oauth.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 *  用户角色权限更新事件
 * </p>
 *
 * @author dbfmp
 * @name: UserRolePermissionUpdateEvent
 * @since 2020/11/2 10:18 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePermissionUpdateEvent extends BaseEvent {

    private static final long serialVersionUID = -7742907530535092061L;

    String userId;

    List<String> groupIdList;

}
