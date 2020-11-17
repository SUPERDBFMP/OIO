package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  数据库ID列表对象
 * </p>
 *
 * @author dbfmp
 * @name: IdListDto
 * @since 2020/11/17 下午10:20
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IdListDto implements Serializable {

    private static final long serialVersionUID = 5227776190534980794L;

    @NotEmpty(message = "ID列表不能为空")
    List<String> idList;

}
