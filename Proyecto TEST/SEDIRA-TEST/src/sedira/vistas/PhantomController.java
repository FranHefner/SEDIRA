/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.model.Organo;
import sedira.model.ValorDescripcion;

/**
 * Clase controladora para el Administrador de Phantoms. 
 * @author Hefner Francisco, Quelin Pablo 
 */
public class PhantomController {
  //Declaracion de los elementos de la interfaz grafica. 
    @FXML
    private TableView <Organo> griOrgano;
    @FXML
    private TableColumn <Organo, String> clOrganoNombre;
    @FXML
    private TableColumn <Organo, String> clOrganoMasa;
    @FXML
    private TableView <ValorDescripcion> griValorDescripcionPhantom;
    @FXML
    private TableColumn <ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidad;
    @FXML
    private Button btnEditarPhantom;
    @FXML
    private Button btnNuevoPhantom;
    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private TextField txtNombrePhantom;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNuevoNombrePhantom;
  
// Fin declaracion. 
}
