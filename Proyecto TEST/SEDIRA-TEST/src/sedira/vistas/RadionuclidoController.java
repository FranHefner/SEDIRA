/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 * FXML Controller class
 * Clase controladora de la intefaz de  usuario Administrador de Radionúclidos. 
 * @author Quelin Pablo, Hefner Francisco. 
 */
public class RadionuclidoController implements Initializable {
    
    @FXML 
    private TableView <Radionuclido> griRadionuclido;
    @FXML
    private TableColumn <Radionuclido, String> clNombreRadNuclido;
    
    @FXML
    private TableView <ValorDescripcion> griInfoRadNuclido;
    @FXML
    private TableColumn <ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidad;
    
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnNuevo;
    @FXML
    private TextField txtCampoBusqueda;
    
    
    //Lista Observable para el manejo de phantoms
    private ObservableList <Radionuclido> radionuclidoData;
    //Lista Observable para el manejo de la informacion de los radionuclidos. 
    public static ObservableList <ValorDescripcion> infoRadNuclido;
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //obtengo el listado de los radionuclidos existentes. 
        radionuclidoData = ConsultasDB.obtenerRadionuclidos();
        
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        // Limpieza de los detalles de Radionuclido. 
        showDetalleRadionuclido(null);

        //Inicializo la tabla de radionuclido - Trae el resultado de la busqueda.  
        clNombreRadNuclido.setCellValueFactory(cellData -> cellData.getValue().getNombreRadNuclidoProperty());
    
        //Listener para la seleccion del phantom en la lista de phantoms que trae la busqueda.
        griRadionuclido.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionRadionuclido(newValue));         
    }    
    
    /**
     * Al buscar el radionuclido , los muestra en la lista para su seleccion. 
     */
    @FXML
    public void buscarRadionuclido(){
       griRadionuclido.setItems(FuncionesGenerales.FiltroListaRadNuclido(griRadionuclido, radionuclidoData, txtCampoBusqueda));
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
     * Metodo que muestra los detalles del radionuclido seleccionado en la tabla de resultado de busqueda. 
     * @param radionuclidoActual radionuclido que se selecciona. 
     */
    public void seleccionRadionuclido(Radionuclido radionuclidoActual) 
    {  
        btnEditar.setDisable(false);
        if (radionuclidoActual != null)
        {
            infoRadNuclido = radionuclidoActual.getPropiedades();
            griInfoRadNuclido.setItems(infoRadNuclido);
            
        } else {
        
        
      
        }
    }
    
    
    
}
