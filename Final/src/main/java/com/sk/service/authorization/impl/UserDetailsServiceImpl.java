package com.sk.service.authorization.impl;

import com.sk.model.authorization.User;
import com.sk.model.authorization.UserRoleEnum;
import com.sk.service.authorization.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUser("admin");

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                roles);
    }

}
