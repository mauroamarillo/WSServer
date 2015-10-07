/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Logica.ControladorUsuario;
import Logica.DataTypes.DataCliente;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Mauro
 */
@WebService(serviceName = "WSUsuarios")
public class WSUsuarios {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public DataCliente login(@WebParam(name = "nick") String nick, @WebParam(name = "pwd") String pwd) throws ClassNotFoundException, SQLException {
        ControladorUsuario CU = new ControladorUsuario();
        
        if(!CU.nickOcupado(nick)){
            return null;
        }
        
        if(!CU.buscarCliente(nick).getPwd().equals(pwd)){
            return null;
        }
        
        return CU.buscarCliente(nick);
    }
}
