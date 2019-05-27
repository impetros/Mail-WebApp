package com.petros.mailapplication.forms;

public class ReplyMailForm {

    private long id;
    private String to;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
