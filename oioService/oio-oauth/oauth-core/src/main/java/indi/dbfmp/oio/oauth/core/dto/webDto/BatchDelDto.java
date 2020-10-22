package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  批量删除
 * </p>
 *
 * @author dbfmp
 * @name: BatchDelDto
 * @since 2020/10/22 11:10 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class BatchDelDto implements Serializable {

    private static final long serialVersionUID = -4036513314903709025L;

    @NotEmpty(message = "删除列表不能为空")
    List<String> delIds;
}
