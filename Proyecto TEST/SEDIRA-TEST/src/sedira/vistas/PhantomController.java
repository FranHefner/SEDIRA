/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.ValorDescripcion;
import sedira.model.Phantom;
import sedira.AplicacionPrincipal;
/**
 * Clase controladora para el Administrador de Phantoms. 
 * @author Hefner Francisco, Quelin Pablo 
 */
public class PhantomController  implements Initializable  {
  //Declaracion de los elementos de la interfaz grafica. 
    @FXML
    private  TableView <Phantom> griPhantom;
    @FXML
    private TableColumn <Phantom, String> clPhantomNombre;
    
          
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
    private Button btnEditarOrganos;
    @FXML
    private TextField txtCampoBusqueda;
    
    //Lista Observable para el manejo de phantoms
    public static ObservableList <Phantom> phantomData;
    //Lista Observable para el manejo de organos
    public static ObservableList <Organo> organosData;
    //Lista Observable para el manejo de phantoms
    public static ObservableList <ValorDescripcion> listaPropiedadValor;
    
    private AplicacionPrincipal aplicacionPrincipal;
    private Stage primaryStage;
    
    
               
    /**
     * Initializes the controller class.
     */
    @Override   
    public void initialize(URL url, ResourceBundle rb) {
        
        //Traigo los datos de los phantoms existentes. 
        phantomData   = ConsultasDB.ListaPhantom();
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrgano());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMass().asString());
       
        // Limpieza de los detalles de organos. 
        showDetalleOrgano(null);

              
       //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        showDetallePhantom(null);
       
        //Inicializo la tabla de Phantom. 
        clPhantomNombre.setCellValueFactory(cellData -> cellData.getValue().getPhantomNombre());
        //Listener para la seleccion del phantom en la lista de phantoms que trae la busqueda.
        griPhantom.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> SeleccionPhantom(newValue));     
            
                
       
          
     
        
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
    
    /**
     * Al buscar el phantom , los muestra en la lista para su seleccion. 
     */
    @FXML
    private void buscarPhantom(){
       griPhantom.setItems(FuncionesGenerales.FiltroListaPhantom(griPhantom, phantomData, txtCampoBusqueda));
    }

    /**
     * Metodo que muestra los detalles del phantom seleccionado. 
     * @param phantomActual 
     */
    private void SeleccionPhantom(Phantom phantomActual) 
    {  
        btnEditarPhantom.setDisable(false);
        
        if (phantomActual != null)
        {
            organosData =  phantomActual.getOrgano();     
            griOrgano.setItems(organosData);
            listaPropiedadValor = phantomActual.getPropiedades();
            griValorDescripcionPhantom.setItems(listaPropiedadValor);
            
        } else {
        
        
      
        }
    }
    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la edicion del Phantom. 
     */
    @FXML
    private void btnEditarPhantom_click (){
        Phantom phantomSeleccionado = griPhantom.getSelectionModel().getSelectedItem();
        if (phantomSeleccionado != null){
            boolean guardarCambios = aplicacionPrincipal.mostrarPhantomEditDialog (phantomSeleccionado);
            if (guardarCambios){
                SeleccionPhantom(phantomSeleccionado);
            }
        }   else {
            //No se selecciono nada
            //CONTROL DE ERROR
            }   
                
        /*// hay que pasarle el objeto asi ya autocompleta el detalle del phantom. 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AbmPhantom.fxml"));
        Scene scene = new Scene(root);
              stage.setScene(scene);
        
        stage.setTitle("Editar Phantom");
        stage.show(); */
    }
    
    /**
     * Metodo para el comportamiento del boton NUEVO. 
     */
    @FXML
    private void btnNuevoPhantom_click () throws IOException{
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AbmPhantom.fxml"));
        Scene scene = new Scene(root);
              stage.setScene(scene);
        
        stage.setTitle("Agregar Phantom");
        stage.show(); 
    }
    
    /**
     * Metodo para el comportamiento al seleccionar un item de la lista de organos. 
     */
    @FXML
    private void griSeleccionarOrgano(){
        btnEditarOrganos.setDisable(false);
    }
    
    /**
     * Metodo para el comportamiento del boton Editar Organo. 
     * @throws IOException 
     */
    @FXML
    private void btnEditarOrgano_click () throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AbmOrgano.fxml"));
        Scene scene = new Scene(root);
              stage.setScene(scene);
        
        stage.setTitle("Editar Organos");
        stage.show(); 
    }
    
    
   
}
