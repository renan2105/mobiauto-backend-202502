package com.renan.security;

import com.renan.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(JwtTokenProvider tokenProvider, UsuarioRepository usuarioRepository) {
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, usuarioRepository);

        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMINISTRADOR")
                        .antMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMINISTRADOR")
                        .antMatchers(HttpMethod.GET, "/api/usuarios/**").authenticated()
                        .antMatchers(HttpMethod.PUT, "/api/usuarios/**")
                        .hasAnyRole("PROPRIETARIO", "ADMINISTRADOR", "GERENTE")
                        .antMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMINISTRADOR")

                        .antMatchers(HttpMethod.POST, "/api/lojas/**").hasRole("ADMINISTRADOR")
                        .antMatchers(HttpMethod.GET, "/api/lojas").hasRole("ADMINISTRADOR")
                        .antMatchers(HttpMethod.GET, "/api/lojas/**").authenticated()
                        .antMatchers(HttpMethod.DELETE, "/api/lojas/**").hasRole("ADMINISTRADOR")

                        .antMatchers(HttpMethod.DELETE, "/api/oportunidades/**").hasRole("ADMINISTRADOR")

                        .antMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Não autenticado: faça login para acessar este recurso\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Acesso negado: você não tem permissão para acessar este recurso\"}");
                })
                .and()
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
