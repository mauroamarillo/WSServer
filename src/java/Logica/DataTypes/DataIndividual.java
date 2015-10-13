/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.DataTypes;

import Logica.Individual;

/**
 *
 * @author Jean
 */
public class DataIndividual extends DataProducto{

    public DataIndividual(){
        super();
    }
    public DataIndividual(String nombre, String descripcion, float precio, String imagen, String restaurante) {
        super(nombre, descripcion, precio, imagen, restaurante);
    }

    public DataIndividual(Individual I) {
        super(I);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }
    
}
