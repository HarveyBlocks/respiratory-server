package org.harvey.respiratory.server.interceptor;


import lombok.NonNull;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权拦截器
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-02 11:21
 */
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {
        if (Constants.ROOT_AUTH_URI.contains(request.getRequestURI())) {
            String identityCardId = UserHolder.getUser().getIdentityCardId();
            if (identityCardId == null || identityCardId.isEmpty()) {
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }


}