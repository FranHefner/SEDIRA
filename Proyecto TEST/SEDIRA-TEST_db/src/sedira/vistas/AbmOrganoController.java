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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private static final String CREACION = "Agregar Órgano";
    private static final String MODIFICACION = "Modificar Órgano";
    private int UltimoFoco;
    ObservableList<String> data = FXCollections.observableArrayList();
    ListView<String> listaSugerida = new ListView<String>();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();

    boolean bandera = false;
    private boolean IgnorarValidacion = false;

    @FXML
    public void IgnorarValidacion() {

        //System.out.println("IgnoraValidacion");
        IgnorarValidacion = true;
    }

    @FXML
    public void RetornarValidacion() {

        //System.out.println("RetornaValidacion");
        IgnorarValidacion = false;
    }

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
        //Listener para la lista Sugerida 
        txtOrganoNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() == 0) {
                    actualizarListaSugerida(newValue);
                }
            }
        });

        //Validacion al perder el Focus en el nombre de organo. 
        txtOrganoNombre.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            UltimoFoco = 1;
            if (!newPropertyValue && txtOrganoNombre.getText().length() > 0 && IgnorarValidacion == false) {
                if (validarNombreOrgano()) {
                        //validacion correcta
                        //  System.out.println("Entro a validar el organo");
                    } else {
                        txtOrganoNombre.requestFocus();
                    }
            }
        });

        txtOrganoMasa.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                UltimoFoco = 2;
                String mensajeError = "";
                String masa = txtOrganoMasa.getText();

                if (!newPropertyValue && txtOrganoMasa.getText().length() > 0 && IgnorarValidacion == false) {
                    //System.out.println("ENTRO A LA VALIDACION");
                    boolean ValidacionOK = true;
                    String Error = "";

                    if (masa == null || masa.length() == 0) {
                        mensajeError += "El campo peso no puede estar vacio. \n";
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
                                            + "Por favor agrege un valor correcto.";
                                    ValidacionOK = false;
                                }

                            } else {
                                mensajeError += "El campo peso debe ser númerico separado por . "
                                        + "  Ej: 12.30 \n";
                                ValidacionOK = false;
                                //throw new IllegalArgumentException();
                            }
                        }

                    }

                    if (ValidacionOK == false) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Validación");
                        alert.setHeaderText(mensajeError);
                        alert.setContentText(Error);
                        alert.showAndWait();
                        txtOrganoMasa.requestFocus();
                    }
                } else {
                    //System.out.println(" NO   ENTRO A LA VALIDACION");
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
                        /*
                         IgnorarValidacion = true;
                         Alert alert = new Alert(Alert.AlertType.WARNING);
                         alert.setTitle("Validación");
                         alert.setHeaderText("Se exedio en la cantidad de caracteres permitidos, el último caracter ingreso fué borrado");
                         alert.setContentText("");
                         alert.showAndWait();
                         IgnorarValidacion = false;*/

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
        //Comportamiento de la lista sugerida. 
        if (data.size() != 0) {
            listaSugerida.setItems(data);
        } else {
            listaSugerida.setItems(data);
            listaSugerida.setVisible(false);
        }

        txtOrganoNombre.textProperty().addListener(
                (observable, oldValue, newValue) -> actualizarListaSugerida(newValue));

        boxControles.getChildren().addAll(txtOrganoNombre, listaSugerida);

        listaSugerida.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarItem(newValue));
    }

    /**
     * Método que controla la seleccion de un item.
     *
     * @param itemSeleccionado
     */
    public void seleccionarItem(String itemSeleccionado) {
        if (itemSeleccionado != null) {
            txtOrganoNombre.setText(itemSeleccionado);
            txtOrganoNombre.requestFocus();
            txtOrganoNombre.positionCaret(txtOrganoNombre.getText().length());
            //System.out.print(txtOrganoNombre.getText() );
        }

    }

    /**
     * Método que controla la lista de sugerencias de nombres para los órganos.
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
        if (organo.getIdOrgano() != -1) {
            /**
             * Obtiene el organo seleccionado de la lista de organos.
             */
            dialogStage.setTitle(MODIFICACION);
            txtOrganoNombre.setText(this.organo.getNombreOrgano());
            txtOrganoMasa.setText(String.valueOf(this.organo.getOrganMass()));
            //listaSugerida.setDisable(true);
            //listaSugerida.setVisible(false);
        } else {
            /**
             * Si el id==-1 se trata de un nuevo órgano.
             */
            dialogStage.setTitle(CREACION);
            txtOrganoNombre.setText("");
            txtOrganoMasa.setText("");
            //listaSugerida.setDisable(true);
            //listaSugerida.setVisible(false);

        }

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
            NombreOrgano = listaSugerida.getSelectionModel().getSelectedItem();
        }
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
        if (validarDatosEntrada()) {

            if (CREACION.equals(this.dialogStage.getTitle())) {

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
        switch (dialogStage.getTitle()) {
            case CREACION:
                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del órgano? ");
                break;
            case MODIFICACION:
                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del órgano? ");
                break;
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            IgnorarValidacion = true;
            dialogStage.close();
        } else {
            if (UltimoFoco == 1) {
                txtOrganoNombre.requestFocus();
            }
            if (UltimoFoco == 2) {
                txtOrganoMasa.requestFocus();
            }
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
     * @throws java.sql.SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {
        String mensajeError = "";
        int idPhantom = phantom.getIdPhantom();
        String nombreOrgano = txtOrganoNombre.getText();
        String masa = txtOrganoMasa.getText();

        if (CREACION.equals(this.dialogStage.getTitle()) || (MODIFICACION.equals(this.dialogStage.getTitle()))) {
            if (!nombreOrgano.equals(organo.getNombreOrgano())) {
                try {
                    // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                    if (org.buscarOrganoPhantom(idPhantom, nombreOrgano) == true) { //separacion modo edicion
                        mensajeError += "El nombre del órgano ingresado ya existe en el phantom!\n";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AbmOrganoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!ValidacionesGenerales.ValidarNombreOrgano(nombreOrgano)) {
                mensajeError += "\nNombre del órgano inválido. Debe tener mas de 3 caracteres\n";

            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreOrgano)) {
                mensajeError += "\nExisten caracteres repetidos.\n";

            }
        }

        if (masa == null || masa.length() == 0) {
            mensajeError += "El campo peso no puede estar vacio. \n";
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
                                + "Por favor agrege un valor correcto.";
                    }
                    //double routine

                } else {
                    mensajeError += "El campo peso debe ser númerico separado por . "
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

    private boolean validarNombreOrgano() {
        String mensajeError = "";
        String nombreOrgano = txtOrganoNombre.getText();
        int idPhantom = phantom.getIdPhantom();

        if (CREACION.equals(this.dialogStage.getTitle()) || (MODIFICACION.equals(this.dialogStage.getTitle()))) {
            if (!nombreOrgano.equals(organo.getNombreOrgano())) {
                try {
                    // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                    if (org.buscarOrganoPhantom(idPhantom, nombreOrgano) == true) {
                        mensajeError += "El nombre del órgano ingresado ya existe!\n";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AbmOrganoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!ValidacionesGenerales.ValidarNombreOrgano(nombreOrgano)) {
                mensajeError += "\nNombre del órgano inválido. Debe tener mas de 3 caracteres\n";

            }
            if (ValidacionesGenerales.validarCaracteresRepetidos(nombreOrgano)) {
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
            alert.showAndWait();

            return false;
        }
    }

}
