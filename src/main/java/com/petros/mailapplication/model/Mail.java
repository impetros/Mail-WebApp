package com.petros.mailapplication.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="mails")
@Table(name="mails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;
    @Column(name="subject",nullable = false)
    private String subject;
    @Column(name="fromMail",nullable = false)
    private String fromMail;
    @Column(name="text",nullable = false,length = 500000)
    private String text;
    @Column(name="date",nullable = false)
    private Date date;

    public Mail() {
    }

    public Mail(String fromMail,String subject, String text,Date date) {
        this.fromMail = fromMail;
        this.subject=subject;
        this.text = text;
        this.date=date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", fromMail=" + fromMail  +
                ", subject=" + subject  +
                ", text=" + text +
                ", date=" + date +
                '}'+"\n";
    }

}
