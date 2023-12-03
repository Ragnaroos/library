package com.library.dao;

import com.library.bean.Book;
import com.library.bean.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDao {

    private final static String NAMESPACE = "com.library.dao.BookDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;


    public ArrayList<Book> selectAllBook(){
        List<Book> books = sqlSessionTemplate.selectList(NAMESPACE+"selectAllBook");
        return (ArrayList<Book>) books;
    }

    public int updateBook(Book book){
        return sqlSessionTemplate.update(NAMESPACE+"updateBook", book);
    }

    public Book selectBookById(int book_id){
        return sqlSessionTemplate.selectOne(NAMESPACE+"selectBookById", book_id);
    }

    public int deleteBookById(int book_id){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteBookById", book_id);
    }

    public ArrayList<Book> queryBook(final String searchWord) {
        String search = "%" + searchWord + "%";
        List<Book> result = sqlSessionTemplate.selectList(NAMESPACE + "queryBook", search);
        return (ArrayList<Book>) result;
    }

    public int insertBook(Book book){
        return sqlSessionTemplate.insert(NAMESPACE+"insertBook", book);
    }
}
