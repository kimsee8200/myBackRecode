package com.fastcampus.ch4.service;

import com.fastcampus.ch4.dao.UserDao;
import com.fastcampus.ch4.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    public int getCount () throws Exception{
        return userDao.count();
    }

    public User getUser (String id) throws Exception{
        return userDao.selectUser(id);
    }

    public int insert(User user) throws Exception{
        return userDao.insertUser(user);
    }

    public int update(User user) throws Exception{
        return userDao.updateUser(user);
    }

    public int deleteUser(String id) throws Exception{
        return userDao.deleteUser(id);
    }

    public int deleteAll() throws  Exception{
        return userDao.deleteAll();
    }
}
