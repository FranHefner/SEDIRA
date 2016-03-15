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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
            
            txtIdRadNuclido.setDisable(true);
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear un Radionúclido");
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
      
            if (validarDatosEntrada()){
                        switch (dialogStage.getTitle()){
                            case "Crear un Radionúclido":
//                                radionuclido.setIdRadNuclido(Integer.parseInt(txtIdRadNuclido.getText()));
                                radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                                radionuclido.setPropiedades(listaAtributoRadNuclido);
                                break;
                            case "Modificar nombre del Radionúclido":
                                radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                                break;
                            case "Modificar Items":
                                itemRadionuclido.setDescripcion(txtPropiedad.getText());
                                itemRadionuclido.setUnidad(txtUnidad.getText());
                                itemRadionuclido.setValor(Double.parseDouble(txtValor.getText()));
                                break;
                        } 
                    
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
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancelar edición");
        alert.setHeaderText("Atención!");
        alert.setContentText("Esta seguro de cancelar la edición del item de radionúclido. ");

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
    public void btnLimpiarValores_click(){
    txtUnidad.setText("");
    txtPropiedad.setText("");
    txtValor.setText("");
    }
                
    public boolean validarDatosEntrada (){
        String mensajeError = "";
        if ("Crear un Radionuclido".equals(this.dialogStage.getTitle())){
            // Solo valido
            if (txtRadNuclidoNombre.getText()== null || txtRadNuclidoNombre.getText().length() == 0){
                mensajeError+= "Nombre del Radionuclido Invalido!";
            }
        } else {
            /*
            Debido  a la utilizacion del mismo formulario para el abm de radionuclido. 
            Cuando se modifica el nombre los campos de unidad, propiedad y valor estan desactivados. 
            Por eso se pregunta si estan prendidos los textfields
            */
            if (txtPropiedad.isDisable()==false){
                       
                if (txtPropiedad.getText() == null || txtPropiedad.getText().length() == 0){
                    mensajeError += "Nombre de Propiedad Invalido! \n";
                }
            }

            if (txtValor.isDisable()==false){
                if (txtValor.getText() == null || txtValor.getText().length() == 0 ){
                    mensajeError += "Valor invalido! \n";
                } else {
                    if (Double.valueOf(txtValor.getText()) == 0.0){
                    mensajeError += "Adventencia - Valor = 0.0 \n";
                     } else {
                    //trato de parsear el valor como un double. 
                    try{
                        Double.parseDouble(txtValor.getText());
                    } catch (NumberFormatException e){
                        mensajeError+= "El atributo valor debe ser un número real!\n";
                    }
                    }   
                }
            }
        }
        // TODO validacion Unidad. 
         
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
