package org.oyach.mybatis.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 *
 * 老师领域对象
 */
public class Teacher {
    // 老师id，0001
    private long id;

    // 老师姓名
    private String name;

    // 老师上过的课程
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
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
