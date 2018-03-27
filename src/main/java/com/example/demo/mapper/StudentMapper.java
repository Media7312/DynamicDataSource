package com.example.demo.mapper;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @Author: 侯玢
 * @Description:
 * @Date: Created in 16:11 2018/3/22
 */
@Repository
public interface StudentMapper {
    Student findDataById(Student student);
    int addStudent(Student student);
}
