package org.oyach.mybatis.dao;

import org.oyach.mybatis.dataSources.util.DataSource;
import org.oyach.mybatis.domain.Course;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
public interface CourseMapper {

    @DataSource("dbWmRead")
    Course findById(long id);
}
