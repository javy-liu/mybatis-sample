package org.oyach.mybatis.dataSources.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.oyach.mybatis.dataSources.util.MultipleDataSource;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/28
 * @since 0.0.1
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})
})
public class SpliteDataSourcePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("=======SpliteDataSourcePlugin-intercept====");
        Object[] args = invocation.getArgs();

        MappedStatement ms = (MappedStatement) args[0];

        String databaseId = ms.getDatabaseId();

        SqlSource sqlSource = ms.getSqlSource();

        Configuration configuration = ms.getConfiguration();

        List<ResultMap> resultMaps =  ms.getResultMaps();

        ResultMap resultMap = resultMaps.get(0);

        String id = resultMap.getId();


        Environment environment = configuration.getEnvironment();

        MultipleDataSource dataSource = (MultipleDataSource) environment.getDataSource();

        // 决定数数据库
        Map<String, String> packageDataSource = dataSource.getPackageDataSource();

        MultipleDataSource.setDataSourceKey("dbWmRead");


        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("=======SpliteDataSourcePlugin-plugin====");
        Object o = Plugin.wrap(target, this);
        return o;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("=======SpliteDataSourcePlugin-setProperties====");
    }
}
