package org.oyach.mybatis.dataSources.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public class MultipleDataSource extends AbstractRoutingDataSource {


    private static final ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();
    private static final Map<String, String> packageDataSource = new HashMap<String, String>();

    public MultipleDataSource() {
    }

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    public static void usePackageDataSource(String pkgName) {
        dataSourceKey.set(packageDataSource.get(pkgName));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dsName = dataSourceKey.get();
        dataSourceKey.remove();
        return dsName;
    }

    public Map<String, String> getPackageDataSource() {
        return packageDataSource;
    }

    public void setPackageDataSource(Map<String, String> packageDataSource) {
        packageDataSource.putAll(packageDataSource);
    }
}

