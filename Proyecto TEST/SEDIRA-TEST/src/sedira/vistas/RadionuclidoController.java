/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    private ObservableList <Radionuclido> radionuclidoData = FXCollections.observableArrayList();;
    //Lista Observable para el manejo de la informacion de los radionuclidos. 
    private ObservableList <ValorDescripcion> infoRadNuclido = FXCollections.observableArrayList();;
    // Stage auxiliar. 
    private Stage primaryStage;
           
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
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griInfoRadNuclido);

        //Inicializo la tabla de radionuclido - Trae el resultado de la busqueda.  
        clNombreRadNuclido.setCellValueFactory(cellData -> cellData.getValue().getNombreRadNuclidoProperty());
        buscarRadionuclido();
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
     * Metodo que muestra los detalles del radionuclido seleccionado en la tabla de resultado de busqueda. 
     * @param radionuclidoActual radionuclido que se selecciona. 
     */
    public void seleccionRadionuclido(Radionuclido radionuclidoActual) 
    {  
        FuncionesGenerales.setRadioNuclidoActual(radionuclidoActual);
        //btnEditar.setDisable(false);
        if (radionuclidoActual != null)
        {
            infoRadNuclido = radionuclidoActual.getPropiedades();
            griInfoRadNuclido.setItems(infoRadNuclido);
            
        } else {
        
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
                        //seleccionPhantom(selectedPhantom);
                }

        } else {
                // Nothing selected. TODO Control por la No seleccion. 
            // Dialogs.

        }
    }
   
    /**
     * Metodo que controla el comportamiento del boton Quitar Item. 
     */
    @FXML
    public void btnEliminarItem (){
         int selectedIndex = griInfoRadNuclido.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                    griInfoRadNuclido.getItems().remove(selectedIndex);
                    //Llamada BD
            } else {
                    // Nothing selected.
                   /* Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();*/
            }
    }
    
    @FXML
    public void btnAgregarItem (){
         
       Radionuclido auxRadionuclido = FuncionesGenerales.getRadioNuclidoActual();
       ValorDescripcion itemRadionuclido = new ValorDescripcion (null,0,null); 
       boolean guardarCambiosClicked = mostrarItemRadionuclidoEditDialog(itemRadionuclido);
 
                    if (infoRadNuclido != null){
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
    public void btnAgregarRadionuclido (){
        Radionuclido tempRadNuclido = new Radionuclido (-1,"",null);
		boolean guardarCambiosClicked = mostrarRadionuclidoDialog(tempRadNuclido);
		if (guardarCambiosClicked) {
			ConsultasDB.radionuclidoData = ConsultasDB.obtenerRadionuclidos();
                        ConsultasDB.agregarRadionuclido(tempRadNuclido);
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
