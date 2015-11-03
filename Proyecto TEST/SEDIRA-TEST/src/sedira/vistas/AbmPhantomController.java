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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
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
    private TableView <ValorDescripcion>  griValorDescripcionPhantom;
    @FXML
    private TableColumn <ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidad;
    
    
    //Objeto Phantom auxiliar. 
    private  Phantom phantom;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Lista Observable para el manejo de phantoms
    private ObservableList <ValorDescripcion> listaAtributoPhantom = FXCollections.observableArrayList(); 
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        mostrarDetalleSeleccion(null);
       
       // Listener para los cambios en la tabla de informacion de Phantoms 
       griValorDescripcionPhantom.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> mostrarDetalleSeleccion(newValue));
    }
    
    
    /**
     * Este metodo setea en los textFields la informacion que el usuario selecciona de la tabla de propiedades de phantoms. 
     * @param valorDescripcion es el tipo de dato que almacena la tabla que muestra la informacion de las propiedades que 
     * contiene un organo. 
     */
    @FXML
    public void mostrarDetalleSeleccion (ValorDescripcion valorDescripcion){
        
        btnQuitar.setDisable(false);
        if (valorDescripcion != null){
            txtPropiedad.setDisable(false);
            txtValor.setDisable(false);
            txtUnidad.setDisable(false);
            txtPropiedad.setText(valorDescripcion.getDescripcion());
            txtValor.setText(valorDescripcion.getValor().toString());
            txtUnidad.setText(valorDescripcion.getUnidad());
            
        } else {
            txtPropiedad.setText("");
            txtValor.setText("");
            txtUnidad.setText("");
        }
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
     * Si el Phanton que viene por parametros tiene el id = -1, significa que el 
     * usuario busca crear un nuevo Phantom. 
     * @param phantom a editar. 
     */
    public void setPhantom (Phantom phantom){
        this.phantom = phantom;
        txtPropiedad.setDisable(false);
        txtValor.setDisable(false);
        txtUnidad.setDisable(false);
        if (phantom.getIdPhantom() != -1){         
            //Atributos de nombre y id. 
            
            txtNombrePhantom.setText(phantom.getPhantomNombre());
            txtIdPhantom.setText(String.valueOf(phantom.getIdPhantom()));

            //Completo las propiedades del phantom. 
            griValorDescripcionPhantom.setItems(phantom.getPropiedades());
            //Prendo los botones
            btnLimpiarValores.setDisable(false);
            btnAgregar.setDisable(false);
        
        } else {
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Agregar Phantom");
            //Activo los TextField
            txtNombrePhantom.setEditable(true);
            
            
            
            //Genero un Nuevo IdPhantom.
            
            txtIdPhantom.setText(String.valueOf(ConsultasDB.getNewIdPhantom()));
            
            //Prendo los botones. 
            btnLimpiarValores.setDisable(false);
            btnAgregar.setDisable(false);
            //btnQuitar.setDisable(false);
        }
            
            
    }
    /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() {
       // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo 
         
        phantom.setIdPhantom(ConsultasDB.getNewIdPhantom());
        phantom.setPhantomNombre(txtNombrePhantom.getText());
        
        
        guardarDatos = true;
        dialogStage.close();
    }
    /**
     * Metodo que controla la agregacion de items valor descripcion a la tabla de info phantoms. 
     */
    @FXML
    public  void btnAgregar() {
       //objeto aux
        ValorDescripcion infoPhantomAux = new ValorDescripcion(null,0,null); 
       
       //Completo los datos en el objeto aux con lo ingresado por el usuario. 
       infoPhantomAux.setDescripcion(txtPropiedad.getText());
       infoPhantomAux.setValor(Double.parseDouble(txtValor.getText()));
       infoPhantomAux.setUnidad(txtUnidad.getText());
       
       //le asigno al phantom el objeto
       if (phantom.getPropiedades() != null){
           phantom.getPropiedades().add(infoPhantomAux);
       } else {
           //el phanton no posee atributos aun. 
           //Agrego el objeto a la lista de atributos de phantom
           listaAtributoPhantom.add(infoPhantomAux);
           phantom.setPropiedades(listaAtributoPhantom);
       }
       
       //lo muestro en la tabla
       FuncionesGenerales.mostrarDetalleTablaValorDescripcion(phantom.getPropiedades(), griValorDescripcionPhantom);
       //refrescarTablaPhantom(phantom.getPropiedades());
       //Limpio los valores en los textField para el nuevo agregado
       btnLimpiarValores_click();
      
        
    }
    
    /**
     * Metodo que controla la eliminacion de items valor descripcion a la tabla de info phantoms. 
     */
    @FXML
    public  void btnQuitar() {
        int selectedIndex = griValorDescripcionPhantom.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                    griValorDescripcionPhantom.getItems().remove(selectedIndex);
                    
            } else {
                    // Nothing selected.
                   /* Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();*/
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
        dialogStage.close();
    }
    
    /**
     * metodo para el control del Boton Limpiar Valores. 
     * limpia los datos agregados en los textFields del formulario. 
     */
    @FXML
    public void btnLimpiarValores_click(){
    txtUnidad.setText("");
    txtPropiedad.setText("");
    txtValor.setText("");
    }
    
    
    
    
    
}
