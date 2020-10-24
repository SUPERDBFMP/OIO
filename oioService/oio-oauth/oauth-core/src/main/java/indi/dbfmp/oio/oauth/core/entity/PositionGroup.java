package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 职位-分组表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-20
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PositionGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private String positionId;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 组织名
     */
    private String positionName;

    /**
     * 分组名
     */
    private String groupName;


}
