package org.oyach.mybatis.dao;

import org.apache.ibatis.annotations.Select;
import org.oyach.mybatis.dataSources.util.DataSource;
import org.oyach.mybatis.domain.Course;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public interface CourseMapper {

    @DataSource("dbWmRead")
    @Select("select id, name from course where id = #{id}")
    Course findById(long id);

    @DataSource("dbWmRead")
    @Select("select id, name from course where id = #{id} and name = #{name}")
    Course findByIdAndName(long id, String name);
}
