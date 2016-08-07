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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializo los botones. 

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
        if (validarDatosEntrada()) {

            if ("Agregar Organo".equals(this.dialogStage.getTitle())) {

                organo.setNombreOrgano(txtOrganoNombre.getText());
                organo.setOrganMass(Double.valueOf(txtOrganoMasa.getText()));

            } else {
                // el organo ya existe, por lo tanto se edita. 
                organo.setNombreOrgano(txtOrganoNombre.getText());
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
                        mensajeError += "El campo Masa no debe ser 0.0 !\n";
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
