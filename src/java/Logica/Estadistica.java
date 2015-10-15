 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.sql.ResultSet;
import java.sql.SQLException;
 
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
    
    public ResultSet getVisitas() throws SQLException, ClassNotFoundException{
        return EstaDatos.consultarVisitasPorURL();
    }
    
    public ResultSet getVisitasPorRestaurante() throws SQLException, ClassNotFoundException{
        return EstaDatos.consultarVisitasRestaurante();
    }
    
    public ResultSet getVisitasPorBrowser() throws SQLException, ClassNotFoundException{
        return EstaDatos.consultarVisitasPorBrowser();
    }
    
    public ResultSet getVisitasPorSO() throws SQLException, ClassNotFoundException{
        return EstaDatos.consultarVisitasPorSO();
    }
}