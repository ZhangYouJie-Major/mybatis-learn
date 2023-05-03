package plugins;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Intercepts(
        {@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class,method = "update",args = {Statement.class})}
)
public class StatementHandlerInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler target = (RoutingStatementHandler) invocation.getTarget();
        Field delegate = target.getClass().getDeclaredField("delegate");
        delegate.setAccessible(true);
        PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler)delegate.get(target);

        System.out.println(preparedStatementHandler);
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long sqlCost = endTime - startTime;
            BoundSql boundSql = target.getBoundSql();

            String sql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();

//                       // 格式化Sql语句，去除换行符，替换参数
//                       sql = formatSql(sql, parameterObject, parameterMappingList);

            System.out.println("SQL：[" + sql + parameterObject+"]执行耗时[" + sqlCost + "ms]");
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
