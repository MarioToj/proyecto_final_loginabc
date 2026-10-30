package com.example.loginabc.config;

import com.example.loginabc.interceptors.AuthInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;


    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/cursos-disponibles",
                        "/login",
                        "/register",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/error",
                        "/favicon.ico"
                );
    }
}
