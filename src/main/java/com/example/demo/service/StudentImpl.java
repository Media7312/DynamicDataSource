package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 侯玢
 * @Description:
 * @Date: Created in 16:16 2018/3/22
 */
@Service
public class StudentImpl {
    @Autowired
    private StudentMapper studentMapper;
    public Student findData(Student student){
        return studentMapper.findDataById(student);
    }
    public int addStudent(Student student){ return studentMapper.addStudent(student);}
}
