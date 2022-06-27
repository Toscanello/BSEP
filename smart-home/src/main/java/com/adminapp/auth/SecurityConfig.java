package com.adminapp.auth;

import com.adminapp.services.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.adminapp.utils.TokenUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfig() {}
    public SecurityConfig(UserAuthService userAuthService,
                          TokenUtils tokenUtils,
                          RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.tokenUtils = tokenUtils;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userAuthService = userAuthService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public SecurityConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

//    @Override
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder com.adminapp.auth) throws Exception {
//        com.adminapp.auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                // Definisemo uputstva AuthenticationManager-u:
//
//                // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
//                // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
//                .userDetailsService(userAuthService)
//
//                // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
//                // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and();

//        http = http.exceptionHandling()
//                .authenticationEntryPoint((req, res, ex) -> {
//                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//                }).and();

        http.authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/auth/login").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/auth/login").permitAll()
                //.antMatchers("/api/privilegedUser/**").permitAll()
                .and().httpBasic().and()
//                .anyRequest().authenticated().and().formLogin().loginProcessingUrl("/api/com.adminapp.auth/loging").and()

                .addFilterAfter(new TokenAuthenticationFilter(tokenUtils, userAuthService), BasicAuthenticationFilter.class);

        http
            .requiresChannel(channel ->
                    channel.anyRequest().requiresSecure());

        http.cors().and().csrf().disable();

    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //config.addAllowedOrigin("*");
        config.setAllowedOrigins(Arrays.asList("https://localhost:3001")); //if using different port add here
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .requiresChannel(channel ->
//                        channel.anyRequest().requiresSecure())
//                .authorizeRequests(authorize ->
//                        authorize.anyRequest().permitAll())
//                .build();
//    }

}
