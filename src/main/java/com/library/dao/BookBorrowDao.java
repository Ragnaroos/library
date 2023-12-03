package com.library.dao;

import com.library.bean.BookBorrow;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookBorrowDao {
    private final static String NAMESPACE = "com.library.dao.BookBorrowDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public BookBorrow selectBookBorrowById(int book_id){
        return sqlSessionTemplate.selectOne(NAMESPACE+"selectBookBorrowById", book_id);
    }

    public int deleteBookBorrowById(int book_id){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteBookBorrowById", book_id);
    }

    public int insertBookBorrow(BookBorrow bookBorrow){
        return sqlSessionTemplate.insert(NAMESPACE+"insertBookBorrow", bookBorrow);
    }

    public ArrayList<BookBorrow> selectAllBookBorrow(){
        List<BookBorrow> bookBorrows = sqlSessionTemplate.selectList(NAMESPACE+"selectAllBookBorrow");
        return (ArrayList<BookBorrow>) bookBorrows;
    }

    public int updateBookBorrow(BookBorrow bookBorrow){
        return sqlSessionTemplate.update(NAMESPACE+"updateBookBorrow", bookBorrow);
    }
}
