package com.petros.mailapplication.forms;

public class ComposeMail {
    private String toEmailAdress;
    private String subject;
    private String text;

    public String getToEmailAdress() {
        return toEmailAdress;
    }

    public void setToEmailAdress(String toEmailAdress) {
        this.toEmailAdress = toEmailAdress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
