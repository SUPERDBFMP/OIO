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
 * @name: UrlRoleGroupDto
 * @since 2020/11/11 10:11 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UrlRoleGroupDto {

    private String roleId;

    private String groupId;

}
