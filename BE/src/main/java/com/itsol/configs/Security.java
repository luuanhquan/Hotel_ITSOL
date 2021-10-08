package com.itsol.configs;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UsersService userService;
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;


    //    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
//                .passwordEncoder(passwordEncoder()); // cung cấp password encoder
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
//                .antMatchers("/auth", "/", "/home", "/forgot-password/**","/forgot-password/")
//                .permitAll()
//
//                .antMatchers("/user/**", "/report/**","/issue/delete/**")
//                .hasAnyAuthority("ADMIN", "MANAGER")
//
//                .antMatchers("/project/**", "/report/**")
//                .hasAnyAuthority("ADMIN", "MANAGER", "PM")

//                .anyRequest().fullyAuthenticated().and().httpBasic();

//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
