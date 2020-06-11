package com.java.push.apps;

import com.java.push.utils.FileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/apps")
public class AppController {
    
    @Autowired
    AppService appService;

    @ResponseBody
    @PostMapping("/save")
    public Object create(String name){
        Application app = new Application();
        app.setName(name);
        return appService.save(app);
        //  "redirect:/form/users";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws Exception{
        // appService.save(app);
        return FileStorage.uploadFile(file);
    }
}