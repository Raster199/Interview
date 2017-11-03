package ru.interview.web;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class SenderMail {

    private String user;
    private String password;
    private Properties props;
    private String textInMail;

    String host = "10.42.42.79";
    String port = "25";


    public SenderMail(String username, String password) {
        this.user = username;
        this.password = password;

        props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
    }

    public void send (String subject, String text, String toEmail,String fromEmail){
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            //От кого
            message.setFrom(new InternetAddress(fromEmail));
            //Кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //Заголовок письма
            message.setSubject(subject);
            //Содержимое
            message.setText(text);

            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String textInMail(){
        UrlParser up = new UrlParser();
        QueryToBase qb = new QueryToBase(up.getConnect());
        UUID key = UUID.randomUUID();
        textInMail = "В рамках работы по развитию Системы Электронного Документооборота Корпорации(СЭД) требуется Ваше мнение о качестве работы СЭД.\n" +              "\n" +
                "Предлагаем Вам принять участие в опросе, перейдя по ссылке ниже:\n https://localhost/Interview/questionary?key=" + key;
        qb.insertKey(key + "");
        return textInMail;
    }

    /*public static void main(String[] args) {

        SenderMail mail = new SenderMail("babayka@balalayka.ru", null );

        //String [] list = {"a.beliaev@rtinform.ru","a.stanishevskii@rtinform.ru","i.dybtsyn@rtinform.ru"};
        String [] list = {"i.dybtsyn@rtinform.ru","i.dybtsyn@rtinform.ru"};
        for (String email : list) {
            mail.send("Test mail", mail.textInMail(),  email, "i.dybtsyn@rtinform.ru" );
        }

        //mail.send("Test mail","Test mail",  "a.koltunova@rtinform.ru","babayka@balalayka.ru" );

    }*/
}
