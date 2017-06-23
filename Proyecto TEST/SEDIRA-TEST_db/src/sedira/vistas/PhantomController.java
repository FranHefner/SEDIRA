/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.ValorDescripcion;
import sedira.model.Phantom;
import sedira.AplicacionPrincipal;
import sedira.model.IOrganoDAO;
import sedira.model.OrganoDAOsql;
import sedira.model.PhantomDAOsql;
import sedira.model.ValorDescripcionDAOsql;
import sedira.model.IPhantomDAO;
import sedira.model.IValorDescripcionDAO;

/**
 * Clase controladora para el Administrador de Phantoms.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PhantomController implements Initializable {

    //Declaracion de los elementos de la interfaz grafica. 
    @FXML
    private TableView<Phantom> griPhantom;
    @FXML
    private TextField txtPesoTotal;
    @FXML
    private TableColumn<Phantom, String> clPhantomNombre;

    @FXML
    private TableView<Organo> griOrgano;
    @FXML
    private TableColumn<Organo, String> clOrganoNombre;
    @FXML
    private TableColumn<Organo, String> clOrganoMasa;

    @FXML
    private TableView<ValorDescripcion> griValorDescripcionPhantom;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;

    @FXML
    private Button btnEditarPhantom;
    @FXML
    private Button btnNuevoPhantom;
    @FXML
    private Button btnEliminarPhantom;
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
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnAdministrar;

    //Lista Observable para el manejo de phantoms
    private ObservableList<Phantom> phantomData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de organos
    private ObservableList<Organo> organosData = FXCollections.observableArrayList();
    //Lista Observable para el manejo de la informacion de los phantoms
    private ObservableList<ValorDescripcion> infoPhantom = FXCollections.observableArrayList();

    private AplicacionPrincipal aplicacionPrincipal;
    private Stage primaryStage;
    //Instancia de Objeto IPhantomDAO. Inicializado como PhantomDAOsql. Para implementacion en MySql
    private IPhantomDAO ph = new PhantomDAOsql();
    //Instancia de objeto IOrganoDAO. Inicializado como OrganoDAOsql. Para implementacion en MySql.  
    private IOrganoDAO org = new OrganoDAOsql();
    //Instancia de objeto tipo IValorDescripcionDAO. Se inicializa como ValorDescripcionDAOsql.  
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();

    private final int LIMIT_NOMBRE = 45;

    /**
     * Initializes la clase controlador. El método setCellValueFactory(...) que
     * aplicamos sobre las columnas de la tabla se usa para determinar qué
     * atributos de la clase Phantom / Organo deben ser usados para cada columna
     * particular. La flecha -> indica que estamos usando una característica de
     * Java 8 denominada Lambdas. Otra opción sería utilizar un
     * PropertyValueFactory, pero entonces no ofrecería seguridad de tipo
     * (type-safe).
     *
     * El método initialize() es invocado automáticamente tras cargar el fxml.
     * En ese momento, todos los atributos FXML deberían ya haber sido
     * inicializados..
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAdministrar.setDisable(true);
        //Traigo los datos de los phantoms existentes. 
        phantomData = ph.obtenerListaPhantom();
        buscarPhantom();
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());

        // Limpieza de los detalles de organos. 
        FuncionesGenerales.mostrarDetalleOrgano(null, griOrgano);

        //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null, griValorDescripcionPhantom);

        //Inicializo la tabla de Phantom. 
        clPhantomNombre.setCellValueFactory(cellData -> cellData.getValue().phantomNombreProperty());
        //Listener para la seleccion del phantom en la lista de phantoms que trae la busqueda.
        griPhantom.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionPhantom(newValue));
        
        //Listener para la seleccion del item phantom en la lista de informacion del phantom
        griValorDescripcionPhantom.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getSelectedItemFromTabla(newValue));
        
        //Listener para la seleccion del organo en la lista de informacion de organos
        griOrgano.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getSelectedItemFromTablaOrgano(newValue));
        
        //Listener para la cantidad de caracteres en el nombre en el campo busqueda 
        txtCampoBusqueda.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtCampoBusqueda.getText().length() >= LIMIT_NOMBRE) {

                        txtCampoBusqueda.setText(txtCampoBusqueda.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        });
    }

    /**
     * Abre el formulario de AbmPhantom.FXml con los datos correspondientes al
     * phantom seleccionado. Si el usuario presiona guardar datos, los cambios
     * son guardados dentro del phantom y returna true.
     *
     * @param phantom para ser editado
     * @return true si el usuario clickea Guardar datos o retorna falso en caso
     * contrario
     */
    public boolean mostrarPhantomEditDialog(Phantom phantom) {

        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmPhantom.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
         //  dialogStage.setTitle("Modificar nombre del Phantom");
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

    /**
     * Método que Muestra el Formulario de edicion de los items de un phantom.
     *
     * @param itemPhantom
     * @return True si guardaron los datos. false si los datos no pudieron ser
     * guardados.
     */
    public boolean mostrarItemPhantomEditDialog(ValorDescripcion itemPhantom) {

        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmPhantom.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modificar ítems");
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
            return false;
        }

    }

    /**
     * Muestra el Formulario para la edicion de un organo.
     *
     * @param organo
     * @param phantom
     * @return
     */
    public boolean mostrarOrganoEditDialog(Organo organo, Phantom phantom) {

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

            try {
                scene.getStylesheets().add((new File("tools/autoCompletado.css")).toURI().toURL().toExternalForm());
            } catch (MalformedURLException ex) {

            }

            dialogStage.setScene(scene);

            // Pone el organo en el controlador AbmOrganoController. 
            AbmOrganoController controladorAbmOrgano = loader.getController();
            controladorAbmOrgano.setDialogStage(dialogStage);
            // le paso el Phantom porque los phantom son los que contienen organos. 
            controladorAbmOrgano.setPhantom(phantom);
            controladorAbmOrgano.setOrgano(organo);
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
    public void buscarPhantom() {
        SortedList listaPhantom = FuncionesGenerales.FiltroListaPhantom(griPhantom, phantomData, txtCampoBusqueda);
       
        //Comportamiento para la busqueda y no encuentro de info.
        if (listaPhantom.size() == 0) {
            griValorDescripcionPhantom.setItems(null);
            griOrgano.setItems(null);
        } else {
            griPhantom.setItems(listaPhantom);
            if (griPhantom.getSelectionModel().isEmpty()) {
                btnAgregarOrgano.setDisable(true);
                btnEliminarOrgano.setDisable(true);
                btnModificarOrgano.setDisable(true);

                btnAgregarItem.setDisable(true);
                btnEliminarItem.setDisable(true);
                btnModificarItem.setDisable(true);

                btnEditarPhantom.setDisable(true);
                btnEliminarPhantom.setDisable(true);

                griValorDescripcionPhantom.setItems(null);
                griOrgano.setItems(null);
                txtPesoTotal.setText("");
            }
        }

    }

    /**
     * Método que muestra los detalles del phantom seleccionado.
     *
     * @param phantomActual
     */
    public void seleccionPhantom(Phantom phantomActual) {
        
        griValorDescripcionPhantom.getSelectionModel().clearSelection();
        griOrgano.getSelectionModel().clearSelection();
        //Se setea el phantom seleccionado como el PhantomActual.
        FuncionesGenerales.setPhantomActual(phantomActual);
        
           
        if (phantomActual != null) {
            //Completo la lista de organos. 
            organosData = ph.obtenerInfoOrgano(phantomActual);
            //Completo la grilla de los organos. 
            griOrgano.setItems(organosData);
            //Completo la lista de items para el phantom
            infoPhantom = ph.obtenerInfoPhantom(phantomActual);
            //Completo la grilla de los items del phantom
            griValorDescripcionPhantom.setItems(infoPhantom);
           
            //Completo el textfield del pesototal
            txtPesoTotal.setText(String.valueOf(phantomActual.getPesoTotal()));
            //Prendo botones
            btnAgregarItem.setDisable(false);
            btnEditarPhantom.setDisable(false);
            btnEliminarPhantom.setDisable(false);
            btnAgregarOrgano.setDisable(false);
        } else {
            //Todo si no se selecciona ningun phantom de la lista
            //Apago los botones.
            btnEditarPhantom.setDisable(true);
            btnEliminarPhantom.setDisable(true);
            btnEliminarOrgano.setDisable(true);
            btnModificarOrgano.setDisable(true);
            btnEliminarItem.setDisable(true);
            btnModificarItem.setDisable(true);
            btnAgregarItem.setDisable(true);
            btnAdministrar.setDisable(true);

        }
    }

    /**
     * Método para el comportamiento del boton editar. Abre un dialogo para la
     * edicion del Phantom.
     */
    @FXML
    public void btnEditarPhantom_click() throws IOException {
        Phantom phantom = FuncionesGenerales.getPhantomActual();
        int index = griPhantom.getSelectionModel().getSelectedIndex();
        boolean guardarCambiosClicked = mostrarPhantomEditDialog(phantom);

        if (guardarCambiosClicked) {
            //Llamada a la Clase de acceso a datos de Phantom
            ph.modificarNombrePhantom(phantom);
            phantomData = ph.obtenerListaPhantom();
            //Actualiza el GridView de los phantoms 
            griPhantom.setItems(phantomData);
            //Comportamiento de botones 
            btnEditarPhantom.setDisable(true);
            txtCampoBusqueda.setText("");
        }

    }

    /**
     * Método para el comportamiento del boton NUEVO.
     */
    @FXML
    public void btnNuevoPhantom_click() {
        /**
         * Este metodo crea una objeto de tipo Phantom Vacio. Luego le asigna
         * las propiedades y los organos que en la primer instancia tambien
         * estan vacios. Las propiedades y los organos se agregan posteriormente
         * con la utilizacion de los botones. Agregar item y Agregar organo.
         *
         */
        Phantom tempPhantom = new Phantom(-1, "", 0, null, null);
        //Lista Observable para el manejo de la informacion de los phantoms
        ObservableList<ValorDescripcion> propiedadesPhantom = FXCollections.observableArrayList();
        //Lista Observable para el manejo de los organos de los phantoms
        ObservableList<Organo> organosPhantom = FXCollections.observableArrayList();

        boolean guardarCambiosClicked = mostrarPhantomEditDialog(tempPhantom);
        String nombrePhantom = tempPhantom.getPhantomNombre();
        if (guardarCambiosClicked) {
            tempPhantom.setPropiedades(propiedadesPhantom);
            tempPhantom.setOrgano(organosPhantom);
            //Guardo el Phantom temporal con los nuevos datos. 
            ph.agregarPhantom(tempPhantom);
            //Actualizo el GridView de Phantoms.
            phantomData = ph.obtenerListaPhantom();
            griPhantom.setItems(phantomData);
            txtCampoBusqueda.setText("");

        }
    }

    /**
     * Método que controla el comportamiento del boton Agregar Organo.
     */
    @FXML
    public void btnAgregarOrgano() {
        //objeto auxiliar de tipo Phantom. Phantom actual seleccionado en el GriPhamtom
        Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        //Objeto nuevo de tipo Organo 
        Organo organo = new Organo(-1, "", 0.0, 0.0, null);
        // identificador del phantom al cual se agregara el item. 
        int idPhantom = selectedPhantom.getIdPhantom();

        boolean guardarCambiosClicked = mostrarOrganoEditDialog(organo, selectedPhantom);
        //El objeto phantom ya tiene la nueva lista de organos. 

        //organosData = selectedPhantom.getOrgano();
        if (guardarCambiosClicked) {
            //Agrego el nuevo organo a la lista de organos. 
            organosData.add(organo);
            //Agrego la lista de organos al phantom
            selectedPhantom.setOrgano(organosData);
            // Llamada a la clase accesode datos de organo
            org.agregarOrgano(organo, idPhantom);
            //ConsultasDB.modificarPhantom(selectedPhantom,griPhantom.getSelectionModel().getSelectedIndex() );  
            //Actualizacion de la informacion de organos
            organosData = ph.obtenerInfoOrgano(selectedPhantom);
            griOrgano.setItems(organosData);
            //Completo el textfield del pesototal
            txtPesoTotal.setText(String.valueOf(selectedPhantom.getPesoTotal()));
            griOrgano.getSelectionModel().clearSelection();
            btnEliminarOrgano.setDisable(true);
            btnModificarOrgano.setDisable(true);

        } else {
            // Nothing selected.

        }

    }

    /**
     * Método que controla el comportamiento del boton Eliminar Organo
     */
    @FXML
    public void btnEliminarPhantom() {
        //Phantom seleccionado para eliminar. 
        Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        //Identificador del phantom a eliminar. 
        int idPhantom = selectedPhantom.getIdPhantom();
        if (selectedPhantom != null) {
            String mensaje = griPhantom.getSelectionModel().getSelectedItem().getPhantomNombre();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Phantom");
            alert.setHeaderText("Atención!");
            alert.setContentText("Esta seguro que desea eliminar el phantom seleccionado? \n"
                    + "Se eliminaran todas las propiedades y los órganos asociados. ");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ph.eliminarPhantom(idPhantom);

                ///Actualizo el GridView de Phantoms.
                phantomData = ph.obtenerListaPhantom();
                //Actualizo los demas gridview
                griPhantom.setItems(phantomData);
                griValorDescripcionPhantom.setItems(null);
                griOrgano.setItems(null);
                txtPesoTotal.setText("");
                txtCampoBusqueda.setText("");

            } else {

            }

        } else {
            // No se selecciono ningun phantom. 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Seleccione el phantom a eliminar");

            alert.showAndWait();
        }

    }

    /**
     * Método que controla el comportamiento del boton Eliminar Organo.
     */
    @FXML
    public void btnEliminarOrgano() {
        //Organo a eliminar. 
        Organo selectedItem = griOrgano.getSelectionModel().getSelectedItem();
        //Phantom que contiene el organo a eliminar. 
        Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        int idOrgano = selectedItem.getIdOrgano();
        if (selectedItem != null) {
            String mensaje = griOrgano.getSelectionModel().getSelectedItem().getNombreOrgano() + "  "
                    + griOrgano.getSelectionModel().getSelectedItem().getOrganMass();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Item");
            alert.setHeaderText("Atención!");
            alert.setContentText("Esta seguro que desea eliminar el órgano seleccionado? \n" + mensaje);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Llamada a la clase de acceso de datos de Organo. 
                org.eliminarOrgano(idOrgano, selectedPhantom.getIdPhantom());
                //Actualizacion de la informacion de organos
                organosData = ph.obtenerInfoOrgano(selectedPhantom);
                //actualizacion de la tabla Organos. 
                //Comportamiento de los botones al eliminar todos los items.
                if (organosData.size() != 0) {
                    griOrgano.setItems(organosData);
                    txtPesoTotal.setText(String.valueOf(selectedPhantom.getPesoTotal()));
                    griOrgano.getSelectionModel().clearSelection();
                } else {
                    griOrgano.setItems(organosData);
                    griOrgano.getSelectionModel().clearSelection();
                    apagarBotonesOrgano();
                }
            } 

        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Seleccione el órgano a eliminar ");

            alert.showAndWait();
        }

    }

    /**
     * Método que controla el comportamiento del boton Quitar Item.
     */
    @FXML
    public void btnEliminarItem() {
        ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        Phantom selectedPhantom = FuncionesGenerales.getPhantomActual();
        int selectedIndex = griValorDescripcionPhantom.getSelectionModel().getSelectedIndex();

        if (selectedItem != null) {
            //Identificador del item a eliminar. 
            int idItem = selectedItem.getId();

            String mensaje = griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getDescripcion() + "  "
                    + griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getValor() + "  "
                    + griValorDescripcionPhantom.getSelectionModel().getSelectedItem().getUnidad();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Ítem");
            alert.setHeaderText("Atención!");
            alert.setContentText("Está seguro que desea eliminar el ítem seleccionado? \n" + mensaje);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                griValorDescripcionPhantom.getItems().remove(selectedIndex);
                //Llamada BD para la eliminacion. 
                vd.eliminarItem(idItem);
                //actualizacion de la informacion del phantom.
                infoPhantom = ph.obtenerInfoPhantom(selectedPhantom);

                //actualizacion de la tabla ValorDescripcionPhantom. 
                //Comportamiento de los botones al eliminar todos los items. 
                if (infoPhantom.size() != 0) {
                    griValorDescripcionPhantom.setItems(infoPhantom);
                    griValorDescripcionPhantom.getSelectionModel().clearSelection();
                } else {
                    apagarBotones();
                    griValorDescripcionPhantom.setItems(infoPhantom);
                    griValorDescripcionPhantom.getSelectionModel().clearSelection();
                }

            } else {
                //Cancelacion de la eliminacion
                //Mensaje de confirmacion.
                /*Alert alerta = new Alert(AlertType.INFORMATION);
                 alerta.setTitle("Confirmación");
                 alerta.setHeaderText(null);
                 alerta.setContentText("Se cancelo la eliminación del ítem  - " + selectedItem.getDescripcion() + " ");
                 alerta.showAndWait();*/
            }

        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Se debe seleccionar un ítem para eliminar");

            alert.showAndWait();
        }
    }

    /**
     * Método que controla el comportamiento del boton modificar item.
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnAgregarItem() throws SQLException {
        //objeto auxiliar de tipo Phantom. Phantom actual seleccionado en el GriPhamtom
        Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
        //Creacion de objeto auxiliar de tipo ValorDescripcion.
        ValorDescripcion itemPhantom = new ValorDescripcion(-1, "", "0.0", "");
        //Llamada al formulario
        boolean guardarCambiosClicked = mostrarItemPhantomEditDialog(itemPhantom);
        // identificador del phantom al cual se agregara el item. 
        int idPhantom = auxPhantom.getIdPhantom();
        if (guardarCambiosClicked) {
            infoPhantom.add(itemPhantom);
            auxPhantom.setPropiedades(infoPhantom);
            // Llamada a la Clase de Acceso de datos de ValorDescripcion.
            // Parametros. Item auxiliar , identificador del phantom que hace la llamada a la funcion, False para radionuclido, false para Radionuclido
            vd.agregarItem(itemPhantom, idPhantom, "phantoms");

            //actualizacion de la informacion del phantom.
            infoPhantom = ph.obtenerInfoPhantom(auxPhantom);
            //actualizacion de la tabla ValorDescripcionPhantom.
            griValorDescripcionPhantom.setItems(infoPhantom);
            griValorDescripcionPhantom.getSelectionModel().clearSelection();
            btnEliminarItem.setDisable(true);
            btnModificarItem.setDisable(true);

        }
    }

    /**
     * Método para el comportamiento del boton editar. Abre un dialogo para la
     * edicion de un atributo del Phantom.
     */
    @FXML
    public void btnModificarItem() {
        //Objeto phantom que contiene el atributo
        Phantom phantomActual = FuncionesGenerales.getPhantomActual();

        //Atributo a editar
        ValorDescripcion selectedItem = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        // id del item a editar  
        int idItem = selectedItem.getId();

        if (selectedItem != null) {
            boolean guardarCambiosClicked = mostrarItemPhantomEditDialog(selectedItem);

            if (guardarCambiosClicked) {
                //True para Phantom,
                //False para Radionuclido
                vd.modificarItem(selectedItem, idItem, "phantoms");
                //Actualizacion de la informacion del radionuclido
                infoPhantom = ph.obtenerInfoPhantom(phantomActual);
                griValorDescripcionPhantom.setItems(infoPhantom);
                griValorDescripcionPhantom.getSelectionModel().clearSelection();
                btnEliminarItem.setDisable(true);
                btnModificarItem.setDisable(true);

            }

        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Se debe seleccionar un ítem para modificar");

            alert.showAndWait();

        }
    }

    @FXML
    /**
     * Método que contiene el comportamiento del boton Modificar Organo.
     */
    public void btnModificarOrgano() {
        //Phantom auxiliar. 
        Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
        //Organo a modificar 
        Organo selectedOrgano = griOrgano.getSelectionModel().getSelectedItem();
        // identificador del phantom que contiene al organo
        int idPhantom = auxPhantom.getIdPhantom();

        if (selectedOrgano != null) {
            boolean guardarCambiosClicked = mostrarOrganoEditDialog(selectedOrgano, auxPhantom);
            if (guardarCambiosClicked) {
                //Bd Modificar Phantom
                //ConsultasDB.modificarPhantom(auxPhantom,griPhantom.getSelectionModel().getSelectedIndex() );
                // LLamada a la clase de acceso de datos de organos. 
                org.modificarOrgano(selectedOrgano, idPhantom);
                //Actualizacion de la informacion de organos
                organosData = ph.obtenerInfoOrgano(auxPhantom);
                griOrgano.setItems(organosData);
                //Completo el textfield del pesototal
                txtPesoTotal.setText(String.valueOf(auxPhantom.getPesoTotal()));
                griOrgano.getSelectionModel().clearSelection();
                btnEliminarOrgano.setDisable(true);
                btnModificarOrgano.setDisable(true);
            }
        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Se debe seleccionar un órgano para modificar");

            alert.showAndWait();

        }
    }

    @FXML
    /**
     * Método que contiene el comportamiento del boton Administrar Organo.
     */
    public void btnAdministrar() throws IOException {
        //Phantom auxiliar. 
        Phantom auxPhantom = FuncionesGenerales.getPhantomActual();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CaracteristicasOrgano.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Administrar caracteristicas del órgano");
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Método para el control de botones.
     */
    @FXML
    public void getSelectedPhantom() {
        Phantom phantom = griPhantom.getSelectionModel().getSelectedItem();
        if (phantom != null) {
            btnAgregarItem.setDisable(false);
            btnAgregarOrgano.setDisable(false);
        }
    }

    /**
     * Método para el control de los botones.
     */
    @FXML
    public void getSelectedItemFromTabla(ValorDescripcion vd) {
        if (vd==null){
            btnEliminarItem.setDisable(true);
            btnModificarItem.setDisable(true);
        } else {
            btnEliminarItem.setDisable(false);
            btnModificarItem.setDisable(false);
        }
    }

    /**
     * Método para el control de los botones.
     */
    @FXML
    public void getSelectedItemFromTablaOrgano(Organo organo) {
        
        if (organo==null) {
            btnEliminarOrgano.setDisable(true);
            btnModificarOrgano.setDisable(true);
            btnAdministrar.setDisable(true);
        } else {
            //Organo a modificar 
            FuncionesGenerales.setOrganoActual(organo);
            btnEliminarOrgano.setDisable(false);
            btnModificarOrgano.setDisable(false);
            btnAdministrar.setDisable(false);
        }
    }

    /**
     * Método para el control de botón cerrar.
     */
    @FXML
    public void btnCerrar_click() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();

        stage.close();
    }

    private void apagarBotones() {
        btnModificarItem.setDisable(true);
        btnEliminarItem.setDisable(true);
    }

    private void apagarBotonesOrgano() {
        btnModificarOrgano.setDisable(true);
        btnEliminarOrgano.setDisable(true);
        btnAdministrar.setDisable(true);
    }
}
