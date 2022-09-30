package com.anas.learning.springboot.springsecurity.student;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(generator = "student_sequence")
    private Long studentId;
    private String studentName;
    private LocalDate dob; // date of birth

    public Student(final String studentName, final LocalDate dob) {
        this.studentName = studentName;
        this.dob = dob;
    }
}
