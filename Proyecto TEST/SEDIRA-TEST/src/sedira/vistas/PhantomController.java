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
    private ObservableList <Phantom> phantomData; // ConsultasDB.iniciarPhantomsDefecto();; 
    //Lista Observable para el manejo de organos
    public static ObservableList <Organo> organosData;
    //Lista Observable para el manejo de phantoms
    public static ObservableList <ValorDescripcion> listaPropiedadValor;
    
    private AplicacionPrincipal aplicacionPrincipal;
    private Stage primaryStage;
    
    
               
    /**
     * Initializes the controller class.
     * El método setCellValueFactory(...) que aplicamos sobre las columnas de la tabla 
     * se usa para determinar qué atributos de la clase Phantom / Organo deben ser usados para 
     * cada columna particular. La flecha -> indica que estamos usando una característica 
     * de Java 8 denominada Lambdas. Otra opción sería utilizar un PropertyValueFactory, 
     * pero entonces no ofrecería seguridad de tipo (type-safe).
     * 
     * El método initialize() es invocado automáticamente tras cargar el fxml.
     * En ese momento, todos los atributos FXML deberían ya haber sido inicializados..
     */
    @Override   
    public void initialize(URL url, ResourceBundle rb) {
        
        //Traigo los datos de los phantoms existentes. 
        phantomData   = ConsultasDB.ObtenerPhantoms();
        System.out.print(phantomData.size());
        
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());
       
        // Limpieza de los detalles de organos. 
        FuncionesGenerales.mostrarDetalleOrgano(null, griOrgano);
              
       //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griValorDescripcionPhantom);
        
        //Inicializo la tabla de Phantom. 
        clPhantomNombre.setCellValueFactory(cellData -> cellData.getValue().phantomNombreProperty());
        //Listener para la seleccion del phantom en la lista de phantoms que trae la busqueda.
        buscarPhantom();
        griPhantom.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionPhantom(newValue));     
            
                
       
          
     
        
    } 
    
    /**
     * Abre el formulario de AbmPhantom.FXml con los datos correspondientes al phantom seleccionado.
     * Si el usuario presiona guardar datos, los cambios son guardados dentro del phantom y returna
     * true. 
     * @param phantom para ser editado
     * @return true si el usuario clickea Guardar datos o retorna falso en caso contrario
     */
    public boolean mostrarPhantomEditDialog (Phantom phantom){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmPhantom.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Phantom");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // setea el Phantom dentro del controlador AbmPhantomController. .
            AbmPhantomController controladorAbmPhantom = loader.getController();
            controladorAbmPhantom.setDialogStage(dialogStage);
            controladorAbmPhantom.setPhantom(phantom);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmPhantom.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
    
    public boolean mostrarOrganoEditDialog (Phantom phantom){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmOrgano.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar/Agregar Organo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // Pone el organo en el controlador AbmOrganoController. 
            AbmOrganoController controladorAbmOrgano = loader.getController();
            controladorAbmOrgano.setDialogStage(dialogStage);
            // le paso el Phantom porque los phantom son los que contienen organos. 
            controladorAbmOrgano.setPhantom(phantom);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmOrgano.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        
       
    }
          
    /**
     * Al buscar el phantom , los muestra en la lista para su seleccion. 
     */
    @FXML
    public void buscarPhantom(){
       griPhantom.setItems(FuncionesGenerales.FiltroListaPhantom(griPhantom, phantomData, txtCampoBusqueda));
    }

    /**
     * Metodo que muestra los detalles del phantom seleccionado. 
     * @param phantomActual 
     */
    public void seleccionPhantom(Phantom phantomActual) 
    {  
        btnEditarPhantom.setDisable(false);
        btnEditarOrganos.setDisable(false);
        if (phantomActual != null)
        {
            organosData =  phantomActual.getOrgano();     
            griOrgano.setItems(organosData);
            listaPropiedadValor = phantomActual.getPropiedades();
            griValorDescripcionPhantom.setItems(listaPropiedadValor);
            System.out.print(griPhantom.getSelectionModel().getSelectedIndex());
        } else {
        
        
      
        }
    }
    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la edicion del Phantom. 
     */
    @FXML
    public void btnEditarPhantom_click (){
        Phantom selectedPhantom = griPhantom.getSelectionModel().getSelectedItem();
            
        if (selectedPhantom != null) {
                boolean guardarCambiosClicked = mostrarPhantomEditDialog(selectedPhantom);
                if (guardarCambiosClicked) {
                        //seleccionPhantom(selectedPhantom);
                }

        } else {
                // Nothing selected. TODO Control por la No seleccion. 

        }
        //TODO. Actualizar la lista de atributos de phantom y de organos una vez cerrada 
        // las ventanas de edicion y nuevo. 
        
       
    }
    
   
    /**
     * Metodo para el comportamiento del boton NUEVO. 
     */
    @FXML
    public void btnNuevoPhantom_click () {
		Phantom tempPhantom = new Phantom(-1,"",null,null);
		boolean guardarCambiosClicked = mostrarPhantomEditDialog(tempPhantom);
		if (guardarCambiosClicked) {
			ConsultasDB.phantomData = ConsultasDB.ObtenerPhantoms();
                        ConsultasDB.AgregarPhantom(tempPhantom);
		}
    }
    
    /**
     * Metodo para el comportamiento al seleccionar un item de la lista de organos. 
     */
    @FXML
    public void griSeleccionarOrgano(){
        btnEditarOrganos.setDisable(false);
    }
    
    /**
     * Metodo para el comportamiento del boton Editar Organo. 
     * @throws IOException 
     */
    @FXML
    public void btnEditarOrgano_click (){
        Phantom selectedPhantom = griPhantom.getSelectionModel().getSelectedItem();
            
        if (selectedPhantom != null) {
                boolean guardarCambiosClicked = mostrarOrganoEditDialog(selectedPhantom);
                if (guardarCambiosClicked) {
                        //showPersonDetails(selectedPhantom);
                }

        } else {
                // Nothing selected.

        }
    }
    
    
   
}
