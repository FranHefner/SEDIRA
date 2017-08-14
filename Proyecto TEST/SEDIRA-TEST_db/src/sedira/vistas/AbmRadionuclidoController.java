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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IRadionuclidoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Radionuclido;
import sedira.model.RadionuclidoDAOsql;
import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class Clase controladora de la interfaz Editar/Nuevo
 * Radionuclido.
 *
 * @author Quelin Pablo, Hefner Francisco.
 */
public class AbmRadionuclidoController implements Initializable {

    @FXML
    private TextField txtRadNuclidoNombre;
    @FXML
    private TextField txtIdRadNuclido;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;

    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private VBox boxControles;

    //Objeto Lista de radionuclidos  auxiliar. 
    private ObservableList<ValorDescripcion> listaAtributoRadNuclido = FXCollections.observableArrayList();

    //Objeto radionuclido auxiliar. 
    private Radionuclido radionuclido;
    //Objeto de tipo ValorDescripcion auxilizar, utilizado para los items que describen a un radionuclido
    private ValorDescripcion itemRadionuclido;
    //RadionuclidoActual es el radionuclido seleccionado con el que se esta trabajando. 
    private Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IRadionuclidoDAO rad = new RadionuclidoDAOsql();
    //Instancia de objeto tipo IValorDescripcionDAO. Se inicializa como ValorDescripcionDAOsql.  
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();

    private static final int LIMIT_NOMBRE = 45;
    private static final int LIMIT_NOMBRERADIONUCLIDO = 45;
    private static final int LIMIT_VALOR = 14;
    private static final int LIMIT_UNIDAD = 255;
    private static final String CREACION = "Crear un Radionúclido";
    private static final String MODIFICACION = "Modificar nombre del Radionúclido";
    private static final String CREACION_ITEM = "Agregar ítems";
    private static final String MODIFICACION_ITEM = "Modificar ítems";
    
    ObservableList<String> data;
    ListView listaSugerida = new ListView();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();
    boolean bandera = false;
    boolean IgnorarValidacion = false;
    int UltimoFoco = 0;

    /**
     * Inicializa la clase initialize del controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Listener para la cantidad de caracteres en el nombre del radionuclido 
        txtRadNuclidoNombre.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtRadNuclidoNombre.getText().length() >= LIMIT_NOMBRE) {
                        txtRadNuclidoNombre.setText(txtRadNuclidoNombre.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        });

        //Validacion al perder el Focus. 
        txtRadNuclidoNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            UltimoFoco = 1;
            if (!newPropertyValue && txtRadNuclidoNombre.getText().length() > 0 && IgnorarValidacion == false) {
                if (!validarNombreRadNuclido()) {
                    txtRadNuclidoNombre.requestFocus();
                } else {
                    // System.out.println("entro a validacion");
                }

            }
        });

        //Listener para la cantidad de caracteres en el nombre en las propiedades
        txtPropiedad.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue
            ) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtPropiedad.getText().length() >= LIMIT_NOMBRE) {
                        txtPropiedad.setText(txtPropiedad.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        }
        );

        //Validacion al perder el Focus. 
        txtPropiedad.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 2;
                if (!newPropertyValue && txtPropiedad.getText().length() > 0 && IgnorarValidacion == false) {
                    if (validarPropiedad()) {
                        //validacion correcta
                        System.out.println("Entro a validar propiedad");
                    } else {
                        txtPropiedad.requestFocus();
                    }

                }
            }
        }
        );
        //Listener para la cantidad de caracteres en el nombre en el valor 
        txtValor.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtValor.getText().length() >= LIMIT_VALOR) {
                        txtValor.setText(txtValor.getText().substring(0, LIMIT_VALOR));
                    }
                }
            }
        }
        );
        //Listener Validacion LostFocus Valor 
        txtValor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 3;
                if (!newPropertyValue && txtValor.getText().length() > 0 && IgnorarValidacion == false) {
                    try {
                        if (validarValor()) {
                            System.out.println("Entro a validar valor");
                        } else {
                            txtValor.requestFocus();

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        );
        //Listener para la cantidad de caracteres en el nombre en el campo unidad 
        txtUnidad.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtUnidad.getText().length() >= LIMIT_UNIDAD) {
                        txtUnidad.setText(txtUnidad.getText().substring(0, LIMIT_UNIDAD));
                    }
                }
            }
        }
        );
        //Listener Validacion LostFocus Unidad 
        txtUnidad.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                        UltimoFoco=4;
                        if (!newPropertyValue && txtUnidad.getText().length() > 0 && IgnorarValidacion == false) {
                                try {
                                    if (validarUnidad()) {
                                      System.out.println("Entro a validar unidad");
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
        dialogStage.initModality(Modality.APPLICATION_MODAL); //To change body of generated methods, choose Tools | Templates.
        //

        dialogStage.setResizable(false);

        //Obtengo el listado de propiedades (valorDescripcion) que pertenecen aun radionuclido. 
        data = vd.listadoPropiedades("radionuclidos");
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
        txtPropiedad.setText(itemSeleccionado);
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
     * Setea el Radionúclido a editar.
     *
     * @param radionuclido a editar.
     */
    public void setRadionuclido(Radionuclido radionuclido) {
        this.radionuclido = radionuclido;

        if (radionuclido.getIdRadNuclido() != -1) {
            /**
             * Obtiente el Radionuclido seleccionado en la busqueda del
             * formulario radionuclido.fxml
             */
            this.dialogStage.setTitle("Modificar nombre del radionúclido");
            txtRadNuclidoNombre.setEditable(true);
            txtRadNuclidoNombre.setFocusTraversable(true);
            txtRadNuclidoNombre.setText(radionuclido.getNombreRadNuclido());

            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            listaSugerida.setDisable(true);
            listaSugerida.setVisible(false);

        } else {

            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear un radionúclido");
            listaSugerida.setDisable(true);
            listaSugerida.setVisible(false);

            //prendo los textFields.
            txtRadNuclidoNombre.setEditable(true);
            txtRadNuclidoNombre.setFocusTraversable(true);

            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Prendo los botones. 

        }
    }

    /**
     * Método SetTer para el item de radionúclido que se selecciono.
     *
     * @param itemRadionuclido
     */
    public void setItemRadionuclido(ValorDescripcion itemRadionuclido) {

        this.itemRadionuclido = itemRadionuclido;
        if (itemRadionuclido.getId() != -1) {
            this.dialogStage.setTitle("Modificar ítems");
            txtRadNuclidoNombre.setText(radionuclidoActual.getNombreRadNuclido());
            txtRadNuclidoNombre.setEditable(false);
            txtRadNuclidoNombre.setFocusTraversable(false);

            txtPropiedad.setText(itemRadionuclido.getDescripcion());
            txtValor.setText(itemRadionuclido.getValor());
            txtUnidad.setText(itemRadionuclido.getUnidad());
        } else {
            this.dialogStage.setTitle("Agregar ítems");
            txtRadNuclidoNombre.setText(radionuclidoActual.getNombreRadNuclido());
            txtRadNuclidoNombre.setEditable(false);
            txtRadNuclidoNombre.setFocusTraversable(false);

            txtPropiedad.setText(itemRadionuclido.getDescripcion());
            txtValor.setText(itemRadionuclido.getValor());
            txtUnidad.setText(itemRadionuclido.getUnidad());
        }

    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnGuardarDatos() throws SQLException {
        // TODO: VALIDACIONES.  

        String NombrePropiedad;
        // Me fijo si hay una propiedad seleccionada o es un nuevo
        if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {
            NombrePropiedad = txtPropiedad.getText();
        } else {
            NombrePropiedad = listaSugerida.getSelectionModel().getSelectedItem().toString();
        }
        if (validarDatosEntrada()) {
            switch (dialogStage.getTitle()) {
                case "Crear un radionúclido":
                    radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                    radionuclido.setPropiedades(listaAtributoRadNuclido);
                    break;
                case "Modificar nombre del radionúclido":
                    this.radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                    break;
                case "Agregar ítems":
                    itemRadionuclido.setDescripcion(NombrePropiedad);
                    itemRadionuclido.setUnidad(txtUnidad.getText());
                    itemRadionuclido.setValor(txtValor.getText());
                    break;
                case "Modificar ítems":
                    itemRadionuclido.setDescripcion(NombrePropiedad);
                    itemRadionuclido.setUnidad(txtUnidad.getText());
                    itemRadionuclido.setValor(txtValor.getText());
                    break;

            }
            //Si las validaciones son correctas se guardan los datos
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
     * Método que se llama al presionar el boton cancelar.
     */
    @FXML
    public void btnCancel_click() {

        Alert alert = new Alert(AlertType.CONFIRMATION);

        switch (dialogStage.getTitle()) {
            case "Crear un radionúclido":
                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del radionúclido? ");
                break;
            case "Modificar nombre del radionúclido":
                alert.setTitle("Cancelar modificación del radionúclido");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del radionúclido? ");
                break;
            case "Modificar ítems":
                alert.setTitle("Cancelar modificación del radionúclido");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del radionúclido? ");
                break;
            case "Agregar ítems":
                alert.setTitle("Cancelar modificación del radionúclido");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del radionúclido? ");
                break;
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            IgnorarValidacion = true;
            dialogStage.close();
        } else {
            if (UltimoFoco == 1) {
                txtRadNuclidoNombre.requestFocus();
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
     * Método para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {
        switch (dialogStage.getTitle()) {
            case "Crear un radionúclido":
                txtRadNuclidoNombre.setText("");
                break;
            case "Modificar nombre del radionúclido":
                txtRadNuclidoNombre.setText("");
                break;
            case "Modificar ítems":
                txtUnidad.setText("");
                txtPropiedad.setText("");
                txtValor.setText("");
                listaSugerida.getSelectionModel().clearSelection();
                listaSugerida.setVisible(false);
                break;
        }
    }

    /**
     * Método que se encarga de la validación de los datos ingresados por el
     * usuario.
     *
     * @return True si acepta. False si falla
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {

        String mensajeError = "";
        String valor = txtValor.getText();
        String propiedad = txtPropiedad.getText();
        String unidad = txtUnidad.getText();
        String nombreRadNuclido = txtRadNuclidoNombre.getText();

        if ("Crear un radionúclido".equals((this.dialogStage.getTitle()))) {
            if (!rad.buscaNombre(nombreRadNuclido) == true) {
                mensajeError = "\nEl nombre de radionúclido ingresado ya existe!\n";
            }
            if (!ValidacionesGenerales.ValidarNombreRadNuclido(nombreRadNuclido)) {
                mensajeError = "\nNombre del radionúclido inválido \n Ejemplo: Yodo-131";
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreRadNuclido)) {
                mensajeError += "\nExisten caracteres repetidos.\n";
            }
        }
        if ("Modificar nombre del radionúclido".equals(this.dialogStage.getTitle())) {
            if (!nombreRadNuclido.equals(radionuclidoActual.getNombreRadNuclido())) { // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                if (!rad.buscaNombre(nombreRadNuclido) == true) { //separacion modo edicion
                    mensajeError += "El nombre de radionúclido ingresado ya existe!\n";
                }
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreRadNuclido)) {
                mensajeError += "\nExisten caracteres repetidos.\n";
            }
        }

        if ("Agregar ítems".equals(this.dialogStage.getTitle())) {
            if (!propiedad.equals(this.itemRadionuclido.getDescripcion())) {
                if (vd.buscaNombre(propiedad, "radionuclidos", radionuclidoActual.getIdRadNuclido())) {
                    mensajeError += "El nombre de la propiedad ya existe en el radionúclido. ";
                }
            }
            if (propiedad == null || !ValidacionesGenerales.ValidarPropRadNuclido(propiedad)) {
                mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
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
        if ("Modificar ítems".equals(this.dialogStage.getTitle())) {
            /*
             Debido  a la utilización del mismo formulario para el abm de radionúclido. 
             Cuando se modifica el nombre los campos de unidad, propiedad y valor están desactivados. 
             Por eso se pregunta si estan prendidos los textfields*/

            // Validacion propiedad
            if (propiedad == null || !ValidacionesGenerales.ValidarPropRadNuclido(propiedad)) {
                mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
            }

            //Si el nombre no cambia en modo edicion
            if (!propiedad.equals(this.itemRadionuclido.getDescripcion())) {
                if (vd.buscaNombre(propiedad, "radionuclidos", radionuclidoActual.getIdRadNuclido())) {
                    mensajeError += "El nombre de la propiedad ya existe en el radionúclido: " + nombreRadNuclido;
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

    /**
     * Método para el comportamiento presionar enter.
     *
     * @throws Exception
     */
    @FXML
    public void onEnter() throws Exception {
        btnGuardarDatos();
    }

    /**
     * Método que valida que el nombre del radionúclido sea correcto.
     *
     * @return
     * @throws SQLException
     */
    private boolean validarNombreRadNuclido() {
        String mensajeError = "";
        String nombreRadNuclido = txtRadNuclidoNombre.getText();

        if ("Crear un radionúclido".equals((this.dialogStage.getTitle()))) {
            try {
                if (!rad.buscaNombre(nombreRadNuclido) == true) {
                    mensajeError = "\nEl nombre de radionúclido ingresado ya existe!\n";

                }
            } catch (SQLException ex) {
                Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!ValidacionesGenerales.ValidarNombreRadNuclido(nombreRadNuclido)) {
                mensajeError = "\nNombre del radionúclido inválido \n Ejemplo: Yodo-131";
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreRadNuclido)) {
                mensajeError += "\nExisten caracteres repetidos.\n";
            }
        }

        if ("Modificar nombre del radionúclido".equals(this.dialogStage.getTitle())) {
            if (!nombreRadNuclido.equals(radionuclidoActual.getNombreRadNuclido())) {
                try {
                    // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                    if (!rad.buscaNombre(nombreRadNuclido) == true) { //separacion modo edicion
                        mensajeError += "El nombre de radionúclido ingresado ya existe!\n";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!ValidacionesGenerales.ValidarNombreRadNuclido(nombreRadNuclido)) {
                mensajeError = "\nNombre del radionúclido inválido \n Ejemplo: Yodo-131";
            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreRadNuclido)) {
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

        if ("Agregar ítems".equals(this.dialogStage.getTitle())) {
            // Validacion propiedad
            if (propiedad == null || !ValidacionesGenerales.ValidarPropRadNuclido(propiedad)) {
                mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
            }
            if (!propiedad.equals(this.itemRadionuclido.getDescripcion())) {
                try {
                    if (vd.buscaNombre(propiedad, "radionuclidos", radionuclidoActual.getIdRadNuclido())) {
                        mensajeError += "El nombre de la propiedad ya existe en el radionúclido: " + radionuclidoActual.getNombreRadNuclido();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if ("Modificar ítems".equals(this.dialogStage.getTitle())) {
            // Validacion propiedad
            if (propiedad == null || !ValidacionesGenerales.ValidarPropRadNuclido(propiedad)) {
                mensajeError += "\nEl nombre de la propiedad debe contener como minimo 4 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones. \n";
            } else {
                //Si el nombre no cambia en modo edicion
                if (!propiedad.equals(this.itemRadionuclido.getDescripcion())) {
                    try {
                        if (vd.buscaNombre(propiedad, "radionuclidos", radionuclidoActual.getIdRadNuclido())) {
                            mensajeError += "El nombre de la propiedad ya existe en el radionúclido: " + radionuclidoActual.getNombreRadNuclido();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AbmRadionuclidoController.class.getName()).log(Level.SEVERE, null, ex);
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
        if ("Agregar ítems".equals(this.dialogStage.getTitle()) || "Modificar ítems".equals(this.dialogStage.getTitle())) {
            // Validacion valor
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
        if ("Agregar ítems".equals(this.dialogStage.getTitle()) || "Modificar ítems".equals(this.dialogStage.getTitle())) {
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
    /*
     @FXML
     boolean onEscape() {
     KeyEvent event = null;
     switch (event.getCode()) {
     case ESCAPE:
     btnCancelar.fire();
     return true;

     default:
     return false;

     }

     }*/

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
