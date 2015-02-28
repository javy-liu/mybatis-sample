package org.oyach.mybatis.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.type.BooleanTypeHandler;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oyach.AppTest;
import org.oyach.mybatis.domain.Course;
import org.oyach.mybatis.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Reader;
import java.util.List;

import static org.junit.Assert.*;

public class TeacherMapperTest extends AppTest{

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void testExistTeacher() throws Exception {
        Boolean exist = teacherMapper.existTeacher(1L);

        System.out.println(exist);
    }

    @Test
    public void testExistTeacherFalse() throws Exception {
        Boolean exist = teacherMapper.existTeacher(4L);


        System.out.println(exist);
    }


    @Test
    public void testFindById() throws Exception {
        Teacher teacher = teacherMapper.findById(1L);
        System.out.println(teacher);
    }

    @Test
    public void testFindByIdNull() throws Exception {
        Teacher teacher = teacherMapper.findById(4L);
        System.out.println(teacher);
    }

    @Test
    public void testFindOwnerCourse() throws Exception {
        List<Course> courses = teacherMapper.findOwnerCourse(1L);

        for (Course course : courses){
            System.out.println(course);
        }
    }

    @Test
    public void testFindLastCourse() throws Exception {
        Course course = teacherMapper.findLastCourse(1L);

        System.out.println(course);
    }
}