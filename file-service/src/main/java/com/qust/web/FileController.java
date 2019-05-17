package com.qust.web;


import com.qust.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;
    @PostMapping(value = "/image" )
    public Map<String,String> fiileLoad (@RequestParam MultipartFile file) {
       return fileService.fiileLoad(file);
    }

    @GetMapping(value = "/delete")
    public void deleteImage (@RequestParam Map map){
        fileService.deleteImage(map);
    }
}
