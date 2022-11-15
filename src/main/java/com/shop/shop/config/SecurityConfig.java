package com.shop.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*설정이라는 단어의 뜻 그대로 선언 된 자바 클래스는 스프링 설정을 담당하는 클래스가 됩니다.
* 자바 클래스를 설정파일로 만들었으면 그안에 객체들은 Bean등록해야된다.*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 url
                .defaultSuccessUrl("/")//로그인 성공시 이동할 url설정
                .usernameParameter("email")//로그인시 사용할 파라미터 이름
                .failureUrl("/members/login/error")//로그인 실패시 이동url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//로그아웃 url
                .logoutSuccessUrl("/");//로그아웃 성공시 이동할 url

        //        permitAll() 모든 사용자가 로그인 없이 경로에 접근 가능
//        /admin 은 ADMIN Role만 접근 가능
        http.authorizeRequests()
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;


        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //인증되지 않은 사용자 접근 시 수행
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
