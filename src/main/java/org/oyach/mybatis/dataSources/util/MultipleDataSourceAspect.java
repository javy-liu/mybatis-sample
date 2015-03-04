package org.oyach.mybatis.dataSources.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public class MultipleDataSourceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(MultipleDataSourceAspect.class);

    public MultipleDataSourceAspect() {
    }

    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        if(LOG.isDebugEnabled()) {
            LOG.debug("MultipleDataSourceAspectAdvice invoked!");
        }

        Signature signature = jp.getSignature();
        String dataSourceKey = this.getDataSourceKey(signature);
        if(StringUtils.hasText(dataSourceKey)) {
            MultipleDataSource.setDataSourceKey(dataSourceKey);
        }

        Object jpVal = jp.proceed();
        return jpVal;
    }

    private String getDataSourceKey(Signature signature) {
        if(signature == null) {
            return null;
        } else {
            if(signature instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature)signature;
                Method method = methodSignature.getMethod();
                MultipleDataSource.setMethod(method);
                if(method.isAnnotationPresent(DataSource.class)) {
                    return this.dsSettingInMethod(method);
                }

                Class declaringClazz = method.getDeclaringClass();
                if(declaringClazz.isAnnotationPresent(DataSource.class)) {
                    try {
                        return this.dsSettingInConstructor(declaringClazz);
                    } catch (Exception var6) {
                        LOG.error("获取构造方法的DataSource注解失败", var6);
                    }
                }

                Package pkg = declaringClazz.getPackage();
                this.dsSettingInPackage(pkg);
            }

            return null;
        }
    }

    private String dsSettingInMethod(Method method) {
        DataSource dataSource = (DataSource)method.getAnnotation(DataSource.class);
        return dataSource.value();
    }

    private String dsSettingInConstructor(Class clazz) {
        DataSource dataSource = (DataSource)clazz.getAnnotation(DataSource.class);
        return dataSource.value();
    }

    private void dsSettingInPackage(Package pkg) {
        if(LOG.isDebugEnabled()) {
            LOG.debug(pkg.getName());
        }

        MultipleDataSource.usePackageDataSource(pkg.getName());
    }
}
