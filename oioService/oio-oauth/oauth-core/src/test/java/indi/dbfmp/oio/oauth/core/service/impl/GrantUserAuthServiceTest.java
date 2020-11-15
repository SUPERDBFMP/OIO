package indi.dbfmp.oio.oauth.core.service.impl;

import indi.dbfmp.oio.oauth.core.SpringTest;
import indi.dbfmp.oio.oauth.core.dto.GrantAuthDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GrantUserAuthServiceTest
 * @since 2020/10/21 11:08 下午
 */
public class GrantUserAuthServiceTest extends SpringTest {


    @Autowired
    private GrantAuthService grantAuthService;

    @Test
    public void grantAuthTest() {
        GrantAuthDto grantAuthDto = GrantAuthDto.builder()
                .orgId("7692375")
                .groupId("859482")
                .userId("1")
                .positionIdList(Arrays.asList("823721","902841"))
                .build();
        //grantAuthService.grantAuthToUser(grantAuthDto);
    }

}
