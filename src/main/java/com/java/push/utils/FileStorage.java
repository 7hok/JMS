package com.java.push.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;


public class FileStorage {

    private static String serverPath = "/home/";
    private static String imagePath = "src/main/resources/static/images"; 
    private static String filePath = "src/main/resources/static/files"; 
    
    public static String uploadImage(MultipartFile files) throws Exception {
        String fileName = UUID.randomUUID() + "." + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".") +  1);
        Files.copy(files.getInputStream(), Paths.get(imagePath,fileName ));
       //Files.copy(files.getInputStream(), Paths.get(fileName));
        return fileName;
    }
    public static String uploadFile(MultipartFile files) throws Exception {
        String fileName = UUID.randomUUID() + "." + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".") +  1);
        Files.copy(files.getInputStream(), Paths.get(filePath,fileName));
        return fileName;
    }
}