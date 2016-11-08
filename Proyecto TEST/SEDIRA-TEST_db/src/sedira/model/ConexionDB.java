/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sedira.CodigosErrorSQL;
import sedira.vistas.BdConfigController;

/**
 * Clase que controla la conexión a la Base de Datos. Implementada sobre JDBC.
 * MySQL
 *
 * @author Quelin Pablo, Francisto Hefner
 */
public class ConexionDB {
    
    
    
    /**
     * +++++++++++++++++++++++++DB++++++++++++++++++++++ Atributos
     */
    public static String bd = "";
    public static String login = "";
    public static String password = "";
    //public String url = "jdbc:mysql://localhost:3306/" + bd;
    //public static String url = ""+bd;
    public static String url = "";
    private boolean error = false;
    Connection connection = null;
    BdConfigController initDB = new BdConfigController();
    /**
     * Contructor para la conexion
     */
    public ConexionDB() {
       initDB.getParametros();
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            connection = DriverManager.getConnection(url, login, password);

            if (connection != null) {
                System.out.println("Conexión a base de datos " + bd + " OK\n");

            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            CodigosErrorSQL.analizarExepcion(e);
            setError(true);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Ocurrio un error al conectarse con la base de datos. ");
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
            setError(true);
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Ocurrio un error al conectarse con la base de datos. ");
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
            setError(true);
        }
    }
    
    @FXML
    public void setConfig(){
        
    }
    
    
    /**
     * Método que retorna la conexión establecida.
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Método para desconectar la Base de datos
     */
    public void desconectar() {
        connection = null;
    }

    /**
     * Método que retorna si la conexión entro en error.
     *
     * @return
     */
    public boolean getError() {
        return this.error;
    }

    private void setError(boolean error) {
        this.error = error;
    }

    public static String getBd() {
        return  bd;
    }

    public static void setBd(String bd) {
        ConexionDB.bd = bd;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        ConexionDB.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConexionDB.password = password;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ConexionDB.url = url + ConexionDB.getBd();
    }
    

     
    
}
