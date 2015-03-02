package org.oyach.mybatis.dataSources.plugin;

import org.apache.commons.lang.ClassUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.oyach.mybatis.dataSources.util.MethodUntil;
import org.oyach.mybatis.dataSources.util.Shared;
import org.oyach.mybatis.dataSources.util.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
public class SplitTablePlugin implements Interceptor {

    private String tableName;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("=======SplitTablePlugin-intercept====");

        Object[] args = invocation.getArgs();

        MappedStatement ms = (MappedStatement) args[0];

        /** 读取接口参数 */
        String methodId = ms.getId();

        Method mapperMethod = MethodUntil.getMethodById(methodId);

        Parameter[] parameters = mapperMethod.getParameters();

        String sharedField = null;
        Class sharedFieldType = null;
        for (Parameter parameter : parameters){
            Shared shared = parameter.getAnnotation(Shared.class);
            if (shared != null){
                sharedFieldType = parameter.getType();
                Param param = parameter.getAnnotation(Param.class);
                if (param == null){
                    sharedField = parameter.getName();
                } else {
                    sharedField = param.value();
                }

            }
        }
        System.out.println(sharedField);

        /** 获取对应id数据 */
        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) args[1];
        Object id = paramMap.get(sharedField);

        SqlSource sqlSource = ms.getSqlSource();

        BoundSql boundSql = sqlSource.getBoundSql(args[1]);
        String sql = boundSql.getSql();
        // 解析sql 设置表名
//        StringBuilder out = new StringBuilder();
//        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
//        MySqlStatementParser parser = new MySqlStatementParser(sql);
//
//        List<SQLStatement> statementList = parser.parseStatementList();
//        SQLStatement sqlStatement = statementList.get(0);
//
//        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
//        SQLSelect sqlSelect = sqlSelectStatement.getSelect();
//        MySqlSelectQueryBlock sqlSelectQuery = (MySqlSelectQueryBlock) sqlSelect.getQuery();
//        SQLJoinTableSource sqlTableSource = (SQLJoinTableSource) sqlSelectQuery.getFrom();
//
//        String alias = sqlTableSource.getAlias();
//        SQLExprTableSource sqlTableSourceLeft = (SQLExprTableSource) sqlTableSource.getLeft();
//        SQLIdentifierExpr table = (SQLIdentifierExpr) sqlTableSourceLeft.getExpr();
//        String name = table.getName();
//
//        table.setName(tableName);
//        sqlStatement.accept(visitor);
//        String newSql = out.toString();

        String newSql = "";

        Class<?> sqlSourceClass = sqlSource.getClass();
        Field fieldSqlSource = sqlSourceClass.getDeclaredField("sqlSource");
        fieldSqlSource.setAccessible(true);
        StaticSqlSource staticSqlSource = (StaticSqlSource) fieldSqlSource.get(sqlSource);
        Field sqlField = staticSqlSource.getClass().getDeclaredField("sql");
        sqlField.setAccessible(true);
        sqlField.set(staticSqlSource, newSql);
//        SqlSourceBuilder builder
//        SqlSource newSqlSource = new StaticSqlSource(ms.getConfiguration(), );

//        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(ms.getConfiguration(),
//                ms.getId(), sqlSource, ms.getSqlCommandType());
//        statementBuilder.resource(ms.getResource());
//        statementBuilder.fetchSize(ms.getFetchSize());
//        statementBuilder.statementType(ms.getStatementType());
//        statementBuilder.keyGenerator(ms.getKeyGenerator());
//        for (String property : ms.getKeyProperties()){
//            statementBuilder.keyProperty(property);
//        }
//        for (String column : ms.getKeyColumns()){
//            statementBuilder.keyProperty(column);
//        }
//        statementBuilder.databaseId(ms.getDatabaseId());
//        statementBuilder.lang(ms.getLang());
//        statementBuilder.resultOrdered(ms.isResultOrdered());
//        for (String resultSet : ms.getResulSets()){
//            statementBuilder.resulSets(resultSet);
//        }
//        statementBuilder.timeout(ms.getTimeout());
//        statementBuilder.parameterMap(ms.getParameterMap());
//        statementBuilder.resultMaps(ms.getResultMaps());
//        statementBuilder.resultSetType(ms.getResultSetType());
//        statementBuilder.flushCacheRequired(ms.isFlushCacheRequired());
//        statementBuilder.useCache(ms.isUseCache());
//        statementBuilder.cache(ms.getCache());
//        MappedStatement statement = statementBuilder.build();

//        Executor executor = (Executor) invocation.getTarget();
//        if (executor instanceof SimpleExecutor){
//            SimpleExecutor simpleExecutor = (SimpleExecutor) executor;
//
//        }
        Method method = invocation.getMethod();

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("=======SplitTablePlugin-plugin====");
        Object o = Plugin.wrap(target, this);
        return o;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("=======SplitTablePlugin-setProperties====");
        tableName = properties.getProperty("tableName");
    }
}
