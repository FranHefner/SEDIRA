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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.Usuario;

/**
 * FXML Controller class
 * Clase controladora para AmbUsuario.FXML
 * @author Quelin Pablo, Hefner Francisco
 */
public class AbmUsuarioController implements Initializable {
    //Declaracion de variables FXML

    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtPass;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTipoUsuario;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnLimpiar;
    
    
     //Objeto Phantom auxiliar. 
    private  Usuario usuario;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    /**
     * Setea el Stage para este Formulario o Dialog. 
     * @param dialogStage 
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Setea el Usuario a editar dentro del Formulario de edicion. 
     * Si el Usuario que viene por parametros tiene el id = -1, significa que el 
     * se busca crear un nuevo User. 
     * @param usuario a editar. 
     */
    public void setPhantom (Usuario usuario){
        this.usuario = usuario;
             
        if (usuario.getIdUsuario()!= -1){         
             /**
             * Obtiente el Usuario seleccionado en Usuario.fxml. 
             */
            //Atributos de nombre y id. 
            txtIdUsuario.setText(String.valueOf(usuario.getIdUsuario()));
            txtNombreUsuario.setEditable(true);
            txtNombreUsuario.setText(usuario.getLogin());
            
            txtPass.setEditable(true);
            txtPass.setText(usuario.getPass());
            
            txtDescripcion.setEditable(true);
            txtDescripcion.setText(usuario.getDescripcion());
            
            txtTipoUsuario.setEditable(true);
            txtTipoUsuario.setText("Algo a ocurrido... verlo con fran");
            
            //Comportamiento de botones. 
            
            
        } else {
                     
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear nuevo usuario");
            //Apago los TextField
            txtNombreUsuario.setEditable(true);
            txtIdUsuario.setDisable(true);
                       
            //Prendo los botones. 
            
        }
            
            
    }
    
    
    
}
