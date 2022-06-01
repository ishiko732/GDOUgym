package edu.gdou.gym_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;



@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    /**
     * 配置 Swagger 2
     * 注册一个 Bean 属性
     * enable()：是否启用 Swagger，启用后才能在浏览器中进行访问
     * groupName()：用于配置 API 文档的分组
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("v1")
                .select()
                // 过滤路径
                //.paths(PathSelectors.ant())
                // 指定扫描的包
                .apis(RequestHandlerSelectors.basePackage("edu.gdou.gym_java.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        /*作者信息*/
        Contact contact = new Contact("GDOU", "https://github.com/GDOU-LYF/GDOUgym", "liuyuanfeng1@stu.gdou.edu.cn");
        return new ApiInfo(
                "海大体育馆接口文档",
                "",
                "v1.0",
                "https://github.com/GDOU-LYF/GDOUgym",
                contact,
                "MIT license",
                "https://github.com/GDOU-LYF/GDOUgym/blob/master/LICENSE",
                new ArrayList()
        );
    }
}
