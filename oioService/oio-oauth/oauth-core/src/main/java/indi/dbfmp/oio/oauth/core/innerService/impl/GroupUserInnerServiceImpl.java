package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.GroupUser;
import indi.dbfmp.oio.oauth.core.mapper.GroupUserMapper;
import indi.dbfmp.oio.oauth.core.innerService.IGroupUserInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分组-用户表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-17
 */
@Service
public class GroupUserInnerServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements IGroupUserInnerService {

}
