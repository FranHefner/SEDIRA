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
import sedira.FuncionesGenerales;
import sedira.model.IRadionuclidoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Radionuclido;
import sedira.model.RadionuclidoDAOsql;
import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class Clase controladora de la interfaz de usuario
 * Administrador de Radionúclidos.
 *
 * @author Quelin Pablo, Hefner Francisco.
 */
public class RadionuclidoController implements Initializable {

    @FXML
    private TableView<Radionuclido> griRadionuclido;
    @FXML
    private TableColumn<Radionuclido, String> clNombreRadNuclido;

    @FXML
    private TableView<ValorDescripcion> griInfoRadNuclido;

    @FXML
    private TableColumn<ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;

    @FXML
    private Button btnModificarRadioNuclido;
    @FXML
    private Button btnAgregarRadionuclido;
    @FXML
    private Button btnEliminarRadionuclido;
    @FXML
    private Button btnAgregarItem;
    @FXML
    private Button btnEliminarItem;
    @FXML
    private Button btnEditarItem;
    @FXML
    private TextField txtCampoBusqueda;
    @FXML
    private Button btnCerrar;

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
       btnAgregarRadionuclido.defaultButtonProperty().bind(btnAgregarRadionuclido.focusedProperty());
       btnModificarRadioNuclido.defaultButtonProperty().bind(btnModificarRadioNuclido.focusedProperty());
       btnEliminarRadionuclido.defaultButtonProperty().bind(btnEliminarRadionuclido.focusedProperty());
       
       btnEditarItem.defaultButtonProperty().bind(btnEditarItem.focusedProperty());
       btnEliminarItem.defaultButtonProperty().bind(btnEliminarItem.focusedProperty());
       btnAgregarItem.defaultButtonProperty().bind(btnAgregarItem.focusedProperty());
        btnCerrar.defaultButtonProperty().bind(btnCerrar.focusedProperty());
      
        //obtengo el listado de los radionuclidos existentes.
        radionuclidoData = rad.obtenerListaRadNuclido();
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos .

        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        // Limpieza de los detalles de Radionuclido.
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null, griInfoRadNuclido);

        //Inicializo la tabla de radionuclido - Trae el resultado de la busqueda.
        clNombreRadNuclido.setCellValueFactory(cellData -> cellData.getValue().getNombreRadNuclidoProperty());
        buscarRadionuclido();
        //Listener para la seleccion del radionuclido en la lista de radionuclidos que trae la busqueda.
        griRadionuclido.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionRadionuclido(newValue));
        
        //Listener para la seleccion del radionuclido en la lista de radionuclidos que trae la busqueda.
        griInfoRadNuclido.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getSelectedItemFromTabla(newValue));
        
    }

    /**
     * Al buscar el radionúclido , los muestra en la lista para su selección.
     */
    @FXML
    public void buscarRadionuclido() {
        griRadionuclido.setItems(FuncionesGenerales.FiltroListaRadNuclido(griRadionuclido, radionuclidoData, txtCampoBusqueda));

    }

    /**
     * Metodo que muestra los detalles del radionúclido seleccionado en la tabla
     * de resultado de busqueda.
     *
     * @param radionuclidoActual radionuclido que se selecciona del grilla de
     * radionuclidos.
     */
    public void seleccionRadionuclido(Radionuclido radionuclidoActual) {
        FuncionesGenerales.setRadioNuclidoActual(radionuclidoActual);
        if (radionuclidoActual != null) {
            //Obtengo de la Base de datos la lista de propiedades 
            infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
            //griInfoRadNuclido.setItems(infoRadNuclido);
            griInfoRadNuclido.setItems(infoRadNuclido);
            //Prendo botones.
            btnAgregarItem.setDisable(false);
            btnModificarRadioNuclido.setDisable(false);
            btnEliminarRadionuclido.setDisable(false);

        } else {
            //Apago botones.
            btnAgregarItem.setDisable(true);
            btnModificarRadioNuclido.setDisable(true);
            btnEliminarRadionuclido.setDisable(true);
        }
    }
    /**
     * Método  que se activa el momento en que el usuario desea modificar el radionúclido.
     * @param radionuclido
     * @return 
     */
    public boolean mostrarRadionuclidoDialog(Radionuclido radionuclido) {
        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmRadionuclido.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar nombre del Radionúclido");
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
    /**
     * Método que se activa cuando el usuario desea moficar o crear un item perteneciente a  un radionúclido. 
     * @param itemRadionuclido
     * @return 
     */
    public boolean mostrarItemRadionuclidoEditDialog(ValorDescripcion itemRadionuclido) {
        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmRadionuclido.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar Items");
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
     * Método que obtiene el item seleccionado de la tabla de Radionúclidos. 
     */
    public void getSelectedItemFromTabla(ValorDescripcion vd) {
        ValorDescripcion selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            btnEliminarItem.setDisable(false);
            btnEditarItem.setDisable(false);
        }

    }

    /**
     * Método para el comportamiento del boton editar. Abre un dialogo para la
     * edicion de un item del Radionuclido.
     */
    @FXML
    public void btnModificarItem() {
        //Objeto a editar.
        ValorDescripcion selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();
        //Radionuclido que contiene al item a editar 
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        // Identificador del radionuclido que contiene el item 
        int idRadionuclido = FuncionesGenerales.getRadioNuclidoActual().getIdRadNuclido();
        
        if (selectedItem != null) {
            boolean guardarCambiosClicked = mostrarItemRadionuclidoEditDialog(selectedItem);

            if (guardarCambiosClicked) {
                //False para Phantom,
                //True para Radionuclido
                vd.modificarItem(selectedItem, idRadionuclido, false, true);
                //actualizacion de la informacion del radionuclido.
                infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
                //actualizacion de la tabla InfoRadNuclido.
                griInfoRadNuclido.setItems(infoRadNuclido);
                
            }

        } else {
            // No se selecciono ningun item.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Seleccione un ítem para modificar");

            alert.showAndWait();

        }
    }

    /**
     * Método que controla el comportamiento del boton Quitar Item.
     */
    @FXML
    public void btnEliminarItem() {
        //Radionuclido seleccionado.
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        //Objeto a eliminar.
        ValorDescripcion selectedItem = griInfoRadNuclido.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null) {
            //identificador del item a eliminar
            int id = selectedItem.getId();
            
            String mensaje = griInfoRadNuclido.getSelectionModel().getSelectedItem().getDescripcion() + "  "
                    + griInfoRadNuclido.getSelectionModel().getSelectedItem().getValor() + "  "
                    + griInfoRadNuclido.getSelectionModel().getSelectedItem().getUnidad();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Item");
            alert.setHeaderText("Atención!");
            alert.setContentText("Esta seguro que desea eliminar el item seleccionado? \n" + mensaje);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                //llamada a la clase de acceso de datos para la eliminacion.
                vd.eliminarItem(id);
                //actualizacion de la informacion del radionuclido.
                infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
                //actualizacion de la tabla InfoRadNuclido.
                griInfoRadNuclido.setItems(infoRadNuclido);
                
            } else {
                //Cancelacion de la eliminacion
                //Mensaje de confirmacion.
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("Se cancelo la eliminación del ítem  - " + selectedItem.getDescripcion() + " ");
                alerta.showAndWait();
            }
            
        } else {
            // No se selecciono ningun item.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Se debe seleccionar un item para eliminar");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Método que elimina un radionúclido. Tambien elimina los items asociados.
     */
    public void btnEliminarRadionuclido() {
        //Radionuclido seleccionado.
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        
        //identificador del radionúclido a eliminar
        int idRadionuclido = radionuclidoActual.getIdRadNuclido();
        
        if (radionuclidoActual != null) {
            String mensaje = radionuclidoActual.getNombreRadNuclido();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar radionúclido");
            alert.setHeaderText("Atención!");
            alert.setContentText("Esta seguro que desea eliminar el radionúclido seleccionado? \n"
                    + "\n Se eliminará el radionuclido con todas sus propiedades."
                    + "\n Detalle:"
                    + "\n Nombre radionúclido: " + radionuclidoActual.getNombreRadNuclido());
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                //llamada a la clase de acceso de datos para la eliminacion.
                rad.eliminarRadionuclido(idRadionuclido);
                //actualizacion de la informacion del radionuclido.
                radionuclidoData = rad.obtenerListaRadNuclido();
                //Actualiza el GridView de Radionuclidos.
                griRadionuclido.setItems(radionuclidoData);
                
                //actualizacion de la informacion del radionuclido.
                infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
                //actualizacion de la tabla InfoRadNuclido.
                griInfoRadNuclido.setItems(infoRadNuclido);
                
            } else {
                //Cancelacion de la eliminacion
                //Mensaje de confirmacion.
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("Se cancelo la eliminación del radionúclido  - " + radionuclidoActual.getNombreRadNuclido() + " ");
                alerta.showAndWait();
            }
            
        } else {
            // No se selecciono ningun item.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Se debe seleccionar un radionúclido para eliminar");
            
            alert.showAndWait();
        }
    }
    /**
     * Método que controla el comportamiento del boton modificar item.
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnAgregarItem() throws SQLException {
        //Creacion de objeto auxilizar de tipo Radionuclido.
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        //Creacion de objeto auxiliar de tipo ValorDescripcion.
        ValorDescripcion itemRadionuclido = new ValorDescripcion(-1, null, 0, null);
        // Llamada al formulario
        boolean guardarCambiosClicked = mostrarItemRadionuclidoEditDialog(itemRadionuclido);
        //Obtencion del id del radionuclido.
        int idRadNuclido = radionuclidoActual.getIdRadNuclido();
        if (guardarCambiosClicked) {
            infoRadNuclido.add(itemRadionuclido);
            radionuclidoActual.setPropiedades(infoRadNuclido);
            // Llamada a la Clase de Acceso de datos de ValorDescripcion.
            // Parametros. Item auxiliar , identificador del radionuclido que hace la llamda a la funcion, False para Phantom, True para Radionuclido

            vd.agregarItem(itemRadionuclido, idRadNuclido, false, true);
            //actualizacion de la informacion del radionuclido.
            infoRadNuclido = rad.obtenerInfoRadNuclido(radionuclidoActual);
            //actualizacion de la tabla InfoRadNuclido.
            griInfoRadNuclido.setItems(infoRadNuclido);

        }

    }

    /**
     * Método para el comportamiento del boton Crear radionuclido. El
     * radionúclido primero se crea sin elemento de tipo propiedad valor.
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnAgregarRadionuclido() throws SQLException {
        //Creacion de objeto Radionuclido auxiliar.
        Radionuclido tempRadNuclido = new Radionuclido(-1, "", null);
        boolean guardarCambiosClicked = mostrarRadionuclidoDialog(tempRadNuclido);
        if (guardarCambiosClicked) {
            rad.agregarRadionuclido(tempRadNuclido);
            radionuclidoData = rad.obtenerListaRadNuclido();
            //Actualiza el GridView de Radionuclidos.
            griRadionuclido.setItems(radionuclidoData);
            btnModificarRadioNuclido.setDisable(true);
            btnEliminarRadionuclido.setDisable(true);
        }
    }

    /**
     * Método que modifica el nombre del radionúclido.
     */
    @FXML
    public void btnModificarRadionuclido() {
        //Radionuclido a editar.
        Radionuclido radioNuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        boolean guardarCambiosClicked = mostrarRadionuclidoDialog(radioNuclidoActual);
        if (guardarCambiosClicked) {

            //Llamada a la Clase de acceso a datos de Radionuclido
            rad.modificarNombreRadionuclido(radioNuclidoActual);
            radionuclidoData = rad.obtenerListaRadNuclido();
            //Actualiza el GridView de Radionuclidos.
            griRadionuclido.setItems(radionuclidoData);
            //Comportamiento de botones.
            btnModificarRadioNuclido.setDisable(true);
               
        }

    }
    /**
     * Método para el comportamiento del boton Cerrar. 
     */
    @FXML
    public void btnCerrar_click()
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();

            stage.close();
    }


    
    
}
