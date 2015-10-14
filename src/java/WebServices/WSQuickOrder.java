/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Logica.ControladorUsuario;
import Logica.DataTypes.DataCalificacion;
import Logica.DataTypes.DataCliente;
import Logica.DataTypes.DataIndividual;
import Logica.DataTypes.DataPedido;
import Logica.DataTypes.DataProdPedido;
import Logica.DataTypes.DataProducto;
import Logica.DataTypes.DataPromocion;
import Logica.DataTypes.DataRestaurante;
import Logica.Estado;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author Shean
 */
@WebService(serviceName = "WSQuickOrder")
public class WSQuickOrder {

    ControladorUsuario CU = null;

    private void iniciar() {
        try {
            if (CU == null) {
                CU = new ControladorUsuario();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean nickOcupado(String nick) {
        try {
            iniciar();
            return CU.nickOcupado(nick);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Boolean emailOcupado(String email) {
        try {
            iniciar();
            return CU.emailOcupado(email);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] getDataClientes() {
        try {
            iniciar();
            return CU.getDataClientes().values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] getDataRestaurantes() {
        iniciar();
        return CU.getDataRestaurantes().values().toArray();
    }

    public Object[] getDataPedidos(String nick) {
        try {
            iniciar();
            return CU.getDataPedidos(nick).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] getDataPedidosTodos() {
        try {
            iniciar();
            return CU.getDataPedidos().values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] getDataPedidosProducto(String R, String P) {
        try {
            iniciar();
            return CU.getDataPedidosProducto(R, P).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataPedido getDataPedido(int numero) {
        try {
            iniciar();
            return CU.getDataPedido(numero);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] consultarCategorias() {
        try {
            iniciar();
            return CU.consultarCategorias().values().toArray();

        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] consultarRestaurantesPorCategoria(String nomCategoria) {
        iniciar();
        return CU.consultarRestaurantesPorCategoria(nomCategoria).values().toArray();
    }

    public int cantidadPorCategoria(String categoria) {
        iniciar();
        return CU.cantidadPorCategoria(categoria);
    }

    public void insertarCliente(String nick, String email, String dir,
            String nombre, String apellido, String D, String M, String A, String pwd) {
        try {
            iniciar();
            CU.insertarCliente(nick, email, dir, nombre, apellido, D, M, A, null, pwd);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarRestaurante(String nick, String email, String dir,
            String nombre, String[] IMGs, int cat[], String pwd) {
        try {
            iniciar();
            HashMap imgs = new HashMap();
            int x = 1;
            for (String img : IMGs) {
                imgs.put(x, img);
                x++;
            }
            CU.insertarRestaurante(nick, email, dir, nombre, imgs, cat, pwd);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarCategoria(String nombre) {
        try {
            iniciar();
            CU.insertarCategoria(nombre);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[] retornarCategoriasRestaurantes(String nick) {
        try {
            iniciar();
            return CU.retornarCategoriasRestaurantes(nick).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object[] filtrarRestaurantes(String filtro) {
        iniciar();
        return CU.filtrarRestaurantes(filtro).values().toArray();
    }

    public Object[] retornarCambiosEstado(int pedido) {
        try {
            iniciar();
            return CU.retornarCambiosEstado(pedido).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataRestaurante buscarRestaurante(String nickname) {
        try {
            iniciar();
            return CU.buscarRestaurante(nickname);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataCliente buscarCliente(String nickname) {
        try {
            iniciar();
            return CU.buscarCliente(nickname);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataCliente buscarClientePorEmail(String email) {
        try {
            iniciar();
            return CU.buscarClientePorEmail(email);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int insertarPedido(String D, String M, String A,
            Estado estado, String cliente, String restaurante,
            DataProdPedido[] dataProdPedidos) {
        try {
            iniciar();
            HashMap LineasPedido = new HashMap();
            for (DataProdPedido lineaPedido : dataProdPedidos) {
                LineasPedido.put(lineaPedido.getProducto().getNombre(), lineaPedido);
            }
            return CU.insertarPedido(D, M, A, estado, cliente, restaurante, LineasPedido);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Object[] getPedidosCarrito(String nick) {
        try {
            iniciar();
            return CU.getPedidosCarrito(nick).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void agregarACarrito(DataProdPedido p, String nick) {
        try {
            iniciar();
            CU.agregarACarrito(p, nick);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void quitarLineaPedido(int pedido, String restaurante, String producto) {
        try {
            iniciar();
            CU.quitarLineaPedido(pedido, restaurante, producto);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calificarPedido(int numero, int calificacion, String comentario) {
        try {
            iniciar();
            CU.calificarPedido(numero, calificacion, comentario);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float getPromedioCalificaciones(String nick) {
        try {
            iniciar();
            return CU.getPromedioCalificaciones(nick);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public DataCalificacion obtenerCalificacionPedido(int pedido) {
        try {
            iniciar();
            return CU.obtenerCalificacionPedido(pedido);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void modificarCliente(String nick, String nombre, String email, String direccion, String apellido, String imagen, String pwd) {
        try {
            iniciar();
            CU.modificarCliente(nick, nombre, email, direccion, apellido, imagen, pwd);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[] getPedidosRestaurante(String restaurante) {
        try {
            iniciar();
            return CU.getPedidosRestaurante(restaurante).values().toArray();
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void cancelarPedido(int numero) {
        try {
            iniciar();
            CU.cancelarPedido(numero);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarEstadoPedido(int numero, int estado) {
        try {
            iniciar();
            CU.cambiarEstadoPedido(numero, estado);
        } catch (SQLException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[] getDataPromociones() {
        iniciar();
        return CU.getCP().getDataPromociones().values().toArray();
    }

    public Object[] getDataIndividuales() {
        iniciar();
        return CU.getCP().getDataIndividuales().values().toArray();
    }

    public DataProducto BuscarDataXRestaurante_Producto(String R_P) {
        iniciar();
        return CU.getCP().BuscarDataXRestaurante_Producto(R_P);
    }

    public void insertarIndividual(String nombre, String descripcion,
            String precio, File img, String restaurante) {
        try {
            iniciar();
            CU.getCP().insertarIndividual(nombre, descripcion, precio, img, restaurante);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPromocion(String nombre, String descripcion,
            File img, boolean activa, float descuento, String restaurante,
            HashMap subProductos) {
        try {
            iniciar();
            CU.getCP().insertarPromocion(nombre, descripcion, img, activa, descuento, restaurante, subProductos);
        } catch (Exception ex) {
            Logger.getLogger(WSQuickOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataIndividual buscarDataIndividual(String nombre, String restaurante) {
        iniciar();
        return CU.getCP().buscarDataIndividual(nombre, restaurante);
    }

    public Object[] buscarProductos(String R) {
        iniciar();
        return CU.getCP().buscarProductos(R).values().toArray();
    }

    public Object[] buscarProductosI(String R) {
        iniciar();
        return CU.getCP().buscarProductosI(R).values().toArray();
    }

    public Object[] buscarProductosP(String R) {
        iniciar();
        return CU.getCP().buscarProductosP(R).values().toArray();
    }
    /*sacar
     public DataIndividual pruebaIndividual() {
     return new DataIndividual();
     }

     public DataProducto pruebaProducto() {
     return new DataProducto();
     }

     public DataPromocion pruebaPromocion() {
     return new DataPromocion();
     }*/
}
