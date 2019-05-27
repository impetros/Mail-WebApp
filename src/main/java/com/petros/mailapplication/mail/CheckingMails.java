package com.petros.mailapplication.mail;

import com.petros.mailapplication.model.Mail;
import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

public class CheckingMails {

    public static List<Mail>  check(String host, String storeType, String user,
                                  String password,long id) {
        List<Mail> mails = new ArrayList<>();
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            // use READ_ONLY if you don't wish the messages
            // to be marked as read after retrieving its content
            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String from = message.getFrom()[0].toString();
                mails.add(new Mail(id, from.substring(from.indexOf("<") + 1, from.indexOf(">")), message.getSubject(), getMessageContent(message), message.getSentDate(), 1));
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mails;
    }


    public static String getMessageContent(Message message) throws MessagingException {
        try {
            Object content=message.getContent();
            if (content instanceof Multipart) {
                StringBuffer messageContent = new StringBuffer();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")||part.isMimeType("TEXT/PLAIN")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }
            return content.toString();

        } catch (IOException e) {
            System.out.println("E grav ba");
            e.printStackTrace();
        }
        return "";
    }


}
