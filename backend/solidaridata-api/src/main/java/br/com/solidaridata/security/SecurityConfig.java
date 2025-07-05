package br.com.solidaridata.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Permite a requisição de login
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        // Permite requisições OPTIONS, essenciais para o CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Permite acesso aos endpoints de cadastro
                        .requestMatchers(HttpMethod.POST, "/api/beneficiarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/beneficiarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/beneficiarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instituicoes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/atendimentos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/atendimentos/**").permitAll()
                        // Permite operações de edição e exclusão (temporário para desenvolvimento)
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/beneficiarios/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/instituicoes/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/atendimentos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/beneficiarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/instituicoes/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/servicos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/atendimentos/**").permitAll()
                        // Protege todas as outras requisições
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}