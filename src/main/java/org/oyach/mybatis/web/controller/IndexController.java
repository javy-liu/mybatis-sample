package org.oyach.mybatis.web.controller;

import org.oyach.mybatis.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/27
 * @since 0.0.1
 */
@Controller
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse resp, Model model) {
        log.info("enter into {}", "index");
        return "index";
    }
}
