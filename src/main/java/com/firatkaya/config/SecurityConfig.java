package com.firatkaya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.firatkaya.security.JwtRequestFilter;
import com.firatkaya.service.UserService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserService userService;

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(UserService userService,JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf()
                .csrfTokenRepository(cookieCsrfTokenRepository()).and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/auth/github").permitAll()
                .antMatchers("/api/v1/user/auth/linkedin").permitAll()
                .antMatchers("/api/v1/user/login").permitAll()
                .antMatchers("/api/v1/user/register").permitAll()
                .antMatchers("/api/v1/user/verification").permitAll()
                .antMatchers("/api/v1/user/sendResetEmail").permitAll()
                .antMatchers("/api/v1/user/reset/**").permitAll()
                .antMatchers("/api/v1/user/validaterecaptcha").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/post/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/comment/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/v1/comment/").authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutUrl("/api/v1/admin/logout").deleteCookies("authenticate").invalidateHttpSession(true);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    private CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        final CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookiePath("/");
       // cookieCsrfTokenRepository.setCookieDomain("kayafirat.com");
        cookieCsrfTokenRepository.setCookieHttpOnly(false);

        return cookieCsrfTokenRepository;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
