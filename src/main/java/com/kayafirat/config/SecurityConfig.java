package com.kayafirat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kayafirat.security.JwtRequestFilter;
import com.kayafirat.service.UserService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private  UserService userService;

    @Autowired
    private  JwtRequestFilter jwtRequestFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf()
                .csrfTokenRepository(cookieCsrfTokenRepository()).and()
                .authorizeRequests()
                .antMatchers("/v1/auth/github").permitAll()
                .antMatchers("/v1/auth/linkedin").permitAll()
                .antMatchers("/v1/login").permitAll()
                .antMatchers("/v1/logout").permitAll()
                .antMatchers("/v1/user/register").permitAll()
                .antMatchers("/v1/recaptcha").permitAll()
                .antMatchers("/v1/post/**").permitAll()
                .antMatchers("/v1/comment/**").permitAll()
                .antMatchers("/v1/comment/").authenticated()
                .antMatchers("/v1/mail").permitAll()
                .antMatchers("/v1/mail/forgot").permitAll()
                .antMatchers("/v1/admin/**").permitAll()
                .antMatchers("/v1/image/save").permitAll()
                .antMatchers("/v1/image/**").permitAll()
                .antMatchers("/v1/contact").permitAll()
                .antMatchers("/v1/contact/**").permitAll()
                .antMatchers("/v1/error/**").permitAll()
                .anyRequest().authenticated()
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutUrl("/v1/logout").deleteCookies("authenticate").invalidateHttpSession(true);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    private CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        final CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookiePath("/");
        //cookieCsrfTokenRepository.setCookieDomain("kayafirat.com");
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
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A,10);
    }


}
