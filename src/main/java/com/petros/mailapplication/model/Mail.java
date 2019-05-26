package com.petros.mailapplication.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="mails")
@Table(name="mails")
public class Mail implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;
    @Column(name="subject",nullable = false)
    private String subject;
    @Column(name="otherEmail",nullable = false)
    private String otherEmail;
    @Column(name="text",nullable = false,length = 500000)
    private String text;
    @Column(name="tip",nullable = false)
    private int tip;
    @Column(name="date",nullable = false)
    private Date date;

    public Mail() {
    }

    public Mail(long id,String fromMail,String subject, String text,Date date,int tip) {
        User user = new User();
        user.setId(id);
        this.user=user;
        this.otherEmail = fromMail;
        this.subject=subject;
        this.text = text;
        this.date=date;
        this.tip=tip;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOtherEmail() {
        return otherEmail;
    }

    public void setOtherEmail(String otherEmail) {
        this.otherEmail = otherEmail;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }


    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", otherEmail=" + otherEmail +
                ", subject=" + subject  +
                ", text=" + text +
                ", date=" + date +
                ", tip=" + tip +
                '}'+"\n";
    }

    @Override
    public int compareTo(Object o) {
        Mail o2=(Mail)o;
        if (o2.getDate().before(this.getDate())) {
            return -1;
        } else if (o2.getDate().after(this.getDate())) {
            return 1;
        } else {
            return 0;
        }
    }

}
