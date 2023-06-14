package com.crud.student.service;

import com.crud.student.dto.StudentRequestDto;
import com.crud.student.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {

    StudentResponseDto saveStudent(StudentRequestDto studentResponseDto);

    StudentResponseDto updateStudent(Long studentId, StudentRequestDto studentResponseDto);

    StudentResponseDto findStudentById(Long studentId);

    List<StudentResponseDto> getAllStudents(String gender);

    void deleteStudent(Long studentId);
}
