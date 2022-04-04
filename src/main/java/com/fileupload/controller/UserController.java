package com.fileupload.controller;

import com.fileupload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public String getUser(@RequestParam  String emailId) {
        return userService.getUserdetails(emailId) != null ? "User exists with emailId::"+ emailId : "User does not exists";
    }
}
