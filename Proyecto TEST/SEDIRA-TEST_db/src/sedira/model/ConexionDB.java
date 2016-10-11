/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sedira.CodigosErrorSQL;

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
    static String bd = "sedira";
    static String login = "root";
    static String password = "root";
    static String url = "jdbc:mysql://localhost:3306/" + bd;
    private boolean error = false;
    Connection connection = null;

    /**
     * Contructor para la conexion
     */
    public ConexionDB() {
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

}
