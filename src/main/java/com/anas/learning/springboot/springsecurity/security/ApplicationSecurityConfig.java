package com.anas.learning.springboot.springsecurity.security;

import com.anas.learning.springboot.springsecurity.auth.ApplicationUserService;
import com.anas.learning.springboot.springsecurity.jwt.JwtConfig;
import com.anas.learning.springboot.springsecurity.jwt.JwtFilter;
import com.anas.learning.springboot.springsecurity.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/09/2022
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // to enable @PreAuthorize
@RequiredArgsConstructor
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity http,
                                            final AuthenticationManagerBuilder auth,
                                            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // set the authentication provider
        auth.authenticationProvider(daoAuthenticationProvider());

        // set the authorization and authentication rules
        return http
                 // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // .and()
                .csrf().disable()
                // Make sure that the session is stateless because we are using JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Add the JWT filter
                // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
                .addFilter(new JwtFilter(authenticationConfiguration.getAuthenticationManager(), jwtConfig, secretKey))
                // Add the JWT verifier filter to verify the token (called after the JWT filter)
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                // allow access to /api/** for students only
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                // allow access to /management/** for admins only
                // .antMatchers("/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINISTRATE.name())

                // .antMatchers(HttpMethod.POST, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                // .antMatchers(HttpMethod.PUT, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                // .antMatchers(HttpMethod.DELETE, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())

                // .antMatchers(HttpMethod.GET, "/management/api/v1/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINISTRATE.name())

                .anyRequest()
                    .authenticated()
/*
                .and()
                // .httpBasic() // to use basic authentication
                .formLogin() // to use form based authentication (login page)
                    .loginPage("/login") // to use custom login page
                    .defaultSuccessUrl("/courses") // to redirect to courses page after login
                    .passwordParameter("password") // to use custom password parameter (default is password)
                    .usernameParameter("username") // to use custom username parameter (default is username)
                    // .successForwardUrl("/courses") // to redirect to courses page after login even if the auth requested another page (not recommended)
                    // .failureForwardUrl("/login-error") // to redirect to login-error page after login failure
                    .permitAll() // to allow all users to access login page
                .and()
                .rememberMe() // to enable remember me functionality (2 weeks by default)
                    .tokenValiditySeconds(((int) TimeUnit.DAYS.toSeconds(30))) // override the default 2 weeks to 30 days
                    .key("somethingverysecured") // to override the default key
                    .rememberMeParameter("remember-me") // to override the default parameter name (default is remember-me)
                    // .tokenRepository(new InMemoryTokenRepositoryImpl()) // to override the default token repository (to use database for example)
                .and()
                .logout()
                    .logoutUrl("/logout") // to override the default logout url
                    .logoutSuccessUrl("/login") // to redirect to login page after logout
                    // .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // to override the default logout method (POST if CSRF is enabled)
                    .clearAuthentication(true) // to clear the authentication after logout
                    .invalidateHttpSession(true) // to invalidate the session after logout
                    .deleteCookies("JSESSIONID", "remember-me") // to delete cookies after logout
                    .permitAll() // to allow all users to access logout page
*/
                .and().build();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        val provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        // provider.setHideUserNotFoundExceptions(true); // to hide the user not found exception
        return provider;
    }

/*
    @Bean
    protected UserDetailsService userDetailsService() {
        final var user = User.builder()
                .username("anas")
                .password(passwordEncoder.encode("pass"))
                // .roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT - spring security automatically adds ROLE_ prefix
                .authorities(ApplicationUserRole.STUDENT.grantedAuthorities())
                .build();

        // the `val` keyword is from the lombok library , used to declare a final variable same as `final var`
        val admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                // .roles(ApplicationUserRole.ADMIN.name()) // ROLE_ADMIN - spring security automatically adds ROLE_ prefix
                .authorities(ApplicationUserRole.ADMIN.grantedAuthorities())
                .build();

        val john = User.builder()
                .username("john")
                .password(passwordEncoder.encode("1234"))
                // .roles(ApplicationUserRole.ADMINISTRATE.name()) // ROLE_ADMINISTRATE - spring security automatically adds ROLE_ prefix
                .authorities(ApplicationUserRole.ADMINISTRATE.grantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user, admin, john); // store users in memory (not recommended)
    }
*/
}
