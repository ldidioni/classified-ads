package com.ldidioni.classifiedads.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private final String USERS_QUERY = "select u.username as username, u.password_hash as password, true as enabled from users u where u.username = ?";
    private final String ROLES_QUERY = "select u.username as username, r.name as role from users u inner join users_roles usro on (u.id = usro.user_id) " +
                                       "inner join roles r on (usro.role_id = r.id) where u.username = ?";

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
            .antMatchers("/ads/{id}/edit").access("@userService.isSeller(#id)")
            .antMatchers(HttpMethod.DELETE,"/ads/{id}").access("@userService.isSeller(#id)")
            .antMatchers("/tags/**", "/categories/**").hasRole("ADMIN")
            .antMatchers("/ads/new").authenticated()
            .antMatchers("/", "/signup", "/ads", "/ads/search").permitAll()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("username").passwordParameter("password")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
            .and()
            .rememberMe();
    }
}
