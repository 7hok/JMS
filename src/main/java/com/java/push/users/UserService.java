package com.java.push.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepo;

    public User saveUser(User user){
        return userRepo.save(user);
    }
}