package com.library.service;

import com.library.bean.User;
import com.library.bean.UserCharacter;
import com.library.bean.character;
import com.library.dao.CharacterDao;
import com.library.dao.UserCharacterDao;
import com.library.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProfileService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CharacterDao characterDao;
    @Autowired
    private UserCharacterDao userCharacterDao;

    public boolean updateUser(User user){
        return userDao.updateUser(user) > 0;
    }

    public boolean updatePassword(User user){
        return userDao.updatePassword(user) > 0;
    }

    public boolean updateCharacter(character cha){
        return characterDao.updateCharacter(cha) > 0;
    }

    public boolean addUser(User user){
        return userDao.addUser(user) > 0;
    }

    public boolean addCharacter(character cha){
        return characterDao.addCharacter(cha) > 0;
    }

    public boolean addUC(UserCharacter uc){
        return userCharacterDao.addUC(uc) > 0;
    }

    public ArrayList<User> selectAllUser(){return userDao.selectAllUser(); }

    public ArrayList<User> queryUser(String searchWord){
        return userDao.queryUser(searchWord);
    }

}
