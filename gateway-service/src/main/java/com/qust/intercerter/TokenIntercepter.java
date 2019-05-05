//package com.qust.intercerter;
//
//import com.qust.Jwt.JwtUtil;
//import com.qust.exception.CommonException;
//import com.qust.exception.ErrorCode;
//import org.json.simple.JSONObject;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TokenIntercepter implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        httpServletResponse.setCharacterEncoding("utf-8");
//
//        Cookie[] cookies =  httpServletRequest.getCookies();
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if(cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    boolean result = JwtUtil.verify(token);
//                    if(result){
//                        return  true;
//                    }
//                }
//            }
//        }
//        String token = httpServletRequest.getHeader("token");
//        if(token != null){
//            boolean result = JwtUtil.verify(token);
//            if(result){
//            return  true;
//            }
//        }
//        Map<String,String> response = new HashMap<>(2);
//        CommonException  commonException  = new CommonException(ErrorCode.TOKEN_INVALID);
//        response.put("status",String.valueOf(commonException.getCode()));
//        response.put("message",commonException.getMsg());
//        httpServletResponse.getWriter().write(JSONObject.toJSONString(response));
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
