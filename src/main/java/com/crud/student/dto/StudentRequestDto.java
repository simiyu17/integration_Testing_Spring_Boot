package com.crud.student.dto;

import com.crud.student.domain.Student;

import java.io.Serializable;
import java.time.LocalDate;

public record StudentRequestDto(
        String firstName,

        String lastName,

        Student.Gender gender,

        LocalDate dateOfBirth
) implements Serializable {
}
