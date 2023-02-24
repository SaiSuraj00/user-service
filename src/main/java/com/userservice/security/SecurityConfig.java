package com.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
	@Autowired
	private UserDetailsService userDetailsService;

	/*
	 * Returns AuthenticationManager Bean with the available
	 * AuthenticationProvider in spring context
	 */
	@Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

	/*
	 * Returns DaoAuthenticationProvider Bean with the userDetailsService
	 * that has the ability to trigger loadUserByUsername method to fetch
	 * the UserDetails
	 * 
	 */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider
                =new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
    /*
     * Returns BCryptPasswordEncoder bean which is injected into
     * DaoAuthenticationProvider to encode & decode the password
     * with default encryption algorithm during the registration
     * and login scenarios
     */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
     * Configures Security rules that are intended for the App
     */
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
        http.csrf().disable().authorizeRequests()
        .antMatchers("/register").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest()
        .authenticated()
        .and().formLogin().permitAll();
        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }
}