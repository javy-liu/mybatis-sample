package org.oyach.mybatis.dao;

import org.junit.Test;
import org.oyach.AppTest;
import org.oyach.mybatis.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CourseMapperTest extends AppTest{

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testFindById() throws Exception {
        Course course = courseMapper.findById(1L);
        System.out.println(course);
    }
}