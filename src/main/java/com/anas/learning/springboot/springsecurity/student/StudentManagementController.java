package com.anas.learning.springboot.springsecurity.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
@RestController
@RequestMapping("management/api/v1/students")
@RequiredArgsConstructor
public class StudentManagementController {
    private final StudentRepository studentRepository;

    // we can use `@PreAuthorize` annotation to authorize the auth instead of use the antMatchers in the WebSecurityConfig
    // hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping(path = {"all", "list", "", "/"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINISTRATE')")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @DeleteMapping(path = "/delete/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") final Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = {"/add", "/create"})
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<?> addStudent(@RequestBody final StudentRequest studentRequest) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri())
                .body(studentRepository.save(new Student(studentRequest.name(), studentRequest.dob())));

    }

    private record StudentRequest(String name, LocalDate dob) {
    }
}
