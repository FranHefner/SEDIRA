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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.control.DatePicker;
import sedira.model.PacienteDAO;

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
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker txtFechaNacimiento;

    boolean editarClicked = false;

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
    private Button btnAceptar;

    @FXML
    private Button btnCerrar;
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
        // Control de botones. 
    
        ModoLectura();
        btnMediciones.setDisable(true);
        btnHistorialSEDIRA.setDisable(true);
        btnContacto.setDisable(true);

        clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
        clTipoDoc.setCellValueFactory(cellData -> cellData.getValue().getTipoDocProperty());
        clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDocProperty().asObject());

        griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));

        try {
            //Obtengo la lista de pacientes desde la base de datos. 
            //pacienteData = ConsultasDB.ListaPacientes();
            pacienteData = PacienteDAO.obtenerPacientes();
            //actualizo el grid
            griListaPacientes.setItems(pacienteData);
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DocumentosData = ConsultasDB.ListaTipoDocumento();
        cbTipoDoc.setItems(DocumentosData);

    }

    /**
     * Método que se activa al escribir un texto en el campo de busqueda.
     */
    @FXML
    private void btnBuscar_click() {
        griListaPacientes.setItems(FuncionesGenerales.FiltroListaPaciente(griListaPacientes, pacienteData, txtCampoBusqueda));
    }

    private void ModoLectura() {
        btnEditar.setDisable(false);
        btnCancelar.setDisable(true);
        btnContacto.setDisable(true);
        btnAceptar.setDisable(true);
        cbTipoDoc.setDisable(true);
        txtFechaNacimiento.setDisable(true);
        txtFechaNacimiento.setEditable(false);
        btnNuevo.setDisable(false);
        
      if  (FuncionesGenerales.pacienteActual != null) 
        {
            btnMediciones.setDisable(false);
            btnHistorialSEDIRA.setDisable(false);
            btnContacto.setDisable(false);   
        }else          
        {
             btnMediciones.setDisable(true);
            btnHistorialSEDIRA.setDisable(true);
            btnContacto.setDisable(true);   
        }
    }

    private void ModoEdicion() {

        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtNumeroDoc.setEditable(true);
        txtFechaNacimiento.setEditable(true);
        txtFechaNacimiento.setDisable(false);
        cbTipoDoc.setDisable(false);
        btnCancelar.setDisable(false);
        btnAceptar.setDisable(false);
        btnEditar.setDisable(true);

        btnMediciones.setDisable(false);
        btnHistorialSEDIRA.setDisable(false);
        btnContacto.setDisable(false);
        btnNuevo.setDisable(true);

    }

    private void SeleccionPaciente(Paciente pacienteSeleccionado) {

        if (pacienteSeleccionado != null) {
            //Control de botones.               

            FuncionesGenerales.setPacienteActual(pacienteSeleccionado);
            txtIdPaciente.setText(String.valueOf(pacienteSeleccionado.getIdPaciente()));
            txtNombre.setText(String.valueOf(pacienteSeleccionado.getNombre()));
            txtApellido.setText(String.valueOf(pacienteSeleccionado.getApellido()));
            txtNumeroDoc.setText(String.valueOf(pacienteSeleccionado.getNumeroDoc()));
            cbTipoDoc.setValue(String.valueOf(pacienteSeleccionado.getTipoDoc()));
            txtFechaNacimiento.setValue(FuncionesGenerales.DateToLocalDate(pacienteSeleccionado.getFechaNacimientoDATE()));
            System.out.print(pacienteSeleccionado.getIdPaciente());
            System.out.print(pacienteSeleccionado.getApellido());
      
        } else {          
            txtIdPaciente.setText("");        
            txtNombre.setText("");          
            txtApellido.setText("");            
            txtNumeroDoc.setText("");
            
            FuncionesGenerales.pacienteActual = null;
        }
          ModoLectura();
    }

    /**
     * Método para el comportamiento del boton Nuevo.
     */
    @FXML
    private void btnNuevo_click() {
        //Validar si los atributos estan vacios. 
        //prendo boton aceptar y cancelar.     

        txtNombre.setText("");
        txtApellido.setText("");
        txtNumeroDoc.setText("");
        txtFechaNacimiento.setValue(null);
        // Id paciente. 
        txtIdPaciente.setText(String.valueOf(PacienteDAO.getLastId()));
        //Comportamiento de Textfiedls
         ModoEdicion();

    }

    /**
     * Método para el comportamiento del boton Cancelar.
     */
    @FXML
    private void btnCancelar_click() {
        //Abro cuadro de decision
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar edición");
        alert.setHeaderText("Atención!");
        alert.setContentText("Está seguro que desea cancelar la edición? \n"
                + "Los datos ingresados se descartarán!  ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        
            SeleccionPaciente(FuncionesGenerales.pacienteActual);
            ModoLectura();
                //   griListaPacientes.getSelectionModel().select(FuncionesGenerales.pacienteActual);
            // griListaPacientes.getSelectionModel().select( g );

        } else {

        }

    }

    /**
     * Método para el comportamiento del boton Aceptar.
     */
    @FXML
    private void btnAceptar_click() throws SQLException {
        //para editar. 
        Paciente PacienteActual = FuncionesGenerales.getPacienteActual();
        if (editarClicked) {
           
            PacienteActual.setApellido(txtApellido.getText());
            PacienteActual.setNombre(txtNombre.getText());
            PacienteActual.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
            PacienteActual.setTipoDoc(cbTipoDoc.getValue().toString());
            PacienteActual.setFechaNacimiento(txtFechaNacimiento.getValue().toString());
            //Llamada a la clase de acceso de datos de pacientes. PacienteDAO. 
            PacienteDAO.modificarPaciente(PacienteActual);
            //Actualiza la informacion de pacientes
            pacienteData = PacienteDAO.obtenerPacientes();
            //Actualiza la grilla. 
            griListaPacientes.setItems(pacienteData);
            ModoLectura();
            
          //      PacienteActual.setFechaNacimiento(Date.valueOf(txtFechaNacimiento.getValue() ) ); 
            //para crear 
        } else {
            //  Falta validacion para atributos vacios. 
            Paciente PacienteTemp = new Paciente(
                    Integer.valueOf(txtIdPaciente.getText()),
                    cbTipoDoc.getValue().toString(), Integer.valueOf(txtNumeroDoc.getText()),
                    txtApellido.getText(), txtNombre.getText());
            //Llamada a Control de acceso de datos de paciente. PacienteDAO
            //pacienteData.add(PacienteTemp);
            PacienteDAO.agregarPaciente(PacienteTemp);
            pacienteData = PacienteDAO.obtenerPacientes();
            griListaPacientes.setItems(pacienteData);
            
        }

    }

    /**
     * Método para el comportamiento del boton Editar.
     */
    @FXML
    private void btnEditar_click() {
        //Paciente seleccionado. 
        Paciente paciente = FuncionesGenerales.getPacienteActual();
        //Control de boton. 
        editarClicked = true;
    
        // Id paciente. 
        txtIdPaciente.setText(String.valueOf(paciente.getIdPaciente()));
        SeleccionPaciente(paciente);

        ModoEdicion();

    }

    /**
     * Método para el comportamiento del boton Cerrar.
     */
    @FXML
    private void btnCerrar_click() {
      
        cbTipoDoc.setDisable(false);
        txtFechaNacimiento.setDisable(false);

        Stage stage = (Stage) btnCerrar.getScene().getWindow();

        stage.close();
    }

    /**
     * Método para el comportamiento del boton Historial.
     *
     * @throws IOException
     */
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
