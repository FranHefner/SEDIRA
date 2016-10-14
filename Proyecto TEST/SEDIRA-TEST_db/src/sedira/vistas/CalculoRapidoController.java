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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.model.IRadionuclidoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Radionuclido;
import sedira.model.RadionuclidoDAOsql;
import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CalculoRapidoController implements Initializable {

    @FXML
    private TextField txtPesoPaciente;
    @FXML
    private TextField txtPesoOrgano;
    @FXML
    private ComboBox<?> cbRadionuclidos;
    @FXML
    private Button btnCalcular;
    @FXML
    private ListView<?> lstListaRdionuclidos;
    @FXML
    private ProgressBar prgBarraProgreso;
    @FXML
    private TextField txtResultado;
    @FXML
    private Button btnCerrar;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;
    
    
    @FXML
    private TableView<ValorDescripcion> griInfoRadNuclido;


    /**
     * Initializes the controller class.
     */
     //Lista Observable para el manejo de phantoms
    private ObservableList<Radionuclido> radionuclidoData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de la informacion de los radionuclidos.
    private ObservableList<ValorDescripcion> infoRadNuclido = FXCollections.observableArrayList();
    // Stage auxiliar.
    private Stage primaryStage;
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IRadionuclidoDAO rad = new RadionuclidoDAOsql();
     //Instancia de objeto tipo IValorDescripcionDAO. Se inicializa como ValorDescripcionDAOsql.  
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //obtengo el listado de los radionuclidos existentes.
        //Version Anterior // radionuclidoData = ConsultasDB.obtenerRadionuclidos();
        radionuclidoData = rad.obtenerListaRadNuclido();
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos .

        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        // Limpieza de los detalles de Radionuclido.
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null, griInfoRadNuclido);

        //Inicializo la tabla de radionuclido - Trae el resultado de la busqueda.
      //  clNombreRadNuclido.setCellValueFactory(cellData -> cellData.getValue().getNombreRadNuclidoProperty());
       // buscarRadionuclido();
        //Listener para la seleccion del radionuclido en la lista de radionuclidos que trae la busqueda.
      
       
       // cbRadionuclidos.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> seleccionRadionuclido(newValue));
    }    

    @FXML
    private void btnCerrar_click(ActionEvent event) {
        
      Stage stage = (Stage) btnCerrar.getScene().getWindow();

            stage.close();
    }
    public void seleccionRadionuclido(Radionuclido radionuclidoActual) {
        FuncionesGenerales.setRadioNuclidoActual(radionuclidoActual);
        if (radionuclidoActual != null) {
            //Obtengo de la Base de datos la lista de propiedades 
            infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
            //griInfoRadNuclido.setItems(infoRadNuclido);
            griInfoRadNuclido.setItems(infoRadNuclido);
            //Prendo botones.         

        } 
    }
     public void getSelectedItemFromTabla() {
        ValorDescripcion selectedItem = new ValorDescripcion();
        selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();
       

    }

}
