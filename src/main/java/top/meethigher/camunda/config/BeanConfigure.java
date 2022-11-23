package top.meethigher.camunda.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * bean配置类
 *
 * @author chenchuancheng
 * @since 2022/11/23 16:10
 */
@Configuration
public class BeanConfigure {

    /**
     * 配置swagger实例，如果有多个开发组，可配置多个docket，ui下滑栏选择
     *
     * @param environment 配置对象。可springboot中所有配置文件信息
     * @return 对ui来说一个docket就是一个开发组
     */
    @Bean
    public Docket docket(Environment environment) {
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
                .paths(PathSelectors.any())
                .build().groupName("开发组");
    }

    /**
     * 配置基本信息
     *
     * @return api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Camunda工作流使用demo",
                "接口文档",
                "1.0",
                "http://127.0.0.1:9999/swagger-ui/index.html",
                new Contact("meethigher", "http://meethigher.top", "meethigher@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }
}
