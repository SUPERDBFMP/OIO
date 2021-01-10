package indi.dbfmp.oio.oauth.core.uitls;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Connection;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioIllegalSQLInnerInterceptor
 * @since 2021/1/10 下午6:17
 */
public class OioIllegalSQLInnerInterceptor extends IllegalSQLInnerInterceptor {

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpStatementHandler = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpStatementHandler.mappedStatement();
        if (ms.getId().contains("selectPage_mpCount")) {
            return;
        }
        super.beforePrepare(sh, connection, transactionTimeout);
    }
}
