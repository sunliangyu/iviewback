package com.qust.service;

import com.auth0.jwt.interfaces.Claim;
import com.qust.Jwt.JwtUtil;
import com.qust.dao.ClientDao;
import com.qust.entity.Client;
import com.qust.exception.ErrorCode;
import com.qust.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    @Autowired
    ClientDao clientDao;


    public Map<String,Object>  login (Map<String,Object> map) {
        String name = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Map<String,Object> result = new HashMap();
        ErrorCode errorCode = null;
        String token = null;
        List<Object[]> list = clientDao.getClient(name);
        if(list == null || list.size() == 0) {
            errorCode = ErrorCode.USER_NOT_FOUND;
        }else {
            Object[] object = list.get(0);
            if(BPwdEncoderUtils.matches(password,String.valueOf(object[0]))){
                token = JwtUtil.getToken(Long.valueOf(String.valueOf(object[1])),restaurant);
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

    public Map getUserInfo( Map<String,Object> map){
        String token = String.valueOf(map.get("token"));
        Map<String, Claim> arg = JwtUtil.parserToken(token);
        Claim value = arg.get("userid");
        Long userid = value.asLong();
        value =  arg.get("restaurant");
        Long restaurant = value.asLong();
        Client user = clientDao.findById(userid);
        if(user != null){
            Map<String , Object> result = new HashMap<>();
            result.put( "name",user.getUsername());
            result.put("user_id",user.getId());
            String[] array = user.getAccess().split(",");
            result.put("access",array);
            result.put("avatar", user.getAvatar());
            result.put("restaurant",restaurant);
            return result;
        }
        return null;
    }


    public Map<String,Object> getShopping (Map<String,Object> map) {
        return null;
    }

    public Map addorder (Map<String,Object> map) {

        return null;
    }


    public Map createUser (Client client) {
        int i  = clientDao.findByUsername(client.getUsername());
        Map<String,Object> result = new HashMap<>();
        if(i != 0) {
            result.put("status",false);
            result.put("reason","已经存在");
        }else{
            String entryPassword= BPwdEncoderUtils.BCryptPassword(client.getPassword());
            client.setPassword(entryPassword);
            client  = clientDao.save(client);
            result.put("id",client.getId());
        }
        return result;
    }
}
