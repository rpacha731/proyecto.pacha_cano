package com.iua.iw3.proyecto.pacha_cano.security;

import com.iua.iw3.proyecto.pacha_cano.model.accounts.IUserBusiness;
import com.iua.iw3.proyecto.pacha_cano.security.authtoken.IAuthTokenBusiness;
import com.iua.iw3.proyecto.pacha_cano.utils.Constant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private IAuthTokenBusiness authTokenBusiness;
    private IUserBusiness userBusiness;
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(Constant.URL_BASE + "/login").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration-security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**").permitAll()
//                .antMatchers(HttpMethod.GET, Constant.URL_BASE + "/ordenes-carga").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
//                .antMatchers(HttpMethod.GET, Constant.URL_BASE + "/ordenes-carga/conciliacion*").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
//                .antMatchers(Constant.URL_BASE + "/ordenes-carga/carga").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
//                .antMatchers(Constant.URL_BASE + "/**").hasAnyRole("ROLE_ADMIN")
                .anyRequest().authenticated();

        http.addFilterAfter(new AuthTokenFilter(authTokenBusiness, userBusiness), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control",
                "Content-Type", "Authorization", "XAUTHTOKEN", "X-Requested-With", "X-AUTH-TOKEN"));

        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
