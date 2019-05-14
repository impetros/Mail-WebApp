package com.petros.mailapplication.mail;

import com.petros.mailapplication.model.Mail;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

public class CheckingMails {

    public static List<Mail> check(String host, String storeType, String user,
                             String password,long id) {
        List<Mail> mails=new ArrayList<>();
        try {

            // create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");

            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    user, password);
                        }
                    });
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect();

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String from=message.getFrom()[0].toString();
//                String messgg = getText(message);
//                mails.add(new Mail(id,from.substring(from.indexOf("<")+1,from.indexOf(">")),message.getSubject(),message.getContent().toString(),message.getSentDate()));
                mails.add(new Mail(id,from.substring(from.indexOf("<")+1,from.indexOf(">")),message.getSubject(),getMessageContent(message),message.getSentDate()));
            }
            Collections.sort(mails, new Comparator<Mail>() {
                @Override
                public int compare(Mail o1, Mail o2) {
                    if (o2.getDate().before(o1.getDate())) {
                        return -1;
                    } else if (o2.getDate().after(o1.getDate())) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mails;
    }

    public static String getMessageContent(Message message) throws MessagingException {
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                StringBuffer messageContent = new StringBuffer();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }
            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String getText(Message msg){
        try {
            String contentType = msg.getContentType();
            String messageContent = "";

            if (contentType.contains("multipart")) {
                Multipart multiPart = (Multipart) msg.getContent();
                int numberOfParts = multiPart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    messageContent = part.getContent().toString();
                }
            } else if (contentType.contains("text/plain")
                    || contentType.contains("text/html")) {
                Object content = msg.getContent();
                if (content != null) {
                    messageContent = content.toString();
                }
            }
            System.out.println(" Message: " + messageContent);
            return messageContent;
        }catch(IOException | MessagingException e){
            e.printStackTrace();
        }
        return null;
    }

}

