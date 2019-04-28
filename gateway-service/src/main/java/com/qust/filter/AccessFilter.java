package com.qust.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.qust.Jwt.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;;

public class AccessFilter  extends ZuulFilter {

    public static String[]  excludeFilt = {"/system/system/login","/system/system/registry"};
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext() ;
        HttpServletRequest request = ctx.getRequest() ;
        String method = request.getMethod();
        if(method.equals("OPTIONS")){
            return false;
        }
        String url = request.getRequestURI();
        if(url != null ){
            for(String arg : excludeFilt ){
                if(arg.equals(url)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext() ;
        HttpServletRequest httpServletRequest = ctx.getRequest() ;
        HttpServletResponse httpServletResponse = ctx.getResponse();
        httpServletResponse.setCharacterEncoding("utf-8");
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    boolean result = false;
                    try {
                        result = JwtUtil.verify(token);
                    } catch (Exception e) {
                        result =false;
                    }
                    if(result){
                        ctx.setSendZuulResponse(true); //对请求进行路由
                        return  null;
                    }
                }
            }
        }

        ctx.setSendZuulResponse(false); //不对其进行路由
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("token is unvalid");
        return null;
    }
}
