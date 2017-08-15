/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IPhantomDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Phantom;
import sedira.model.PhantomDAOsql;

import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class Clase controladora para el Abm de Phantoms.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class AbmPhantomController implements Initializable {

    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNombrePhantom;
    @FXML
    private TitledPane titledEdicion;

    @FXML
    private VBox boxControles;

    //Objeto Phantom auxiliar. 
    private Phantom phantom;
    private Phantom phantomActual = FuncionesGenerales.getPhantomActual();
    // Stage aux
    private Stage dialogStage;
    // Valor Descripcion aux para items de phantom 
    private ValorDescripcion itemPhantom;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Lista Observable para el manejo de phantoms
    private ObservableList<ValorDescripcion> listaAtributoPhantom = FXCollections.observableArrayList();
    //Se cambia el PhantomDAOsql por la implementacion Correcta para otro motor. 
    private IPhantomDAO ph = new PhantomDAOsql();

    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();
    private static final int LIMIT_NOMBRE = 45;
    private static final int LIMIT_NOMBREPHANTOM = 45;
    private static final int LIMIT_VALOR = 14;
    private static final int LIMIT_UNIDAD = 255;
    private static final String CREACION = "Crear un Phantom";
    private static final String MODIFICACION = "Modificar nombre del Phantom";
    private static final String CREACION_ITEM = "Agregar ítems";
    private static final String MODIFICACION_ITEM = "Modificar ítems";
    
    ObservableList<String> data;
    ListView listaSugerida = new ListView();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();
    boolean bandera = false;
    private boolean IgnorarValidacion = false;
    private int UltimoFoco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtValor.setText("");
        //Listener para la cantidad de caracteres en el nombre del phantom
        txtNombrePhantom.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtNombrePhantom.getText().length() >= LIMIT_NOMBREPHANTOM) {
                        txtNombrePhantom.setText(txtNombrePhantom.getText().substring(0, LIMIT_NOMBREPHANTOM));
                    }
                }
            }
        });
        //Listener para LostFocus txtNombrePhantom
        txtNombrePhantom.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 1;
                if (!newPropertyValue && txtNombrePhantom.getText().length() > 0 && IgnorarValidacion == false) {
                    if (!validarNombrePhantom()) {
                        txtNombrePhantom.requestFocus();
                    } else {
                        // System.out.println("entro a validacion");
                    }

                }

            }
        });
        //Listener para la cantidad de caracteres en el nombre en las propiedades
        txtPropiedad.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtPropiedad.getText().length() >= LIMIT_NOMBRE) {
                        txtPropiedad.setText(txtPropiedad.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        });
        //Validacion al perder el Focus. 
        txtPropiedad.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 2;
                if (!newPropertyValue && txtPropiedad.getText().length() > 0 && IgnorarValidacion == false) {
                    if (validarPropiedad()) {
                        //validacion correcta
                        //  System.out.println("Entro a validar propiedad");
                    } else {
                        txtPropiedad.requestFocus();
                    }

                }
            }
        });
        //Listener para la cantidad de caracteres en el nombre en el valor 
        txtValor.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtValor.getText().length() >= LIMIT_VALOR) {

                        txtValor.setText(txtValor.getText().substring(0, LIMIT_VALOR));
                    }
                }
            }
        });
        //Listener Validacion LostFocus Valor 
        txtValor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 3;
                if (!newPropertyValue && txtValor.getText().length() > 0 && IgnorarValidacion == false) {
                    try {
                        if (validarValor()) {
                            //    System.out.println("Entro a validar valor");
                        } else {
                            txtValor.requestFocus();

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        //Listener para la cantidad de caracteres en el nombre en el campo unidad 
        txtUnidad.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtUnidad.getText().length() >= LIMIT_UNIDAD) {

                        txtUnidad.setText(txtUnidad.getText().substring(0, LIMIT_UNIDAD));
                    }
                }
            }
        });
        //Listener Validacion LostFocus Unidad 
        txtUnidad.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 4;
                if (!newPropertyValue && txtUnidad.getText().length() > 0 && IgnorarValidacion == false) {
                    try {
                        if (validarUnidad()) {
                            //  System.out.println("Entro a validar unidad");
                        } else {
                            txtUnidad.requestFocus();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        data = vd.listadoPropiedades("phantoms");
        //Comportamiento de la lista sugerida. 
        if (data.size() != 0) {
            listaSugerida.setItems(data);
        } else {
            listaSugerida.setItems(data);
            listaSugerida.setVisible(false);
        }

        txtPropiedad.textProperty().addListener(
                (observable, oldValue, newValue) -> actualizarListaSugerida(newValue));

        boxControles.getChildren().addAll(txtPropiedad, listaSugerida);

        listaSugerida.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                bandera = true;
                if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {

                } else {
                    seleccionarItem(newValue);
                }
                bandera = false;

            }
        });
    }

    public void seleccionarItem(String itemSeleccionado) {
        if (itemSeleccionado != null) {
            txtPropiedad.setText(itemSeleccionado);
            txtPropiedad.requestFocus();
            txtPropiedad.positionCaret(txtPropiedad.getText().length());

        }

    }

    /**
     * Método que actualiza la lista de propiedades al completar el textField
     *
     * @param filtro
     */
    private void actualizarListaSugerida(String filtro) {
        listaSugerida.getSelectionModel().clearSelection();
        //Comportamiento de la lista sugerida. 
        if (listaSugerida.getSelectionModel().isEmpty()) {
            listaSugerida.setFocusTraversable(false);

        } else {
            listaSugerida.setFocusTraversable(true);

        }
        //FIn Comportamiento de la lista sugerida.

        if (bandera == false) {
            if (filtro == null || filtro.length() == 0) {
                if (data.size() != 0) {
                    listaSugerida.setItems(data);
                    listaSugerida.setVisible(true);
                } else {
                    listaSugerida.setVisible(false);
                }
            } else {

                dataFiltrada = data.filtered(s -> s.toLowerCase().contains(filtro.toLowerCase()));
                if (dataFiltrada.size() == 0) {
                    listaSugerida.setVisible(false);
                } else {
                    listaSugerida.setItems(dataFiltrada);
                    listaSugerida.setVisible(true);
                }

            }
        }
    }

    /**
     * Setea el Phantom a editar dentro del Formulario de edicion. Si el Phanton
     * que viene por parametros tiene el id = -1, significa que el usuario busca
     * crear un nuevo Phantom.
     *
     * @param phantom a editar.
     */
    public void setPhantom(Phantom phantom) {
        this.phantom = phantom;
        if (phantom.getIdPhantom() != -1) {
            /**
             * Obtiente el Phantom seleccionado en la busqueda del formulario
             * phantom.fxml
             */
            //Atributos de nombre y id. 
            dialogStage.setTitle(MODIFICACION);
            txtNombrePhantom.setEditable(true);
            txtNombrePhantom.setText(phantom.getPhantomNombre());
            listaSugerida.setDisable(true);
            listaSugerida.setVisible(false);
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Comportamiento de botones. 

        } else {

            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle(CREACION);
            listaSugerida.setDisable(true);
            listaSugerida.setVisible(false);

            //Apago los TextField
            txtNombrePhantom.setEditable(true);
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);

        }

    }

    /**
     * Setea los Items en el phantom
     *
     * @param itemPhantom
     */
    public void setItemPhantom(ValorDescripcion itemPhantom) {
        Phantom phantomActual = FuncionesGenerales.getPhantomActual();
        this.itemPhantom = itemPhantom;
        if (itemPhantom.getId() != -1) {

            this.dialogStage.setTitle(MODIFICACION_ITEM);
            txtNombrePhantom.setFocusTraversable(false);
            txtNombrePhantom.setEditable(false);
            txtNombrePhantom.setText(phantomActual.getPhantomNombre());
            txtPropiedad.setText(this.itemPhantom.getDescripcion());
            txtValor.setText(String.valueOf(this.itemPhantom.getValor()));
            txtUnidad.setText(this.itemPhantom.getUnidad());
        } else {
            this.dialogStage.setTitle(CREACION_ITEM);
            txtNombrePhantom.setFocusTraversable(false);
            txtNombrePhantom.setEditable(false);
            txtNombrePhantom.setText(phantomActual.getPhantomNombre());
            txtPropiedad.setText(this.itemPhantom.getDescripcion());
            txtValor.setText("");
            txtUnidad.setText(this.itemPhantom.getUnidad());
        }
    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnGuardarDatos() throws SQLException {

        String NombrePropiedad;

        // Me fijo si hay una propiedad seleccionada o es un nuevo
        if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {
            NombrePropiedad = txtPropiedad.getText();
        } else {
            NombrePropiedad = listaSugerida.getSelectionModel().getSelectedItem().toString();
        }

        // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
        if (validarDatosEntrada()) {
            //Validacion preguntando si esta seguro guardar cambios.
            switch (dialogStage.getTitle()) {
                case CREACION:
                    phantom.setPhantomNombre(txtNombrePhantom.getText());
                    phantom.setPropiedades(listaAtributoPhantom);
                    break;
                case MODIFICACION:
                    phantom.setPhantomNombre(txtNombrePhantom.getText());
                    break;
                case CREACION_ITEM:
                    itemPhantom.setDescripcion(NombrePropiedad);
                    itemPhantom.setUnidad(txtUnidad.getText());
                    itemPhantom.setValor(txtValor.getText());
                    break;
                case MODIFICACION_ITEM:
                    itemPhantom.setDescripcion(NombrePropiedad);
                    itemPhantom.setUnidad(txtUnidad.getText());
                    itemPhantom.setValor(txtValor.getText());
                    break;
            }
            // Si las validaciones son correctas se guardan los datos. 

            guardarDatos = true;
            dialogStage.close();
        }

    }

    /**
     * Método que retorna si el usuario presiono el boton Guardar Datos.
     *
     * @return guardarDatos
     */
    public boolean isGuardarDatosClicked() {
        return this.guardarDatos;
    }

    /**
     * Metodo que se llama al presionar el boton cancelar.
     */
    @FXML
    public void btnCancel_click() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        switch (dialogStage.getTitle()) {
            case CREACION:
                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del phantom ? ");
                break;
            case MODIFICACION:
                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del phantom? ");
                break;
            case MODIFICACION_ITEM:
                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación de la propiedad?");
                break;
            case CREACION_ITEM:
                alert.setTitle("Cancelar creación de la propiedad");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación de la propiedad? ");
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            IgnorarValidacion = true;
            dialogStage.close();
        } else {
            if (UltimoFoco == 1) {
                txtNombrePhantom.requestFocus();
            }
            if (UltimoFoco == 2) {
                txtPropiedad.requestFocus();
            }
            if (UltimoFoco == 3) {
                txtValor.requestFocus();
            }
            if (UltimoFoco == 4) {
                txtUnidad.requestFocus();
            }

        }
    }

    /**
     * metodo para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {

        switch (dialogStage.getTitle()) {
            case CREACION:
                txtNombrePhantom.setText("");
                break;
            case MODIFICACION:
                txtNombrePhantom.setText("");
                break;
            case MODIFICACION_ITEM:
                txtUnidad.setText("");
                txtPropiedad.setText("");
                txtValor.setText("");
                listaSugerida.getSelectionModel().clearSelection();
                listaSugerida.setVisible(false);
                break;
        }
    }

    /**
     * Método que valida que el nombre del phantom sea correcto.
     *
     * @return
     * @throws SQLException
     */
    private boolean validarNombrePhantom() {
        String mensajeError = "";
        String nombrePhantom = txtNombrePhantom.getText();

        if (CREACION.equals((this.dialogStage.getTitle()))) {
            try {
                if (!ph.buscaNombre(nombrePhantom) == true) {
                    mensajeError += "\nEl nombre del phantom ingresado ya existe!\n";
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!ValidacionesGenerales.ValidarNombrePhantom(nombrePhantom)) {
                mensajeError += "\nNombre del phantom inválido. Debe tener mas de 3 caracteres\n";

            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombrePhantom)) {
                mensajeError += "\nExisten caracteres repetidos.\n";

            }
        }

        if (MODIFICACION.equals(this.dialogStage.getTitle())) {
            if (!nombrePhantom.equals(phantomActual.getPhantomNombre())) {
                try {
                    // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                    if (!ph.buscaNombre(nombrePhantom) == true) { //separacion modo edicion
                        mensajeError += "El nombre del phantom ingresado ya existe!\n";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!ValidacionesGenerales.ValidarNombrePhantom(nombrePhantom)) {
                mensajeError += "\nNombre del phantom inválido. Debe tener mas de 3 caracteres\n";
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombrePhantom)) {
                mensajeError += "\nExisten caracteres repetidos.\n";
            }
        }
        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            //  txtRadNuclidoNombre.requestFocus();
            alert.showAndWait();

            return false;
        }
    }

    /**
     * Método que valida el campo propiedad
     *
     * @return
     * @throws SQLException
     */
    private boolean validarPropiedad() {
        String mensajeError = "";
        String propiedad = txtPropiedad.getText();

        if (CREACION_ITEM.equals(this.dialogStage.getTitle()) || MODIFICACION_ITEM.equals(this.dialogStage.getTitle())) {
            // Validacion propiedad
            if (propiedad == null || propiedad.length() == 0) {
                mensajeError += "El campo propiedad no puede estar vacio. \n";
            } else {
                if (!ValidacionesGenerales.ValidarPropPhantom(propiedad)) {
                    mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
                }
                if (!propiedad.equals(this.itemPhantom.getDescripcion())) {
                    try {
                        if (vd.buscaNombre(propiedad, "phantoms", phantomActual.getIdPhantom())) {
                            mensajeError += "El nombre de la propiedad ya existe en el phantom: " + phantomActual.getPhantomNombre();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AbmPhantomController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ValidacionesGenerales.validarCaracteresRepetidos(propiedad)) {
                    mensajeError += "\nExisten caracteres repetidos.\n";
                }
            }

        }

        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            //txtPropiedad.requestFocus();
            alert.showAndWait();
            return false;
        }

    }

    /**
     * Método que valida el campo Valor
     *
     * @return
     * @throws SQLException
     */
    private boolean validarValor() throws SQLException {
        String mensajeError = "";
        String valor = txtValor.getText();
        if (CREACION_ITEM.equals(this.dialogStage.getTitle()) || MODIFICACION_ITEM.equals(this.dialogStage.getTitle())) {
            // Validacion valor
            if (valor == null || valor.length() == 0) {
                mensajeError += "El campo Valor no puede estar vacio. \n";
            } else {
                try {
                    int i = Integer.parseInt(valor);
                    //int routine
                    //Si puede se pasa el entero a Double. 
                    if (i == 0) {
                        mensajeError += "El campo peso no debe ser 0 !\n"
                                + "Por favor agrege un valor correcto.";
                    }
                } catch (NumberFormatException e) {
                    if (ValidacionesGenerales.ValidarNumericoFloat(valor)) {
                        double d = Double.parseDouble(valor);
                        if (d == 0.0) {
                            mensajeError += "El campo Valor no debe ser 0.0 \n";
                        }
                        //Double routine 
                    } else {
                        mensajeError += "El campo Valor debe ser de tipo númerico separado por . (punto) "
                                + "  Ej: 12.30, por favor no utilize , (coma) \n";
                    }
                }
            }
        }
        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            //txtValor.requestFocus();
            alert.showAndWait();
            return false;
        }

    }

    /**
     * Método para validar el campo unidad.
     *
     * @return
     * @throws SQLException
     */
    private boolean validarUnidad() throws SQLException {
        String mensajeError = "";
        String unidad = txtUnidad.getText();
        if (CREACION_ITEM.equals(this.dialogStage.getTitle()) || MODIFICACION_ITEM.equals(this.dialogStage.getTitle())) {
            // Validacion valor
            if (unidad == null || unidad.length() == 0) {
                mensajeError += "El campo Unidad es inválido. \n";
            }
        }
        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            //  txtUnidad.requestFocus();
            alert.showAndWait();
            return false;
        }

    }

    /**
     * Método que se encarga de la validación de los datos ingresados
     *
     * @return
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {

        String mensajeError = "";
        String valor = txtValor.getText();
        String propiedad = txtPropiedad.getText();
        String unidad = txtUnidad.getText();
        String nombrePhantom = txtNombrePhantom.getText();

        if (CREACION.equals(this.dialogStage.getTitle()) || MODIFICACION.equals(this.dialogStage.getTitle())) {
            if (phantomActual != null) {
                if (!nombrePhantom.equals(phantomActual.getPhantomNombre())) { //Al entrar al modo edicion. Si el nombre no cambia no valida
                    if (ph.buscaNombre(nombrePhantom) == false) {
                        mensajeError += "\nEl nombre ingresado para el phantom ya existe!\n";
                    }
                }
            } else {
                if (ph.buscaNombre(nombrePhantom) == false) {
                    mensajeError += "\nEl nombre ingresado para el phantom ya existe!\n";
                }
            }

            if (!ValidacionesGenerales.ValidarNombrePhantom(nombrePhantom)) {
                mensajeError += "Nombre del phantom inválido. Debe tener mas de 3 caracteres";
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombrePhantom)) {
                mensajeError += "\nExisten caracteres repetidos.\n";
            }

        }

        if (CREACION_ITEM.equals(this.dialogStage.getTitle()) || MODIFICACION_ITEM.equals(this.dialogStage.getTitle())) {
            if (propiedad == null || propiedad.length() == 0) {
                mensajeError += "El campo propiedad no puede estar vacio. \n";
            } else {
                if (!propiedad.equals(this.itemPhantom.getDescripcion())) {
                    if (vd.buscaNombre(propiedad, "phantoms", phantomActual.getIdPhantom())) {
                        mensajeError += "El nombre de la propiedad ya existe en el phantom. ";
                    }
                }
                if (!ValidacionesGenerales.ValidarPropPhantom(propiedad)) {
                    mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
                }
                if (ValidacionesGenerales.validarCaracteresRepetidos(propiedad)) {
                    mensajeError += "\nExisten caracteres repetidos.\n";
                }
            }
            //Validacion Valor
            if (valor == null || valor.length() == 0) {
                mensajeError += "El campo Valor no puede estar vacio. \n";
            } else {
                try {
                    int i = Integer.parseInt(valor);
                    //int routine
                    //Si puede se pasa el entero a Double. 
                } catch (NumberFormatException e) {
                    if (ValidacionesGenerales.ValidarNumericoFloat(valor)) {
                        double d = Double.parseDouble(valor);
                        if (d == 0.0) {
                            mensajeError += "El campo Valor no debe ser 0.0 \n";
                        }
                        //Double routine 
                    } else {
                        mensajeError += "El campo Valor debe ser de tipo númerico separado por . (punto) "
                                + "  Ej: 12.30, por favor no utilize , (coma) \n";

                    }
                }

            }

            //Validacion Unidad. 
            // Al no saber con ciencia cierta lo que el usuario seleccionara como unidad. Este campo solo valida que el 
            // no este vacio o sin caracteres. 
            if (unidad == null || unidad.length() == 0) {
                mensajeError += "El campo Unidad es inválido. \n";
            }
        }

        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            alert.showAndWait();
            return false;
        }

    }

    @FXML
    public void IgnorarValidacion() {
        //  System.out.println("IgnoraValidacion");
        IgnorarValidacion = true;
    }

    @FXML
    public void RetornarValidacion() {
        // System.out.println("RetornaValidacion");
        IgnorarValidacion = false;
    }
}
