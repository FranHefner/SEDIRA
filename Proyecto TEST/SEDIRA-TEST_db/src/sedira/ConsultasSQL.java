/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import sedira.model.ConexionDB;

/**
 *
 * @author INVAP
 */
public class ConsultasSQL {
    
    private void VerificarUserPass(String Usuario, String Password)            
    {
       ConexionDB conexion = new ConexionDB();
                
        try {
            
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                      "SELECT count(*), "                         
                            + "NOW() AS HoraServer "
                      + "FROM usuarios "
                     + "WHERE login = ? AND pass = ? "
                  + "GROUP BY ID_USUARIO");  
                  
                // LLAVE = 'llave'
                consulta.setString(1, Usuario);
                consulta.setString(1, Password);
        
                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                 conexion.desconectar();
                 
         /*        byte[] key = (SALT2 + username + password).getBytes("UTF-8");
MessageDigest sha = MessageDigest.getInstance("SHA-1");
key = sha.digest(key);
key = Arrays.copyOf(key, 16); // use only first 128 bit

SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

*/

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("Login OK");
                alerta.showAndWait();
             
        } catch (SQLException e) {
            System.out.println("Ocurrió un error verificar el usuario/contraseña " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error verificar el usuario/contraseña " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
}
