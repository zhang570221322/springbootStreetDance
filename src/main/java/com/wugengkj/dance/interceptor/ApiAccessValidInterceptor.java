package com.wugengkj.dance.interceptor;

import com.wugengkj.dance.common.enums.ErrorStatus;
import com.wugengkj.dance.common.exception.GlobalException;
import com.wugengkj.dance.service.IBusinessService;
import com.wugengkj.dance.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api访问验证拦截器
 *
 * @author leaf
 * <p>date: 2018-05-10 08:55</p>
 * <p>version: 1.0</p>
 */
@Slf4j
public class ApiAccessValidInterceptor implements HandlerInterceptor {

    @Lazy
    @Autowired
    private IBusinessService businessService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("-----开始进行API访问权限验证-----");
        String token = httpServletRequest.getParameter("token");
        String appid = httpServletRequest.getParameter("appid");

        String s = businessService.queryAppidById(1L);
        if (s.equals(appid) && AccessTokenUtil.valid(token)) {
            return true;
        }

        throw new GlobalException(ErrorStatus.API_ACCESS_VALID_ERROR);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
