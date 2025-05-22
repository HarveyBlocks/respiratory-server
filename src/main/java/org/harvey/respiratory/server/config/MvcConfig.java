package org.harvey.respiratory.server.config;

import org.harvey.respiratory.server.interceptor.AuthorizeInterceptor;
import org.harvey.respiratory.server.interceptor.ExpireInterceptor;
import org.harvey.respiratory.server.interceptor.LoginInterceptor;
import org.harvey.respiratory.server.properties.AuthProperties;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.util.JwtTool;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * MVC的配置类
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-03 14:12
 */
@Configuration
@EnableWebSocket
@EnableConfigurationProperties(AuthProperties.class)
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private AuthProperties authProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JwtTool jwtTool;
    @Resource
    private RoleService roleService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(
                new ExpireInterceptor(stringRedisTemplate, jwtTool, roleService));


        List<String> excludePaths = authProperties.getExcludePaths();
        if (excludePaths == null) {
            excludePaths = Collections.emptyList();
        }
        List<String> includePaths = authProperties.getIncludePaths();
        if (includePaths == null) {
            includePaths = Collections.emptyList();
        }

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(includePaths);

        registry.addInterceptor(new AuthorizeInterceptor())
                .excludePathPatterns(includePaths);
    }


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
