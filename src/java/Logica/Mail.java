/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.MessagingException;
import java.util.*;

/**
 *
 * @author Mauro
 */
public class Mail {

    public Mail() {
    }
    
        public void sendMail(String para, String asunto, String mensaje){
            System.out.println(para);
            System.out.println(asunto);
            System.out.println(mensaje);
            Properties properties = System.getProperties();
            
            properties.setProperty("mail.smtp.host", "localhost");
            
            Session session = Session.getDefaultInstance(properties);
            
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("no_contestar@QuickOrder.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
                message.setSubject(asunto);
                message.setContent(mensaje, "text/html; charset=utf-8");
                Transport.send(message);
                System.out.println("yay");
            }catch (MessagingException mex) {
                System.out.println(mex.getMessage());
            }
    }
}