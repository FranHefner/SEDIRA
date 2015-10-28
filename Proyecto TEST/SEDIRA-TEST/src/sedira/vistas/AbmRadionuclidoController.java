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
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 * FXML Controller class
 *  Clase controladora de la interfaz Editar/Nuevo Radionuclido. 
 * @author Quelin Pablo, Hefner Francisco.
 */
public class AbmRadionuclidoController implements Initializable {
    
    @FXML 
    private TextField txtRadNuclidoNombre;
    @FXML 
    private TextField txtIdRadNuclido;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    
    @FXML
    private TableView <ValorDescripcion> griInfoRadNuclido;
    @FXML
    private TableColumn <ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidad;
    
    
    @FXML
    private Button btnLimpiarValores;
    @FXML   
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    
    //******************** variables 
    //Objeto Lista de radionuclidos  auxiliar. 
    private ObservableList <ValorDescripcion> listaAtributoRadNuclido = FXCollections.observableArrayList(); 
    //Objeto radionuclido auxiliar. 
    private Radionuclido radionuclido;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    
    
    /**
     * Inicializa la clase initialize del controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        
        // Listener para los cambios en la tabla de informacion de Phantoms 
       griInfoRadNuclido.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> mostrarDetalleSeleccion(newValue));
        // Limpieza de los detalles de Radionuclido. 
        showDetalleRadionuclido(null);
        btnEditar.setDisable(true);
        btnAgregar.setDisable(true);
        btnQuitar.setDisable(true);
        
        
    }
    
    /**
     * Este metodo setea en los textFields la informacion que el usuario selecciona de la tabla de propiedades de radionuclidos. 
     * @param valorDescripcion es el tipo de dato que almacena la tabla que muestra la informacion de las propiedades que 
     * contiene un radionuclido. 
     */
    @FXML
    public void mostrarDetalleSeleccion (ValorDescripcion valorDescripcion){
        btnEditar.setDisable(false);
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
     * Metodo que muestra el detalle de los radionuclidos en la tabla tipo Valor-Descripcion. 
     * @param infoRadionuclido lista de todos los atributos del radionuclido. 
     */
    @FXML
    public void showDetalleRadionuclido(ObservableList<ValorDescripcion> infoRadionuclido) {
       //Aca se utiliza la tabla Descripcion - Valor. 
        griInfoRadNuclido.setItems(infoRadionuclido);
      
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
     * Setea el Radionuclido a editar. 
     * @param radionuclido a editar. 
     */
    public void setRadionuclido (Radionuclido radionuclido){
        this.radionuclido = radionuclido;
                
        if (radionuclido.getIdRadNuclido() != -1){         
            /**
             * Obtiente el Radionuclido seleccionado en la busqueda del formulario radionuclido.fxml
             */
            txtIdRadNuclido.setText(String.valueOf(radionuclido.getIdRadNuclido()));
            txtRadNuclidoNombre.setText(radionuclido.getNombreRadNuclido());
            griInfoRadNuclido.setItems(radionuclido.getPropiedades());
            //Prendo los botones
            btnLimpiarValores.setDisable(false);
            btnAgregar.setDisable(false);
        
        } else {
            //Genero un nuevo Id para el radionuclido. 
            txtIdRadNuclido.setText(String.valueOf(ConsultasDB.getNewIdRadNuclido()));
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Agregar Radionuclido");
            
            //Prendo los botones. 
            btnLimpiarValores.setDisable(false);
            btnAgregar.setDisable(false);
            //btnQuitar.setDisable(false);
        }
    }
    
    /**
     * Este metodo setea en los textFields la informacion que el usuario selecciona de la tabla de radionuclidos. 
     * @param radionuclido es el radionuclido seleccionado desde la tabla. 
     */
    @FXML
    public void mostrarDetalleSeleccion (Radionuclido radionuclido){
        btnQuitar.setDisable(false);
        if (radionuclido != null){
          
            txtIdRadNuclido.setText(String.valueOf(radionuclido.getIdRadNuclido()));
            txtRadNuclidoNombre.setText(radionuclido.getNombreRadNuclido());
            
        } else {
           txtIdRadNuclido.setText("");
           txtRadNuclidoNombre.setText("");
           
        }
    }
    /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() {
       // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo 
         
        radionuclido.setIdRadNuclido(ConsultasDB.getNewIdPhantom());
        radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
        
        
        guardarDatos = true;
        dialogStage.close();
    }
    /**
     * Metodo que controla la agregacion de items valor descripcion a la tabla de propiedades de radionuclido. 
     */
    @FXML
    public  void btnAgregar() {
       //Objeto aux ValorDescripcion.  
       ValorDescripcion radNuclidoPropiedades = new ValorDescripcion (null,0,null);    
       //Completo los datos en el objeto radionuclido  con lo ingresado por el usuario. 
       radNuclidoPropiedades.setDescripcion(txtPropiedad.getText());
       radNuclidoPropiedades.setValor(Double.parseDouble(txtValor.getText()));
       radNuclidoPropiedades.setUnidad(txtUnidad.getText());
       
       //le asigno al radionuclido el objeto
       if (radionuclido.getPropiedades() != null){
           radionuclido.getPropiedades().add(radNuclidoPropiedades);
       } else {
           //el radionuclido no posee atributos/propiedades aun. 
           //Agrego el objeto a la lista de atributos del radionuclido. 
           listaAtributoRadNuclido.add(radNuclidoPropiedades);
           radionuclido.setPropiedades(listaAtributoRadNuclido);
       }
       
       //lo muestro en la tabla
       refrescarTablaRadionuclido (radionuclido.getPropiedades());
       //Limpio los valores en los textField para el nuevo agregado
       btnLimpiarValores_click();
    }
    
    /**
     * Metodo que controla la edicion de un item seleccionado en la tabla de propiedades del radionuclido. 
     */
    @FXML
    public  void btnEditar() {
       //Objeto aux ValorDescripcion.  
       ValorDescripcion radNuclidoPropiedades = new ValorDescripcion (null,0,null);    
       //Inicializo la lista de propiedades con la informacion del radionuclido
       listaAtributoRadNuclido = radionuclido.getPropiedades();
       
       //obtengo el indice del elemento seleccionado. 
       int index = griInfoRadNuclido.getSelectionModel().getSelectedIndex();
       
       radNuclidoPropiedades.setDescripcion(txtPropiedad.getText());
       radNuclidoPropiedades.setValor(Double.parseDouble(txtValor.getText()));
       radNuclidoPropiedades.setUnidad(txtUnidad.getText());
       
       listaAtributoRadNuclido.set(index, radNuclidoPropiedades);
       //lo muestro en la tabla
       refrescarTablaRadionuclido (radionuclido.getPropiedades());
       //Limpio los valores en los textField para el nuevo agregado
       btnLimpiarValores_click();
    }
    /**
     * Metodo que controla la eliminacion de items valor descripcion a la tabla de info Radionuclido. 
     */
    @FXML
    public  void btnQuitar() {
        int selectedIndex = griInfoRadNuclido.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                    griInfoRadNuclido.getItems().remove(selectedIndex);
                    
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
     * Muestra el detalle del Radionuclido. 
     * @param infoRadNuclido
     */
     @FXML
    public void refrescarTablaRadionuclido(ObservableList<ValorDescripcion> infoRadNuclido) {
       //Aca se utiliza la tabla Descripcion - Valor. 
        griInfoRadNuclido.setItems(infoRadNuclido);
      
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
    txtUnidad.setText("");
    txtPropiedad.setText("");
    txtValor.setText("");
    }
                

    
}
