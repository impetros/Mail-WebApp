package com.petros.mailapplication.forms;
import java.io.File;

public class ComposeMail {
    private int nrOfEmails;
    private String emailsAddresses;
    private String subject;
    private String text;
    private String file;
    public int getNrOfEmails() {
        return nrOfEmails;
    }

    public void setNrOfEmails(int nrOfEmails) {
        this.nrOfEmails = nrOfEmails;
    }

    public String getEmailsAddresses() {
        return emailsAddresses;
    }

    public void setEmailsAddresses(String emailsAddresses) {
        this.emailsAddresses = emailsAddresses;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
