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
    private TableView griOrgano;
    @FXML
    private TableColumn clOrganoNombre;
    @FXML
    private TableColumn clOrganoMasa;
    
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
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
