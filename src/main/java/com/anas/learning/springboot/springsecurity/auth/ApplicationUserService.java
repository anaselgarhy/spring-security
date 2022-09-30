package com.anas.learning.springboot.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/09/2022
 */
@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    @Qualifier("fake") // this is the bean name of the implementation of ApplicationUserDao if there are multiple implementations
    // The right place to use @Qualifier is in the constructor but we can use it in the field as well if we using the @RequiredArgsConstructor and the https://stackoverflow.com/a/50287955/14076156
    private final ApplicationUserDao applicationUserDao;
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return applicationUserDao.selectApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
