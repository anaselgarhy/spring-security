package com.anas.learning.springboot.springsecurity.auth;

import com.anas.learning.springboot.springsecurity.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/09/2022
 */
@Repository("fake")
@RequiredArgsConstructor
public class FakeApplicationUserDaoService implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(final String username) {
        return getDefaultUsers().stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getDefaultUsers() {
        return List.of(
                new ApplicationUser(
                        "anas",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.STUDENT.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.ADMIN.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("pass"),
                        ApplicationUserRole.ADMINISTRATE.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
