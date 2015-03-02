package org.oyach.mybatis.until;

import com.foundationdb.sql.compiler.TypeComputer;
import com.foundationdb.sql.parser.*;
import com.foundationdb.sql.unparser.NodeToString;
import org.junit.Test;
import org.oyach.mybatis.dataSources.util.NodeToStringForMybatis;

import java.util.Properties;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/28
 * @since 0.0.1
 */
public class SQLParserTest {

    private static final String select_sql_01 = "select * from `user` where id = ? and name = ?";
    private static final String select_sql_02 = "select id, name from `user` where id = 1";
    private static final String select_sql_03 = "select \n" +
            "\t\t\tid,wm_order_id,wm_food_id,food_price,unit,count,box_num,box_price,food_name,order_time,origin_food_price\n" +
            "\t\tfrom \n" +
            "\t\t\twm_order_detail\n" +
            "\t\twhere\n" +
            "\t\t\twm_order_id in (1,2,3)\n" +
            "\t\t\t";
    private static final String select_sql_04 = "select \n" +
            "\t\t\tid,wm_order_id,wm_food_id,food_price,unit,count,box_num,box_price,food_name,order_time,origin_food_price\n" +
            "\t\tfrom \n" +
            "\t\t\twm_order_detail\n" +
            "\t\twhere\n" +
            "\t\t\twm_order_id in (1,2,3) and wm_food_id in (-1)";
    private static final String select_sql_05 = "select \n" +
            "\t\t\tid,wm_order_id,wm_food_id,food_price,unit,count,box_num,box_price,food_name,order_time,origin_food_price\n" +
            "\t\tfrom \n" +
            "\t\t\twm_order_detail\n" +
            "\t\twhere\n" +
            "\t\t\twm_order_id in (1,2,3) ORDER BY id DESC limit 0,100";
    private static final String select_sql_06 = "select w.id,w.wm_order_id,w.wm_food_id,w2.oid,w2.id from wm_order_detail w, wm_order_detailw w2 where w.id = w2.oid and w.id = ?";
    private static final String select_sql_07 = "select id,wm_order_id,wm_remark_id,deal_uid,deal_uname,remark,ctime,utime,valid,deal_point,type,result_type,remarkdic_cate1_id,remarkdic_cate2_id from wm_order_deal_remark t inner join \"\n" +
            "                    + \" (select max(id) as max_id from wm_order_deal_remark\"\n" +
            "                    + \" \twhere valid=1 and wm_order_id in (1,2,3)\"\n" +
            "                    + \" \tgroup by wm_order_id) g on t.id = g.max_id";

    private static final String insert_sql_01 = "insert\n" +
            "        into wm_order_detail (wm_order_id,wm_food_id,food_id,food_price,unit,count,box_num,box_price,food_name,order_time,origin_food_price)\n" +
            "        values\n" +
            "        (?,?,0,?,?,?,?,?,?,?,?)\n";

    private static final String delete_sql_01 = "DELETE FROM wm_order\n" +
            "        WHERE id = #{id}";

    private static final String update_sql_01 = "update\n" +
            "\t\t\twm_order\n" +
            "\t\tset\n" +
            "\t\t\tuser_id = ?\n" +
            "\t\twhere \n" +
            "\t\t\tid in (1, 2, 3)\n" +
            "\t\t\tAND user_id = 0\n" +
            "\t\t\tAND valid=1";

    private static final NodeToStringForMybatis nodeToStringForMybatis = new NodeToStringForMybatis();

    @Test
    public void testInsertSql01() throws Exception {
        SQLParser parser = new SQLParser();
        StatementNode statementNode = parser.parseStatement(insert_sql_01);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.INSERT_NODE){
            InsertNode insertNode = (InsertNode) statementNode;

            TableName tableName = insertNode.getTargetTableName();

            if (tableName.getTableName().equals("wm_order_detail")){
                tableName.init(null, "wm");
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);

    }

    @Test
    public void testUpdateSql01() throws Exception {
        SQLParser parser = new SQLParser();
        StatementNode statementNode = parser.parseStatement(update_sql_01);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.UPDATE_NODE){
            UpdateNode updateNode = (UpdateNode) statementNode;

            TableName tableName = updateNode.getTargetTableName();

            if (tableName.getTableName().equals("wm_order")){
                tableName.init(null, "wm");
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);

    }

    @Test
    public void testDeleteSql01() throws Exception {
        SQLParser parser = new SQLParser();
        StatementNode statementNode = parser.parseStatement(update_sql_01);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.DELETE_NODE){
            DeleteNode deleteNode = (DeleteNode) statementNode;

            TableName tableName = deleteNode.getTargetTableName();

            if (tableName.getTableName().equals("wm_order")){
                tableName.init(null, "wm");
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);

    }

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

    @Test
    public void testSelectSql03() throws Exception {
        SQLParser parser = new SQLParser();

        StatementNode statementNode = parser.parseStatement(select_sql_03);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.CURSOR_NODE){
            CursorNode cursorNode = (CursorNode) statementNode;
            ResultSetNode resultSetNode = cursorNode.getResultSetNode();
            if (resultSetNode.getNodeType() == NodeTypes.SELECT_NODE){
                SelectNode selectNode = (SelectNode) resultSetNode;

                FromList fromList = selectNode.getFromList();
                TableName tableName = fromList.get(0).getTableName();
                if (tableName.getTableName().equals("wm_order_detail")){
                    tableName.init(null, "wm");
                }
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);
    }

    @Test
    public void testSelectSql04() throws Exception {
        SQLParser parser = new SQLParser();

        StatementNode statementNode = parser.parseStatement(select_sql_04);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.CURSOR_NODE){
            CursorNode cursorNode = (CursorNode) statementNode;
            ResultSetNode resultSetNode = cursorNode.getResultSetNode();
            if (resultSetNode.getNodeType() == NodeTypes.SELECT_NODE){
                SelectNode selectNode = (SelectNode) resultSetNode;

                FromList fromList = selectNode.getFromList();
                TableName tableName = fromList.get(0).getTableName();
                if (tableName.getTableName().equals("wm_order_detail")){
                    tableName.init(null, "wm");
                }
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);
    }

    @Test
    public void testSelectSql05() throws Exception {
        SQLParser parser = new SQLParser();

        StatementNode statementNode = parser.parseStatement(select_sql_05);

        int type = statementNode.getNodeType();

        if (type == NodeTypes.CURSOR_NODE){
            CursorNode cursorNode = (CursorNode) statementNode;
            ResultSetNode resultSetNode = cursorNode.getResultSetNode();
            if (resultSetNode.getNodeType() == NodeTypes.SELECT_NODE){
                SelectNode selectNode = (SelectNode) resultSetNode;

                FromList fromList = selectNode.getFromList();
                TableName tableName = fromList.get(0).getTableName();
                if (tableName.getTableName().equals("wm_order_detail")){
                    tableName.init(null, "wm");
                }
            }
        }
        String sql = nodeToStringForMybatis.toString(statementNode);
        System.out.println(sql);
    }
}
