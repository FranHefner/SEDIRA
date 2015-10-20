/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.Organo;
import sedira.model.Phantom;

/**
 * FXML Controller class
 * Clase Controladora para AbmOrgano.Fxml
 * @author Quelin Pablo, Hefner Francisco
 */
public class AbmOrganoController implements Initializable {
    //declaración de elementos de interfaz gráfica
    
    @FXML 
    private TextField txtOrganoNombre;
    @FXML 
    private TextField txtOrganoMasa;
    
    @FXML
    private TableView <Organo> griOrgano;
    @FXML
    private TableColumn <Organo, String> clOrganoNombre;
    @FXML
    private TableColumn <Organo, String> clOrganoMasa;
    
    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnAgregarOrgano;
    @FXML
    private Button btnQuitarOrgano;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label phantomInfo;
    
    //******************** variables 
    //Objeto Organo auxiliar. 
    private  Organo organo;
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
        // Inicializo la tabla de Organos
      // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());
       
        // Limpieza de los detalles de organos. 
        showDetalleOrgano(null);
        
        //Listener para la seleccion del organo en la lista de organos que tiene un phantom. 
         griOrgano.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> mostrarDetalleSeleccion(newValue));     
    }  
    /**
     * Setea el Stage para este Formulario o Dialog. 
     * @param dialogStage 
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setPhantom (Phantom phantom){
        this.phantom = phantom;
        String aux = "Órganos pertenecientes al Phantom: " + phantom.getPhantomNombre();
        phantomInfo.setText(aux);
        //txtPropiedad.setDisable(false);
       // txtValor.setDisable(false);
       // txtUnidad.setDisable(false);
        if (phantom.getIdPhantom() != -1){         
            //Atributos de nombre y id. 
            
            //txtNombrePhantom.setText(phantom.getPhantomNombre());
            //txtIdPhantom.setText(String.valueOf(phantom.getIdPhantom()));

            //Completo los organos que corresponden al phantom. 
            griOrgano.setItems(phantom.getOrgano());
            //Prendo los botones
            btnLimpiarValores.setDisable(false);
            //btnAgregar.setDisable(false);
        
        } else {
           
        }
            
            
    }
    /**
     * Muestra el detalle de los Organos pertenecientes al Phantom encontrado en la busqueda. 
     * @param organo 
     */
    @FXML
    private void showDetalleOrgano(ObservableList<Organo> organo) {
        griOrgano.setItems(organo);
       
    }
    
    /**
     * Este metodo setea en los textFields la informacion que el usuario selecciona de la tabla de organos. 
     * @param organo es el organo seleccionado desde la tabla. 
     */
    @FXML
    public void mostrarDetalleSeleccion (Organo organo){
        
        //btnQuitar.setDisable(false);
        if (organo != null){
          
            txtOrganoNombre.setText(organo.getNombreOrgano());
            txtOrganoMasa.setText(organo.getOrganMass().toString());
            
        } else {
           txtOrganoNombre.setText("");
           txtOrganoMasa.setText("");
           
        }
    }
    
     /**
     * Metodo que retorna si el usuario presiono el boton Guardar Datos. 
     * @return guardarDatos 
     */
    public boolean isGuardarDatosClicked(){
        return this.guardarDatos;
    }
    
     /**
     * Metodo que se llama al presionar el boton cancelar. 
     */
    @FXML
    private void btnCancel_click() {
        dialogStage.close();
    }
    
    /**
     * metodo para el control del Boton Limpiar Valores. 
     * limpia los datos agregados en los textFields del formulario. 
     */
    @FXML
    private void btnLimpiarValores_click(){
    txtOrganoNombre.setText("");
    txtOrganoMasa.setText("");
    }
}
