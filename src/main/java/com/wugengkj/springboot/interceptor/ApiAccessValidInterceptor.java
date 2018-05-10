package com.wugengkj.springboot.interceptor;

import com.wugengkj.springboot.common.enums.ErrorStatus;
import com.wugengkj.springboot.common.exception.GlobalException;
import com.wugengkj.springboot.service.IBusinessService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
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
        log.info("-----处理请求完成后视图渲染之前的处理操作-----");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("-----视图渲染之后的操作-----");
    }
}
