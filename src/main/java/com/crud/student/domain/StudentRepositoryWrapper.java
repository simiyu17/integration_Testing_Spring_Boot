package com.crud.student.domain;

import com.crud.student.dto.StudentRequestDto;
import com.crud.student.dto.StudentResponseDto;
import com.crud.student.exception.StudentNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StudentRepositoryWrapper {

    private final StudentRepository studentRepository;

    public StudentRepositoryWrapper(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(StudentRequestDto studentRequestDto) {
        return this.studentRepository.save(Student.createStudent(studentRequestDto));
    }

    public Student updateStudent(Long studentId, StudentRequestDto studentRequestDto) {
        var student = this.findStudentById(studentId);
        student.updateStudent(studentRequestDto);
        return this.studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findStudentById(Long studentId) {
        return this.studentRepository.findById(studentId).orElseThrow(()-> new StudentNotFound(studentId));
    }

    @Transactional(readOnly = true)
    public List<Student> getAvailableStudents(String gender) {
        return "female".equalsIgnoreCase(gender) || "male".equalsIgnoreCase(gender) ? this.studentRepository.findByGender(Student.Gender.valueOf(gender.toUpperCase())) : this.studentRepository.findAll();
    }

    public void deleteStudent(Long studentId) {
        final var student = this.findStudentById(studentId);
        this.studentRepository.delete(student);
    }
}
