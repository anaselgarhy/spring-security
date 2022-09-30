package com.anas.learning.springboot.springsecurity.auth;

import java.util.Optional;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/09/2022
 */
public interface ApplicationUserDao {
    Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
