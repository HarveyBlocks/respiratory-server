package org.harvey.respiratory.server.interceptor;


import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-02 11:21
 */
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (Constants.ROOT_AUTH_URI.contains(request.getRequestURI())){
            if (UserHolder.getUser().getRole() != Role.DATABASE_ADMINISTRATOR){
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }


}