package org.oyach.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.oyach.mybatis.dataSources.util.DataSource;
import org.oyach.mybatis.dataSources.util.Shared;
import org.oyach.mybatis.domain.Student;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public interface StudentMapper {

    @DataSource("dbWmWrite")
    @Select("select id, name from student where id = #{id}")
    Student findById(@Shared @Param("id")long id);

    @Select("select id, name from student where id = #{id} and name = #{name}")
    Student findByIdAndName(@Param("id")long id, String name);
}
