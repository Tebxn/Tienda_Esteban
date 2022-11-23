/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 *
 * @author Sharon Muñoz
 */

@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Bean //El siguiente metodo es una declaracion que queremos se realice en el momento de ejecucion
    public SessionLocaleResolver localeResolver(){ //retorna un objeto de tipo localeresolver
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es")); //el local representa el lenguaje, la region geografica, variantes del dialecto/idioma de un usuario
        return slr;
    
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){ //detecta cualquier cambio de parte del usuario a lo que es el local
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); //parametro que utilizamos para pasar nuestro lenguaje
        return lci;
    
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    
    }

    
    
}
