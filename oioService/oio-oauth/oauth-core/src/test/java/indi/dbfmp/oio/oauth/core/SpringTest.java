package indi.dbfmp.oio.oauth.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: SpringTest
 * @since 2020/10/19 12:04 上午
 */
@ActiveProfiles("dev")
@SpringBootTest(classes = OioOauthCoreApplication.class,properties = "dev")
@RunWith(SpringRunner.class)
public class SpringTest {
}
