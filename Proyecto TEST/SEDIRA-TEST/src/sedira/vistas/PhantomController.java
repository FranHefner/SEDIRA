/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.ValorDescripcion;
import sedira.model.Phantom;
import sedira.AplicacionPrincipal;
import sedira.model.Radionuclido;


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
    
    @FXML
    private Button btnModificarItem;
    @FXML
    private Button btnAgregarItem;
    @FXML
    private Button btnEliminarItem;
    @FXML
    private Button btnAgregarOrgano;
    @FXML
    private Button btnEliminarOrgano;
    @FXML
    private Button btnModificarOrgano;
       
    //Lista Observable para el manejo de phantoms
    private ObservableList <Phantom> phantomData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de organos
    private ObservableList <Organo> organosData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de la informacion de los phantoms
    private ObservableList <ValorDescripcion> listaPropiedadValor = FXCollections.observableArrayList();
    
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
        //System.out.print(phantomData.size());
        
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
    
    public boolean mostrarEditaNombreDialog (Phantom phantom){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("EditaNombre.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // Pone el organo en el controlador AbmOrganoController. 
            EditaNombreController controlador = loader.getController();
            controlador.setDialogStage(dialogStage);
            // le paso el Phantom porque los phantom son los que contienen organos. 
            controlador.setPhantom(phantom);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();
            //Return
            return controlador.isGuardarDatosClicked();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } 
        
    }
    
    public boolean mostrarItemPhantomEditDialog (ValorDescripcion itemPhantom){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmPhantom.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar items ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // setea el Phantom dentro del controlador AbmPhantomController. .
            AbmPhantomController controladorAbmPhantom = loader.getController();
            controladorAbmPhantom.setDialogStage(dialogStage);
            controladorAbmPhantom.setItemPhantom(itemPhantom);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmPhantom.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }
      
    /**
     * Muestra el Formulario para la edicion de un organo. 
     * @param phantom
     * @return 
     */
    public boolean mostrarOrganoEditDialog (Phantom phantom){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmOrgano.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Agregar Organo");
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
     * Muestra el formulario para la edicion de un organo ya creado.  
     * @param selectedOrgano
     * @return 
     */
     public boolean mostrarItemOrganoEditDialog (Organo selectedOrgano){
        
        // cargo el nuevo FXML para crear un ventana tipo PopUp

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmOrgano.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Organo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // Pone el organo en el controlador AbmOrganoController. 
            AbmOrganoController controladorAbmOrgano = loader.getController();
            controladorAbmOrgano.setDialogStage(dialogStage);
            // El paramaetro para el controlador de organos sera un Organo. 
            controladorAbmOrgano.setOrgano(selectedOrgano);

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
        FuncionesGenerales.setPhantomActual(phantomActual);
        FuncionesGenerales.setIndice(griPhantom.getSelectionModel().getSelectedIndex());
        btnEditarPhantom.setDisable(false);
      
        if (phantomActual != null)
        {
            organosData =  phantomActual.getOrgano();     
            griOrgano.setItems(organosData);
            listaPropiedadValor = phantomActual.getPropiedades();
            griValorDescripcionPhantom.setItems(listaPropiedadValor);
            System.out.print(griPhantom.getSelectionModel().getSelectedIndex());
        } else {
         //Todo si no se selecciona ningun phantom de la lista
        
      
        }
    }
    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la edicion del Phantom. 
     */
    @FXML
    public void btnEditarPhantom_click () throws IOException{
        Phantom phantom = FuncionesGenerales.getPhantomActual();
        int index = griPhantom.getSelectionModel().getSelectedIndex();
        boolean guardarCambiosClicked = mostrarEditaNombreDialog(phantom);
                    
        if (guardarCambiosClicked){
            ConsultasDB.modificarPhantom(phantom,index);
        }
               
    }
    
   
    /**
     * Metodo para el comportamiento del boton NUEVO. 
     */
    @FXML
    public void btnNuevoPhantom_click () {
        /**
         * Este metodo crea una objeto de tipo Phantom Vacio. Luyego le asigna las propiedaes
         * y los organos que en la primer instancia tambien estan vacios. 
         * Las propiedades y los organose se agregan posteriormente con la utilizacion de los botones. 
         * Agregar item y Agregar organo. 
         * 
         */
        Phantom tempPhantom = new Phantom(-1,"",null,null);
        //Lista Observable para el manejo de la informacion de los phantoms
        ObservableList <ValorDescripcion> propiedadesPhantom = FXCollections.observableArrayList();
        //Lista Observable para el manejo de los organos de los phantoms
        ObservableList <Organo> organosPhantom = FXCollections.observableArrayList();
        
        boolean guardarCambiosClicked = mostrarEditaNombreDialog(tempPhantom);
        if (guardarCambiosClicked) {
                tempPhantom.setIdPhantom(ConsultasDB.getNewIdPhantom());
                tempPhantom.setPropiedades(propiedadesPhantom);
                tempPhantom.setOrgano(organosPhantom);
                ConsultasDB.AgregarPhantom(tempPhantom);
        }
    }
    
    /**
     * Metodo que controla el comportamiento del boton Agregar Organo.
     */
    @FXML
    public void btnAgregarOrgano (){
         
       Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        if (selectedPhantom != null) {
                boolean guardarCambiosClicked = mostrarOrganoEditDialog(selectedPhantom);
                if (guardarCambiosClicked) {
                    ConsultasDB.modificarPhantom(selectedPhantom,griPhantom.getSelectionModel().getSelectedIndex() );    
                }

        } else {
                // Nothing selected.

        }
                                   
    }
    
     /**
     * Metodo que controla el comportamiento del boton Eliminar Organo.
     */
    @FXML
    public void btnEliminarOrgano (){
         Organo selectedItem = griOrgano.getSelectionModel().getSelectedItem();
         int selectedIndex = griOrgano.getSelectionModel().getSelectedIndex();
         Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        
            if (selectedItem != null) {
                String mensaje = griOrgano.getSelectionModel().getSelectedItem().getNombreOrgano()+ "  " +
                           griOrgano.getSelectionModel().getSelectedItem().getOrganMass();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Item");
                alert.setHeaderText("Atención!");
                alert.setContentText("Esta seguro que desea eliminar el item seleccionado? \n"+mensaje);
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    griOrgano.getItems().remove(selectedIndex);
                     ConsultasDB.modificarPhantom(selectedPhantom,griPhantom.getSelectionModel().getSelectedIndex() );    
                    
                } else {

                }
                    
            } else {
                // No se selecciono ningun item. 
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("No hay items para eliminar");

                alert.showAndWait();
            }
                                    
    }
    /**
     * Metodo que controla el comportamiento del boton Quitar Item. 
     */
    @FXML
    public void btnEliminarItem (){
        ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
         int selectedIndex = griValorDescripcionPhantom.getSelectionModel().getSelectedIndex();
         
            if (selectedItem != null) {
                String mensaje = griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getDescripcion() + "  " +
                           griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getValor() + "  " + 
                            griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getUnidad();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Item");
                alert.setHeaderText("Atención!");
                alert.setContentText("Esta seguro que desea eliminar el item seleccionado? \n"+mensaje);
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    griValorDescripcionPhantom.getItems().remove(selectedIndex);
                    //Llamada BD para la eliminacion. 
                    ConsultasDB.modificarPhantom(selectedPhantom,griPhantom.getSelectionModel().getSelectedIndex() );    
                } else {

                }
                    
            } else {
                // No se selecciono ningun item. 
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("No hay items para eliminar");

                alert.showAndWait();
            }
    }
    /**
     * Metodo que controla el comportamiento del boton modificar item.
     */
    @FXML
    public void btnAgregarItem (){
         
       Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
       ValorDescripcion itemPhantom = new ValorDescripcion (null,0,null); 
       ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
       if (selectedItem != null){
        boolean guardarCambiosClicked = mostrarItemPhantomEditDialog(itemPhantom);
        if (guardarCambiosClicked){
            listaPropiedadValor.add(itemPhantom);
            auxPhantom.setPropiedades(listaPropiedadValor);
            ConsultasDB.modificarPhantom(auxPhantom,griPhantom.getSelectionModel().getSelectedIndex() );
        }
       }
       
                        
                                      
    }
    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la edicion de un atributo del Phantom. 
     */
    @FXML
    public void btnModificarItem (){
        Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
        ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
                boolean guardarCambiosClicked = mostrarItemPhantomEditDialog(selectedItem);
                  
                if (guardarCambiosClicked) {
                     ConsultasDB.modificarPhantom(auxPhantom,griPhantom.getSelectionModel().getSelectedIndex() );
                }

        } else {
           // No se selecciono ningun item. 
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("No existen items para modificar");

            alert.showAndWait();

        }
    }
    
    @FXML
    public void btnModificarOrgano (){
        Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
        Organo selectedOrgano = griOrgano.getSelectionModel().getSelectedItem();
        if (selectedOrgano != null){
            boolean guardarCambiosClicked = mostrarItemOrganoEditDialog (selectedOrgano);
            if (guardarCambiosClicked){
                //Bd Modificar Phantom
                ConsultasDB.modificarPhantom(auxPhantom,griPhantom.getSelectionModel().getSelectedIndex() );
            }
        }
        else {
           // No se selecciono ningun item. 
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("No existen items para modificar");

            alert.showAndWait();

        }
    }
    /**
     * Metodo para el control de botones. 
     */
    @FXML
    public void getSelectedPhantom(){
        Phantom phantom = griPhantom.getSelectionModel().getSelectedItem();
        if (phantom != null) {
            btnAgregarItem.setDisable(false);
            btnAgregarOrgano.setDisable(false);
        }
    }
    /**
     * Metodo para el control de los botones. 
     */
    @FXML
    public void getSelectedItemFromTabla(){
        
        ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnEliminarItem.setDisable(false);
            btnModificarItem.setDisable(false);
        } 
            
    }   
    /**
     * Metodo para el control de los botones. 
     */
    @FXML
    public void getSelectedItemFromTablaOrgano(){
        Organo selectedItem = griOrgano.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            btnEliminarOrgano.setDisable(false);
            btnModificarOrgano.setDisable(false);
        } else {
            btnEliminarOrgano.setDisable(true);
            btnModificarOrgano.setDisable(true);
        }
        
    }     
   
   
}
