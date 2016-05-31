/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class LoginController implements Initializable {

    
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void btnAceptar_click()
    {
        String Usuario;
        String Contraseña;
        
        Usuario = txtUsuario.getText();
        Contraseña = txtPassword.getText();

                
    }
    
}
