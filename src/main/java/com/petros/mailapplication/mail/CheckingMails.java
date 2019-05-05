package com.petros.mailapplication.mail;

import com.petros.mailapplication.model.Mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

public class CheckingMails {

    public static List<Mail> check(String host, String storeType, String user,
                             String password) {
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

                mails.add(new Mail(from.substring(from.indexOf("<")+1,from.indexOf(">")),message.getSubject(),getText(message)));//,message.getContent().toString()));//
            }

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



