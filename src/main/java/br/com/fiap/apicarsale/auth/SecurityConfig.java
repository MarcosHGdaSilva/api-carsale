package br.com.fiap.apicarsale.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthorizationFilter authorizationFilter) throws Exception{
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/cars").permitAll()
                    .requestMatchers(HttpMethod.GET, "/cars/{id}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/comments").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/comments/{id}").hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/comments").authenticated()
            )
            .csrf(csrf -> csrf.disable()
        );
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}