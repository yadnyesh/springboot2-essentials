package yb.yadnyesh.springboot2.essentials.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import yb.yadnyesh.springboot2.essentials.repository.DevDojoUserRepository;
import yb.yadnyesh.springboot2.essentials.service.DevDojoUserDetailsService;

@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DevDojoUserDetailsService devDojoUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Testing Encoder password {} ", passwordEncoder.encode("academy"));
        auth.inMemoryAuthentication()
                .withUser("devdojo2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER")
                .and()
                .withUser("admin2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER", "ADMIN");
        auth.userDetailsService(devDojoUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
