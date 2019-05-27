package com.petros.mailapplication.mail;

import com.petros.mailapplication.forms.ComposeMail;
import com.petros.mailapplication.model.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class ForwardMail {
    public static void forward(String email, String password, String to, Mail mail, ComposeMail composeMail){
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
            System.out.println(emailNo);
            Message emailMessage = messages[emailNo];

            Message mimeMessage = new MimeMessage(emailSession);
            mimeMessage = (MimeMessage) emailMessage.reply(false);
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setSubject("Fwd: " + subject);
            mimeMessage.setText(composeMail.getText());
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

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

