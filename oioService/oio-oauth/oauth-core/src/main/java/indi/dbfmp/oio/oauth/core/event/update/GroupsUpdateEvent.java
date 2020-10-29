package indi.dbfmp.oio.oauth.core.event.update;

import indi.dbfmp.oio.oauth.core.event.BaseEvent;
import indi.dbfmp.validator.core.group.AddGroup;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GroupsUpdateEvent
 * @since 2020/10/24 9:05 下午
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupsUpdateEvent extends BaseEvent{

    private static final long serialVersionUID = 4908437218098180541L;

    private String id;
    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 组织机构id
     */
    private String orgId;

}
