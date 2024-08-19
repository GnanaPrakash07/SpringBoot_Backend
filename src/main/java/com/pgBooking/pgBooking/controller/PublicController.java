package com.pgBooking.pgBooking.controller;

import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
   public String health(){
       return "ok";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
