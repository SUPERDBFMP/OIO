package indi.dbfmp.oio.oauth.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: UserPositionGroupDto
 * @since 2020/11/10 9:26 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPositionGroupDto {

    private String positionId;

    private String groupId;

}
