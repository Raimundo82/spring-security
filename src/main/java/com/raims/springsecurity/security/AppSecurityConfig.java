package com.raims.springsecurity.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.raims.springsecurity.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private static final String MANAGEMENT_PATH = "/management/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
//                .antMatchers("/student/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE, MANAGEMENT_PATH).hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, MANAGEMENT_PATH).hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, MANAGEMENT_PATH).hasAuthority(AppUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, MANAGEMENT_PATH).hasAnyRole(ADMIN.name(), NEW_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
//                .roles(AppRole.STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails newAdmin = User.builder()
                .username("newadmin")
                .password(passwordEncoder.encode("password"))
//                .roles(NEW_ADMIN.name())
                .authorities(NEW_ADMIN.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(user, admin, newAdmin);
    }
}
