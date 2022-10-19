package com.wang.database.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.wang.database.utils.SystemParameterUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description TODO
 * @Author wxf
 * @Date 2022/10/19
 * @change 2022/10/19 by wangxiaofei for init
 **/
@Order
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class DefaultSwaggerConfig {
    public DefaultSwaggerConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({Docket.class})
    public Docket createDefaultRestApi() {
        String springApplicationName = SystemParameterUtil.getApplicationName();
        return (new Docket(DocumentationType.SWAGGER_2)).groupName(StringUtils.isBlank(springApplicationName) ? "default" : springApplicationName).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        String springApplicationTitle = SystemParameterUtil.getApplicationDesc();
        String springApplicationDesc = SystemParameterUtil.getApplicationDesc();
        String springApplicationVersion = SystemParameterUtil.getApplicationVersion();
        if (StringUtils.isEmpty(springApplicationDesc)) {
            springApplicationDesc = "API接口文档，及相关接口的说明";
        } else {
            springApplicationDesc = springApplicationDesc + "-API接口文档，及相关接口的说明";
        }

        if (StringUtils.isEmpty(springApplicationTitle)) {
            springApplicationTitle = SystemParameterUtil.getApplicationName();
        }

        if (StringUtils.isEmpty(springApplicationTitle)) {
            springApplicationTitle = "API接口文档";
        } else {
            springApplicationTitle = springApplicationTitle + "-API接口文档";
        }

        if (StringUtils.isEmpty(springApplicationVersion)) {
            springApplicationVersion = "1.0";
        }

        String url = "http://%s:%s%s";
        url = String.format(url, SystemParameterUtil.getApplicationIp(), SystemParameterUtil.getApplicationPort(), SystemParameterUtil.getContextPath());
        return (new ApiInfoBuilder()).title(springApplicationTitle).description(springApplicationDesc).version(springApplicationVersion).termsOfServiceUrl(url).build();
    }
}
