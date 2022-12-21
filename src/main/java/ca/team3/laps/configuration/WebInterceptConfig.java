package ca.team3.laps.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ca.team3.laps.interceptor.SecurityInterceptor;

// @Component
// public class WebInterceptConfig implements WebMvcConfigurer {
//     @Autowired
//     SecurityInterceptor securityInterceptor;

//     @Override
//     public void addInterceptors(InterceptorRegistry registry) {
//         registry.addInterceptor(securityInterceptor).addPathPatterns("/api/getholidays", "/api/staff/");
//     }
// }
