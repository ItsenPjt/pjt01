package com.newcen.newcen.common.config;



import com.newcen.newcen.common.config.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


     private final JwtAuthFilter jwtAuthFilter;


    //패스워드 인코딩 클래스를 등록
    //<bean id=? class=? />
    //encoder가 bean id, PasswordEncoder가 클래스
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    //시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //시큐리티 빌더
        http.cors() //크로스 오리진 정책
                .and()
                .csrf() //csrf 정책
                .disable() //스프링 시큐리티에서 제공하는 검증기능 사용안함.
                .httpBasic().disable() // 기본 시큐리티 인증 해제, 토큰 인증 쓸꺼니까
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 인증 안함
                .and()
                .authorizeRequests().antMatchers( "/api/**","/").permitAll() //인증요청중에서 "/"경로랑, "/api/auth 경로는 모두 허용
                .anyRequest().authenticated(); //그 외의 경로는 모두 인증을 거쳐야함.


        //토큰 인증 필터 등록
        //(커스텀 필터, 누구뒤에 붙일지)
        http.addFilterAfter(jwtAuthFilter, CorsFilter.class // 임포트 주의 ; 스프링껄로
        );


        return http.build();
    }

}
