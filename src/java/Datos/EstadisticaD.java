/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Logica.Fecha;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mauro
 */
public class EstadisticaD {
    
    private final Estructura es = new Estructura();
    private Statement st;

    public EstadisticaD() {
        
    }
    
    public void insertarVisita(String ip, String url, String browser, String so) throws SQLException, ClassNotFoundException{
        this.st = es.generarSt();
        String insert = "INSERT INTO historialvisitas(ip, url, browser, so, fecha) "
            + " VALUES ('" + ip + "', '" + url + "', '" + browser + "', '" + so + "', '" + new Fecha().getSQLDate() + "');";
        st.execute(insert);
        st.getConnection().close();
    }
    
    public ResultSet consultarVisitasRestaurante() throws SQLException, ClassNotFoundException{
        this.st = es.generarSt();
        String seleccion = "SELECT id, nickname, nombre, email, direccion, visitas" 
                + " FROM usuarios, (SELECT split_part(url, '?r=', 2) AS restaurante, count(id) AS visitas" 
                + "                 FROM historialvisitas"
                + "                 GROUP BY restaurante) AS historial" 
                + " WHERE usuarios.nickname = historial.restaurante" 
                + " ORDER BY visitas DESC"
                + " LIMIT 10;";
        ResultSet rs = st.executeQuery(seleccion);
        st.getConnection().close();
        return rs;
    }

    public ResultSet consultarVisitasPorURL() throws SQLException, ClassNotFoundException {
        this.st = es.generarSt();
        String seleccion = "SELECT id, substring(url from position('/' in url)) AS url1, count(id) AS visitas" 
                + " FROM historialvisitas" 
                + " GROUP BY url1"
                + " ORDER BY visitas DESC;";
        ResultSet rs = st.executeQuery(seleccion);
        st.getConnection().close();
        return rs;
    }
    
    public ResultSet consultarVisitasPorSO() throws SQLException, ClassNotFoundException{
        this.st = es.generarSt();
        String seleccion = "SELECT id,  so, count(id) AS visitas" 
                + " FROM historialvisitas" 
                + " GROUP BY so"
                + " ORDER BY visitas DESC;";
        ResultSet rs = st.executeQuery(seleccion);
        st.getConnection().close();
        return rs;
    }
    
    public ResultSet consultarVisitasPorBrowser() throws SQLException, ClassNotFoundException{
        this.st = es.generarSt();
        String seleccion = "SELECT id,  browser, count(id) AS visitas" 
                + " FROM historialvisitas" 
                + " GROUP BY browser"
                + " ORDER BY visitas DESC;";
        ResultSet rs = st.executeQuery(seleccion);
        st.getConnection().close();
        return rs;
    }
}