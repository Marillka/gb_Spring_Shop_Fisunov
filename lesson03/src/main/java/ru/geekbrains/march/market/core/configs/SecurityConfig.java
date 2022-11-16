package ru.geekbrains.march.market.core.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()// отключаем csrf токены. Это токены которые генерятся и подвязываются к формам, если у нас есть mvc и сессии.
                .cors().disable()     // когда на бек будут приходить запросы из других сервисов и ваш бек будет на это ругаться (можно ли давать им эти данные, или нельзя).
                .authorizeRequests()
                .antMatchers("/api/v1/cart").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// сессии не должно быть, нет, и не будет.
                .and()
                .headers().frameOptions().disable()// для доступа к консоли H2
                .and()
                .exceptionHandling()// Может быть такое, что вы пытаетесь достучаться до защищенной области и полуается что в случае REST вы стучитесь в защищенную область, то вы должны понимать, что от вас трубуется наличие заголовка Authorization. Если вы целенаправленно туда стучитесь и хедер не предоставляете, то вам нужно ругаться. То есть - если вы по какой то причине нарушаете правила безопасности (пытаетесь добраться до защищенной части) - то вы на автомате получите ошибку со статус кодом 401.
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
