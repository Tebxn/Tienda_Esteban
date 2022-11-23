/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda;

import com.tienda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Sharon Muñoz
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // web security permite manejar dentro del spring framework toda la autenticacion del usuario y el manejo de las rutas al que el usuario puede ingresar
    
    @Autowired
    private UserService userDetailsService; //se obtiene una persona mediante el username
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ //spring security utiliza el metodo de encriptacion de contraseñas BCrypt
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserService getUserService() {
        return new UserService();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); //crea el objeto
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // setea el password con el metodo de encryptacion
        daoAuthenticationProvider.setUserDetailsService(getUserService()); 
        return daoAuthenticationProvider;
    }
    
    @Bean
    public AuthenticationSuccessHandler appAuthenticationSucessHandler(){
        return new AppAuthenticationSuccessHandler();
    }
    
    public SecurityConfig(UserService userPrincipalDetailsService){
        this.userDetailsService = userPrincipalDetailsService;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }
    
    //El siguiente metodo funciona para hacer la autenticacion del usuario
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
                .antMatchers("/persona","/login","/personasN") //los roles a los cuales admin puede acceder
                .hasRole("ADMIN")
                .antMatchers("/persona", "/", "/login") //los roles a los cuales puede acceder user, vendedor y admin
                .hasAnyRole("USER", "VENDEDOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/persona",true);
        
    }
    
}
