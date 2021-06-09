/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Conexion {
    public Connection conectar(){
        java.sql.Connection connect = null;
        try {
            //middleware
            Class.forName("com.mysql.jdbc.Driver");
            // de donde saco la conexion, usuario, clave
            connect=DriverManager.getConnection ("jdbc:mysql://db4free.net:3306/gestor_clinica","grupo1_ajm", "grupo1_ajm");
            //JOptionPane.showMessageDialog(null, "Ok, conectado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return connect;
    }
            
}
