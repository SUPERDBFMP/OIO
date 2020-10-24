package indi.dbfmp.oio.oauth.core.event.update;

import indi.dbfmp.validator.core.group.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GroupsUpdateEvent
 * @since 2020/10/24 9:05 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupsUpdateEvent {


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
