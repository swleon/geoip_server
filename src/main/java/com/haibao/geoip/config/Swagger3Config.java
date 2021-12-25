package com.haibao.geoip.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wuque
 * @title: SwaggerConfig
 * @projectName geoip_server
 * @description:
 * @date 2021/12/256:44 下午
 */
@EnableKnife4j
@Configuration    //表明当前类是配置类
@EnableOpenApi    //表示开启生成接口文档功能（只有开启了OpenApi,才可以实现生成接口文档的功能
public class Swagger3Config {

//    Boolean swaggerEnabled=true;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                // 是否开启
//                .enable(swaggerEnabled)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.haibao.geoip.controller"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GEO服务接口文档")//标题
                .description("更多请咨询服务开发者wuque。")//描述
                //附加信息
                .contact(new Contact("wuque", "http://www.xxx.com", "xxx@email.com"))
                .version("1.0")//版本
                .build();
    }


}
