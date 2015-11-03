/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;

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
    private ObservableList <Organo> organo = FXCollections.observableArrayList(); 
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
      //Inicializo los botones. 
        btnLimpiarValores.setDisable(true);
        btnQuitarOrgano.setDisable(true);
        
      // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());
       
        // Limpieza de los detalles de organos. 
        FuncionesGenerales.mostrarDetalleOrgano(null, griOrgano);
        
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
    /**
     * Setea el Phantom a editar. Se edita el phantom porque lo organos estan incluidos dentro de un phantom en particular.  
     * @param phantom a editar. 
     */
    public void setPhantom (Phantom phantom){
        this.phantom = phantom;
        String aux = "Órganos pertenecientes al Phantom: " + phantom.getPhantomNombre();
        phantomInfo.setText(aux);
        
        if (phantom.getIdPhantom() != -1){         
            /**
             * Obtiente el Phantom seleccionado en la busqueda del formulario phantom.fxml
             */
            griOrgano.setItems(phantom.getOrgano());
            //Prendo los botones
            btnLimpiarValores.setDisable(false);
            btnAgregarOrgano.setDisable(false);
        
        } else {
           
        }
            
            
    }
        
    /**
     * Este metodo setea en los textFields la informacion que el usuario selecciona de la tabla de organos. 
     * @param organo es el organo seleccionado desde la tabla. 
     */
    @FXML
    public void mostrarDetalleSeleccion (Organo organo){
        btnQuitarOrgano.setDisable(false);
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
     * Metodo que controla la agregacion de items valor descripcion a la tabla de info phantoms. 
     */
    @FXML
    public  void btnAgregar() {
       Organo organoAux = new Organo(null, -1,-1);    
       //Completo los datos en el objeto organo  con lo ingresado por el usuario. 
       organoAux.setNombreOrgano(txtOrganoNombre.getText());
       organoAux.setOrganMass(Double.valueOf(txtOrganoMasa.getText()));
       //Agrego el objeto a la lista de atributos de phantom
       if (phantom.getOrgano()!= null){
           //el phantom no posee una lista de organos
           phantom.getOrgano().add(organoAux);
       } else {
           //el phanton no posee organos asignados. organo es una lista. 
           organo.add(organoAux);
           phantom.setOrgano(organo);
       }
       
       //lo muestro en la tabla
       FuncionesGenerales.mostrarDetalleOrgano(phantom.getOrgano(),griOrgano);
       //Limpio los valores en los textField para el nuevo agregado
       btnLimpiarValores();
    }
    
    /**
     * Metodo que controla la eliminacion de items en la tabla de organos. 
     */
    @FXML
    public  void btnQuitar() {
        int selectedIndex = griOrgano.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                    griOrgano.getItems().remove(selectedIndex);
                    btnLimpiarValores();
            } else { //TODO 
                    // Nothing selected.
                   /* Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();*/
            }
        
                 
        
    }        

    /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() {
        // TODO: VALIDACIONES.  
        // la llamada a la db se realiza desde PhantomController.  
              
        guardarDatos = true;
        dialogStage.close();
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
    private void btnCancel() {
        dialogStage.close();
    }
    
    /**
     * metodo para el control del Boton Limpiar Valores. 
     * limpia los datos agregados en los textFields del formulario. 
     */
    @FXML
    private void btnLimpiarValores(){
    txtOrganoNombre.setText("");
    txtOrganoMasa.setText("");
    
    }
   
}
