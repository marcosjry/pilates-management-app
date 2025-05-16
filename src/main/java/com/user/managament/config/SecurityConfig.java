package com.user.managament.config;

import com.user.managament.services.TokenService;
import com.user.managament.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public SecurityFilter securityFilter(TokenService tokenService, UserService userService) {
        return new SecurityFilter(tokenService, userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityFilter securityFilter) throws Exception {
        return httpSecurity
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect(EndPointsAPI.AUTH_DENIED);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            String message = accessDeniedException.getMessage();
                            response.getWriter().write("{\"message\": "+message+" }");
                        }))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, EndPointsAPI.USER).permitAll()
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.AUTH_DENIED).permitAll()
                        .requestMatchers(HttpMethod.POST, EndPointsAPI.AUTH_LOGIN).permitAll()
                        .requestMatchers(HttpMethod.POST, EndPointsAPI.CONTRACT).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CONTRACT).hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, EndPointsAPI.CONTRACT_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, EndPointsAPI.CONTRACT_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CONTRACTS_OF_USER).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CONTRACT_LAST_BY_USER).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CONTRACT_TOTALS).hasRole("USER")
                        .requestMatchers(HttpMethod.POST, EndPointsAPI.CUSTOMER).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CUSTOMER_SEARCH).hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, EndPointsAPI.CUSTOMER_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CUSTOMER_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, EndPointsAPI.CUSTOMER_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.POST, EndPointsAPI.CLASSROOM).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.CLASSROOM_TODAY_CLASSES).hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, EndPointsAPI.CLASSROOM_ID).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.FREQUENCY).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, EndPointsAPI.FREQUENCY_ID).hasRole("USER")
                        .anyRequest().authenticated() // Exige autenticação para outras rotas
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // permite todas as rotas
                        .allowedOrigins("http://localhost:8080", "http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // métodos permitidos
                        .allowedHeaders("*") // todos os headers
                        .allowCredentials(true); // se você estiver usando cookies/autenticação
            }
        };
    }

}
