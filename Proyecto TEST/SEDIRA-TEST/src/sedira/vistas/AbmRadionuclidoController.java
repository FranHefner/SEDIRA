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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
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
    private ValorDescripcion itemRadionuclido;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    
    
    /**
     * Inicializa la clase initialize del controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
        
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
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Prendo los botones
        
        } else {
            //Genero un nuevo Id para el radionuclido. 
            txtIdRadNuclido.setText(String.valueOf(ConsultasDB.getNewIdRadNuclido()));
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Agregar Radionuclido");
            //apago los textFields.
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Prendo los botones. 
            
        }
    }
    public void setItemRadionuclido (ValorDescripcion itemRadionuclido){
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        this.itemRadionuclido = itemRadionuclido;
        txtIdRadNuclido.setText(String.valueOf(radionuclidoActual.getIdRadNuclido()));
        txtRadNuclidoNombre.setText(radionuclidoActual.getNombreRadNuclido());
        
        txtPropiedad.setText(itemRadionuclido.getDescripcion());
        txtValor.setText(itemRadionuclido.getValor().toString());
        txtUnidad.setText(itemRadionuclido.getUnidad());
    }
    
    /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() {
       // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde RadionuclidoController. Editar/Nuevo 
        if ("Agregar Radionuclido".equals(this.dialogStage.getTitle()) ){ 
            //Nuevo radionuclido, debe guardar el nombre y el id primero.
            radionuclido.setIdRadNuclido(Integer.parseInt(txtIdRadNuclido.getText()));
            radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
            radionuclido.setPropiedades(listaAtributoRadNuclido);
        }else { 
            //Modificacion del un radionuclido existente. 
            
            itemRadionuclido.setDescripcion(txtPropiedad.getText());
            itemRadionuclido.setUnidad(txtUnidad.getText());
            itemRadionuclido.setValor(Double.parseDouble(txtValor.getText()));
        }
        
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
