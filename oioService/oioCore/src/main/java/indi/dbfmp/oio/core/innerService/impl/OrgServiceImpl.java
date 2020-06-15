package indi.dbfmp.oio.core.innerService.impl;

import indi.dbfmp.oio.core.mapper.OrgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.dbfmp.oio.core.innerService.IOrgService;
import indi.dbfmp.oio.inner.entity.Org;
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
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

}
