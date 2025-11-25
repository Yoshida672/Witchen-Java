package br.com.fiap.gs_witchen_java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {

        UserDetails garcom = User.builder()
                .username("garcom")
                .password("{noop}1234")
                .roles("GARCOM")
                .build();

        UserDetails cozinha = User.builder()
                .username("cozinha")
                .password("{noop}1234")
                .roles("COZINHA")
                .build();

        return new InMemoryUserDetailsManager(garcom, cozinha);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/css/**", "/img/**").permitAll()

                        // GARÇOM
                        .requestMatchers("/comandas/**").hasRole("GARCOM")

                        // COZINHA
                        .requestMatchers("/cozinha/**").hasRole("COZINHA")

                        // Qualquer usuário autenticado
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/erro/403")
                );

        return http.build();
    }

}