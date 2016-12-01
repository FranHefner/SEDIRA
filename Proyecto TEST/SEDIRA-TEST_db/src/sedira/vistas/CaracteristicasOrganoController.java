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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.model.ValorDescripcion;

/**
 * FXML Controller class
 *
 * @author Quelin Pablo, Hefner Francisco.
 * 
 */
public class CaracteristicasOrganoController implements Initializable {
    
    @FXML
    private TextField txtPhantom;
    @FXML
    private TextField txtOrgano;
    
    @FXML
    private TableView<ValorDescripcion> griValorDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;
    
    @FXML
    private Button btnModificarItem;
    @FXML
    private Button btnAgregarItem;
    @FXML
    private Button btnEliminarItem;
    @FXML
    private Button btnCerrar;
    
    
    //Lista Observable para el manejo de la informacion de los phantoms
    private ObservableList<ValorDescripcion> infoOrgano = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtOrgano.setFocusTraversable(false);
        txtPhantom.setFocusTraversable(false);
        
        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        
        
        
    }
    
    /**
     * Método que controla el comportamiento del boton modificar item.
     */
    @FXML
    public void btnAgregarItem() {
        
    }
    
    /**
     * Método para el comportamiento del boton editar. Abre un dialogo para la
     * edicion de un atributo del organo. 
     */
    @FXML
    public void btnModificarItem() {
        
    }  
    
    /**
     * Método que controla el comportamiento del boton Quitar Item.
     */
    @FXML
    public void btnEliminarItem() {
        
    }
    
    /**
     * Método que controla el comportamiento del boton Quitar Item.
     */
    @FXML
    public void btnCerrar() {
        
    }
    
    
    
    
    
}
