package com.library.dao;

import com.library.bean.BookCirculate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookCirculateDao {
    private final static String NAMESPACE = "com.library.dao.BookCirculateDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public BookCirculate selectBookCirculateById(int id){
        return sqlSessionTemplate.selectOne(NAMESPACE+"selectBookCirculateById", id);
    }

    public int deleteBookCirculateById(int id){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteBookCirculateById", id);
    }

    public int insertBookCirculate(BookCirculate bookCirculate){
        return sqlSessionTemplate.insert(NAMESPACE+"insertBookCirculate", bookCirculate);
    }

    public ArrayList<BookCirculate> selectAllBookCirculate(){
        List<BookCirculate> bookCirculates = sqlSessionTemplate.selectList(NAMESPACE+"selectAllBookCirculate");
        return (ArrayList<BookCirculate>) bookCirculates;
    }

    public int updateBookCirculate(BookCirculate bookCirculate){
        return sqlSessionTemplate.update(NAMESPACE+"updateBookCirculate", bookCirculate);
    }
}
