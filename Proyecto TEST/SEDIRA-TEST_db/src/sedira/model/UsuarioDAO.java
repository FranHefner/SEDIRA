/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 * Clase de acceso de datos para Usuarios
 * @author Quelin Pablo, Hefner Francisco
 */
public abstract class UsuarioDAO {
    /**
     * Método para agregar un usuario a la base de datos 
     * @param usuario
     * @param tipoUsuario 
     */
    public static void agregarUsuario(Usuario usuario, int tipoUsuario){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //obtengo el nombre de usuario para la busqueda. 
        String nombreUsuario = usuario.getLogin();
        
        try {
            if (buscaUsuario(nombreUsuario)==false){
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "INSERT INTO usuarios (descripcion, login, pass, id_tipoUsuario) "
                        + "VALUES(?,?,?,?)");
                consulta.setString(1, usuario.getDescripcion());
                consulta.setString(2, usuario.getLogin());
                consulta.setString(3, usuario.getPass());
                consulta.setInt(4,tipoUsuario);

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                
                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El usuario fué agregado.");
                alerta.showAndWait();
            } else {
                
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la inserción del usuario " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción del usuario " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Método que modifica un usuario existente en la base de datos
     * @param usuario
     * @param tipoUsuario
     */
    public static void modificarUsuario(Usuario usuario, int tipoUsuario) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreUsuario = usuario.getLogin();

        try {
            if (buscaUsuario(nombreUsuario) == false) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE usuario SET login = ?"
                        + ",pass = ?"
                        + ", descripcion=?"
                        + ",id_tipoUsuario=? "
                        + "WHERE id_usuario = ?");

                consulta.setString(1, usuario.getLogin());
                consulta.setString(2, usuario.getPass());
                consulta.setString(3, usuario.getDescripcion());
                consulta.setInt(4, tipoUsuario);
                consulta.setInt(5, usuario.getIdUsuario());

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                conexion.desconectar();
            } else {

            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la modificación del usuario " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la modificación del usuario " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Método que eliminar un usuario existente en la base de datos 
     * @param idUsuario 
     */
    public static void eliminarUsuario (int idUsuario){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM usuarios WHERE id_usuario = ?");
            consulta.setInt(1, idUsuario);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();
            
            
            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El usuario fué eliminado. ");
            alerta.showAndWait();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al eliminar el usuario \n" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el usuario \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
            
    }
    /**
     * Método que busca un nombre de usuario en la base de datos. 
     * En caso que exista returna True. 
     * En caso que no exista retorna False
     * @param usuario
     * @return True o False
     */
    public static boolean buscaUsuario (String usuario){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT login FROM usuarios "
                            + "WHERE login = ?");
            consulta.setString(1, usuario);
          
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                
                return true;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }
    /**
     * Método que retorna la informacion completa de la tabla de usuarios
     * @param usuarioSeleccionado
     * @return 
     */
    public static ObservableList <Usuario> obtenerUsuarios (){
        //Creo una lista auxiliar
        ObservableList<Usuario> usuarioData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM Usuarios");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                // parametros de inicializacion del contructor (int idUsuario, String descripcion, String login, String pass)
                Usuario usuario = new Usuario(0, "", "", "");
                //obtencion de los datos desde la bd.
                usuario.setIdUsuario(Integer.parseInt(resultado.getString("id_usuario")));
                usuario.setDescripcion(resultado.getString("descripcion"));
                usuario.setLogin(resultado.getString("login"));
                usuario.setPass(resultado.getString("pass"));
                //Join con Tipos de usuario para traer el tipo. 
                
                usuarioData.add(usuario);

            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar el usuario /n" + e);
            System.out.print(e);
        }

        return usuarioData;
    }
}
