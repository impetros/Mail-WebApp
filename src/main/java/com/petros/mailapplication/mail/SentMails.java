package com.petros.mailapplication.mail;
import com.petros.mailapplication.model.Mail;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

public class SentMails
{
    public static List<Mail> read(String email, String password,long id)
    {
        Folder sent;
        /*  Set the mail properties  */
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        List<Mail> mails=new ArrayList<>();
        try
        {
            /*  Create the session and get the store for read the mail. */
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com",email, password);

            /*  Mention the folder name which you want to read. */
            sent = store.getFolder("[Gmail]/Mesaje trimise");
            System.out.println("No of Sent Messages : " +sent.getMessageCount());

            /*Open the inbox using store.*/
            sent.open(Folder.READ_ONLY);

            /*  Get the messages which is unread in the Sent Mails*/
            Message messages[] = sent.search(new FlagTerm(new Flags(Flag.SEEN), true));

            /* Use a suitable FetchProfile    */
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            sent.fetch(messages, fp);

            try
            {
//                printAllMessages(messages);
                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    Address[] a;
                    // FROM
                    String otherMail="";
                    if ((a = message.getRecipients(Message.RecipientType.TO)) != null)
                    {
                        for (int j = 0; j < a.length; j++)
                        {
                            otherMail+=a[j].toString();
                        }
                    }
                    mails.add(new Mail(id,otherMail,message.getSubject(),CheckingMails.getMessageContent(message),message.getReceivedDate(),2));
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
        return mails;
    }

}