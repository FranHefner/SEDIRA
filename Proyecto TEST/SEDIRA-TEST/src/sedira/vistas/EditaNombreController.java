/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Phantom;

/**
 * FXML Controller class
 *  Clase controladora para el cambio de nombres de PHANTOMS y RADIONUCLIDOS. 
 * @author Quelin Pablo, Hefner Francisco
 */
public class EditaNombreController implements Initializable {
    
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtAtributo;
    @FXML
    private Label lblInformacion; 
    
    @FXML
    private Stage dialogStage;
    private boolean guardarDatos;
    private Phantom phantom; 
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
     * Comportamiento del boton aceptar. 
     * Guarda el nombre del phantom editado. 
     */
    public void btnAceptar (){
      
      if (validarDatosEntrada()){  
         Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Guardar datos");
        alert.setHeaderText("Atención!");
        alert.setContentText("Desea guardar los datos modificados y salir?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            phantom.setPhantomNombre(txtAtributo.getText());
            guardarDatos = true;
            dialogStage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
       
      }
    }
    /**
     * Metodo que controla el comportamiento del boton Cancelar. 
     */
    public void btnCancelar (){
         Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancelar edición");
        alert.setHeaderText("Atención!");
        alert.setContentText("Esta seguro de cancelar la edición? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dialogStage.close();
        } else {
            
        }
    } 
        
     
     /**
     * Metodo que retorna si el usuario presiono el boton Guardar Datos / En este caso el boton dice Aceptar. 
     * @return guardarDatos 
     */
    public boolean isGuardarDatosClicked(){
        return this.guardarDatos;
    }
    public void setPhantom(Phantom phantom){
        this.phantom = phantom;
        
        if (phantom.getIdPhantom() != -1){         
           lblInformacion.setText("Editar phantom = " +phantom.getPhantomNombre());
             
        } else {
            
            //Genero un Nuevo IdPhantom.
            lblInformacion.setText("Nuevo phantom con Id = "+ConsultasDB.getNewIdPhantom()+" ");
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Agregar Phantom");
            //Activo los TextField
           
            
        }
            
    }
    
    public boolean validarDatosEntrada (){
        String mensajeError = "";
        if (txtAtributo.getText() == null || txtAtributo.getText().length() == 0){
                mensajeError += "Nombre de Propiedad Invalido! \n";
            }
        if (mensajeError.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);

            alert.showAndWait();
            return false;
        }
    }
    
    
}
