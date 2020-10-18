package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.mapper.GroupsMapper;
import indi.dbfmp.oio.oauth.core.innerService.IGroupsInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分组详情表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Service
public class GroupsInnerServiceImpl extends ServiceImpl<GroupsMapper, Groups> implements IGroupsInnerService {

}
