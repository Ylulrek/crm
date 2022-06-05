package com.Ylulrek.crm.settings.service;

import com.Ylulrek.crm.exception.LoginException;
import com.Ylulrek.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
