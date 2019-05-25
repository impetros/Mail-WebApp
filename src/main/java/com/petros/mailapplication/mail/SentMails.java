package com.petros.mailapplication.mail;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

public class SentMails
{


    //Constructor of the class.
    public static void read(String email,String password)
    {
        Folder sent;
        /*  Set the mail properties  */
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
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
                printAllMessages(messages);
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

    public static void printAllMessages(Message[] msgs) throws Exception
    {
        for (int i = msgs.length-1; i > 0; i--)
        {
            System.out.println("MESSAGE #" + (i + 1) + ":");
            printEnvelope(msgs[i]);
        }
    }

    /*  Print the envelope(FromAddress,ReceivedDate,Subject)  */
    public static void printEnvelope(Message message) throws Exception
    {
        Address[] a;
        // FROM
        if ((a = message.getFrom()) != null)
        {
            for (int j = 0; j < a.length; j++)
            {
                System.out.println("FROM: " + a[j].toString());
            }
        }
        // TO
        if ((a = message.getRecipients(Message.RecipientType.TO)) != null)
        {
            for (int j = 0; j < a.length; j++)
            {
                System.out.println("TO: " + a[j].toString());
            }
        }
        String subject = message.getSubject();
        Date receivedDate = message.getReceivedDate();
        String content = message.getContent().toString();
        System.out.println("Subject : " + subject);
        System.out.println("Received Date : " + receivedDate.toString());
        System.out.println("Content : " + content);
        getContent(message);
    }

    public static void getContent(Message msg)
    {
        try
        {
            String contentType = msg.getContentType();
            System.out.println("Content Type : " + contentType);
            Multipart mp = (Multipart) msg.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
            {
                readMessageToFile(mp.getBodyPart(i));
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception arise at get Content");
            ex.printStackTrace();
        }
    }

    public static void readMessageToFile(Part p) throws Exception
    {
        // Dump input stream ..
        InputStream is = p.getInputStream();
        // If "is" is not already buffered, wrap a BufferedInputStream
        // around it.
        if (!(is instanceof BufferedInputStream))
        {
            is = new BufferedInputStream(is);
        }
        int c;
        System.out.println("Message : ");
        while ((c = is.read()) != -1)
        {
            System.out.write(c);
        }
    }
}