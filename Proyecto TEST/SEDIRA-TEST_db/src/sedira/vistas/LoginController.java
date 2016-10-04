/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.ConsultasSQL;
import sedira.Security;
import sedira.model.Usuario;

/**
 * FXML Controller class
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Label lblInicio;

    @FXML
    private Usuario infoLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnAceptar_click() throws Exception {
        String Usuario;
        String Contraseña;

        Usuario = txtUsuario.getText();
        Contraseña = txtPassword.getText();

        infoLogin = ConsultasSQL.VerificarUserPass(Usuario, Contraseña);

        IniByCode(infoLogin.getTipoUsuario());

        String usuario = Usuario;
        String usuarioEnc = Security.encrypt(usuario);
        String usuarioDec = Security.decrypt(usuarioEnc);

        System.out.println("Plain Text : " + usuario);
        System.out.println("Encrypted Text : " + usuarioEnc);
        System.out.println("Decrypted Text : " + usuarioDec);

        String password = Contraseña;
        String passwordEnc = Security.encrypt(password);
        String passwordDec = Security.decrypt(passwordEnc);

        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);

    }

    private void IniByCode(int CodigoInicio) throws IOException {

        if (CodigoInicio == 1) {
            MenuPrincipalController.TipoUsuario = "Cientifico";
            CodigoInicio = 219346217;
        }
        if (CodigoInicio == 2) {
            MenuPrincipalController.TipoUsuario = "Medico";
            CodigoInicio = 219346217;
        }
        if (CodigoInicio == 3) {
            MenuPrincipalController.TipoUsuario = "Administrador";
            CodigoInicio = 219346217;
        }

        if (CodigoInicio == 219346217) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Ingresando al sistema...");
            alerta.setHeaderText(null);
            alerta.setContentText("Bienvenido " + infoLogin.getDescripcion());
            alerta.show();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            Stage stageLogin = (Stage) btnCerrar.getScene().getWindow();
            stageLogin.close();
            alerta.close();

        }

    }

    @FXML
    private void btnCerrar_click() throws Exception {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onEnter() throws Exception {

        btnAceptar_click();

    }

}
