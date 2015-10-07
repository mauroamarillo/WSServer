/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Logica.DataTypes.DataRestaurante;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jean
 */
public abstract class HerramientaListas {

    public static HashMap ordenarRestaurantesCalificacion(HashMap HM, ControladorUsuario CU) throws SQLException, ClassNotFoundException {
        HashMap lista = new HashMap();
        lista.putAll(HM);
        HashMap lista2 = new HashMap();
        lista.putAll(HM);
        HashMap listaFinal = new HashMap();
        lista.putAll(HM);
        Iterator it1 = lista.entrySet().iterator();

        while (it1.hasNext()) {
            Map.Entry entry1 = (Map.Entry) it1.next();
            Iterator it2 = lista2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                DataRestaurante DR1 = (DataRestaurante) entry1.getValue();
                DataRestaurante DR2 = (DataRestaurante) entry2.getValue();
                if (CU.getPromedioCalificaciones(DR1.getNickname()) > CU.getPromedioCalificaciones(DR2.getNickname())) {
                    entry1 = entry2;
                }
            }
            listaFinal.put(entry1.getKey(), entry1.getValue());
            lista2.remove(entry1.getKey());
        }
        return listaFinal;
    }

}
