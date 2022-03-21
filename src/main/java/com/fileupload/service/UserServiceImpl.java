package com.fileupload.service;

import com.fileupload.entity.User;
import com.fileupload.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public String getUserdetails(String emailId) {
        Optional<User> user = userRepository.findById(emailId);
        return user.map(User::getEmailId).orElse(null);

    }
}
