package com.anas.learning.springboot.springsecurity.student;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
