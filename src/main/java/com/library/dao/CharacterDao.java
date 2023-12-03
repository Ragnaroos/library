package com.library.dao;



import com.library.bean.character;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class CharacterDao {
    private final static String NAMESPACE = "com.library.dao.CharacterDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public character selectCharacterById(int id) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectCharacterById", id);
    }

    public int addCharacter(character cha){
        return sqlSessionTemplate.insert(NAMESPACE + "addCharacter", cha);
    }

    public int updateCharacter(character cha){return sqlSessionTemplate.update(NAMESPACE+"updateCharacter", cha);}

}
