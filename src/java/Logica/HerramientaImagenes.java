/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import WebServices.WSQuickOrder;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Jean
 */
public abstract class HerramientaImagenes {

    public static void convertirAlChivo(String path, String base64) {
        String imagenBase64 = base64;
        String header = imagenBase64.split(",")[0];//"data:image/jpg;base64,";
        String encodedImage = imagenBase64.substring(header.length() + 1);
        File destino = new File(path);
        byte dearr[] = Base64.decodeBase64(encodedImage);
        try {
            FileOutputStream fos = new FileOutputStream(destino);
            fos.write(dearr);
            fos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ImageIcon obtenerImagenEscalada(String path, int w, int h) {
        Image img = new ImageIcon(path).getImage();
        Image newImg;
        newImg = img.getScaledInstance(w, h, java.awt.Image.SCALE_AREA_AVERAGING);
        return new ImageIcon(newImg);
    }

    public static ImageIcon escalarIcono(ImageIcon ico, int w, int h) {
        Image img = ico.getImage();
        Image newImg;
        newImg = img.getScaledInstance(w, h, java.awt.Image.SCALE_FAST);
        return new ImageIcon(newImg);
    }

    /**
     *
     * @param path
     * @return
     */
    public static ImageIcon cargarImagen(String path) {
        try {
            /*  if (path.equals("sin_imagen")) {
            return new ImageIcon("src/img/sin_img.jpg");
            }
            try {
            URL intlLogoURL = new URL(path);
            InputStream in = intlLogoURL.openStream();
            return new ImageIcon(ImageIO.read(in));
            } catch (Exception e) {
            try {
            InputStream in = new FileInputStream(new File(path)); // こころ
            return new ImageIcon(ImageIO.read(in));
            } catch (Exception ee) {
            return new ImageIcon("src/img/NoImgDisp.png");
            }
            }*/
            byte[] btDataFile = Base64.decodeBase64(path);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
            return new ImageIcon(image);
        } catch (IOException ex) {
            Logger.getLogger(HerramientaImagenes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ImageIcon cargarYescalar(String path, int w, int h) {
        return escalarIcono(cargarImagen(path), w, h);
    }
}
