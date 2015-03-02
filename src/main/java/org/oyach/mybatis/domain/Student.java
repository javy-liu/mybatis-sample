package org.oyach.mybatis.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 *
 * 学生领域对象
 */
public class Student {

    // 学生id，生成策略是基于时间+自动递增、如：2015(届)+0001
    private long id;

    // 学生姓名
    private String name;

    // 该学生上过的课程
    private List<Course> courses = new ArrayList<Course>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
