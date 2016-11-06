/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import sedira.CodigosErrorSQL;
import sedira.Security;

/**
 * Clase de acceso de datos para Usuarios
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class UsuarioDAOsql implements IUsuarioDAO {

    /**
     * Método para agregar un usuario a la base de datos
     *
     * @param usuario
     * @param tipoUsuario
     */
    @Override
    public void agregarUsuario(Usuario usuario, int tipoUsuario) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //obtengo el nombre de usuario para la busqueda. 
        String nombreUsuario = usuario.getLogin();
        int TipoUsuario;

        try {
            String UsuarioEnc = Security.encrypt(usuario.getLogin());
            String passwordEnc = Security.encrypt(usuario.getPass());
          
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "INSERT INTO usuarios (descripcion, login, pass, id_usuarioTipos) "
                        + "VALUES(?,?,?,?)");
                consulta.setString(1, usuario.getDescripcion());
                consulta.setString(2, Security.encrypt(usuario.getLogin()));
                consulta.setString(3, Security.encrypt(usuario.getPass()));
                consulta.setInt(4, tipoUsuario);

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();

                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El usuario fué agregado.");
                alerta.showAndWait();
          
        } catch (SQLException e) {
             CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAOsql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que modifica un usuario existente en la base de datos
     *
     * @param usuario
     * @param tipoUsuario
     */
    @Override
    public void modificarUsuario(Usuario usuario, int tipoUsuario) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreUsuario = usuario.getLogin();

        try {
            String UsuarioEnc = Security.encrypt(usuario.getLogin());
            String passwordEnc = Security.encrypt(usuario.getPass());
            if (buscaUsuario(nombreUsuario) == false) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE usuarios SET login = ?"
                        + ",pass = ?"
                        + ", descripcion=?"
                        + ",id_usuarioTipos=? "
                        + "WHERE id_usuario = ?");

                consulta.setString(1, UsuarioEnc);
                consulta.setString(2, passwordEnc);
                consulta.setString(3, usuario.getDescripcion());
                consulta.setInt(4, tipoUsuario);
                consulta.setInt(5, usuario.getIdUsuario());

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                conexion.desconectar();
            } else {

            }
        } catch (SQLException e) {
             CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAOsql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que eliminar un usuario existente en la base de datos
     *
     * @param idUsuario
     */
    @Override
    public void eliminarUsuario(int idUsuario) {
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
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

    }

    /**
     * Método que busca un nombre de usuario en la base de datos. En caso que
     * exista returna True. En caso que no exista retorna False
     *
     * @param usuario
     * @return True o False
     */
    @Override
    public boolean buscaUsuario(String usuario) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        
        try {
//            String decUsuario = Security.decrypt(usuario);
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT login FROM usuarios "
                    + "WHERE login = ?");
            consulta.setString(1, usuario);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                conexion.desconectar(); 
                return true;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                conexion.desconectar();
                return false;
                
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
            conexion.desconectar();
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAOsql.class.getName()).log(Level.SEVERE, null, ex);
            conexion.desconectar();
            return false;
        }
       
    }     
    
    

    /**
     * Método que retorna la informacion completa de la tabla de usuarios
     *
     * @return
     */
    @Override
    public ObservableList<Usuario> obtenerUsuarios() {
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

        } catch (SQLException | NumberFormatException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return usuarioData;
    }

    /**
     * Método que retorna el tipo de usuario a partir de un id de usuario pasado
     * por parametros.
     *
     * @param id
     * @return
     */
    @Override
    public String obtenerTipoUsuario(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String tipoUsuario = "";
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT usuariotipos.descripcion FROM usuarios "
                    + "JOIN usuariotipos "
                    + "ON usuarios.id_usuarioTipos = usuariotipos.id_usuarioTipos "
                    + "where usuarios.id_usuario = ?");
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar

                //obtencion de los datos desde la bd.
                tipoUsuario = resultado.getString("usuariotipos.descripcion");

            }

            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
             CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return tipoUsuario;

    }
}
