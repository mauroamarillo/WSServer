/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.DataTypes;

import Logica.Fecha;
import java.sql.Date;
import java.util.HashMap;

/**
 *
 * @author Jean
 */
public class DataCliente {

    String nickname;
    String pwd;
    String nombre;
    String email;
    String direccion;
    String apellido;
    Fecha fechaNac;
    String imagen;
    private final HashMap pedidos;

    public void setFechaNac(Fecha fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

   /* public void setPedidos(HashMap pedidos) {
        this.pedidos = pedidos;
    }*/

    public DataCliente() {
        this.nickname = null;
        this.pwd = null;
        this.nombre = null;
        this.email = null;
        this.direccion = null;
        this.apellido = null;
        this.fechaNac = null;
        this.imagen = null;
        this.pedidos = null;
    }

    public DataCliente(String nickname, String nombre, String email, String direccion, String apellido, Date fechaNac, String imagen, HashMap pedidos, String pwd) {
        this.nickname = nickname;
        this.pwd = pwd;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.apellido = apellido;
        this.fechaNac = new Fecha(fechaNac);
        this.imagen = imagen;
        this.pedidos = pedidos;
    }

    public DataCliente(String nickname, String nombre, String email, String direccion, String apellido, Fecha fechaNac, String imagen, HashMap pedidos, String pwd) {
        this.nickname = nickname;
        this.pwd = pwd;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.imagen = imagen;
        this.pedidos = pedidos;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getApellido() {
        return apellido;
    }

    public Fecha getFechaNac() {
        return fechaNac;
    }

    public String getImagen() {
        return imagen;
    }

    public HashMap getPedidos() {
        return pedidos;
    }

}
