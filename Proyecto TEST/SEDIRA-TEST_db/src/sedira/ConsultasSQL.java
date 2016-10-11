/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javax.swing.JOptionPane;
import sedira.model.ConexionDB;
import sedira.model.Usuario;

/**
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class ConsultasSQL {
    /**
     * Metodo que es llamado en el momento de iniciar sesión 
     * Se encarga de comprobar el nombre de usuario y la contraseña almacenados en la base de datos. 
     * @param Usuario
     * @param Password
     * @return
     * @throws Exception 
     */
    public static Usuario VerificarUserPass(String Usuario, String Password) throws Exception {
        ConexionDB conexion = new ConexionDB();
        String passwordEnc = Security.encrypt(Password);
        String UsuarioEnc = Security.encrypt(Usuario);
        Usuario UsuarioLogin;
        
        

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " SELECT *, NOW() AS HoraServer"
                    + " FROM usuarios "
                    + "     JOIN usuariotipos "
                    + "       ON usuarios.id_usuarioTipos = usuariotipos.id_usuarioTipos "
                    + " WHERE     login = ? "
                    + "     AND pass = ? "
                    + "GROUP BY ID_USUARIO");

            consulta.setString(1, UsuarioEnc);
            consulta.setString(2, passwordEnc);
            

         //   String passwordDec = Security.decrypt(passwordEnc);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {

                UsuarioLogin = new Usuario( resultado.getString("descripcion"), resultado.getInt("id_UsuarioTipos"));
                Date NOW = resultado.getDate("HoraServer");
                
               //   public Usuario(String descripcion, int TipoUsuario ) {
                        
                return UsuarioLogin;
            } else {
                consulta.close();
                conexion.desconectar();

                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("Error de validación, ingrese los datos nuevamente");
                alerta.showAndWait();
                return new Usuario("Error de validación", -1);
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            /*System.out.println(e.getErrorCode());
            JOptionPane.showMessageDialog(null, "Ocurrió un error verificar el usuario/contraseña " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            */
            return new Usuario("Error génerico", -2);
        }

    }
    
    /**
     * Método que se llama al crear un nuevo usuario. 
     * @param Descripcion
     * @param Usuario
     * @param Password
     * @param Id_TipoUsuario
     * @return True si el usuario fué creado. False si ocurrio algun error. 
     * @throws Exception 
     */
    public static boolean GuardarUserPass(String Descripcion, String Usuario, String Password, int Id_TipoUsuario) throws Exception {
        ConexionDB conexion = new ConexionDB();

       
        String UsuarioEnc = Security.encrypt(Usuario);
        String passwordEnc = Security.encrypt(Password);

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO usuarios (descripcion,login,pass,id_usuarioTipos)"
                    + "VALUES (?,?,?,?)");
            consulta.setString(1, Descripcion);
            consulta.setString(2, UsuarioEnc);
            consulta.setString(3, passwordEnc);
            consulta.setInt(4, Id_TipoUsuario);

            String passwordDec = Security.decrypt(passwordEnc);

            consulta.executeUpdate();
            consulta.close();
            conexion.desconectar();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Guardado OK");
            alerta.showAndWait();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al guardar el usuario/contraseña " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el usuario/contraseña " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
}
