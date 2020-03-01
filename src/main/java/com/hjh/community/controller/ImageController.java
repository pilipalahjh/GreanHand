package com.hjh.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author hujiaheng
 * @date 2020/3/1 16:10
 */

@Slf4j
@Controller
public class ImageController {
    @GetMapping("/image")
    public String getImage(){
        return "/image";
    }

    @PostMapping("/image")
    public String postImage(@RequestParam MultipartFile file){
        String fileName = file.getOriginalFilename();
        String localPath = "E:/image/";
        File localFile = new File(localPath,fileName);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "/image";
    }
}
