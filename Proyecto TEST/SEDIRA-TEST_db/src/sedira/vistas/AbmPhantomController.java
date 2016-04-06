/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Phantom;
import sedira.model.PhantomDAO;
import sedira.model.RadionuclidoDAO;
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
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNombrePhantom;
    @FXML
    private TextField txtIdPhantom;
    
   
    //Objeto Phantom auxiliar. 
    private  Phantom phantom;
    // Stage aux
    private Stage dialogStage;
    // Valor Descripcion aux para items de phantom 
    private ValorDescripcion itemPhantom;
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
             
        if (phantom.getIdPhantom() != -1){         
             /**
             * Obtiente el Phantom seleccionado en la busqueda del formulario phantom.fxml
             */
            //Atributos de nombre y id. 
            txtNombrePhantom.setEditable(true);
            txtNombrePhantom.setText(phantom.getPhantomNombre());
            txtIdPhantom.setText(String.valueOf(phantom.getIdPhantom()));
            txtIdPhantom.setEditable(false);
            txtIdPhantom.setDisable(true);
           
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Comportamiento de botones. 
            
            
        } else {
                     
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear un Phantom");
            //Apago los TextField
            txtNombrePhantom.setEditable(true);
            txtIdPhantom.setDisable(true);
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
           
            //Prendo los botones. 
            
        }
            
            
    }
    /**
     * 
     * @param itemPhantom 
     */
    public void setItemPhantom (ValorDescripcion itemPhantom){
        Phantom phantomActual = FuncionesGenerales.getPhantomActual();
        this.itemPhantom = itemPhantom;
        txtIdPhantom.setText(String.valueOf(phantomActual.getIdPhantom()));
        txtNombrePhantom.setText(phantomActual.getPhantomNombre());
        txtPropiedad.setText(itemPhantom.getDescripcion());
        txtValor.setText(itemPhantom.getValor().toString());
        txtUnidad.setText(itemPhantom.getUnidad());
    }
    
     /**
     * Metodo llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public  void btnGuardarDatos() throws SQLException {
       // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
            if (validarDatosEntrada()){
                //Validacion preguntando si esta seguro guardar cambios.
                switch (dialogStage.getTitle()) {
                    case "Crear un Phantom":
                        phantom.setPhantomNombre(txtNombrePhantom.getText());
                        phantom.setPropiedades(listaAtributoPhantom);
                        break;
                    case "Modificar nombre del Phantom":
                        phantom.setPhantomNombre(txtNombrePhantom.getText());
                        break;
                    case "Modificar Items":
                        itemPhantom.setDescripcion(txtPropiedad.getText());
                        itemPhantom.setUnidad(txtUnidad.getText());
                        System.out.print(itemPhantom.getUnidad());
                        itemPhantom.setValor(Double.parseDouble(txtValor.getText()));
                        break;
                }
                    // Si las validaciones son correctas se guardan los datos. 
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
        switch (dialogStage.getTitle()) {
            case "Crear un Phantom":

                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del phantom ? ");
                break;
            case "Modificar nombre del Phantom":

                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del phantom? ");
                break;
            case "Modificar Items":
                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del phantom?");
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dialogStage.close();
        } else {

        }
    }

    /**
     * metodo para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {
        txtUnidad.setText("");
        txtPropiedad.setText("");
        txtValor.setText("");
    }

    
    public boolean validarDatosEntrada() throws SQLException {
        String mensajeError = "";
        String nombrePhantom = txtNombrePhantom.getText();
        if ("Crear un Phantom".equals(this.dialogStage.getTitle())||"Modificar nombre del Phantom".equals(this.dialogStage.getTitle())) {
            // Solo valido
            if (txtNombrePhantom.getText() == null || txtNombrePhantom.getText().length() == 0) {
                mensajeError += "Nombre del Phantom Invalido!";
            }
            if (PhantomDAO.buscaNombre(nombrePhantom)==false){
                mensajeError+= "El nombre del phantom ya existe!";
            }
        } else {

            if (txtPropiedad.getText() == null || txtPropiedad.getText().length() == 0) {
                mensajeError += "Nombre de Propiedad Invalido! \n";
            }


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