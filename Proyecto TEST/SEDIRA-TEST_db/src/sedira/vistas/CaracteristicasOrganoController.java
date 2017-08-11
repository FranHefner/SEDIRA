/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import sedira.FuncionesGenerales;
import sedira.model.IOrganoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Organo;
import sedira.model.OrganoDAOsql;
import sedira.model.Phantom;
import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class
 * Clase controladora de interfaz gráfica CaracteristicasOrgano.fxml
 * @author Quelin Pablo, Hefner Francisco.
 * 
 */
public class CaracteristicasOrganoController implements Initializable {
    
    @FXML
    private TextField txtPhantom;
    @FXML
    private TextField txtOrgano;
    
    @FXML
    private TableView<ValorDescripcion> griValorDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;
    
    @FXML
    private Button btnModificarItem;
    @FXML
    private Button btnAgregarItem;
    @FXML
    private Button btnEliminarItem;
    @FXML
    private Button btnCerrar;
    
    Phantom phantomActual = FuncionesGenerales.getPhantomActual();
    Organo organoActual = FuncionesGenerales.getOrganoActual();
    private Stage primaryStage;
    
    //Lista Observable para el manejo de la informacion de los phantoms
    private ObservableList<ValorDescripcion> infoOrgano = FXCollections.observableArrayList();
    //Instancia de objeto IOrganoDAO. Inicializado como OrganoDAOsql. Para implementacion en MySql.  
    private IOrganoDAO org = new OrganoDAOsql();
    //Instancia de objeto tipo IValorDescripcionDAO. Se inicializa como ValorDescripcionDAOsql.  
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregarItem.setDisable(false);
        txtOrgano.setFocusTraversable(false);
        txtPhantom.setFocusTraversable(false);
        //Consulta a la base de datos
        infoOrgano=org.obtenerInfoOrgano(organoActual,phantomActual.getIdPhantom());
        
        iniciarDatosBasicos();
        //llenarTablaOrgano();
        
        
        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null, griValorDescripcion);
        griValorDescripcion.setItems(infoOrgano);
        
        
    }
    public void iniciarDatosBasicos(){
        txtPhantom.setText(phantomActual.getPhantomNombre());
        txtOrgano.setText(organoActual.getNombreOrgano());
        
    }
    
   
    public void RecargarGrilla()
    {
      infoOrgano=org.obtenerInfoOrgano(organoActual,phantomActual.getIdPhantom());
            //actualizacion de la tabla ValorDescripcionPhantom.
            griValorDescripcion.setItems(infoOrgano);
            griValorDescripcion.getSelectionModel().clearSelection();
            btnEliminarItem.setDisable(true);
            btnModificarItem.setDisable(true);
    
    }
    /**
     * 
     * Método que controla el comportamiento del boton Agregar item.
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnAgregarItem() throws SQLException {
        //objeto auxiliar de tipo organo. Organo actual seleccionado en el GriOrgano
        Organo auxOrgano  = FuncionesGenerales.getOrganoActual();
        //obtengo el id del organo seleccionado. 
        int idOrgano = auxOrgano.getIdOrgano();
       
        //Creacion de objeto auxiliar de tipo ValorDescripcion.
        ValorDescripcion itemOrgano = new ValorDescripcion(-1, "", "0.0","");
        //Llamada al formulario
        boolean guardarCambiosClicked = mostrarItemOrganoEditDialog(itemOrgano);
        // Obtengo el id de la relacion phantomOrgano.. 
        int idOrganoPhantom = org.obtenerIdOrganoPhantom(idOrgano);
        if (guardarCambiosClicked) {
            infoOrgano.add(itemOrgano);
            auxOrgano.setPropiedades(infoOrgano);
            // Llamada a la Clase de Acceso de datos de ValorDescripcion.
            // Parametros. Item auxiliar , id del organo dentro del Phantom. 
            vd.agregarItem(itemOrgano, idOrganoPhantom, "organos");

            //actualizacion de la informacion del organo.
          
                RecargarGrilla();
        }
    }
    
    /**
     * Método para el comportamiento del boton editar. Abre un dialogo para la
     * edicion de un atributo del organo. 
     */
    @FXML
    public void btnModificarItem() {
        //Objeto Organo que contiene el atributo
        organoActual = FuncionesGenerales.getOrganoActual();
        
        //Atributo a editar
        ValorDescripcion selectedItem = griValorDescripcion.getSelectionModel().getSelectedItem();
        int idItem = selectedItem.getId();
        if (selectedItem != null) {
            boolean guardarCambiosClicked = mostrarItemOrganoEditDialog(selectedItem);

            if (guardarCambiosClicked) {
                //True para Phantom,
                //False para Radionuclido
                vd.modificarItem(selectedItem, idItem, "organos");
                //Actualizacion de la informacion del radionuclido
                        
                  RecargarGrilla();

            }

        } else {
            //No se selecciono ningun item

        }
        
    }  
    
    /**
     * Método que controla el comportamiento del boton Quitar Item.
     */
    @FXML
    public void btnEliminarItem() {
        
        ValorDescripcion selectedItem = griValorDescripcion.getSelectionModel().getSelectedItem();
        int idItem = selectedItem.getId();
        vd.eliminarItem(idItem );
        
          RecargarGrilla();
    }
    
    /**
     * Método para el control de los botones.
     */
    @FXML
    public void getSelectedItemFromTabla() {
        
        ValorDescripcion selectedItem = griValorDescripcion.getSelectionModel().getSelectedItem();

        if (griValorDescripcion.getSelectionModel().isEmpty()) {
            btnEliminarItem.setDisable(true);
            btnModificarItem.setDisable(true);
        } else {
            btnEliminarItem.setDisable(false);
            btnModificarItem.setDisable(false);
        }
    }
    
    
    public boolean mostrarItemOrganoEditDialog(ValorDescripcion itemOrgano) {

        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CaracteristicasOrganoController.class.getResource("AbmItemOrgano.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Modificar Items");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            
            AbmItemOrganoController controladorAbmItemOrgano = loader.getController();
            controladorAbmItemOrgano.setDialogStage(dialogStage);
            controladorAbmItemOrgano.setItemOrgano(itemOrgano);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmItemOrgano.isGuardarDatosClicked();
        } catch (IOException e) {
            
            return false;
        }

    }
    /**
     * Método para el control de botón cerrar.
     */
    @FXML
    public void btnCerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();

        stage.close();
    }

    
    
    
    
}
