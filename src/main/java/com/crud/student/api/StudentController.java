package com.crud.student.api;


import com.crud.student.dto.StudentRequestDto;
import com.crud.student.dto.StudentResponseDto;
import com.crud.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {
    
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> listAllStudents(@RequestParam(required = false) String gender) {
        return new ResponseEntity<>(this.service.getAllStudents(gender), HttpStatus.OK);
    }


    @GetMapping(value = "/{student-id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable("student-id") Long studentId) {
        return new ResponseEntity<>(this.service.findStudentById(studentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        return new ResponseEntity<>(this.service.saveStudent(studentRequestDto), HttpStatus.CREATED);

    }

    @PutMapping(value = "/{student-id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable("student-id") Long studentId, @RequestBody StudentRequestDto studentRequestDto) {
        return new ResponseEntity<>(this.service.updateStudent(studentId, studentRequestDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{student-id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("student-id") Long studentId) {
        this.service.deleteStudent(studentId);
        return new ResponseEntity<>("Student Deleted", HttpStatus.OK);
    }
}