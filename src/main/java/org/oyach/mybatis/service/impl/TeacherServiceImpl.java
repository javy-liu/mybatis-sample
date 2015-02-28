package org.oyach.mybatis.service.impl;

import org.oyach.mybatis.dao.TeacherMapper;
import org.oyach.mybatis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


}
