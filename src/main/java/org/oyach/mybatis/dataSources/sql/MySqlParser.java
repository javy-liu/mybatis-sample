package org.oyach.mybatis.dataSources.sql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlSelectGroupByExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * mysql的sql解析
 *
 * @author liuzhenyuan
 * @version Last modified 15/3/5
 * @since 0.0.1
 */
public class MySqlParser implements SqlParser {


    private static final Cache<String, SQLStatement> cache;

    static {
        /** cache设置 */
        cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public String replaceTableName(final String sql, String tableName, String replaceTableName) throws SqlParserException {
        final String cacheKey = StringUtils.trimAllWhitespace(sql);

        SQLStatement sqlStatement;
        try {
            sqlStatement = cache.get(cacheKey, new Callable<SQLStatement>() {
                @Override
                public SQLStatement call() throws Exception {
                    MySqlStatementParser parser = new MySqlStatementParser(sql);
                    return parser.parseStatement();
                }
            });
        } catch (ExecutionException ex) {
            throw new SqlParserException("sql parser cache exception " + ex);
        }

        if (sqlStatement instanceof MySqlUpdateStatement) {
            /** 处理update sql语句 */
            buildUpdate(sqlStatement, tableName, replaceTableName);
        } else if (sqlStatement instanceof MySqlDeleteStatement) {
            /** 处理delete sql语句 */
            buildDelete(sqlStatement, tableName, replaceTableName);
        } else if (sqlStatement instanceof MySqlInsertStatement) {
            /** 处理insert sql语句 */
            buildInsert(sqlStatement, tableName, replaceTableName);
        } else if (sqlStatement instanceof SQLSelectStatement) {
            /** 处理select sql语句 */
            buildSelect(((SQLSelectStatement) sqlStatement).getSelect(), tableName, replaceTableName);

        } else {
            throw new SqlParserException("unknown SQLStatement type. SQLStatement=" + sqlStatement.getClass());
        }

        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);

        sqlStatement.accept(visitor);

        return out.toString();
    }




    /**
     * 查询sql处理
     *
     * @param sqlSelect
     * @param tableName
     * @param replaceTableName
     * @throws SqlParserException
     */
    private void buildSelect(SQLSelect sqlSelect, String tableName, String replaceTableName) throws SqlParserException {
        MySqlSelectQueryBlock query = (MySqlSelectQueryBlock) sqlSelect.getQuery();

        /** from */
        replaceTableName(query.getFrom(), tableName, replaceTableName);
        /** where */
        replaceTableName(query.getWhere(), tableName, replaceTableName);
        /** select */
        List<SQLSelectItem> selectItems = query.getSelectList();
        for (SQLSelectItem selectItem : selectItems) {
            replaceTableName(selectItem, tableName, replaceTableName);
        }
        /** order by */
        SQLOrderBy orderBy = query.getOrderBy();
        if (orderBy != null) {
            List<SQLSelectOrderByItem> orderByItems = orderBy.getItems();
            if (orderByItems != null) {
                for (SQLSelectOrderByItem item : orderByItems) {
                    replaceTableName(item.getExpr(), tableName, replaceTableName);
                }
            }
        }

        /** group by */
        MySqlSelectGroupBy groupBy = (MySqlSelectGroupBy) query.getGroupBy();
        if (groupBy != null) {
            List<SQLExpr> groupBys = groupBy.getItems();
            if (groupBys != null) {
                for (SQLExpr item : groupBys) {
                    replaceTableName(((MySqlSelectGroupByExpr) item).getExpr(), tableName, replaceTableName);
                }
            }
            /** having */
            replaceTableName(groupBy.getHaving(), tableName, replaceTableName);
        }


    }

    /**
     * 插入sql构建
     *
     * @param sqlStatement
     * @param tableName
     * @param replaceTableName
     * @throws SqlParserException
     */
    private void buildInsert(SQLStatement sqlStatement, String tableName, String replaceTableName) throws SqlParserException {
        MySqlInsertStatement mySqlInsertStatement = (MySqlInsertStatement) sqlStatement;
        /** from */
        replaceTableName(mySqlInsertStatement.getTableName(), tableName, replaceTableName);

        /** 嵌套select */
        buildSelect(((MySqlInsertStatement) sqlStatement).getQuery(), tableName, replaceTableName);

    }

    /**
     * 更新sql构建
     *
     * @param sqlStatement
     * @param tableName
     * @param replaceTableName
     * @throws SqlParserException
     */
    private void buildUpdate(SQLStatement sqlStatement, String tableName, String replaceTableName) throws SqlParserException {
        MySqlUpdateStatement mySqlUpdateStatement = (MySqlUpdateStatement) sqlStatement;
        /** from */
        replaceTableName(mySqlUpdateStatement.getTableSource(), tableName, replaceTableName);
        /** set */
        List<SQLUpdateSetItem> items = mySqlUpdateStatement.getItems();
        for (SQLUpdateSetItem item : items) {
            replaceTableName(item.getColumn(), tableName, replaceTableName);
            replaceTableName(item.getValue(), tableName, replaceTableName);
        }
        /** where */
        replaceTableName(mySqlUpdateStatement.getWhere(), tableName, replaceTableName);
        /** oreder by */
        SQLOrderBy orderBy = mySqlUpdateStatement.getOrderBy();
        if (orderBy != null) {
            List<SQLSelectOrderByItem> orderByItems = orderBy.getItems();
            if (orderByItems != null) {
                for (SQLSelectOrderByItem item : orderByItems) {
                    replaceTableName(item.getExpr(), tableName, replaceTableName);
                }
            }
        }

    }

    /**
     * 删除sql构建
     *
     * @param sqlStatement
     * @param tableName
     * @param replaceTableName
     * @throws SqlParserException
     */
    private void buildDelete(SQLStatement sqlStatement, String tableName, String replaceTableName) throws SqlParserException {
        MySqlDeleteStatement mySqlDeleteStatement = (MySqlDeleteStatement) sqlStatement;

        /** from */
        replaceTableName(mySqlDeleteStatement.getTableSource(), tableName, replaceTableName);
        /** where */
        replaceTableName(mySqlDeleteStatement.getWhere(), tableName, replaceTableName);
        /** order by */
        SQLOrderBy orderBy = mySqlDeleteStatement.getOrderBy();
        if (orderBy != null) {
            List<SQLSelectOrderByItem> orderByItems = orderBy.getItems();
            if (orderByItems != null) {
                for (SQLSelectOrderByItem item : orderByItems) {
                    replaceTableName(item.getExpr(), tableName, replaceTableName);
                }
            }
        }

    }


    /**
     * 替换表名
     */
    private void replaceTableName(SQLObject sqlTableSource, String tableName, String replaceTableName) throws SqlParserException {
        if (sqlTableSource instanceof SQLExprTableSource) {
            SQLIdentifierExpr leftExpr = (SQLIdentifierExpr) ((SQLExprTableSource) sqlTableSource).getExpr();
            String ident = leftExpr.getName();
            if (tableName.equalsIgnoreCase(ident)) {
                leftExpr.setName(replaceTableName);
            }
            /** 别名不做替换 */

        } else if (sqlTableSource instanceof SQLJoinTableSource) {
            SQLTableSource left = ((SQLJoinTableSource) sqlTableSource).getLeft();
            SQLTableSource right = ((SQLJoinTableSource) sqlTableSource).getRight();

            replaceTableName(left, tableName, replaceTableName);
            replaceTableName(right, tableName, replaceTableName);

            /** join on */
            SQLExpr joinOn = ((SQLJoinTableSource) sqlTableSource).getCondition();
            replaceTableName(joinOn, tableName, replaceTableName);

        } else if (sqlTableSource instanceof SQLPropertyExpr) {
            SQLExpr sqlExpr = ((SQLPropertyExpr) sqlTableSource).getOwner();
            replaceTableName(sqlExpr, tableName, replaceTableName);

        } else if (sqlTableSource instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) sqlTableSource;

            SQLExpr left = binaryOpExpr.getLeft();
            SQLExpr right = binaryOpExpr.getRight();

            replaceTableName(left, tableName, replaceTableName);
            replaceTableName(right, tableName, replaceTableName);

        } else if (sqlTableSource instanceof SQLIdentifierExpr) {
            String ident = ((SQLIdentifierExpr) sqlTableSource).getName();
            if (tableName.equalsIgnoreCase(ident)) {
                ((SQLIdentifierExpr) sqlTableSource).setName(replaceTableName);
            }
        } else if (sqlTableSource instanceof SQLQueryExpr) {
            /** 再次构造select */
            buildSelect(((SQLQueryExpr) sqlTableSource).getSubQuery(), tableName, replaceTableName);
        }

        /** 未知类型默认不处理 */

    }
}
