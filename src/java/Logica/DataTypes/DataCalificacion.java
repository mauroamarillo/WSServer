/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.DataTypes;

/**
 *
 * @author Mauro
 */
public class DataCalificacion {

    int puntaje;
    String comentario;

    public DataCalificacion(int puntaje, String comentario) {
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public DataCalificacion() {
        this.puntaje = 0;
        this.comentario = "No calificado";
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
}
