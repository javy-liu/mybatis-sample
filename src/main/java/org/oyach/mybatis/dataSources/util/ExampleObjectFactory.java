package org.oyach.mybatis.dataSources.util;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/3
 * @since 0.0.1
 */
public class ExampleObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        System.out.println("--------create1----------");
        System.out.println(type);
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    @Override
    protected Class<?> resolveInterface(Class<?> type) {
        System.out.println("--------resolveInterface----------");
        System.out.println(type);
        return super.resolveInterface(type);
    }

    @Override
    public <T> T create(Class<T> type) {
        System.out.println("--------create2----------");
        System.out.println(type);
        return super.create(type);
    }
}
