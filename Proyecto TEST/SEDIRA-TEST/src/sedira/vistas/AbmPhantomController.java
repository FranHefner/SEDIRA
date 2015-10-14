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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.Phantom;
import sedira.model.ValorDescripcion;

/**
 * FXML Controller class
 * Clase controladora para el Abm de Phantoms. 
 * @author Hefner Francisco, Quelin Pablo
 */
public class AbmPhantomController implements Initializable {
        
    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnQuitar;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNombrePhantom;
    @FXML
    private TextField txtIdPhantom;
    
    @FXML
    private TableView griValorDescripcionPhantom;
    @FXML
    private TableColumn clVdDescripcion;
    @FXML
    private TableColumn clVdValor;
    @FXML
    private TableColumn clVdUnidad;
    
    
    //Objeto Phantom auxiliar. 
    private Phantom phantom;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }
   
    /**
     * metodo para el control del Boton Limpiar Valores. 
     * limpia los datos agregados en los textFields del formulario. 
     */
    @FXML
    private void btnLimpiarValores_click(){
    txtUnidad.setText("");
    txtPropiedad.setText("");
    txtValor.setText("");
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
     * Setea el Phantom a editar dentro del Formulario de edicion. 
     * @param phantom 
     */
    public void setPhantom (Phantom phantom){
        this.phantom = phantom;
        
        txtNombrePhantom.setText(phantom.getPhantomNombre());
        txtIdPhantom.setText(String.valueOf(phantom.getIdPhantom()));
        
        //Agregar el Contenido de la lista valor Descripcion. 
        
        
//Demas atributos del Phantom. 
    }
    
    
    /**
     * Metodo que retorna si el usuario presiono el boton Guardar Datos. 
     * @return guardarDatos 
     */
    public boolean isOkClicked(){
        return this.guardarDatos;
    }
    
    
    
    
    
}
