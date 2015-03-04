package org.oyach.mybatis.aop;

import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/4
 * @since 0.0.1
 */
//@Component
//@Aspect
public class MapperAop {

//    @Pointcut("execution(public * org.apache.ibatis.binding.MapperProxy.invoke(..))")
    private void pointCutMethod() {

    }

//    @Around(value = "pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("--------");
        System.out.println();
        return pjp.proceed();
    }

}
