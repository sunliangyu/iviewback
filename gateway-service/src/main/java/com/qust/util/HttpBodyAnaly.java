package com.qust.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpBodyAnaly {

    public static String  getValue(String key, HttpServletRequest request) {

        StringBuilder wholeStr = new StringBuilder();
        String str;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while((str = reader.readLine()) != null){
                wholeStr.append(str);
            }
            int index = wholeStr.indexOf(key);
            if(index >= 0 ){
                int start = index + key.length() + 2;
                int end = wholeStr.indexOf(",",start );
                if(end < 0 ){
                    end = wholeStr.length()-1;
                }
                String value =  wholeStr.substring(start,end);
                return value;
            }
            return null;
        }catch (Exception E){
            return  null;
        }
    }

}
