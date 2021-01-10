package indi.dbfmp.oio.oauth.core.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import indi.dbfmp.oio.oauth.core.entity.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-12
 */
@InterceptorIgnore(illegalSql = "true")
public interface ClientMapper extends BaseMapper<Client> {

}
