package com.qust.service;

import com.auth0.jwt.interfaces.Claim;
import com.qust.dao.UserDao;
import com.qust.Jwt.JwtUtil;
import com.qust.entity.User;
import com.qust.exception.ErrorCode;
import com.qust.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    UserDao userDao;


    public Map userLogin (User arg) {
        User user= userDao.findByUsername(arg.getUsername());
        Map<String,Object> result = new HashMap();
        ErrorCode errorCode = null;
        String token = null;
        if(user == null) {
             errorCode = ErrorCode.USER_NOT_FOUND;
        }else {
            if(BPwdEncoderUtils.matches(arg.getPassword(),user.getPassword())){
                token = JwtUtil.getToken(user.getId());
                if(token == null){
                    errorCode = ErrorCode.TOKEN_INVALID;
                }
            }else{
                errorCode = ErrorCode.USER_PASSWORD_ERROR;
            }
        }
        if(token == null) {
            result.put("message",errorCode.getMsg());
        }
        result.put("token",token);
        return result;
    }

    public User getUserInfo(long id){
        return userDao.findById(id);
    }


    public Map createUser (User arg){
        User user = userDao.findByUsername(arg.getUsername());
        Map<String,Object> result = new HashMap<>();
        if(user != null) {
            result.put("status",false);
            result.put("reason","已经存在");
        }else{
            user = userDao.save(arg);
            result.put("id",user.getId());
        }
        return result;
    }

    public Map getUserInfo(String token){
        Map<String, Claim> arg = JwtUtil.parserToken(token);
        Claim value = arg.get("userid");
        Long userid = value.asLong();
        User user = userDao.findById(userid);
        if(user != null){
            Map<String , Object> result = new HashMap<>();
            result.put( "name",user.getUsername());
            result.put("user_id",user.getId());
            String[] array = user.getAccess().split(",");
            result.put("access",array);
            result.put("avatar", user.getAvatar());
            result.put("restaurant",user.getRestaurantId());
            return result;
        }
        return null;
    }

}
