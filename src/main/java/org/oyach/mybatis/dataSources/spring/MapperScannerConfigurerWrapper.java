package org.oyach.mybatis.dataSources.spring;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/4
 * @since 0.0.1
 */
public class MapperScannerConfigurerWrapper extends MapperScannerConfigurer{
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
    }
}
