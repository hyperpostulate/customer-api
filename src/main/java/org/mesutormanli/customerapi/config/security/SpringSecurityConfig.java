package org.mesutormanli.customerapi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "USER";
    private static final String URL_PATTERN_CUSTOMER = "/customer/**";

    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles(ROLE_USER)
                .and()
                .withUser("admin").password("{noop}admin").roles(ROLE_USER, ROLE_ADMIN);

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, URL_PATTERN_CUSTOMER).hasRole(ROLE_USER)
                .antMatchers(HttpMethod.PUT, URL_PATTERN_CUSTOMER).hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, URL_PATTERN_CUSTOMER).hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/customers").hasRole(ROLE_USER)
                .antMatchers(HttpMethod.POST, "/customer").hasRole(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/customers").hasRole(ROLE_ADMIN)
                .and()
                .formLogin().disable()
                .csrf().disable();
    }
}
