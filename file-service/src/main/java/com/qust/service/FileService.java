package com.qust.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {
    private String start = "E:/test/";
    public Map fiileLoad (MultipartFile file){
        StringBuilder path = new StringBuilder();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String time = String.valueOf(System.currentTimeMillis());
        path.append(time).append(suffix);
        time = path.toString();
        path.insert(0,start);
        File newFile = new File(path.toString());
        Map<String,String> result =new HashMap<>();
        try {
            file.transferTo(newFile);
            result.put("url",time);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
    public void  deleteImage (Map map) {
        StringBuilder builder = new StringBuilder();
        builder.append(start);
        String url = String.valueOf(map.get("url"));
        builder.append(url);
        File newFile = new File(builder.toString());
        if(newFile.exists()) {
            newFile.delete();
        }
    }
}
