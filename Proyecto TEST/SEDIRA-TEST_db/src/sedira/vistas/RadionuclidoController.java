/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Radionuclido;
import sedira.model.RadionuclidoDAO;
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
    private Button btnModificarRadioNuclido;
    @FXML
    private Button btnAgregarRadionuclido;
    @FXML
    private Button btnAgregarItem;
    @FXML
    private Button btnEliminarItem;
    @FXML
    private Button btnEditarItem;
    @FXML
    private TextField txtCampoBusqueda;
    
    
    //Lista Observable para el manejo de phantoms
    private ObservableList <Radionuclido> radionuclidoData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de la informacion de los radionuclidos. 
    private ObservableList <ValorDescripcion> infoRadNuclido = FXCollections.observableArrayList();
    // Stage auxiliar. 
    private Stage primaryStage;
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //obtengo el listado de los radionuclidos existentes. 
        //Version Anterior // radionuclidoData = ConsultasDB.obtenerRadionuclidos();
        radionuclidoData = RadionuclidoDAO.obtenerListaRadNuclido();
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        // Limpieza de los detalles de Radionuclido. 
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griInfoRadNuclido);

        //Inicializo la tabla de radionuclido - Trae el resultado de la busqueda.  
        clNombreRadNuclido.setCellValueFactory(cellData -> cellData.getValue().getNombreRadNuclidoProperty());
        buscarRadionuclido();
        //Listener para la seleccion del radionuclido en la lista de radionuclidos que trae la busqueda.
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
     * Metodo que muestra los detalles del radionuclido seleccionado en la tabla de resultado de busqueda. 
     * @param radionuclidoActual radionuclido que se selecciona del grilla de radionuclidos. 
     */
    public void seleccionRadionuclido(Radionuclido radionuclidoActual) 
    {  
        FuncionesGenerales.setRadioNuclidoActual(radionuclidoActual);
        if (radionuclidoActual != null)
        {
            //infoRadNuclido = radionuclidoActual.getPropiedades(); // La coleccion de propiedades del RadNuclido
            
            infoRadNuclido = RadionuclidoDAO.obtenerInfoRadNuclido(radionuclidoActual);
            //griInfoRadNuclido.setItems(infoRadNuclido);
            griInfoRadNuclido.setItems(infoRadNuclido);
            //Prendo botones.
            //System.out.print(radionuclidoActual.getNombreRadNuclido());
            btnAgregarItem.setDisable(false);
            
            
        } else {
            //TODO Si no se selecciona un radionuclido desde la lista. 
        }
    }
    
    public boolean mostrarRadionuclidoDialog (Radionuclido radionuclido){
        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmRadionuclido.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar RadioNuclido");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Pone el radionuclido en el controlador AbmRadionuclidoController. 
            AbmRadionuclidoController controladorAbmRadNuclido = loader.getController();
            controladorAbmRadNuclido.setDialogStage(dialogStage);
            // le paso el Item
            controladorAbmRadNuclido.setRadionuclido(radionuclido);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmRadNuclido.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }  
    
    public boolean mostrarItemRadionuclidoEditDialog (ValorDescripcion itemRadionuclido){
        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmRadionuclido.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar items");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Pone el radionuclido en el controlador AbmRadionuclidoController. 
            AbmRadionuclidoController controladorAbmRadNuclido = loader.getController();
            controladorAbmRadNuclido.setDialogStage(dialogStage);
            // le paso el Item
            controladorAbmRadNuclido.setItemRadionuclido(itemRadionuclido);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmRadNuclido.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }  
        
    public void getSelectedItemFromTabla(){
        ValorDescripcion selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnEliminarItem.setDisable(false);
            btnEditarItem.setDisable(false);
        }
        
    }         
    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la edicion del RadioNuclido. 
     */
    @FXML
    public void btnModificarItem (){
        //Radionuclido selectedRadNuclido = griRadionuclido.getSelectionModel().getSelectedItem();
        ValorDescripcion selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
                boolean guardarCambiosClicked = mostrarItemRadionuclidoEditDialog(selectedItem);
                  
                if (guardarCambiosClicked) {
                        
                }

        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("No existe item para modificar");

            alert.showAndWait();

        }
    }
   
    /**
     * Metodo que controla el comportamiento del boton Quitar Item. 
     */
    @FXML
    public void btnEliminarItem (){
         int selectedIndex = griInfoRadNuclido.getSelectionModel().getSelectedIndex();
         String mensaje = griInfoRadNuclido.getSelectionModel().getSelectedItem().getDescripcion() + "  " +
                           griInfoRadNuclido.getSelectionModel().getSelectedItem().getValor() + "  " + 
                            griInfoRadNuclido.getSelectionModel().getSelectedItem().getUnidad();
            if (selectedIndex >= 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Item");
                alert.setHeaderText("Atención!");
                alert.setContentText("Esta seguro que desea eliminar el item seleccionado? \n"+mensaje);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    griInfoRadNuclido.getItems().remove(selectedIndex);griInfoRadNuclido.getItems().remove(selectedIndex);
                    //Llamada BD para la eliminacion. 
                    //Llamada BD para la eliminacion. 
                    
                } else {

                }
                
            } else {
                // No se selecciono ningun item. 
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Debe seleccionar un item para eliminar");

                alert.showAndWait();
            }
    }
     
    /**
     * Metodo que controla el comportamiento del boton modificar item.
     */
    @FXML
    public void btnAgregarItem (){
         
       Radionuclido auxRadionuclido = FuncionesGenerales.getRadioNuclidoActual();
       ValorDescripcion itemRadionuclido = new ValorDescripcion (null,0,null); 
       boolean guardarCambiosClicked = mostrarItemRadionuclidoEditDialog(itemRadionuclido);
                    
        if (guardarCambiosClicked){
            infoRadNuclido.add(itemRadionuclido);
            auxRadionuclido.setPropiedades(infoRadNuclido);
            ConsultasDB.modificarRadionuclido(auxRadionuclido, griRadionuclido.getSelectionModel().getSelectedIndex());
            
        }
                        
                                      
    }
       
    
    /**
     * Metodo para el comportamiento del boton Crear radioNuclido
     * El radionuclido primero se crea sin elemento de tipo propiedad valor. 
     */
    @FXML
    public void btnAgregarRadionuclido () throws SQLException{
        Radionuclido tempRadNuclido = new Radionuclido (-1,"",null);
		boolean guardarCambiosClicked = mostrarRadionuclidoDialog(tempRadNuclido);
		if (guardarCambiosClicked) {
			//ConsultasDB.radionuclidoData = ConsultasDB.obtenerRadionuclidos();
                        
                        RadionuclidoDAO.agregarRadionuclido(tempRadNuclido);
                        radionuclidoData=RadionuclidoDAO.obtenerListaRadNuclido();
                        griRadionuclido.setItems(radionuclidoData);
        }
    }
    
    @FXML 
    public void btnModificarRadionuclido (){
        Radionuclido radioNuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        boolean guardarCambiosClicked = mostrarRadionuclidoDialog(radioNuclidoActual);
		if (guardarCambiosClicked) {
			ConsultasDB.radionuclidoData = ConsultasDB.obtenerRadionuclidos();
                        ConsultasDB.modificarRadionuclido(radioNuclidoActual, griRadionuclido.getSelectionModel().getSelectedIndex());
                        
        }
    }
       
   
    
    
}
