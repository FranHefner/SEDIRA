/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sedira.model.Paciente;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PacienteController implements Initializable {

    @FXML
    private TableView<Paciente> griListaPacientes;
    @FXML
    private TableColumn<Paciente, String> clTipoDoc;
    @FXML
    private TableColumn<Paciente, Integer> clNumeroDoc;
    @FXML
    private TableColumn<Paciente, String> clNombre;
    @FXML
    private TableColumn<Paciente, String> clApellido;
    @FXML
    private TextField txtIdPaciente;
    @FXML
    private TextField txtNumeroDoc;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCampoBusqueda;
    @FXML
    private ImageView imgPaciente;
    @FXML
    private ComboBox cbTipoDoc;

    private Paciente PacienteActual;

    /**
     * Inicializacion de la clase Controlador.
     */
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
    private ObservableList<String> DocumentosData = FXCollections.observableArrayList();
    @FXML
    private Pane grbBotones;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEditar1;
    @FXML
    private Button btnEditar11;
    @FXML
    private TextField txtFechaNacimiento;
    @FXML
    private Button btnHistorialSEDIRA;
    @FXML
    private Button btnContacto;
    @FXML
    private CheckBox chkTratamiento;
    @FXML
    private Button btnMediciones;

    public ObservableList<Paciente> getPacienteData() {
        return pacienteData;
    }
    // public ObservableList<TipoDocumento> getDocumentosData() {
    //    return DocumentosData;
    // }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellido());
        clTipoDoc.setCellValueFactory(cellData -> cellData.getValue().getTipoDoc());
        clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDoc().asObject());

        griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));

        pacienteData = ConsultasDB.ListaPacientes();
        DocumentosData = ConsultasDB.ListaTipoDocumento();
        cbTipoDoc.setItems(DocumentosData);

    }

    @FXML
    private void btnBuscar_click() {
        griListaPacientes.setItems(FuncionesGenerales.FiltroListaPaciente(griListaPacientes, pacienteData, txtCampoBusqueda));
    }

    private void SeleccionPaciente(Paciente PacienteActual) {

        if (PacienteActual != null) {

            this.PacienteActual = PacienteActual;
            txtIdPaciente.setText(String.valueOf(PacienteActual.getidPaciente().getValue()));
            txtNombre.setText(String.valueOf(PacienteActual.getNombre().getValue()));
            txtApellido.setText(String.valueOf(PacienteActual.getApellido().getValue()));
            txtNumeroDoc.setText(String.valueOf(PacienteActual.getNumeroDoc().getValue()));
            cbTipoDoc.setValue(String.valueOf(PacienteActual.getTipoDoc().getValue()));

        } else {
            txtIdPaciente.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtNumeroDoc.setText("");

        }
    }

    @FXML
    private void btnNuevo_click() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumeroDoc.setText("");

        txtIdPaciente.setText(String.valueOf(pacienteData.size() + 1));

        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtNumeroDoc.setEditable(true);
        cbTipoDoc.setDisable(false);

    }

    @FXML
    private void btnAceptar_click() {

        if (Integer.valueOf(txtIdPaciente.getText()) <= pacienteData.size()) {
            PacienteActual.setApellido(txtApellido.getText());
            PacienteActual.setNombre(txtNombre.getText());
            PacienteActual.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
            PacienteActual.setTipoDoc(cbTipoDoc.getValue().toString());

            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtNumeroDoc.setEditable(false);
            cbTipoDoc.setDisable(true);
        } else {
            //  
            Paciente PacienteTemp = new Paciente(Integer.valueOf(txtIdPaciente.getText()), cbTipoDoc.getValue().toString(), Integer.valueOf(txtNumeroDoc.getText()), txtApellido.getText(), txtNombre.getText());

            pacienteData.add(PacienteTemp);
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtNumeroDoc.setEditable(false);
            cbTipoDoc.setDisable(true);
        }

    }

    @FXML
    private void btnEditar_click() {
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtNumeroDoc.setEditable(true);
        //   txtTipoDoc.setEditable(true); 
        cbTipoDoc.setDisable(false);

    }

    @FXML
    private void btnHistorialSEDIRA_click() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("HistorialSEDIRA.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Cálculos SEDIRA");
        stage.show();
    }

    @FXML
    private void btnMediciones_click() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ProgresoPaciente.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Progreso Paciente");
        stage.show();
    }

    @FXML
    private void btnContacto_click() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ContactoPaciente.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Contacto Paciente");
        stage.show();
    }

    @FXML
    private void txtNumeroDoc_KeyPress() throws IOException {

        if (txtNumeroDoc.isEditable()) {
            if (!ValidacionesGenerales.ValidarNumero(txtNumeroDoc.getText())) {

                //  txtNumeroDoc.setText(txtNumeroDoc.getText().substring(0, txtNumeroDoc.getText().length() - 1));
                txtNumeroDoc.setText(ValidacionesGenerales.DejarSoloNumeros(txtNumeroDoc.getText()));
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingreso de datos inválido");
                alert.setHeaderText("Solo se permiten números ");
                alert.setContentText("El caracter ingresado fué borrado");
                alert.showAndWait();
                txtNumeroDoc.positionCaret(txtNumeroDoc.getText().length());
            }
        }

    }

    @FXML
    private void txtNombre_KeyPress() throws IOException {
        if (txtNombre.isEditable()) {
            if (!ValidacionesGenerales.ValidarNombre(txtNombre.getText())) {

                //  txtNombre.setText(txtNombre.getText().substring(0, txtNombre.getText().length() - 1));
                txtNombre.setText(ValidacionesGenerales.DejarSoloLetras(txtNombre.getText()));
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingreso de datos inválido");
                alert.setHeaderText("Solo se permiten letras ");
                alert.setContentText("El caracter ingresado fué borrado");
                alert.showAndWait();
                txtNombre.positionCaret(txtNombre.getText().length());
            }
        }
    }

    @FXML
    private void txtApellido_KeyPress() throws IOException {
        if (txtApellido.isEditable()) {
            if (!ValidacionesGenerales.ValidarNombre(txtApellido.getText())) {

                // txtApellido.setText(txtApellido.getText().substring(0, txtApellido.getText().length() - 1));
                txtApellido.setText(ValidacionesGenerales.DejarSoloLetras(txtApellido.getText()));

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingreso de datos inválido");
                alert.setHeaderText("Solo se permiten letras ");
                alert.setContentText("El caracter ingresado fué borrado");
                alert.showAndWait();

                txtApellido.positionCaret(txtApellido.getText().length());
            }
        }

    }
}
