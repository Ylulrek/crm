package com.Ylulrek.crm.settings.service.impl;

import com.Ylulrek.crm.settings.dao.UserDao;
import com.Ylulrek.crm.settings.service.UserService;
import com.Ylulrek.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
