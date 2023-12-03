package com.library.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Book implements Serializable {
    private int book_id;
    private String book_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publish_time;
    private String writer;
    private String publish_house;
    private String book_class;
    private int pages;
    private BigDecimal price; // 用于精确的小数表示
    private String photo_path;
    private String book_state;
    private String belongto;

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public String getBook_state() {
        return book_state;
    }

    public void setBook_state(String book_state) {
        this.book_state = book_state;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublish_house() {
        return publish_house;
    }

    public void setPublish_house(String publish_house) {
        this.publish_house = publish_house;
    }

    public String getBook_class() {
        return book_class;
    }

    public void setBook_class(String book_class) {
        this.book_class = book_class;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
