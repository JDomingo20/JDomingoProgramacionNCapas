package com.JDomingoProgramacionNCapas.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Usuario/Form/0").hasAuthority("Moderador")
                .requestMatchers("/Usuario/Form/0").hasAuthority("Administrador")
                .requestMatchers("/Usuario/formEditable").hasAuthority("Moderador")
                .requestMatchers("/Usuario/CargaMasiva").hasAnyAuthority("Moderador", "Administrador")
                .requestMatchers("/Usuario").hasAnyAuthority("Moderador", "Administrador", "Cliente")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/Login")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/Usuario", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIOID")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);
                    response.sendRedirect("/login?logout");
                })
                )
                .exceptionHandling((exceptions) -> exceptions
                .accessDeniedPage("/access-denied")
                )
                .headers(headers -> headers
                .cacheControl(cache -> cache.disable())
                );

        return http.build();
    }

    @Bean
    public UserDetailsService jdbDetailsService(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT UserName, Password, Estatus FROM Usuario WHERE UserName = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT UserName, NombreRol FROM RolManager WHERE UserName =?");

        return jdbcUserDetailsManager;
    }
}
