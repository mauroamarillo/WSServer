/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author usuario
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuracion {

    private Connection conexion;

    private final static String CARPETA = System.getProperty("user.home") + "\\QuickOrder\\";
    private final static String ARCHIVO = "QuickOrderWSDATA.properties";
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
            bw.write("host=localhost\n");
            bw.write("puerto=5432\n");
            bw.write("bd=quickorderDB\n");
            bw.write("usuario=postgres\n");
            bw.write("pass=1234\n");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void conectarBD() throws ClassNotFoundException, SQLException {
        if (!existe()) {
            configurar();
        }

        try {
            Properties propiedades = new Properties();
            propiedades.load(new FileInputStream(RUTA));
          
            String host= propiedades.getProperty("host");
            String puerto= propiedades.getProperty("puerto");
            String bd= propiedades.getProperty("bd");
            String usuario= propiedades.getProperty("usuario");
            String pass= propiedades.getProperty("pass");
            
            String url = "jdbc:postgresql://"+host+":"+puerto+"/"+bd;
            
            if (conexion != null) {
                return;
            }
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(url, usuario, pass);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectarBD() throws SQLException {
        conexion.close();
    }

}
