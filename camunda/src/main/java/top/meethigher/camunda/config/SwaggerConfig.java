package top.meethigher.camunda.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 *
 *
 * @author chenchuancheng
 * @since 2023/4/19 14:28
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置swagger实例，如果有多个开发组，可配置多个docket，ui下滑栏选择
     *
     * @param environment 配置对象。可springboot中所有配置文件信息
     * @return 对ui来说一个docket就是一个开发组
     */
    @Bean
    public Docket openDocket(Environment environment) {
        //开发环境时生效
        Profiles profiles = Profiles.of("dev");
        boolean flag = environment.acceptsProfiles(profiles);
        //测试环境放开
        flag = true;
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
                //.paths(PathSelectors.ant(String.format("%s/open/**", contextPath)))
                .build().groupName("web");
    }

    /**
     * 配置基本信息
     *
     * @return api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "接口文档",
                "用于测试多模块项目构建",
                "接口文档1.0",
                "",
                new Contact("top/meethigher", "https://meethigher.top", "meethigher@qq.com"),
                "",
                "",
                new ArrayList<>()
        );
    }


}
