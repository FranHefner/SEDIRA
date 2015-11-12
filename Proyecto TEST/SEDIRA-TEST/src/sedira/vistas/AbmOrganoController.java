/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import org.controlsfx.dialog.Dialogs;

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
    private Button btnLimpiarValores;
    
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
           
            //Prendo los botones
            btnLimpiarValores.setDisable(false);
          
        
        } else {
           
        }
            
            
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
       
      
       btnLimpiarValores();
    }
    
          
        
            

    /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() {
       // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
            if (validarDatosEntrada()){
                
                guardarDatos = true;
                dialogStage.close();
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
    public void btnCancel_click() {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar edición");
        alert.setHeaderText("Atención!");
        alert.setContentText("Esta seguro de cancelar la edición del órgano? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dialogStage.close();
        } else {
            
        }
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
    /**
     * Validacion de los datos de entrada para Organos. 
     * @return 
     */
    public boolean validarDatosEntrada (){
        String mensajeError = "";
            if (txtOrganoNombre.getText()== null || txtOrganoNombre.getText().length() == 0){
                mensajeError+= "Nombre del órgano Invalido!";
            }
        
            if (txtOrganoMasa.getText() == null || txtOrganoMasa.getText().length() == 0 ){
                mensajeError += "Valor invalido! \n";
            } else {
                if (Double.valueOf(txtOrganoMasa.getText()) == 0.0){
                mensajeError += "Adventencia - Valor = 0.0 \n";
                 } else {
                //trato de parsear el valor como un double. 
                try{
                    Double.parseDouble(txtOrganoMasa.getText());
                } catch (NumberFormatException e){
                    mensajeError+= "El atributo valor debe ser un número real!\n";
                    }
                }   
              
        }
        // TODO validacion Unidad. 
         
        if (mensajeError.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);

            alert.showAndWait();
            return false;
        }
        
    }
    
}
