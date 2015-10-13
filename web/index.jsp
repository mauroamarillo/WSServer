<%-- 
    Document   : index
    Created on : 05/10/2015, 08:27:59 PM
    Author     : Mauro
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="Logica.ControladorUsuario"%>
<%@page import="Logica.DataTypes.DataCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ControladorUsuario CU = new ControladorUsuario();
    Object Clientes[] = CU.getDataClientes().values().toArray();

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Encaro :) </h1>
        <br/>
        <%
            HashMap HashCliente = new HashMap();
            for (Object c : Clientes) {
                DataCliente DC = (DataCliente) c;
                HashCliente.put(DC.getNickname(), DC);
            }
            
            Iterator it = HashCliente.entrySet().iterator();
            
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                DataCliente DC = (DataCliente) entry.getValue();
                out.print("<br/>Nombre: " + DC.getNombre()
                        + "<br/>Apellido: " + DC.getApellido()
                        + "<br/>Email: " + DC.getEmail()
                        + "<br/>FN: " + DC.getFechaNac()
                        + "<br/>---------------");
            }
            
        %>
    </body>
</html>
