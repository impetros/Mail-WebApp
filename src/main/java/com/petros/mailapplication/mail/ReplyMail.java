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

            int emailNo=-1;
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String from1 = message.getFrom()[0].toString();
                if (message.getSentDate().equals(date) && CheckingMails.getMessageContent(message).equals(text) && message.getSubject().equals(subject) &&
                        from1.substring(from1.indexOf("<") + 1, from1.indexOf(">")).equals(from)) {
                    emailNo=i;
                }
            }
            System.out.println(1);
            Message emailMessage = messages[emailNo];
            System.out.println(2);
            Message mimeMessage = new MimeMessage(emailSession);

            mimeMessage = (MimeMessage) emailMessage.reply(false);
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setText(replyMailForm.getText());
            mimeMessage.setSubject("RE: " + subject);
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    emailMessage.getFrom()[0]);
            System.out.println(4);
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

