package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Org;
import indi.dbfmp.oio.oauth.core.mapper.OrgMapper;
import indi.dbfmp.oio.oauth.core.innerService.IOrgInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class OrgInnerServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgInnerService {

}
