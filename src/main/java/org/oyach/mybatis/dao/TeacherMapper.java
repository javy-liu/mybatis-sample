package org.oyach.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.oyach.mybatis.dataSources.util.DataSource;
import org.oyach.mybatis.domain.Course;
import org.oyach.mybatis.domain.Teacher;

import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public interface TeacherMapper {

    /**
     * 判断是否存在该老师
     *
     * @param id 老师id
     * @return true/false: 存在/不存在
     */
//    @Select("SELECT 1 FROM teacher WHERE id = #{id} limit 1;")
    Boolean existTeacher(long id);

    /**
     * 根据id查找对应老师
     *
     * @param id 老师id
     * @return 老师对象  null
     */
    Teacher findById(long id);

    @DataSource("dbWmRead")
    List<Course> findOwnerCourse(long id);

    Course findLastCourse(long id);
}
