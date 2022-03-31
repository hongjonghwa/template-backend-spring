package io.ssafy.test.spring;

import io.ssafy.test.spring.auth.CustomUserDetailsService;
import io.ssafy.test.spring.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final CustomUserDetailsService customUserDetailsService;
    final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // h2-console
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll();

        http.headers()
                .frameOptions()
                .sameOrigin();

        http.antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/lookup/*","/api/v1/auth/register","/api/v1/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/todos").permitAll()
                .antMatchers("/api/admin/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers("/", "/error").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class );
    }
}
