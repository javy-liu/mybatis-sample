package org.oyach.mybatis.dao;

import org.junit.Test;
import org.oyach.AppTest;
import org.oyach.mybatis.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class StudentMapperTest extends AppTest{

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testFindById() throws Exception {
        Student student = studentMapper.findById(20150002L);
        System.out.println(student);
        Student student1 = studentMapper.findByIdAndName(1L, "aaaa");
        System.out.println(student);
    }

    @Test
    public void testFindByIdAndName() throws Exception {
        Student student = studentMapper.findByIdAndName(1L, "aaaa");
        System.out.println(student);
    }

    @Test
    public void testInsertStudent() throws Exception {
        Student student = new Student();
        student.setId(4L);
        student.setName("aaaasdsadasd");
        long row = studentMapper.insertStudent(student);

        System.out.println(row);
    }
}