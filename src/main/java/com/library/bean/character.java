package com.library.bean;

import java.util.Date;

public class character {
    private int character_id;
    private String character_name;
    private Date created_time;
    private String character_state;
    private String comment;

    public int getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(int character_id) {
        this.character_id = character_id;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getCharacter_state() {
        return character_state;
    }

    public void setCharacter_state(String character_state) {
        this.character_state = character_state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
