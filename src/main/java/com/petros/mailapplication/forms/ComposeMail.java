package com.petros.mailapplication.forms;

public class ComposeMail {
    private int nrOfEmails;
    private String emailsAddresses;
    private String subject;
    private String text;

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
}
