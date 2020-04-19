package com.xlx.mpd.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig配置类
 *
 * @author xielx at 2020/4/18 22:26
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    
    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("")) // 生成文档包位置
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo() {
        // 联系
        Contact contact = new Contact("xielinx","https://www.xielx.com","xielinx@foxmial.com");
        return new ApiInfoBuilder()
                .title("MyBatis-Plus demo接口文档")
                .description("Swagger2构建RESTFUL APIs")
                .version("1.0")
                .contact(contact)
                .build();
    }
}
