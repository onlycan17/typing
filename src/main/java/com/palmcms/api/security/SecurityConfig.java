package com.palmcms.api.security;

import com.palmcms.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PalmAuthenticationProvider authenticationProvider;

    @Autowired
    private Environment environment;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "*")
                .antMatchers("/static/bootstrap/**")
                .antMatchers("/static/css/**")
                .antMatchers("/static/images/**")
                .antMatchers("/images/**")
                .antMatchers("/static/js/**")
                .antMatchers("/static/**")
                .antMatchers("/webjars/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/health")
                .antMatchers("/auth/**")
                .antMatchers("/code/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()

//                .formLogin()
//                .disable()
//
//                .httpBasic()
//                .disable()

                .authorizeRequests()
//                .antMatchers(Constants.PALMCMS_API_AUTH + "/**").permitAll()
//                .antMatchers(Constants.PALMCMS_API_CODE + "/**").permitAll()
                .antMatchers(Constants.PALMCMS_API_CMS + "/**").permitAll()
                .antMatchers(Constants.PALMCMS_API_CUSOMER + "/**").permitAll()
                .antMatchers(Constants.PALMCMS_API_USER + "/**").permitAll()


                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(tokenAuthentificationFilter(),
                        AbstractPreAuthenticatedProcessingFilter.class)
        ;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthentificationFilter() {
        return new TokenAuthenticationFilter("/palmcms/api/**");
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        return firewall;
    }
}
