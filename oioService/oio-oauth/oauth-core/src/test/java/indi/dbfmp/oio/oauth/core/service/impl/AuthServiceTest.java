package indi.dbfmp.oio.oauth.core.service.impl;

import indi.dbfmp.oio.oauth.core.SpringTest;
import indi.dbfmp.oio.oauth.core.dto.RoleCheckDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: AuthServiceTest
 * @since 2020/10/19 12:03 上午
 */
@Slf4j
public class AuthServiceTest extends SpringTest {

    @Autowired
    private AuthService authService;

    @Test
    public void roleCheckTest() {
        boolean check = authService.roleCheck(RoleCheckDto.builder()
                .url("/oauth/test")
                .userId("7409173")
                .build());
        log.info("角色检查结果:{}",check);
    }

}
