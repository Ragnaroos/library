package com.library.bean;

import java.util.Date;

public class BookBorrow {
    private int bookbrw_id;
    private int book_id;
    private int user_id;
    private String  isbrwed;
    private Date brw_time;
    private Date return_time;
    private String brw_reason;
    private String comment;


    public int getBookbrw_id() {
        return bookbrw_id;
    }

    public void setBookbrw_id(int bookbrw_id) {
        this.bookbrw_id = bookbrw_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIsbrwed() {
        return isbrwed;
    }

    public void setIsbrwed(String isbrwed) {
        this.isbrwed = isbrwed;
    }

    public Date getBrw_time() {
        return brw_time;
    }

    public void setBrw_time(Date brw_time) {
        this.brw_time = brw_time;
    }

    public Date getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Date return_time) {
        this.return_time = return_time;
    }

    public String getBrw_reason() {
        return brw_reason;
    }

    public void setBrw_reason(String brw_reason) {
        this.brw_reason = brw_reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
