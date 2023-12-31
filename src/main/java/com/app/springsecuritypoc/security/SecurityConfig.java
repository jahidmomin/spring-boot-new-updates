package com.app.springsecuritypoc.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  /*  @Bean
    public UserDetailsService authentication(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("12345")).roles("ADMIN").build();
        UserDetails user = User.withUsername("jahid").password(passwordEncoder.encode("12345")).roles("USER").build();

        //InMemoryUserDetailsManager internally implements UserDetailsServiceManager implements UserDetailsService
        return new InMemoryUserDetailsManager(admin, user);
    }*/

    @Bean
    public UserDetailsService authentication() {
        //custom out impl to provide db users not hard coded users.
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain authorization(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome", "/products/new").permitAll()
                .and()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //it will internally call userDetailsService load method and perform that logic
        provider.setUserDetailsService(authentication());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
