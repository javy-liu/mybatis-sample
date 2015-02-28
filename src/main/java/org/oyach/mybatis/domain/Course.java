package org.oyach.mybatis.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 *
 * 课程领域对象
 */
public class Course {
    // 自动生成，采用mysql AUTO_INCREMENT
    private long id;

    // 课程名称
    private String name;

    // 上本节课程的老师
    private Teacher owner;

    // 上本节课程的学生
    private List<Student> students = new ArrayList<Student>();

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

    public Teacher getOwner() {
        return owner;
    }

    public void setOwner(Teacher owner) {
        this.owner = owner;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", students=" + students +
                '}';
    }
}
