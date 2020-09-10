package io.gitee.welkinfast.admin.config;

import io.gitee.welkinfast.common.response.CustomResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description Swagger配置
 * @Author yuanjg
 * @CreateTime 2020/8/13 12:54
 * @Version 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("user_token").description("认证token")
                .modelRef(new ModelRef("string")).parameterType("header")
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        List<Parameter> pars = new ArrayList();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SPRING_WEB)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.gitee.welkinfast.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                //.forCodeGeneration(true)
                //遇到对应泛型类型的外围类，直接解析成泛型类型，WelkinResult<T>，应该直接输出成类型T
                .genericModelSubstitutes(CustomResponse.class);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("open-welkin")
                .description("open-welkin文档")
                .termsOfServiceUrl("http://www.welkincode.top")
                .contact(new Contact("yuanjg", "http://www.welkincode.top", "yjg_678@163.com"))
                .version("1.0.0")
                .build();
    }


}
