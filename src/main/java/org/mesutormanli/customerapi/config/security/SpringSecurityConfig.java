package org.mesutormanli.customerapi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/customer/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/customers").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/customer").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/customer/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/customer/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/customers").hasRole("ADMIN")
                .and()
                .formLogin().disable()
                .csrf().disable();
    }
}
