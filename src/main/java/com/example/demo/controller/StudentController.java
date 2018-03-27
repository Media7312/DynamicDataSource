package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: 侯玢
 * @Description:
 * @Date: Created in 16:22 2018/3/22
 */
@Controller
public class StudentController {
    @Autowired
    private StudentImpl studentImpl;

    @RequestMapping(value = "/student",method = RequestMethod.POST)
    public String findS(){
        Student student = new Student();
        student.setId(10001);
        student = studentImpl.findData(student);
        System.out.println(student.getName()+"=====================");
        return student.getName();
    }

    @RequestMapping(value = "/addstudent",method = RequestMethod.POST)
    public String addS(){
        Student student = new Student();
        student.setId(2000);
        student.setName("Yui");
        student.setAge(20);
        student.setAddress("CHINA");
        studentImpl.addStudent(student);
        System.out.println("=============");
        return "END";
    }
}
