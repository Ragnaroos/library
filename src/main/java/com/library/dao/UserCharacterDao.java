package com.library.dao;


import com.library.bean.UserCharacter;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserCharacterDao {
    private final static String NAMESPACE = "com.library.dao.UserCharacterDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public UserCharacter selectByUserId(int id) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByUserId", id);
    }

    public int addUC(UserCharacter uc) {

        return sqlSessionTemplate.insert(NAMESPACE + "addUC", uc);
    }
}
