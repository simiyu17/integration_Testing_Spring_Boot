
package com.crud.student.domain;

import com.crud.student.dto.StudentRequestDto;
import com.crud.student.dto.StudentResponseDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author simiyu
 */
@Entity
@Table(name = "students")
public class Student implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    private Long id;
    
    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    public Student() {
    }

    private Student(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public static Student createStudent(StudentRequestDto studentRequestDto){
        return new Student(studentRequestDto.firstName(), studentRequestDto.lastName(), studentRequestDto.gender(), studentRequestDto.dateOfBirth());
    }

    public void updateStudent(StudentRequestDto studentRequestDto){
        final var newFirstName = studentRequestDto.firstName();
        if (Objects.nonNull(newFirstName) && !Objects.equals(newFirstName, this.firstName)){
            this.firstName = newFirstName;
        }
        final var newLastName = studentRequestDto.lastName();
        if (Objects.nonNull(newLastName) && !Objects.equals(newLastName, this.lastName)){
            this.lastName = newLastName;
        }
        final var newGender = studentRequestDto.gender();
        if (Objects.nonNull(newGender) && !Objects.equals(newGender, this.gender)){
            this.gender = newGender;
        }
        final var newDob = studentRequestDto.dateOfBirth();
        if (Objects.nonNull(newDob) && !Objects.equals(newDob, this.dateOfBirth)){
            this.dateOfBirth = newDob;
        }
    }

    public StudentResponseDto fromEntity(){
        return new StudentResponseDto(this.id, this.firstName, this.lastName, this.gender, this.dateOfBirth);
    }

    public static List<StudentResponseDto> fromEntity(List<Student> students){
        var studentDtos = new ArrayList<StudentResponseDto>();
        students.forEach(student -> studentDtos.add(student.fromEntity()));
        return studentDtos;
    }

    public enum Gender {
        MALE("Male"), FEMALE("Female");

        private final String name;

        Gender(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    

    
}
