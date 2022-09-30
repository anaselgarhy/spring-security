package com.anas.learning.springboot.springsecurity;

import com.anas.learning.springboot.springsecurity.student.Student;
import com.anas.learning.springboot.springsecurity.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
@Configuration
public class SetupSampleConfig {
    @Bean
    CommandLineRunner setupStudentsSample(final StudentRepository studentRepository) {
        return args ->
                studentRepository.saveAll(List.of(
                        new Student("Anas", LocalDate.of(2003, 3, 26)),
                        new Student("Ahmed", LocalDate.of(1898, 2, 3)),
                        new Student("Ali", LocalDate.of(2000, 1, 1))
                ));
    }
}
