package org.oyach.mybatis.until;

import com.foundationdb.sql.compiler.TypeComputer;
import com.foundationdb.sql.parser.*;
import com.foundationdb.sql.unparser.NodeToString;
import org.junit.Test;
import org.oyach.mybatis.dataSources.util.NodeToStringForMybatis;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/28
 * @since 0.0.1
 */
public class SQLParserTest {

    private static final String select_sql_01 = "select * from `user` where id = ? and name = ?";
    private static final String select_sql_02 = "select id, name from `user` where id = 1";
    private static final String select_sql_03 = "select * from `user` where id = ?";
    private static final String select_sql_04 = "select * from `user` where id = ?";
    private static final String select_sql_05 = "select * from `user` where id = ?";

    private static final String insert_sql_01 = "select * from `user` where id = ?";
    private static final String insert_sql_02 = "select * from `user` where id = ?";
    private static final String insert_sql_03 = "select * from `user` where id = ?";
    private static final String insert_sql_04 = "select * from `user` where id = ?";
    private static final String insert_sql_05 = "select * from `user` where id = ?";


    @Test
    public void testSelectSql01() throws Exception {
        SQLParser parser = new SQLParser();

        CursorNode cursorNode = (CursorNode) parser.parseStatement(select_sql_01);

        SelectNode selectNode = (SelectNode) cursorNode.getResultSetNode();

        FromList fromList = selectNode.getFromList();

        for (int i = 0; i < fromList.size(); i++) {
            FromBaseTable fromBaseTable = (FromBaseTable) fromList.get(i);
            TableName tableName = fromBaseTable.getTableName();
            tableName.init(null, "user2");
        }
        NodeToStringForMybatis nodeToStringForMybatis = new NodeToStringForMybatis();
        String sql = nodeToStringForMybatis.toString(cursorNode);
        System.out.println(sql);
    }

    @Test
    public void testSelectSql02() throws Exception {
        SQLParser parser = new SQLParser();

        CursorNode cursorNode = (CursorNode) parser.parseStatement(select_sql_02);

        SelectNode selectNode = (SelectNode) cursorNode.getResultSetNode();

        FromList fromList = selectNode.getFromList();

        for (int i = 0; i < fromList.size(); i++) {
            FromBaseTable fromBaseTable = (FromBaseTable) fromList.get(i);
            TableName tableName = fromBaseTable.getTableName();
            tableName.init(null, "user2");
        }
        NodeToStringForMybatis nodeToStringForMybatis = new NodeToStringForMybatis();
        String sql = nodeToStringForMybatis.toString(cursorNode);
        System.out.println(sql);
    }
}
