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

    //******************** variables 
    //Objeto Lista de radionuclidos  auxiliar. 
    private ObservableList<ValorDescripcion> listaAtributoRadNuclido = FXCollections.observableArrayList();
    //Objeto radionuclido auxiliar. 
    private Radionuclido radionuclido;
    private ValorDescripcion itemRadionuclido;
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
    private static final int LIMIT_VALOR = 14;
    private static final int LIMIT_UNIDAD = 255;

    ObservableList<String> data;
    ListView listaSugerida = new ListView();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();
    boolean bandera = false;

    /**
     * Inicializa la clase initialize del controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL); //To change body of generated methods, choose Tools | Templates.
        dialogStage.setResizable(false);

        data = vd.listadoPropiedades("radionuclidos");
        listaSugerida.setItems(data);

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
                listaSugerida.setItems(data);
                listaSugerida.setVisible(true);
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

            //apago los textFields.
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

        txtRadNuclidoNombre.setText(radionuclidoActual.getNombreRadNuclido());
        txtRadNuclidoNombre.setEditable(false);
        txtRadNuclidoNombre.setFocusTraversable(false);

        txtPropiedad.setText(itemRadionuclido.getDescripcion());
        txtValor.setText(itemRadionuclido.getValor());
        txtUnidad.setText(itemRadionuclido.getUnidad());
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
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dialogStage.close();
        } else {

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
                break;
        }
    }

    /**
     * Método que se encarga de la validación de los datos ingresados por el
     * usuario.
     *
     * @return
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {

        String mensajeError = "";
        String valor = txtValor.getText();
        String propiedad = txtPropiedad.getText();
        String unidad = txtUnidad.getText();
        String nombreRadNuclido = txtRadNuclidoNombre.getText();

        if ("Crear un radionúclido".equals(this.dialogStage.getTitle()) || "Modificar nombre del radionúclido".equals(this.dialogStage.getTitle())) {
            if (nombreRadNuclido.length()==0){
                mensajeError += "Debe agregar un nombre para el radionúclido \n";
            }
            if (!nombreRadNuclido.equals(this.radionuclido.getNombreRadNuclido())) {
                // Solo valido
                // campo en NULL y Campo con logitud 0
                if (txtRadNuclidoNombre.getText() == null || txtRadNuclidoNombre.getText().length() == 0) {
                    mensajeError += "Debe agregar un nombre para el radionúclido \n";
                }
                if (!ValidacionesGenerales.ValidarNombreRadNuclido(nombreRadNuclido)) {
                    mensajeError += "Nombre del radionúclido inválido \n Ej: Yodo-131";
                }
                if (!rad.buscaNombre(nombreRadNuclido)) {
                    mensajeError += "El nombre del radionúclido ya existe! \n";
                }
            }
        } else {
            /*
             Debido  a la utilización del mismo formulario para el abm de radionúclido. 
             Cuando se modifica el nombre los campos de unidad, propiedad y valor están desactivados. 
             Por eso se pregunta si estan prendidos los textfields*/

            // Validacion propiedad
            if (propiedad == null || propiedad.length() == 0) {
                mensajeError += "El campo Propiedad no puede estar vacio. \n";
            } else {
                //Si el nombre no cambia en modo edicion
                if (!propiedad.equals(this.itemRadionuclido.getDescripcion())) {
                    if (vd.buscaNombre(propiedad, "radionuclidos", radionuclidoActual.getIdRadNuclido())) {
                        mensajeError += "El nombre de la propiedad ya existe. \n";
                    }
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
                        //double routine

                    } else {
                        mensajeError += "El campo Valor debe ser númerico separado por . (punto) "
                                + "  Ej: 12.30 \n";
                        //throw new IllegalArgumentException();
                    }
                }

            }

            //Validacion Unidad. 
            // Al no saber con ciencia cierta lo que el usuario seleccionara como unidad. Este campo solo valida que el 
            // no este vacio o sin caracteres. 
            if (unidad == null || unidad.length() == 0) {
                mensajeError += "El campo Unidad es inválido! \n";
            }
        }
            

        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
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

}
