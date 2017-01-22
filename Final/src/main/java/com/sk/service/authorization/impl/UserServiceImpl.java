package com.sk.service.authorization.impl;

import com.sk.model.authorization.User;
import com.sk.service.authorization.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String login) {
        return new User(login, "7110eda4d09e062aa5e4a390b0a572ac0d2c0220");
    }

}
