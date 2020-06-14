package indi.dbfmp.oio.core.service.impl;

import indi.dbfmp.oio.core.entity.App;
import indi.dbfmp.oio.core.mapper.AppMapper;
import indi.dbfmp.oio.core.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-06-14
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

}
