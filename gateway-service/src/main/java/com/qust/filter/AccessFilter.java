package com.qust.filter;

import com.auth0.jwt.interfaces.Claim;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.qust.Jwt.JwtUtil;
import com.qust.util.HttpBodyAnaly;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;;import java.util.Map;

public class AccessFilter  extends ZuulFilter {

    public static String[]  excludeFilt = {"/system/system/login","/system/system/registry"};
    public static String[]  noRestaurant = {"/system/system/getUserInfo"};
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
        String restaurant ;
        if(httpServletRequest.getMethod().equals("GET")){
            restaurant = httpServletRequest.getParameter("restaurant");
        }else{
            restaurant  = HttpBodyAnaly.getValue("restaurant",httpServletRequest);
        }
        if(restaurant != null){
            Cookie[] cookies =  httpServletRequest.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("token")){
                        String token = cookie.getValue();
                        Map result ;
                        try {
                            result = JwtUtil.verify(token);
                        } catch (Exception e) {
                            ctx.setSendZuulResponse(false); //不对其进行路由
                            ctx.setResponseStatusCode(401);
                            ctx.setResponseBody("token is unvalid");
                            return null;
                        }
                        Claim arg = (Claim)result.get("restaurant");
                        if(arg != null){
                            Long b = arg.asLong();
                            if(String.valueOf(b).equals(restaurant)){
                                ctx.setSendZuulResponse(true); //对请求进行路由
                                return  null;
                            }
                        }
                    }
                }
            }
        }else {
            String url = httpServletRequest.getRequestURI();
            for(String arg : noRestaurant ){
                if(arg.equals(url)){
                    ctx.setSendZuulResponse(true); //对请求进行路由
                    return  null;
                }
            }
        }
        ctx.setSendZuulResponse(false); //不对其进行路由
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("token is unvalid");
        return null;
    }


}
