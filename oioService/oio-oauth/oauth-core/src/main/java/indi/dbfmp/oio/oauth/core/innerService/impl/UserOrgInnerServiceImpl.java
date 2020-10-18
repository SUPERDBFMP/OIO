package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.UserOrg;
import indi.dbfmp.oio.oauth.core.mapper.UserOrgMapper;
import indi.dbfmp.oio.oauth.core.innerService.IUserOrgInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-组织表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class UserOrgInnerServiceImpl extends ServiceImpl<UserOrgMapper, UserOrg> implements IUserOrgInnerService {

}
