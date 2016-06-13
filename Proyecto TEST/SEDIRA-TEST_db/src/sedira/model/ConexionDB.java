/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *  Clase que controla la conexion a la Base de Datos
 * @author Quelin Pablo, Francisto Hefner
 */
public class ConexionDB {
    /**
     * +++++++++++++++++++++++++DB++++++++++++++++++++++
     * Atributos
     */ 
    
   static String bd = "sedira";
   static String login = "root";
   static String password = "root";
   static String url = "jdbc:mysql://localhost:3306/"+bd;
 
   Connection connection = null;

    /** Constructor de ConexionDB */
   public ConexionDB() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         connection = DriverManager.getConnection(url,login,password);
 
         if (connection!=null){
            System.out.println("Conexión a base de datos "+bd+" OK\n");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   
   /**Permite retornar la conexión*/
   public Connection getConnection(){
      return connection;
   }
   /**
    * Metodo para desconectar la Base de datos
    */
   public void desconectar(){
      connection = null;
   }
    
    
   

}
