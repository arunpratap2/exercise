package com.synchrony.service;

import com.synchrony.exception.UserException;
import com.synchrony.model.User;
import com.synchrony.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getAllUsers(){
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User createUser(final User user){
         final String userName= user.getUserName();
         final String password = user.getPassword();
        if(StringUtils.isBlank(userName)){
            throw new UserException("User name can not be blank");
        }
        if(StringUtils.isBlank(password)){
            throw new UserException("Password can not be blank");
        }
        final User userDetail = userRepository.save(new User(user.getFirstName(), user.getLastName(), userName,password));

        return userDetail;
    }

}
