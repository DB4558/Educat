package com.example.educat_be.AuthenticationAndAuthorisation;

import com.example.educat_be.Service.AdminServiceImpl;
import com.example.educat_be.Service.FacultyServiceImpl;
import com.example.educat_be.Service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private AdminJwtAuthticationFilter adminJwtAuthticationFilter;

    @Autowired
    private FacultyServiceImpl employeeService;

    @Autowired
    private EmployeeJwtAuthenticationFilter employeeJwtAuthenticationFilter;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private StudentJwtAuthenticationFilter studentJwtAuthenticationFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    @Order(1)
    public SecurityFilterChain adminsecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/refresh-token").permitAll()
                        .requestMatchers("/admin/addAdmin").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                ).userDetailsService(adminService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(adminJwtAuthticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain facultysecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/faculty/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/faculty/login").permitAll()
                        .requestMatchers("/faculty/refresh-token").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/faculty/**").hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated()
                ).userDetailsService(employeeService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(employeeJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain accountsecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/accounts/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/accounts/login").permitAll()
                        .requestMatchers("/accounts/refresh-token").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/accounts/**").hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated()
                ).userDetailsService(employeeService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(employeeJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain placementsecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/placement/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/placement/login").permitAll()
                        .requestMatchers("/placement/refresh-token").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/placement/**").hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated()
                ).userDetailsService(employeeService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(employeeJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    @Order(5)
    public SecurityFilterChain estatesecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/estate/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/estate/login").permitAll()
                        .requestMatchers("/estate/refresh-token").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/estate/**").hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated()
                ).userDetailsService(employeeService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(employeeJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    @Order(6)
    public SecurityFilterChain studentsecurityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/student/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/student/login").permitAll()
                        .requestMatchers("/student/refresh-token").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/student/**").hasAuthority("STUDENT")
                        .anyRequest().authenticated()
                ).userDetailsService(studentService)
                .sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(studentJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
}
