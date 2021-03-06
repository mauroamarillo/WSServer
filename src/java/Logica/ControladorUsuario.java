package Logica;

import Datos.CategoriaD;
import Datos.PedidoD;
import Datos.UsuarioD;
import Logica.DataTypes.DataCalificacion;
import Logica.DataTypes.DataCategoria;
import Logica.DataTypes.DataCliente;
import Logica.DataTypes.DataHistorialPedido;
import Logica.DataTypes.DataIndividual;
import Logica.DataTypes.DataPedido;
import Logica.DataTypes.DataProdPedido;
import Logica.DataTypes.DataRestaurante;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ControladorUsuario {
    /*acceso a capa logica*/

    private final CategoriaD CategoriaDatos;
    private final UsuarioD UsuarioDatos;
    private final PedidoD PedidoDatos;

    /*acceso a datos de productos*/
    private final ControladorProductos CP;
    /*Datos de usuarios, categorias guardados en el sistema*/
    private HashMap Clientes = new HashMap();
    private HashMap Restaurantes = new HashMap();
    private HashMap Categorias = new HashMap();

    public ControladorUsuario() throws SQLException, ClassNotFoundException {
        this.UsuarioDatos = new UsuarioD();
        this.CategoriaDatos = new CategoriaD();
        this.PedidoDatos = new PedidoD();

        this.Restaurantes = retornarRestaurantes();
        this.Clientes = retornarClientes();
        this.Categorias = consultarCategorias();
        this.CP = new ControladorProductos(this);
        this.asignarPedidosAClientes();
    }

    public boolean nickOcupado(String nick) throws SQLException, ClassNotFoundException {
        return UsuarioDatos.nickOcupado(nick);
    }

    public boolean emailOcupado(String email) throws SQLException, ClassNotFoundException {
        return UsuarioDatos.emailOcupado(email);
    }

    public HashMap getDataClientes() throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        Iterator it = Clientes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = (Cliente) entry.getValue();
            resultado.put(C.getNickname(), C.getDataType());
        }
        return resultado;
    }

    public HashMap getDataRestaurantes() {
        HashMap resultado = new HashMap();
        Iterator it = Restaurantes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = (Restaurante) entry.getValue();
            resultado.put(R.getNickname(), R.getDataType());
        }
        return resultado;
    }

    public ControladorProductos getCP() {
        return CP;
    }

    public HashMap getClientes() {
        return Clientes;
    }

    public HashMap getRestaurantes() {
        return Restaurantes;
    }

    public HashMap getCategorias() {
        return Categorias;
    }


    /*obtener pedidos de un cliente especifico*/
    public HashMap getDataPedidos(String nick) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        Cliente C = this._buscarCliente(nick);
        Iterator it = C.getPedidos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pedido P = (Pedido) entry.getValue();
            resultado.put(P.getNumero(), P.getDataType());
        }
        return resultado;
    }

    /*Obtener todos los pedidos registrados*/
    public HashMap getDataPedidos() throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        Iterator it = this.getClientes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = (Cliente) entry.getValue();
            resultado.putAll(getDataPedidos(C.getNickname()));
        }
        return resultado;
    }

    /*obtener pedidos de un producto*/
    public HashMap getDataPedidosProducto(String R, String P) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        HashMap aux = getDataPedidos();
        Iterator it = aux.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            DataPedido DP = (DataPedido) entry.getValue();
            if (DP.getRestaurante().equals(R)) {
                Iterator it2 = DP.getProdPedidos().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    DataProdPedido DPP = (DataProdPedido) entry2.getValue();
                    if (DPP.getProducto().getNombre().equals(P)) {
                        resultado.put(DP.getNumero(), DP);
                        break;
                    }
                }
            }
        }
        return resultado;
    }
    /*obtener pedido por numero*/

    public DataPedido getDataPedido(int numero) throws SQLException, ClassNotFoundException {
        return (DataPedido) getDataPedidos().get(numero);
    }

    public HashMap consultarCategorias() throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = CategoriaDatos.consultarCategorias();
        while (rs.next()) {
            resultado.put(rs.getInt("idCat"), rs.getString("nombre"));
        }
        return resultado;
    }

    public HashMap consultarRestaurantesPorCategoria(String nomCategoria) {
        Categoria categoria = new Categoria(nomCategoria);
        HashMap res = new HashMap();
        Iterator it = Restaurantes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = ((Restaurante) entry.getValue());
            if (R.getCategorias().containsKey(categoria.getNombre())) {
                res.put(R.getNickname(), new DataRestaurante(R));
            }
        }
        return res;
    }

    public int cantidadPorCategoria(String categoria) {
        return consultarRestaurantesPorCategoria(categoria).size();
    }

    public void insertarCliente(String nick, String email, String dir, String nombre, String apellido,
            String D, String M, String A, String img, String pwd) throws SQLException, Exception {
        Cliente C;
        Fecha fecha;
        try {
            NumberFormat.getInstance().parse(M);
            fecha = new Fecha(Integer.valueOf(D), Integer.valueOf(M), Integer.valueOf(A));
        } catch (ParseException e) {
            fecha = new Fecha(D, M, A);
        }

        if (img.equals("NO")) {
            C = new Cliente(nick, nombre, email, dir, apellido, fecha.getSQLDate(), "sin_imagen", new HashMap(), pwd);
        } else {
            C = new Cliente(nick, nombre, email, dir, apellido, fecha.getSQLDate(), img, new HashMap(), pwd);
        }
        validarDatosC(C);
        UsuarioDatos.agregarCliente(C.getNickname(), C.getNombre(), C.getEmail(), C.getDireccion(), C.getApellido(), C.getFechaNac(), img, pwd);
        this.Clientes = this.retornarClientes();
        this.asignarPedidosAClientes();
    }

    public void insertarRestaurante(String nick, String email, String dir, String nombre,
            HashMap IMGs, int cat[], String pwd) throws SQLException, Exception {
        Restaurante R = new Restaurante(nick, nombre, email, dir, null, null, null, null, pwd);
        validarDatosR(R, cat);
        UsuarioDatos.agregarRestaurante(nick, nombre, email, dir, pwd);
        for (int x = 0; x < cat.length; x++) {
            UsuarioDatos.agregarCategoriaARestaurante(nick, cat[x]);
        }
        Iterator it = IMGs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String path = (String) entry.getValue();
            UsuarioDatos.agregarImgRestaurante(nick, path);
        }
        this.Restaurantes = retornarRestaurantes();
    }

    public void insertarCategoria(String nombre) throws SQLException, ClassNotFoundException {
        CategoriaDatos.agregarCategoria(nombre);
        this.Categorias = consultarCategorias();
    }

    private void validarDatosC(Cliente C) throws SQLException, Exception {
        if (C.getNickname().isEmpty()) {
            throw new Exception("Asignar Nickname");
        }
        if (C.getNombre().isEmpty()) {
            throw new Exception("Asignar Nombre");
        }
        if (C.getDireccion().isEmpty()) {
            throw new Exception("Asignar Direccion");
        }
        if (C.getApellido().isEmpty()) {
            throw new Exception("Asignar Apellido");
        }
        if (C.getEmail().isEmpty()) {
            throw new Exception("Asignar Email");
        }
        if (UsuarioDatos.nickOcupado(C.nickname)) {
            throw new Exception("Nickname Ocupado");
        }
        if (UsuarioDatos.emailOcupado(C.email)) {
            throw new Exception("Email Ocupado");
        }
    }

    private void validarDatosR(Restaurante R, int cat[]) throws SQLException, Exception {
        if (R.getNickname().isEmpty()) {
            throw new Exception("Asignar Nickname");
        }
        if (R.getNombre().isEmpty()) {
            throw new Exception("Asignar Nombre");
        }
        if (R.getDireccion().isEmpty()) {
            throw new Exception("Asignar Direccion");
        }
        if (R.getEmail().isEmpty()) {
            throw new Exception("Asignar Email");
        }
        if (cat.length < 1) {
            throw new Exception("Asignar al menos 1 Categoria");
        }
        if (UsuarioDatos.nickOcupado(R.nickname)) {
            throw new Exception("Nickname Ocupado");
        }
        if (UsuarioDatos.emailOcupado(R.email)) {
            throw new Exception("Email Ocupado");
        }
    }
    /*FALTA COMPLETAR ESTO!!!!!!!!!!!!!!!!!!*/

    public HashMap retornarClientes() throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = UsuarioDatos.listarClientes();
        while (rs.next()) {
            Cliente C = new Cliente(rs.getString("nickname"), rs.getString("nombre"), rs.getString("email"), rs.getString("direccion"), rs.getString("apellido"), rs.getDate("fechaN"), "sin_imagen", null, rs.getString("contrasenia"));
            resultado.put(C.getNickname(), C);
        }
        Iterator it = resultado.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = ((Cliente) entry.getValue());
            C.setImagen(retornarIMGCliente(C.getNickname()));
        }
        return resultado;
    }

    private String retornarIMGCliente(String nick) throws SQLException, ClassNotFoundException {
        return UsuarioDatos.obtenerIMGCliente(nick);
    }

    public HashMap retornarRestaurantes() throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = UsuarioDatos.listarRestaurantes();
        while (rs.next()) {
            Restaurante R = new Restaurante(rs.getString("nickname"), rs.getString("nombre"), rs.getString("email"), rs.getString("direccion"), new HashMap(), new HashMap(), new HashMap(), new HashMap(), rs.getString("contrasenia"));
            resultado.put(R.getNickname(), R);
        }
        Iterator it = resultado.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = ((Restaurante) entry.getValue());
            R.setImagenes(retornarIMGsRestaurantes(R.getNickname()));
        }
        it = resultado.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = ((Restaurante) entry.getValue());
            R.setCategorias(retornarCategoriasRestaurantes(R.getNickname()));
        }
        return resultado;
    }

    public HashMap retornarIMGsRestaurantes(String nick) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = UsuarioDatos.listarIMGsRestaurante(nick);
        int index = 1;
        while (rs.next()) {
            resultado.put(index, rs.getString("imagen"));
            index++;
        }
        return resultado;
    }

    public HashMap retornarCategoriasRestaurantes(String nick) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = CategoriaDatos.listarCatsRestaurante(nick);
        while (rs.next()) {
            String C = rs.getString("nombre");
            resultado.put(C, C);
        }
        return resultado;
    }

    public HashMap filtrarRestaurantes(String filtro) {
        HashMap res = new HashMap();
        Iterator it = Restaurantes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = ((Restaurante) entry.getValue());
            if (R.getNickname().contains(filtro)) {
                res.put(R.getNickname(), R.getDataType());
            }
        }
        return res;
    }

    public HashMap retornarCambiosEstado(int pedido) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = PedidoDatos.consultarCambiosEstado(pedido);

        while (rs.next()) {
            resultado.put(rs.getInt("pedido") + "_" + rs.getInt("estado"), new DataHistorialPedido(rs.getInt("estado"), rs.getString("fechahora")));
        }

        return resultado;
    }

    /*
     MECANISMO DE BUSQUEDA:
     Primero vemos si el usuario que buscamos esta entre los datos que 
     el sistema ya tiene y si no lo encontramos, buscamos en la base de datos
     */
    public DataRestaurante buscarRestaurante(String nickname) throws SQLException, ClassNotFoundException {
        if ((Restaurante) Restaurantes.get(nickname) == null) {
            Restaurantes = this.retornarRestaurantes();
        }
        Restaurante R = (Restaurante) Restaurantes.get(nickname);
        if (R == null) {
            return null;
        }
        return new DataRestaurante(R);
    }

    public DataCliente buscarCliente(String nickname) throws SQLException, ClassNotFoundException {
        if ((Cliente) Clientes.get(nickname) == null) {
            Clientes = this.retornarClientes();
        }
        Cliente C = (Cliente) Clientes.get(nickname);
        if (C == null) {
            return null;
        }
        return C.getDataType();
    }

    public DataCliente buscarClientePorEmail(String email) throws SQLException, ClassNotFoundException {
        Iterator it = Clientes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = (Cliente) entry.getValue();
            if (C.getEmail().equals(email)) {
                return C.getDataType();
            }
        }
        return null;
    }

    public Restaurante _buscarRestaurante(String nickname) throws SQLException, ClassNotFoundException {
        if ((Restaurante) Restaurantes.get(nickname) == null) {
            Restaurantes = this.retornarRestaurantes();
        }
        return (Restaurante) Restaurantes.get(nickname);
    }

    public Restaurante _buscarRestauranten_n(String nickname) throws SQLException {
        Iterator it = Restaurantes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Restaurante R = (Restaurante) entry.getValue();
            if ((R.getNickname() + " - " + R.getNombre()).equals(nickname)) {
                return R;
            }
        }
        return null;
    }

    public Cliente _buscarCliente(String nickname) throws SQLException, ClassNotFoundException {
        if ((Cliente) Clientes.get(nickname) == null) {
            Clientes = this.retornarRestaurantes();
        }
        return (Cliente) Clientes.get(nickname);
    }

    /*-- PEDIDOS --*/
    private void validarPedido(Pedido P) throws Exception {
        if (P.getCliente() == null) {
            throw new Exception("Cliente invalido");
        }
        if (P.getRestaurante() == null) {
            throw new Exception("Restaurante invalido");
        }
        if (P.getProdPedidos() == null) {
            throw new Exception("Error al generar pedido");
        }
        if (P.getProdPedidos().size() < 1) {
            throw new Exception("No se agregaron Productos al pedido");
        }
    }

    public int insertarPedido(String D, String M, String A, Estado estado, String cliente, String restaurante, HashMap dataProdPedidos) throws SQLException, Exception {
        Fecha fecha;
        try {
            NumberFormat.getInstance().parse(M);
            fecha = new Fecha(Integer.valueOf(D), Integer.valueOf(M), Integer.valueOf(A));
        } catch (ParseException e) {
            fecha = new Fecha(D, M, A);
        }
        Pedido P = new Pedido(0, fecha.getSQLDate(), estado, this._buscarCliente(cliente), this._buscarRestaurante(restaurante), dataProdPedidos, null);
        validarPedido(P);
        int numero = (PedidoDatos.agregarPedido(fecha.getSQLDate(), estado.ordinal(), cliente, restaurante));
        Iterator it = P.getProdPedidos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            DataProdPedido DPP = (DataProdPedido) entry.getValue();
            PedidoDatos.agregarLineaDePedido(numero, DPP.getProducto().getRestaurante(), DPP.getProducto().getNombre(), DPP.getCantidad());
        }
        asignarPedidosAClientes();
        return numero;
    }

    private void asignarPedidosAClientes() throws SQLException, ClassNotFoundException {
        Iterator it = Clientes.entrySet().iterator();
        //Asigno a cada cliente sus pedidos
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = (Cliente) entry.getValue();
            java.sql.ResultSet rs = PedidoDatos.listarPedidosDeCliente(C.getNickname());
            C.setPedidos(new HashMap());
            while (rs.next()) {
                Pedido P = new Pedido(rs.getInt("numero"), rs.getDate("fecha"), Estado.values()[rs.getInt("Estado")], this._buscarCliente(rs.getString("cliente")), this._buscarRestaurante(rs.getString("restaurante")), null, retornarCambiosEstado(rs.getInt("numero")));
                C.getPedidos().put(P.getNumero(), P);
            }
        }
        //asigno cada linea de producto a cada pedido
        it = Clientes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Cliente C = (Cliente) entry.getValue();
            Iterator it2 = C.getPedidos().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                Pedido P = (Pedido) entry2.getValue();
                java.sql.ResultSet rs2 = PedidoDatos.listarLineasDePedidos(P.getNumero());
                HashMap Lineas = new HashMap();
                while (rs2.next()) {
                    ProdPedido PP;
                    Individual PI = CP.buscarIndividual(rs2.getString("producto"), rs2.getString("restaurante"));
                    if (PI != null) {
                        PP = new ProdPedido(rs2.getInt("cantidad"), PI);
                    } else {
                        PP = new ProdPedido(rs2.getInt("cantidad"), CP.buscarPromocion(rs2.getString("producto"), rs2.getString("restaurante")));
                    }
                    Lineas.put(PP.getProducto().getNombre(), PP);
                }
                P.setProdPedidos(Lineas);
            }
        }

    }

    public HashMap getPedidosCarrito(String nick) throws SQLException, ClassNotFoundException {
        HashMap pedidos = getDataPedidos(nick);
        HashMap result = new HashMap();
        Iterator it = pedidos.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            DataPedido pedido = (DataPedido) entry.getValue();
            if (pedido.getEstado().equals(Estado.aconfirmar)) {
                result.put(pedido.getNumero(), pedido);
            }
        }

        return result;
    }

    public void agregarACarrito(DataProdPedido p, String nick) throws SQLException, ClassNotFoundException, Exception {
        HashMap carrito = getPedidosCarrito(nick);
        Iterator it = carrito.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            DataPedido pedido = (DataPedido) entry.getValue();
            if (pedido.getRestaurante().equals(p.getProducto().getRestaurante())) {
                Iterator itProd = pedido.getProdPedidos().entrySet().iterator();

                while (itProd.hasNext()) {
                    Map.Entry entryProd = (Map.Entry) itProd.next();
                    DataProdPedido prod = (DataProdPedido) entryProd.getValue();
                    if (prod.getProducto().getNombre().equals(p.getProducto().getNombre())) {
                        PedidoDatos.modificarCantidadProducto(pedido.getNumero(), pedido.getRestaurante(), prod.getProducto().getNombre(), p.getCantidad() + prod.getCantidad());
                        this.asignarPedidosAClientes();
                        return;
                    }
                }
                PedidoDatos.agregarLineaDePedido(pedido.getNumero(), p.getProducto().getRestaurante(), p.getProducto().getNombre(), p.getCantidad());
                this.asignarPedidosAClientes();
                return;
            }
        }

        int numero = PedidoDatos.agregarPedido(new Fecha().getSQLDate(), 3, nick, p.getProducto().getRestaurante());

        PedidoDatos.agregarLineaDePedido(numero, p.getProducto().getRestaurante(), p.getProducto().getNombre(), p.getCantidad());
        this.asignarPedidosAClientes();
    }

    public void quitarLineaPedido(int pedido, String restaurante, String producto) throws SQLException, ClassNotFoundException, Exception {
        if (getDataPedido(pedido).getEstado().equals(Estado.aconfirmar)) {
            PedidoDatos.removerLineaDePedido(pedido, restaurante, producto);
            this.Clientes = retornarClientes();
            asignarPedidosAClientes();
            if (getDataPedido(pedido).getProdPedidos().entrySet().isEmpty()) {
                cancelarPedido(pedido);
            }
        } else {
            throw new Exception("No se pueden modificar pedidos confirmados.");
        }
    }

    public void calificarPedido(int numero, int calificacion, String comentario) throws SQLException, ClassNotFoundException, Exception {
        if (getDataPedido(numero).getEstado().equals(Estado.recibido)) {
            PedidoDatos.calificarPedido(numero, new Fecha().getSQLDate(), calificacion, comentario);
        } else {
            throw new Exception("No se pueden calificar pedidos no recibidos.");
        }
    }

    public float getPromedioCalificaciones(String nick) throws SQLException, ClassNotFoundException {
        return UsuarioDatos.obtenerCalificacionRestaurante(nick);
    }

    public DataCalificacion obtenerCalificacionPedido(int pedido) throws SQLException, ClassNotFoundException {
        return getDataPedido(pedido).getCalificacion();
    }

    public void modificarCliente(String nick, String nombre, String email, String direccion, String apellido, String imagen, String pwd) throws SQLException, ClassNotFoundException {
        UsuarioDatos.modificarCliente(nick, nombre, email, direccion, apellido, imagen, pwd);
        actualizarDatos();
    }

    public HashMap getPedidosRestaurante(String restaurante) throws SQLException, ClassNotFoundException {
        HashMap resultado = new HashMap();
        java.sql.ResultSet rs = PedidoDatos.obtenerPedidosRestaurante(restaurante);

        while (rs.next()) {
            DataPedido DP = getDataPedido(rs.getInt("numero"));
            resultado.put(DP.getNumero(), DP);
        }

        return resultado;
    }

    public void cancelarPedido(int numero) throws SQLException, ClassNotFoundException {
        PedidoDatos.eliminarPedido(numero);
        asignarPedidosAClientes();
    }

    public void cambiarEstadoPedido(int numero, int estado) throws SQLException, ClassNotFoundException {
        PedidoDatos.modificarEstado(numero, estado);
        asignarPedidosAClientes();
    }

    public void confirmarPedido(int numero) throws SQLException, ClassNotFoundException {
        //Cambiar estado del pedido
        cambiarEstadoPedido(numero, 0);

        //generar cuerpo del mail
        DataPedido pedido = (DataPedido) getDataPedidos().get(numero);

        Cliente c = (Cliente) Clientes.get(pedido.getCliente());

        String mensaje = "<!DOCTYPE html><html><head>Estimado <tt style=\"font-style: italic\">" + c.getNombre() + " " + c.getApellido() + "</tt>. Su pedido ha sido recibido con exito:</head>";

        mensaje += "<body><h5>--Detalles del pedido</h5>"
                + "<h5>-Productos:</h5>"
                + "<ul>";

        Iterator it = pedido.getProdPedidos().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entryProd = (Map.Entry) it.next();
            DataProdPedido prod = (DataProdPedido) entryProd.getValue();

            mensaje += "<li>Nombre: " + prod.getProducto().getNombre() + " - Tipo : ";

            if (prod.getProducto() instanceof DataIndividual) {
                mensaje += "Individual";
            } else {
                mensaje += "Promocion";
            }

            mensaje += " - Cantidad: " + prod.getCantidad() + " - PU: $" + prod.getProducto().getPrecio() + " - PV: $" + (prod.getProducto().getPrecio() * prod.getCantidad()) + "</li>";
        }

        Restaurante r = (Restaurante) Restaurantes.get(pedido.getRestaurante());

        mensaje += "</ul>"
                + "<h5>-Precio Total: $" + pedido.getPrecio() + "</h5>"
                + "<p>Gracias por preferirnos,</p>"
                + "<p>Saludos.</p>"
                + "<p><tt style=\"font-style: italic\">" + r.getNombre() + "</tt></p>"
                + "</body></html>";

        Mail m = new Mail();

        m.sendMail(c.getEmail(), "QuickOrder [" + pedido.getFecha().toString() + "]", mensaje);

        System.out.println(mensaje);
    }

    public HashMap getInfoPedidos() throws SQLException, ClassNotFoundException {
        java.sql.ResultSet rs = PedidoDatos.consultarInfoPedidos();
        HashMap resultado = new HashMap();

        while (rs.next()) {
            resultado.put(rs.getInt("numero"), new DataPedido(rs.getInt("numero"), new Fecha(rs.getDate("fecha")), null, null, rs.getString("cliente"), rs.getString("restaurante"), 0, null, null));
        }

        return resultado;
    }

    public HashMap getDataCategorias() throws ClassNotFoundException, SQLException {
        java.sql.ResultSet rs = CategoriaDatos.consultarCategorias();
        HashMap resultado = new HashMap();
        while (rs.next()) {
            resultado.put(rs.getInt("idCat"), new DataCategoria(rs.getInt("idCat"), rs.getString("nombre")));
        }
        return resultado;
    }

    public void actualizarDatos() throws SQLException, ClassNotFoundException {
        this.Restaurantes = retornarRestaurantes();
        CP.actualizarDatos();
        this.Clientes = retornarClientes();
        this.Categorias = consultarCategorias();
        asignarPedidosAClientes();
    }

    public HashMap getDataEstadisticaXURL() throws SQLException, ClassNotFoundException {
        java.sql.ResultSet datos = new Estadistica().getVisitas();
        HashMap resultado = new HashMap();
        while (datos.next()) {
            resultado.put(datos.getString("url1"), datos.getString("url1") + "%" + datos.getString("visitas"));
        }
        
        return resultado;
    }
    
    public HashMap getDataEstadisticaXSO() throws SQLException, ClassNotFoundException {
        java.sql.ResultSet datos = new Estadistica().getVisitasPorSO();
        HashMap resultado = new HashMap();
        while (datos.next()) {
            resultado.put(datos.getString("so"), datos.getString("so") + "%" + datos.getString("visitas"));
        }
        
        return resultado;
    }
    
    public HashMap getDataEstadisticaXBrowser() throws SQLException, ClassNotFoundException {
        java.sql.ResultSet datos = new Estadistica().getVisitasPorBrowser();
        HashMap resultado = new HashMap();
        while (datos.next()) {
            resultado.put(datos.getString("browser"), datos.getString("browser") + "%" + datos.getString("visitas"));
        }
        
        return resultado;
    }
    
    public HashMap getDataEstadisticaXRestaurante() throws SQLException, ClassNotFoundException {
        java.sql.ResultSet datos = new Estadistica().getVisitasPorRestaurante();
        HashMap resultado = new HashMap();
        while (datos.next()) {
            resultado.put(datos.getString("nickname"), datos.getString("nickname") + "%" + datos.getString("nombre") + "%" + datos.getString("email") + "%" + datos.getString("direccion") + "%" + datos.getInt("visitas"));
        }
        
        return resultado;
    }
}
