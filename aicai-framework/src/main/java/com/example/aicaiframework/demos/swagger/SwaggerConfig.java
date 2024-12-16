package com.example.aicaiframework.demos.swagger;

import com.alibaba.fastjson.JSON;
import com.example.aicaiframework.demos.constant.ApiConstant;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuCheng
 * @Date: 2021/11/11 14:32
 * @description:Swagger基础配置
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    public static final String SSO_USER_KEY = "user_oss_obj";

    /**
     * 微服务名称
     */
    @Value("${spring.application.name}")
    private  String springApplicationName;

    private static final Map<String,Object> DEFAULT_TOKEN;

    static {
        DEFAULT_TOKEN = new HashMap<>();
        DEFAULT_TOKEN.put("loginCorpId","2");
        DEFAULT_TOKEN.put("loginType",1);
        DEFAULT_TOKEN.put("userName","超级管理员");
        DEFAULT_TOKEN.put("source",3001);
        DEFAULT_TOKEN.put("mobile","13818307757");
        DEFAULT_TOKEN.put("tokenExpireTime",604800);
        DEFAULT_TOKEN.put("realName","超级管理员");
        DEFAULT_TOKEN.put("loginAuthInfo","0");
        DEFAULT_TOKEN.put("userId",43844);
        DEFAULT_TOKEN.put("accType",2);
        DEFAULT_TOKEN.put("tenantId",2);
        DEFAULT_TOKEN.put("userInfoId",617441);
        DEFAULT_TOKEN.put("xc_RPO_PExtUserId","1");
        DEFAULT_TOKEN.put("xc_RPO_PExtCompanyId","100000");
    }

    @Bean
    public Docket createRestApi() {

        List<Parameter> parameters = new ArrayList<Parameter>();
//        parameters.add(new ParameterBuilder().name(ApiConstant.TOKEN).description("令牌")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build());
////        parameters.add(new ParameterBuilder().name(ApiConstant.PICVCODESID).description("验证码")
////                .modelRef(new ModelRef("string")).parameterType("header")
////                .required(false).build());
//        parameters.add(new ParameterBuilder().name(SSO_USER_KEY).defaultValue(JSON.toJSONString(DEFAULT_TOKEN)).description("租户用户令牌")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build());
        SwaggerProperties swaggerProperties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build()
                .enable(true);
//        docket.globalOperationParameters(parameters);
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title(springApplicationName != null ? springApplicationName.toUpperCase() + "端接口" : "API接口")
//                .description("B端后台相关接口文档")
                .version("1.0")
                .contactName("牛豪")
                .enableSecurity(true)
                .build();
    }
}
