package org.oyach.mybatis.dataSources.util;

import org.junit.Test;
import org.oyach.mybatis.dao.StudentMapper;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MethodUntilTest {

    @Test
    public void test01() throws Exception {
        Method method = StudentMapper.class.getMethod("findById");


        System.out.println(method);
    }
}