package com.crud.student.exception;

public class StudentNotFound extends RuntimeException {

    public StudentNotFound(Long studentId){
        super(String.format("No Student Found With ID %d", studentId));
    }
}
