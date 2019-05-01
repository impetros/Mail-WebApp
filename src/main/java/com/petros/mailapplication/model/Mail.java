package com.petros.mailapplication.model;

import javax.persistence.*;

@Entity(name="mails")
@Table(name="mails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;
    @Column(name="subject",nullable = false)
    private String subject;
    @Column(name="from",nullable = false)
    private String from;
    @Column(name="text",nullable = false)
    private String text;

    public Mail() {
    }

    public Mail(String destination,String subject, String text) {
        this.from = destination;
        this.subject=subject;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
