package com.crud.student.service;

import com.crud.student.domain.Student;
import com.crud.student.domain.StudentRepositoryWrapper;
import com.crud.student.dto.StudentRequestDto;
import com.crud.student.dto.StudentResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepositoryWrapper studentRepositoryWrapper;

    public StudentServiceImpl(StudentRepositoryWrapper studentRepositoryWrapper) {
        this.studentRepositoryWrapper = studentRepositoryWrapper;
    }


    @Override
    public StudentResponseDto saveStudent(StudentRequestDto studentRequestDto) {
        return this.studentRepositoryWrapper.saveStudent(studentRequestDto).fromEntity();
    }

    @Override
    public StudentResponseDto updateStudent(Long studentId, StudentRequestDto studentRequestDto) {
        return this.studentRepositoryWrapper.updateStudent(studentId, studentRequestDto).fromEntity();
    }

    @Override
    public StudentResponseDto findStudentById(Long studentId) {
        return this.studentRepositoryWrapper.findStudentById(studentId).fromEntity();
    }

    @Override
    public List<StudentResponseDto> getAllStudents(String gender) {
        return Student.fromEntity(this.studentRepositoryWrapper.getAvailableStudents(gender));
    }

    @Override
    public void deleteStudent(Long studentId) {
        this.studentRepositoryWrapper.deleteStudent(studentId);
    }
}
