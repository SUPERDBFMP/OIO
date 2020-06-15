package indi.dbfmp.oio.core.innerService.impl;

import indi.dbfmp.oio.core.mapper.GroupsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.dbfmp.oio.core.innerService.IGroupsService;
import indi.dbfmp.oio.inner.entity.Groups;
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
public class GroupsServiceImpl extends ServiceImpl<GroupsMapper, Groups> implements IGroupsService {

}
