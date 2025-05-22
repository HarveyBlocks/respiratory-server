package org.harvey.respiratory.server.config;


import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.interceptor.ExpireInterceptor;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.service.RoleService;
import org.harvey.respiratory.server.util.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 获取HttpSession的配置类
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-5-30 15:11
 */
@Configuration
@Slf4j
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    public static final String SESSION_USER_KEY = "user";
    private StringRedisTemplate stringRedisTemplate;
    private JwtTool jwtTool;
    private RoleService roleService;

    @Autowired
    public void setJwtTool(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 握手
     *
     * @param sec      配置对象
     * @param request  握手请求对象
     * @param response 握手响应对象
     */
    @Override
    public void modifyHandshake(
            ServerEndpointConfig sec,
            HandshakeRequest request,
            HandshakeResponse response) {
        // 获取HttpSession
        String token = request.getHeaders().get(Constants.AUTHORIZATION_HEADER).get(0);

        if (token == null || token.isEmpty()) {
            return;
        }
        String id;
        try {
            id = jwtTool.parseToken(token).toString();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return;
        }
        UserDto userDto = new ExpireInterceptor(stringRedisTemplate, jwtTool, roleService).doPreHandle(id);
        if (userDto == null || userDto.getId() == null) {
            return;
        }
        sec.getUserProperties().put(SESSION_USER_KEY, userDto);
    }
}