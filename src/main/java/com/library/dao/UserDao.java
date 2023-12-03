package com.library.dao;


import com.library.bean.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    private final static String NAMESPACE = "com.library.dao.UserDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public User selectUserByUserName(String user_name, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", user_name);
        map.put("password", password);
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectUserByUserName", map);
    }

    public ArrayList<User> queryUser(final String searchWord) {
        String search = "%" + searchWord + "%";
        List<User> result = sqlSessionTemplate.selectList(NAMESPACE + "queryUser", search);
        return (ArrayList<User>) result;
    }

    public ArrayList<User> selectAllUser(){
        List<User> list = sqlSessionTemplate.selectList(NAMESPACE + "selectAllUser");
        return (ArrayList<User>)list;
    }

    public User selectUserByUserName2(String user_name) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectUserByUserName2", user_name);
    }

    public int updateUser(User user){
        return sqlSessionTemplate.update(NAMESPACE + "updateUser", user);
    }

    public int updatePassword(User user){
        return sqlSessionTemplate.update(NAMESPACE + "updatePassword", user);
    }

    public int addUser(User user){
        return sqlSessionTemplate.insert(NAMESPACE +"addUser", user);
    }

    public User selectUserById(int id){
        return sqlSessionTemplate.selectOne(NAMESPACE+"selectUserById", id);
    }
}
