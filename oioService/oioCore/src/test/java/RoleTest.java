import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.core.OioCoreMain;
import indi.dbfmp.oio.core.innerService.impl.IRolesServiceImpl;
import indi.dbfmp.oio.inner.entity.Roles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @NAME: RoleTest
 * @AUTHOR dbfmp
 * @DATE: 2020/6/14 9:09 下午
 **/
@SpringBootTest(value = "dev",classes = OioCoreMain.class)
@RunWith(SpringRunner.class)
public class RoleTest {

    @Autowired
    private IRolesServiceImpl rolesService;

    @Test
    public void testRoles() {
        System.out.println(rolesService.getOne(new LambdaQueryWrapper<Roles>().eq(Roles::getId,1)));
    }

}
