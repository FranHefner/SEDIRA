/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.ConsultasDB;
import sedira.model.Organo;
import sedira.model.ValorDescripcion;
import sedira.model.Phantom;

/**
 * Clase controladora para el Administrador de Phantoms. 
 * @author Hefner Francisco, Quelin Pablo 
 */
public class PhantomController {
  //Declaracion de los elementos de la interfaz grafica. 
    @FXML
    private TableView <Phantom> griPhantom;
    @FXML
    private TableView <Organo> griOrgano;
    @FXML
    private TableColumn <Phantom, String> clPhantomNombre;
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
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNuevoNombrePhantom;
    @FXML
    private TextField txtCampoBusqueda;
    
    //Lista de Phantoms
    public static  ObservableList <Phantom> listaPhantom = ConsultasDB.listaPhantom();
    
       
// Fin declaracion. 
    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrgano());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMass().asString());
       
        // Limpieza de los detalles de organos. 
        showDetalleOrgano(null);

        //listener para los cambios en la seleccion. Y mostrarlo en la Tabla de Organo. 
        /*griOrgano.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> seleccionOrgano(newValue));*/
        
       
        
       //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        showDetallePhantom(null);
       
         //listener para los cambios en la seleccion. Y mostrarlo en la Tabla de Phantoms 
          /*griValorDescripcionPhantom.getSelectionModel().selectedItemProperty().addListener(
              (observable, oldValue, newValue) -> seleccionPhantom(newValue));*/
         
        //Inicializo la lista de Phantoms para el ChoiceBox
        //initListaPhantom();
        
         // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Phantom> filteredData = new FilteredList<>(listaPhantom, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        txtCampoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Phantom -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Phantom.getPhantom().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else {
                    return false; // Does not match.
                }
            });
        });
        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Phantom> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(griPhantom.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        griPhantom.setItems(listaPhantom);
    }   
     /**
     * Muestra el detalle de los Organos pertenecientes al Phantom encontrado en la busqueda. 
     * @param organo 
     */
    @FXML
    private void showDetalleOrgano(ObservableList<Organo> organo) {
        griOrgano.setItems(organo);
    }
    
    /**
     * Muestra el detalle del Phantom en la tabla Phantoms 
     * @param infoPhantom 
     */
     @FXML
    private void showDetallePhantom(ObservableList<ValorDescripcion> infoPhantom) {
       //Aca se utiliza la tabla Descripcion - Valor. 
        griValorDescripcionPhantom.setItems(infoPhantom);
      
    }
    
   
}
