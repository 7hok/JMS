package com.java.push.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * File Name                : 
 * File Path                : 
 * File Description         : 
 * File Author              : 
 * Created Date             :
 * Developed By             :
 * Modified Date            :
 * Modified By              :
 */

@Configuration
@EnableSwagger2
public class SpringFoxConfig {     
    /**
     |---------------------------------------------------------------------------------------- 
     |  Method Name              => 
     |  Parameters               => String username
     |  Developed By             => 
     |  Created Date             => 17-May-2020
     |  Updated By               =>
     |  Updated Date             => 
     |---------------------------------------------------------------------------------------- 
     |  
     |  
     |  
     */
                               
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}