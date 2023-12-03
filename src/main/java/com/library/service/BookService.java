package com.library.service;

import com.library.bean.Book;
import com.library.bean.BookBorrow;
import com.library.bean.BookCirculate;
import com.library.dao.BookBorrowDao;
import com.library.dao.BookCirculateDao;
import com.library.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookBorrowDao bookBorrowDao;
    @Autowired
    private BookCirculateDao bookCirculateDao;

    public ArrayList<Book> selectAllBook(){
        return bookDao.selectAllBook();
    }

    public BookBorrow selectBookBorrowById(int book_id){
        return bookBorrowDao.selectBookBorrowById(book_id);
    }

    public boolean updateBook(Book book){
        return bookDao.updateBook(book)>0;
    }

    public Book selectBookById(int book_id){
        return bookDao.selectBookById(book_id);
    }

    public boolean deleteBookById(int book_id){
        return bookDao.deleteBookById(book_id)>0;
    }

    public boolean deleteBookBorrowById(int book_id){
        return bookBorrowDao.deleteBookBorrowById(book_id)>0;
    }

    public ArrayList<Book> queryBook(String searchWord){
        return bookDao.queryBook(searchWord);
    }

    public boolean insertBook(Book book){
        return bookDao.insertBook(book)>0;
    }

    public boolean insertBookBorrow(BookBorrow bookBorrow){
        return bookBorrowDao.insertBookBorrow(bookBorrow)>0;
    }

    public ArrayList<BookBorrow> selectAllBookBorrow(){
        return bookBorrowDao.selectAllBookBorrow();
    }

    public boolean updateBookBorrow(BookBorrow bookBorrow){
        return bookBorrowDao.updateBookBorrow(bookBorrow)>0;
    }

    public BookCirculate selectBookCirculateById(int id){
        return bookCirculateDao.selectBookCirculateById(id);
    }

    public boolean deleteBookCirculateById(int id){
        return bookCirculateDao.deleteBookCirculateById(id)>0;
    }

    public boolean insertBookCirculate(BookCirculate bookCirculate){
        return bookCirculateDao.insertBookCirculate(bookCirculate)>0;
    }

    public ArrayList<BookCirculate> selectAllBookCirculate(){
        return bookCirculateDao.selectAllBookCirculate();
    }

    public boolean updateBookCirculate(BookCirculate bookCirculate){
        return bookCirculateDao.updateBookCirculate(bookCirculate)>0;
    }
}
