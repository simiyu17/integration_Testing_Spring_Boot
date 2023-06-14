package com.crud.student.api;

import com.crud.IntegrationTestBase;
import com.crud.student.domain.Student;
import com.crud.student.dto.StudentRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/insertStudentData.sql")
class StudentControllerTest extends IntegrationTestBase {


    static Stream<Arguments> genderSource(){
        return Stream.of(Arguments.of(Student.Gender.FEMALE.name(), 4),
                Arguments.of(Student.Gender.MALE.name(), 3),
                Arguments.of(null, 7),
                Arguments.of("Unknown", 7),
                Arguments.of("", 7),
                Arguments.of(" ", 7));
    }

    @ParameterizedTest
    @MethodSource("genderSource")
    void given_user_fetching_for_one_gender_students_then_return_all_students_for_gender_provided(String gender, int expectedSize) throws Exception {
        final var getStudentsUrl = "/students?gender="+gender;
        final var requestBuilder = MockMvcRequestBuilders.get(getStudentsUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(expectedSize)))
                .andReturn();
    }

    @Test
    void given_student_id_of_known_student_to_find_student_then_return_student() throws Exception {
        final var getStudentByIdUrl = "/students/{student-id}";
        final var requestBuilder = MockMvcRequestBuilders.get(getStudentByIdUrl, 10001L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(10001))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.dateOfBirth").value("2010-10-12"))
                .andReturn();
    }

    @Test
    void given_student_id_of_unknown_student_to_find_student_then_throw_Exception() throws Exception {
        final var getStudentByIdUrl = "/students/{student-id}";
        final var requestBuilder = MockMvcRequestBuilders.get(getStudentByIdUrl, 10088L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Student Not Found"))
                .andExpect(jsonPath("$.detail").value("No Student Found With ID 10088"));
    }


    @Test
    void createStudent() throws Exception {
        final var studentsUrl = "/students";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(studentsUrl)
                .content(objectMapper.writeValueAsString(new StudentRequestDto( "Benson", "Doe", Student.Gender.MALE, LocalDate.of(2008, Month.AUGUST, 18))))
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void updateStudent() throws Exception {
        final var getStudentByIdUrl = "/students/{student-id}";
        var requestBuilder = MockMvcRequestBuilders.put(getStudentByIdUrl, 10004L)
                .content(objectMapper.writeValueAsString(new StudentRequestDto( null, "Simpson", null, LocalDate.of(2008, Month.AUGUST, 18))))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        requestBuilder = MockMvcRequestBuilders.get(getStudentByIdUrl, 10004L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(10004))
                .andExpect(jsonPath("$.firstName").value("Cecilia"))
                .andExpect(jsonPath("$.lastName").value("Simpson"))
                .andExpect(jsonPath("$.dateOfBirth").value("2008-08-18"))
                .andReturn();
    }

    @Test
    void given_student_id_of_known_student_to_delete_student_then_remove_student() throws Exception {
        var getStudentByIdUrl = "/students/{student-id}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(getStudentByIdUrl, 10001L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        requestBuilder = MockMvcRequestBuilders.get(getStudentByIdUrl, 10001L)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Student Not Found"))
                .andExpect(jsonPath("$.detail").value("No Student Found With ID 10001"));
    }
}