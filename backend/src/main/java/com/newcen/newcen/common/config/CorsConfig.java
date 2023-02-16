package com.newcen.newcen.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //api cors 정책 설정
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000, http://newcen.co.kr.s3-website.ap-northeast-2.amazonaws.com") //api 요청 허용 URL
                .allowedMethods("GET","POST","DELETE","PUT","PATCH")
                .allowedHeaders("*")
                .allowCredentials(true) //자격증명하고와라
                .maxAge(3600); //1시간
    }
}
