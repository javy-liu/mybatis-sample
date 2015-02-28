package org.oyach.mybatis.dataSources.util;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.ParameterNode;
import com.foundationdb.sql.unparser.NodeToString;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/28
 * @since 0.0.1
 */
public class NodeToStringForMybatis extends NodeToString {

    @Override
    protected String parameterNode(ParameterNode node) throws StandardException {
        return "?";
    }
}
