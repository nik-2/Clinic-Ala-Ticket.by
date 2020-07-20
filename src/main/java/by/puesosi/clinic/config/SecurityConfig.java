package by.puesosi.clinic.config;

import by.puesosi.clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Service service;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(service);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }

    @Scheduled(fixedDelay = 120000)
    public void checkReestablishCode(){
        service.deleteReestablishCode();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(service)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
               .authorizeRequests()
               .antMatchers("/login", "/registration", "/send/**").not().authenticated()
               .antMatchers("/admin/**").hasRole("ADMIN")
               .antMatchers("/client/**").hasRole("CLIENT")
               .antMatchers("/doctor/**").hasRole("DOCTOR")
               .antMatchers("/profile").hasAnyRole("ADMIN","CLIENT","DOCTOR")
               .antMatchers("/*", "/clinic/main", "/css/**",
                       "/fonts/**", "/images/**", "/js/**", "/vendor/**").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin()
               .usernameParameter("email")
               .loginPage("/login")
               .defaultSuccessUrl("/clinic/profile")
               .permitAll()
               .and()
               .rememberMe()
               .alwaysRemember(true)
               .and()
               .logout()
               .permitAll()
               .and()
               .exceptionHandling().accessDeniedPage("/clinic/error");
    }
}