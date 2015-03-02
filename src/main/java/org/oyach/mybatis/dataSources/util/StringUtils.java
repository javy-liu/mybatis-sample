package org.oyach.mybatis.dataSources.util;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/2
 * @since 0.0.1
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {

    public static String[] splitLast(String toSplit, String delimiter){
        int index = toSplit.lastIndexOf(delimiter);
        String startString = toSplit.substring(0, index);
        String endString = toSplit.substring(index + delimiter.length());
        return new String[]{startString, endString};
    }
}
