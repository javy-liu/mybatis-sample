package org.oyach.mybatis.dataSources.sql;

/**
 * sql解析接口
 *
 * @author liuzhenyuan
 * @version Last modified 15/3/5
 * @since 0.0.1
 */
public interface SqlParser {

    /**
     * 根据sql把对应的表名替换为新的表名
     *
     * @param sql
     * @param tableName        需要替换的表名
     * @param replaceTableName 替换后的表名
     * @return 替换后的sql
     * @throws
     */
    String replaceTableName(String sql, String tableName, String replaceTableName) throws SqlParserException;


    public static class Builder {
        public static SqlParser build() {
            return new MySqlParser();
        }
    }
}
