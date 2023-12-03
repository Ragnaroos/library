package com.library.service;

import com.library.bean.User;
import com.library.bean.UserCharacter;
import com.library.bean.character;
import com.library.dao.UserDao;
import com.library.dao.CharacterDao;
import com.library.dao.UserCharacterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private CharacterDao characterDao;
    @Autowired
    private UserCharacterDao userCharacterDao;

    public User selectUserByUserName(String user_name, String password){
        return userDao.selectUserByUserName(user_name, password);
    }

    public User selectUserByUserName2(String user_name){
        return userDao.selectUserByUserName2(user_name);
    }

    public character selectCharacterById(int id){
        return characterDao.selectCharacterById(id);
    }

    public UserCharacter selectByUserId(int id) {
        return userCharacterDao.selectByUserId(id);
    }

    public User selectUserById(int id){
        return userDao.selectUserById(id);
    }





}
