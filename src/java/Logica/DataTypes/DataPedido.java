/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.DataTypes;

import Logica.Estado;
import Logica.Fecha;
import Logica.Pedido;
import Logica.ProdPedido;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jean
 */
public class DataPedido {

    int numero;
    Fecha fecha;
    Estado estado;
    private final HashMap prodPedidos;
    private final HashMap historialEstados;
    String cliente;
    String restaurante;
    float precio;
    DataCalificacion calificacion;

    public DataPedido() {
        this.numero = 0;
        this.fecha = null;
        this.estado = null;
        this.prodPedidos = null;
        this.cliente = null;
        this.restaurante = null;
        this.precio = 0;
        this.calificacion = null;
        this.historialEstados = null;
    }

    public DataPedido(int numero, Fecha fecha, Estado estado, HashMap prodPedidos, String cliente, String restaurante, float precio, DataCalificacion calificacion, HashMap historialEstados) {
        this.numero = numero;
        this.fecha = fecha;
        this.estado = estado;
        this.prodPedidos = prodPedidos;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.precio = precio;
        this.calificacion = calificacion;
        this.historialEstados = historialEstados;
    }

    public DataPedido(int numero, Date fecha, Estado estado, HashMap prodPedidos, String cliente, String restaurante, float precio, DataCalificacion calificacion, HashMap historialEstados) {
        this.numero = numero;
        this.fecha = new Fecha(fecha);
        this.estado = estado;
        this.prodPedidos = prodPedidos;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.precio = precio;
        this.calificacion = calificacion;
        this.historialEstados = historialEstados;
    }

    public DataPedido(Pedido P) throws SQLException, ClassNotFoundException {
        this.numero = P.getNumero();
        this.fecha = new Fecha(P.getFecha());
        this.estado = P.getEstado();
        this.cliente = P.getCliente().getNickname();
        this.restaurante = P.getRestaurante().getNickname();
        this.precio = P.getPrecio();
        /*por cada objeto producto de pedido se  crea un datatype*/
        Iterator it = P.getProdPedidos().entrySet().iterator();
        this.prodPedidos = new HashMap();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ProdPedido p = (ProdPedido) entry.getValue();
            prodPedidos.put(p.getProducto().getNombre(), new DataProdPedido(p));
        }
        this.calificacion = P.getCalificacion();
        this.historialEstados = P.getHistorialEstados();
    }

    public float getPrecio() {
        return precio;
    }

    public int getNumero() {
        return numero;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public HashMap getProdPedidos() {
        return prodPedidos;
    }

    public String getCliente() {
        return cliente;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public DataCalificacion getCalificacion() {
        return calificacion;
    }

    public HashMap getHistorialEstados() {
        return historialEstados;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCalificacion(DataCalificacion calificacion) {
        this.calificacion = calificacion;
    }

}
