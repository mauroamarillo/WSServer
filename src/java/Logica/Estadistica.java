/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
 
/**
 *
 * @author Mauro
 */
public class Estadistica {
    private final Datos.EstadisticaD EstaDatos;

    public Estadistica() {
        this.EstaDatos = new Datos.EstadisticaD();
    }
    public void registrarVisita(String ip, String url, String browser, String so) throws SQLException, ClassNotFoundException{
        EstaDatos.insertarVisita(ip, url, browser, so);
    }
    
    public HashMap getVisitas() throws SQLException, ClassNotFoundException{
        ResultSet datos = EstaDatos.consultarVisitasPorURL();
        HashMap resultado = new HashMap();
        while(datos.next()){
            resultado.put(datos.getString("restaurante"), datos.getInt("visitas"));
        }
        return resultado;
    }
    
    public HashMap getVisitasPorRestaurante() throws SQLException, ClassNotFoundException{
        ResultSet datos = EstaDatos.consultarVisitasRestaurante();
        HashMap resultado = new HashMap();
        while(datos.next()){
            resultado.put(datos.getString("restaurante"), datos.getInt("visitas"));
        }
        return resultado;
    }
    
    //GET VISITAS POR SO Y POR BROWSERRRRRRR
}