package com.tw.clubmanagement.annotation;

import com.tw.clubmanagement.entity.Role;
import com.tw.clubmanagement.entity.UserRole;
import com.tw.clubmanagement.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

@Component
public class AccessPermissionInterceptor implements HandlerInterceptor {
    private final SecurityService securityService;

    @Autowired
    public AccessPermissionInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AccessDeniedException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessPermission accessPermission = method.getAnnotation(AccessPermission.class);
        if (accessPermission != null || handlerMethod.getBeanType().getAnnotation(AccessPermission.class) != null) {
            Integer accessId = Integer.valueOf(request.getHeader("currentUserId"));
            String roleName = accessPermission.hasRole();
            Role role = securityService.findByName(roleName);
            UserRole userRole = securityService.findByUserIdAndRoleId(accessId, role.getId());
            if (null == userRole) {
                throw new AccessDeniedException("未授权");
            }
        }

            return true;
        }
    }

