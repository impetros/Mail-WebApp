package com.petros.mailapplication.mail;

import java.util.Properties;

import javax.mail.*;

public class Authentication {
    public static boolean succesAuthentication(String username, String password) {
        try {

            // create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3s.host", "pop.gmail.com");
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");

            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    username, password);
                        }
                    });
            Store store = emailSession.getStore("pop3s");

            store.connect();
            store.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
