package com.jal.reboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

// 스프링 인 액션
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //사용자의 http 요청 경로에 대한 접근 제한 등의 보안 관련 처리

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .failureUrl("/loginError")
            .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
            .and()
                .authorizeRequests()
                .antMatchers("/write").hasAuthority("USER") // 왜 hasRole("USER")는 forbidden 뜨지?
                .antMatchers("/", "/**").permitAll()
            .and().httpBasic();
    }

    //jdbc기반 사용자 스토어로 인증하기
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, 1 as enabled from member " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role_type from member " +
                                "where username=?")
                .passwordEncoder(new NoEncodingPasswordEncoder());

    }
}
