package com.petros.mailapplication.model;

import org.springframework.format.datetime.standard.DateTimeFormatterFactory;

import javax.persistence.*;

@Entity
@Table(name="mails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
//    @JoinColumn(name="fk_id")
    private User user;

    private String destination;
    private String text;

    public Mail() {
    }

    public Mail(String destination, String text) {
        this.destination = destination;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
