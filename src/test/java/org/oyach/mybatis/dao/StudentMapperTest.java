package org.oyach.mybatis.dao;

import org.junit.Test;
import org.oyach.AppTest;
import org.oyach.mybatis.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class StudentMapperTest extends AppTest{

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testFindById() throws Exception {
        Student student = studentMapper.findById(20150001L);
        System.out.println(student);
    }

    @Test
    public void testFindByIdAndName() throws Exception {
        Student student = studentMapper.findByIdAndName(1L, "aaaa");
        System.out.println(student);
    }
}