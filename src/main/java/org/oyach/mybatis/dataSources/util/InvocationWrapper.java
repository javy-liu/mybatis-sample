package org.oyach.mybatis.dataSources.util;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/4
 * @since 0.0.1
 */
public class InvocationWrapper extends Invocation {

    private Object target;
    private Method method;
    private Object[] args;

    /**
     * 屏蔽掉父类参数构造
     *
     * @param target
     * @param method
     * @param args
     */
    private InvocationWrapper(Object target, Method method, Object[] args) {
        super(target, method, args);
    }

    /**
     * 提供父类类型构造支持
     *
     * @param invocation
     */
    public InvocationWrapper(Invocation invocation) throws IllegalArgumentException{
        this(invocation.getTarget(), invocation.getMethod(), invocation.getArgs());
        Object[] args = getArgs();
        // 进行处理
        if (args == null || args.length < 2){
            throw new IllegalArgumentException("args can't be null and length must > 3. args=" + args);
        }
        // 第一个是MappedStatement
        Object arg0 = args[0];

        if (!(arg0 instanceof MappedStatement)){
            throw new IllegalArgumentException("args[0] type must MappedStatement. args=" + arg0);
        }
        MappedStatement mappedStatement = (MappedStatement) arg0;
        // 进行拷贝



    }

    @Override
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return super.proceed();
    }
}
