package com.java.push.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/save")
    public String create(User user){
        userService.saveUser(user);
        return "done";
    }
}