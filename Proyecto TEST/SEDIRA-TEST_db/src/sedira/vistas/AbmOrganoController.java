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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IOrganoDAO;

import sedira.model.Organo;
import sedira.model.OrganoDAOsql;
import sedira.model.Phantom;

/**
 * FXML Controller class Clase Controladora para AbmOrgano.Fxml
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class AbmOrganoController implements Initializable {
    //declaración de elementos de interfaz gráfica

    @FXML
    private TextField txtOrganoNombre;
    @FXML
    private TextField txtOrganoMasa;
    @FXML
    private Label phantomInfo;

    @FXML
    private VBox boxControles;

    //******************** variables 
    //Objeto ListaOrgano auxiliar. 
    private ObservableList<Organo> listaOrgano = FXCollections.observableArrayList();
    //Objeto auxiliar de tipo Organo. 
    private Organo organo;
    //Objeto Phantom auxiliar. 
    private Phantom phantom;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Instancia de objeto IOrganoDAO. Inicializado como OrganoDAOsql. Para implementacion en MySql.  
    private IOrganoDAO org = new OrganoDAOsql();
    final private int LIMIT_NOMBRE = 45;
    final private int LIMIT_MASA = 14;

    ObservableList<String> data = FXCollections.observableArrayList();
    ListView<String> listaSugerida = new ListView<String>();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();

    boolean bandera = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Listener para la cantidad de caracteres en el nombre 
        txtOrganoNombre.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtOrganoNombre.getText().length() >= LIMIT_NOMBRE) {

                        txtOrganoNombre.setText(txtOrganoNombre.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        });

        //Listener para la cantidad de caracteres en el valor 
        txtOrganoMasa.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtOrganoMasa.getText().length() >= LIMIT_MASA) {

                        // if it's 11th character then just setText to previous
                        // one
                        txtOrganoMasa.setText(txtOrganoMasa.getText().substring(0, LIMIT_MASA));
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

        data = org.listadoOrganos();
        listaSugerida.setItems(data);

        txtOrganoNombre.textProperty().addListener(
                (observable, oldValue, newValue) -> actualizarListaSugerida(newValue));

        boxControles.getChildren().addAll(txtOrganoNombre, listaSugerida);

        listaSugerida.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarItem(newValue));
    }

    public void seleccionarItem(String itemSeleccionado) {

        bandera = true;
        if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {
            bandera = false;
        } else {
            txtOrganoNombre.setText(itemSeleccionado);
        }

    }

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
     * Setea el Phantom a editar. Se edita el phantom porque lo órganos están
     * incluidos dentro de un phantom en particular.
     *
     * @param phantom a editar.
     */
    public void setPhantom(Phantom phantom) {
        this.phantom = phantom;
        String aux = "Órganos pertenecientes al Phantom: " + phantom.getPhantomNombre();
        phantomInfo.setText(aux);

    }

    /**
     * Setea el órgano seleccionado en la lista de órganos de un phantom para la
     * edición.
     *
     * @param organo
     */
    public void setOrgano(Organo organo) {
        this.organo = organo;
        // lblOrganoId.setText("Id: " + String.valueOf(ConsultasDB.getNewIdOrgano()));
        txtOrganoNombre.setText(this.organo.getNombreOrgano());
        txtOrganoMasa.setText(String.valueOf(this.organo.getOrganMass()));
    }

    /**
     * Este método setea en los textFields la información que el usuario
     * selecciona de la tabla de órganos.
     *
     * @param organo es el organo seleccionado desde la tabla.
     */
    @FXML
    public void mostrarDetalleSeleccion(Organo organo) {

        //btnQuitar.setDisable(false);
        if (organo != null) {

            txtOrganoNombre.setText(organo.getNombreOrgano());

            txtOrganoMasa.setText(organo.getOrganMass().toString());

        } else {
            txtOrganoNombre.setText("");
            txtOrganoMasa.setText("");

        }
    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     * Guarda los datos cargados por el usuario en el phantom correspondiente.
     * Antes, valida que los datos sean correctos.
     */
    @FXML
    public void btnGuardarDatos() throws SQLException {

        //Obtengo el phantom actual. El phantom contiene la lista de órganos. 
        phantom = FuncionesGenerales.getPhantomActual();
        String NombreOrgano;

        // Me fijo si hay un órgano seleccionado o es un nuevo órgano
        if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {
            NombreOrgano = txtOrganoNombre.getText();
        } else {
            NombreOrgano = listaSugerida.getSelectionModel().getSelectedItem().toString();
        }
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
        if (validarDatosEntrada()) {

            if ("Agregar Organo".equals(this.dialogStage.getTitle())) {

                organo.setNombreOrgano(NombreOrgano);
                organo.setOrganMass(Double.valueOf(txtOrganoMasa.getText()));

            } else {
                // el organo ya existe, por lo tanto se edita. 
                organo.setNombreOrgano(NombreOrgano);
                organo.setOrganMass(Double.valueOf(txtOrganoMasa.getText()));
            }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar edición");
        alert.setHeaderText("Atención!");
        alert.setContentText("Esta seguro de cancelar la edición del órgano? ");

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
    private void btnLimpiarValores() {
        txtOrganoNombre.setText("");
        txtOrganoMasa.setText("");

    }

    /**
     * Validación de los datos de entrada para Organos.
     *
     * @return
     */
    public boolean validarDatosEntrada() throws SQLException {
        String mensajeError = "";
        int idPhantom = phantom.getIdPhantom();
        String nombre = txtOrganoNombre.getText();
        String masa = txtOrganoMasa.getText();

        // TODO. Validar los datos de entrada 
        if (nombre == null || nombre.length() == 0) {
            mensajeError += "Nombre del órgano invalido!\n";
        }

        if (!"Editar Organo".equals(this.dialogStage.getTitle())) {

            if (org.buscaNombre(nombre, idPhantom) == false) {
                mensajeError += "El órgano que desea agregar ya existe! \n";
            }
        }
        if (masa == null || masa.length() == 0) {
            mensajeError += "Valor inválido! \n";
        } else {
            try {
                int i = Integer.parseInt(masa);
                //int routine
                //Si puede se pasa el entero a Double. 
            } catch (NumberFormatException e) {
                if (ValidacionesGenerales.ValidarNumericoFloat(masa)) {
                    double d = Double.parseDouble(masa);
                    if (d == 0.0) {
                        mensajeError += "El campo peso no debe ser 0.0 !\n"
                                + "Por favor agrege un valor de peso correcto.";
                    }
                    //double routine

                } else {
                    mensajeError += "El campo Masa debe ser númerico separado por . "
                            + "  Ej: 12.30 \n";
                    //throw new IllegalArgumentException();
                }
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

}
