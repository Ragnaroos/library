package com.library.bean;

import java.util.Date;

public class BookCirculate {
    private int cir_id;
    private int book_id;
    private int user_id;
    private String cir_state;
    private Date cir_time;
    private Date return_time;
    private String cir_reason;
    private String comment;


    public int getCir_id() {
        return cir_id;
    }

    public void setCir_id(int cir_id) {
        this.cir_id = cir_id;
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

    public String getCir_state() {
        return cir_state;
    }

    public void setCir_state(String cir_state) {
        this.cir_state = cir_state;
    }

    public Date getCir_time() {
        return cir_time;
    }

    public void setCir_time(Date cir_time) {
        this.cir_time = cir_time;
    }

    public Date getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Date return_time) {
        this.return_time = return_time;
    }

    public String getCir_reason() {
        return cir_reason;
    }

    public void setCir_reason(String cir_reason) {
        this.cir_reason = cir_reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
