/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Conexion {
    private static Connection conexion = null;
    private String driver;
    private String url;
    private String usuario;
    private String password;
   
    private Conexion(){
        String url = "jdbc:mysql://localhost:3306/gestor_clinica";
        String driver = "com.mysql.jdbc.Driver";
        String usuario = "root";
        String password = "";
        
        try{
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    public static Connection getInstancia(){
        if (conexion == null){
            new Conexion();
        }
        return conexion;
    } 
            
}
