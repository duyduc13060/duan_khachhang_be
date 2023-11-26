package com.example.du_an_demo_be.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // todo: inject qua contructor dùng annotation @RequiredArgsConstructor
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager Bean
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService) // Cung cáp userservice cho spring security
                .passwordEncoder(passwordEncoder); // cung cấp password encoder
    }

    // todo: Dạnh sách URL api dùng để khi cần phân quyền
    private static final String[] AUTH_WHITELIST = {
            "/api/**",
            "/api/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").permitAll()  // với permitAll Cho phép tất cả mọi người truy cập vào web
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/ws/**").permitAll()
                .anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được

    }


}
