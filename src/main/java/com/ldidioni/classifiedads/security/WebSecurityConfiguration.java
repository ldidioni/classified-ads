package com.ldidioni.classifiedads.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String USERS_QUERY = "select email, password_hash, true from users where username=?";
    private final String ROLES_QUERY = "select u.username, r.role from users u inner join users_roles ur on (u.id = ur.user_id) " +
                                       "inner join roles r on (ur.role_id = r.id) where u.username=?";


    /**
     * Configuration de l'authentification par JDBC
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //JDBC Authentication
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/", "/ads", "/ads/search").permitAll()
                .antMatchers().authenticated()
                .antMatchers("/ads/**", "/users/**", "/tags/**", "/categories/**").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("email").passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .and()
                .rememberMe();
    }
}
