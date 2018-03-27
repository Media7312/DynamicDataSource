package com.example.demo.conf;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @Author: 侯玢
 * @Description: 读写分离拦截器
 * @Date: Created in 23:33 2018/3/22
 */
@Intercepts({
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class DyamicPlugin implements Interceptor {
    private final static String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synActive = TransactionSynchronizationManager.isSynchronizationActive();
        if (!synActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];
            DyamicDataSourceType dyamicDataSourceType = null;
            if (ms.getStatementType().equals(SqlCommandType.SELECT)) {
                // dyamicDataSourceType = DyamicDataSourceType.READ;
                BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", "");
                if (sql.matches(REGEX)) {
                    dyamicDataSourceType = DyamicDataSourceType.WRITE;
                } else {
                    dyamicDataSourceType = DyamicDataSourceType.READ;
                }
            } else {
                dyamicDataSourceType = DyamicDataSourceType.WRITE;
            }
            DyamicDataSourceHolder.putDataSource(dyamicDataSourceType);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
