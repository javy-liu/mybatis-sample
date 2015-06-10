package org.oyach.mybatis.dataSources.sql;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/5
 * @since 0.0.1
 */
public class SqlParserException extends Exception {

    public SqlParserException() {
    }

    public SqlParserException(String message) {
        super(message);
    }

    public SqlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlParserException(Throwable cause) {
        super(cause);
    }
}
