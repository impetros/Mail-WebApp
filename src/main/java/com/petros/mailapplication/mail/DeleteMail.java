package com.petros.mailapplication.mail;

import com.petros.mailapplication.model.Mail;

import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

public class DeleteMail
{
    public static void deleteSentMail(String email, String password, long id,Mail mail)
    {
        Folder sent;
        /*  Set the mail properties  */
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        String from=mail.getOtherEmail();
        String text=mail.getText();
        String subject=mail.getSubject();
        Date date=mail.getDate();
        try
        {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com",email, password);

            /*  Mention the folder name which you want to read. */
            sent = store.getFolder("[Gmail]/Mesaje trimise");


            /*Open the inbox using store.*/
            sent.open(Folder.READ_WRITE);

            /*  Get the messages which is unread in the Sent Mails*/
            Message messages[] = sent.search(new FlagTerm(new Flags(Flag.SEEN), true));

            /* Use a suitable FetchProfile    */
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            sent.fetch(messages, fp);

            try
            {
                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    Address[] a;
                    String otherMail="";
                    if ((a = message.getRecipients(Message.RecipientType.TO)) != null)
                    {
                        for (int j = 0; j < a.length; j++)
                        {
                            otherMail+=a[j].toString();
                        }
                    }
                    if(otherMail.equals(from)&& message.getSubject().equals(subject)&&CheckingMails.getMessageContent(message).equals(text)&& message.getReceivedDate().equals(date)){
                        message.setFlag(Flags.Flag.DELETED, true);
                        break;
                    }
                }
                sent.close(true);
                store.close();
            }
            catch (Exception ex)
            {
                System.out.println("Exception arise at the time of read mail");
                ex.printStackTrace();
            }
        }
        catch (NoSuchProviderException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            System.exit(2);
        }
    }

    public static void deleteInboxMail(String email, String password, long id,Mail mail) {
        String from = mail.getOtherEmail();
        String text = mail.getText();
        String subject = mail.getSubject();
        Date date = mail.getDate();
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect("pop.gmail.com", email, password);

            Folder emailFolder = store.getFolder("INBOX");

            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String from1 = message.getFrom()[0].toString();
                if (message.getSentDate().equals(date) && CheckingMails.getMessageContent(message).equals(text) && message.getSubject().equals(subject) &&
                        from1.substring(from1.indexOf("<") + 1, from1.indexOf(">")).equals(from)) {
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("\n\n\n\n\nMerge\n\n\n\n");
                    break;
                }
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}

