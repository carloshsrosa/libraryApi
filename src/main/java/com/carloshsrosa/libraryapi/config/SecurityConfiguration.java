package com.carloshsrosa.libraryapi.config;

import com.carloshsrosa.libraryapi.security.CustomUserDetailService;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> {
                    configurer
                            .loginPage("/login").permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .build();
//        authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasAuthority("CADASTRAR_AUTOR");
//        authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN");
//        authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
//        authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN");
//        authorize.requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USER", "ADMIN");
//        authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
//                .formLogin(Customizer.withDefaults())
//                .formLogin(configurer -> configurer.loginPage("/login.html").successForwardUrl("/home.html"))
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

//    login utilizando usuarios na memória
//    @Bean
//    public UserDetailsService userDetailsServiceInMemory(PasswordEncoder encoder) {
//
//        var user = User.builder()
//                .username("user")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        var admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("321"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    //    login utilizando usuarios do banco de dados
    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
            return new CustomUserDetailService();
    }
}
