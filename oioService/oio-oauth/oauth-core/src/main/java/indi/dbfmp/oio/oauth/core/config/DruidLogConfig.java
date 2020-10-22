package indi.dbfmp.oio.oauth.core.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.sql.SQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: DruidLogConfig
 * @since 2020/10/16 8:35 下午
 */
@Slf4j
@Configuration
public class DruidLogConfig {

    @Bean
    public Slf4jLogFilter loggerInit() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setStatementExecutableSqlLogEnable(true);
        filter.setResultSetLogEnabled(false);
        filter.setConnectionLogEnabled(false);
        filter.setDataSourceLogEnabled(false);
        filter.setStatementCreateAfterLogEnabled(false);
        filter.setConnectionConnectBeforeLogEnabled(false);
        filter.setStatementPrepareAfterLogEnabled(false);
        filter.setStatementPrepareCallAfterLogEnabled(false);
        filter.setStatementExecuteAfterLogEnabled(false);
        filter.setStatementExecuteQueryAfterLogEnabled(false);
        filter.setStatementExecuteUpdateAfterLogEnabled(false);
        filter.setStatementExecuteBatchAfterLogEnabled(true);
        filter.setStatementCloseAfterLogEnabled(false);
        filter.setStatementParameterSetLogEnabled(false);
        filter.setStatementParameterClearLogEnable(false);
        filter.setStatementLogErrorEnabled(true);
        filter.setStatementSqlFormatOption(new SQLUtils.FormatOption(true,false));
        return filter;
    }

}
