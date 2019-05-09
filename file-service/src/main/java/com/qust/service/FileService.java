package com.qust.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {

    public Map fiileLoad (MultipartFile file){
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newFileName = String.valueOf(System.currentTimeMillis()) + suffix;
        String path = "E:/test/";
        File newFile = new File(path + newFileName);
        Map<String,String> result =new HashMap<>();
        try {
            file.transferTo(newFile);
            result.put("state","成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state","失败");
        }finally {
            return result;
        }
    }
    public void  deleteImage (Map map) {
    }
}
