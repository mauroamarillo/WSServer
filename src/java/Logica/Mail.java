/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.Configuracion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Mauro
 */
public class Mail {

    private final static String CARPETA = System.getProperty("user.home") + "\\QuickOrder\\";
    private final static String ARCHIVO = "QuickOrderWSEMAIL.properties";
    private final static String RUTA = CARPETA + ARCHIVO;

    private static boolean existe() {
        return (new java.io.File(RUTA)).exists();
    }

    private static void configurar() {
        try {
            File archivo = new File(RUTA);
            new File(CARPETA).mkdirs();
            archivo.createNewFile();
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("email=mauro.aamarillo@gmail.com\n");
            bw.write("pass=iwanna666\n");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mail() {
    }

    public void sendMail(String para, String asunto, String mensaje) {
        if (!existe()) {
            configurar();
        }

        try {
            Properties propiedades = new Properties();
            propiedades.load(new FileInputStream(RUTA));

            String email = propiedades.getProperty("email");
            String pass = propiedades.getProperty("pass");

            Properties mailServerProperties;
            Session getMailSession;
            MimeMessage generateMailMessage;

            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            generateMailMessage.setSubject(asunto);
            generateMailMessage.setContent(mensaje, "text/html");

            Transport transport = getMailSession.getTransport("smtp");

            transport.connect("smtp.gmail.com", email, pass);
            // transport.connect("smtp.gmail.com", "mauro.aamarillo@gmail.com", "iwanna666");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
