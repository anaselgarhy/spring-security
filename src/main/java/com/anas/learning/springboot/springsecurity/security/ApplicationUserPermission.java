package com.anas.learning.springboot.springsecurity.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
@RequiredArgsConstructor
public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write")
    ;

    @Getter
    private final String permission;
}
