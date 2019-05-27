package com.petros.mailapplication.mail;

import com.petros.mailapplication.forms.ReplyMailForm;
import com.petros.mailapplication.model.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

public class ReplyMail {
//    public static void reply(String email, String password, Mail mail, ReplyMailForm replyMailForm) {
//        String from = mail.getOtherEmail();
//        String text = mail.getText();
//        String subject = mail.getSubject();
//        Date date = mail.getDate();
//        try {
//            Properties properties = new Properties();
//            properties.put("mail.store.protocol", "imaps");
//            Session emailSession = Session.getDefaultInstance(properties);
//            Store store = emailSession.getStore();
//            store.connect("pop.gmail.com", email, password);
//
//            Folder emailFolder = store.getFolder("INBOX");
//
//            emailFolder.open(Folder.READ_ONLY);
//
//            Message[] messages = emailFolder.getMessages();
//            System.out.println("messages.length---" + messages.length);
//            for (int i = 0, n = messages.length; i < n; i++) {
//                Message message = messages[i];
//                String from1 = message.getFrom()[0].toString();
//                if (message.getSentDate().equals(date) && CheckingMails.getMessageContent(message).equals(text) && message.getSubject().equals(subject) &&
//                        from1.substring(from1.indexOf("<") + 1, from1.indexOf(">")).equals(from)) {
//                    System.out.println(0);
//                    Message replyMessage = new MimeMessage(emailSession);
//                    replyMessage = (MimeMessage) message.reply(false);
//                    String to = InternetAddress.toString(message
//                            .getRecipients(Message.RecipientType.TO));
////                    String from2 = InternetAddress.toString(message.getFrom());
//                    System.out.println(from);
//                    System.out.println(to);
//                    replyMessage.setFrom(new InternetAddress(from));
//                    replyMessage.setText(replyMailForm.getText());
//                    replyMessage.setReplyTo(message.getReplyTo());
//                    System.out.println(1);
//                    // Send the message by authenticating the SMTP server
//                    // Create a Transport instance and call the sendMessage
//                    Transport t = emailSession.getTransport("smtp");
//                    try {
//                        System.out.println(2);
//                        t.connect(email, password);
//                        t.sendMessage(replyMessage,
//                                replyMessage.getAllRecipients());
//                        System.out.println(3);
//                    } finally {
//                        t.close();
//                    }
//                    System.out.println("message replied successfully ....");
//                    break;
//                }
//            }
//
//            emailFolder.close(false);
//            store.close();
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void reply(String email, String password, Mail mail, ReplyMailForm replyMailForm) {

        String from = mail.getOtherEmail();
        String text = mail.getText();
        String subject = mail.getSubject();
        Date date = mail.getDate();
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host", "smtp.gmail.com");
            properties.setProperty("mail.smtp.user",email);
            properties.setProperty("mail.smtp.password", password);
            properties.setProperty("mail.smtp.auth", "true");
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect("pop.gmail.com", email, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            System.out.println("Total Message - " + messages.length);

            int emailNo=0;
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String from1 = message.getFrom()[0].toString();
                if (message.getSentDate().equals(date) && CheckingMails.getMessageContent(message).equals(text) && message.getSubject().equals(subject) &&
                        from1.substring(from1.indexOf("<") + 1, from1.indexOf(">")).equals(from)) {
                    emailNo=i;
                }
            }
            Message emailMessage = folder.getMessage(emailNo);
            Message mimeMessage = new MimeMessage(emailSession);

            mimeMessage = (MimeMessage) emailMessage.reply(false);
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setText(replyMailForm.getText());
            mimeMessage.setSubject("RE: " + mimeMessage.getSubject());
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    emailMessage.getFrom()[0]);

            Transport t = emailSession.getTransport("smtp");

            t.connect(email, password);
            t.sendMessage(mimeMessage,
                    mimeMessage.getAllRecipients());

            System.out.println("Email message " +
                    "replied successfully.");

            folder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in replying email.");
        }
    }
}

