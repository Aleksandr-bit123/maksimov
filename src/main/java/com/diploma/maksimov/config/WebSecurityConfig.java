package com.diploma.maksimov.config;

import com.diploma.maksimov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    /*Autowired – аннотация позволяет автоматически установить значение поля. Функциональность этой аннотации заключается в том,
     что нам не нужно заботиться о том, как лучше всего Bean'у передать экземпляр другого Bean'a. Spring сам найдет нужный Bean и
      подставит его значение в свойство, которое отмечено аннотацией.*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/registration").hasRole("BOSS")
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**","/registration").hasRole("ADMIN")
                //Доступ только для пользователей с ролью Пользователь
                .antMatchers("/news").hasRole("USER")
                //Доступ только для пользователей с ролью Водитель
                .antMatchers("/driver").hasRole("DRIVER")
                //Доступ только для пользователей с ролью Логист
                .antMatchers("/logist").hasRole("LOGIST")
                //Доступ только для пользователей с ролью Директор
                .antMatchers("/boss/**").hasRole("BOSS")

                //Доступ разрешен всем пользователей
                .antMatchers("/", "/resources/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
