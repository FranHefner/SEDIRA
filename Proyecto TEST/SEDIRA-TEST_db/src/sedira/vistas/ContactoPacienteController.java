/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IPacienteDAO;
import sedira.model.Paciente;
import sedira.model.PacienteDAOsql;

/**
 * FXML Controller class Clase que controla la interfaz gráfica de Contacto para
 * pacientes.
 *
 * @author Francisco Hefner . Quelin Pablo.
 */
public class ContactoPacienteController implements Initializable {

    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCerrar;

    String DireccionAux;
    String TelefonoAux;
    String CelularAux;
    String EmailAux;

      private IPacienteDAO pac = new PacienteDAOsql(); 
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Paciente PacienteActual = FuncionesGenerales.getPacienteActual();

        if (PacienteActual.getEsNuevo() == false) {
            txtDireccion.setText(PacienteActual.getDireccion());
            txtTelefono.setText(PacienteActual.getTelefono());
            txtCelular.setText(PacienteActual.getcelular());
            txtEmail.setText(PacienteActual.getEmail());

            DireccionAux = PacienteActual.getDireccion();
            TelefonoAux = PacienteActual.getTelefono();
            CelularAux = PacienteActual.getcelular();
            EmailAux = PacienteActual.getEmail();
        }

        ModoLectura();

                txtCelular.textProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (txtCelular.isEditable()) {
                   if (!ValidacionesGenerales.ValidarNumero(txtCelular.getText())) {

                       //  txtNumeroDoc.setText(txtNumeroDoc.getText().substring(0, txtNumeroDoc.getText().length() - 1));
                       txtCelular.setText(ValidacionesGenerales.DejarSoloNumeros(txtCelular.getText()));
                       Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Ingreso de datos inválido");
                       alert.setHeaderText("Solo se permiten números ");
                       alert.setContentText("El caracter ingresado fué borrado");
                       alert.showAndWait();
                       txtCelular.positionCaret(txtCelular.getText().length());
                   }

               }
            }
        });
                
                txtTelefono.textProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (txtTelefono.isEditable()) {
                   if (!ValidacionesGenerales.ValidarNumero(txtTelefono.getText())) {

                       //  txtNumeroDoc.setText(txtNumeroDoc.getText().substring(0, txtNumeroDoc.getText().length() - 1));
                       txtTelefono.setText(ValidacionesGenerales.DejarSoloNumeros(txtTelefono.getText()));
                       Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Ingreso de datos inválido");
                       alert.setHeaderText("Solo se permiten números ");
                       alert.setContentText("El caracter ingresado fué borrado");
                       alert.showAndWait();
                       txtTelefono.positionCaret(txtTelefono.getText().length());
                   }

               }
            }
        });
       
    }

    private void ModoLectura() {

        txtDireccion.setDisable(false);
        txtTelefono.setDisable(false);
        txtCelular.setDisable(false);
        txtEmail.setDisable(false);

        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtCelular.setEditable(false);
        txtEmail.setEditable(false);
        
        btnAceptar.setDisable(true);
        btnEditar.setDisable(false);
    }

    private void ModoEdicion() {
         Platform.runLater(new Runnable() {
        @Override
            public void run() {
                txtDireccion.requestFocus();
            }
        });

        txtDireccion.setDisable(false);
        txtTelefono.setDisable(false);
        txtCelular.setDisable(false);
        txtEmail.setDisable(false);

        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtCelular.setEditable(true);
        txtEmail.setEditable(true);

        btnAceptar.setDisable(false);
        btnEditar.setDisable(true);

    }

    /**
     * Método para el comportamiento del boton Aceptar.
     */
    @FXML
    private void btnAceptar_click() throws SQLException {
        //para editar. 
        Paciente PacienteActual = FuncionesGenerales.getPacienteActual();
        if (btnEditar.isDisable()) {

            PacienteActual.setDireccion(txtDireccion.getText());
            PacienteActual.setTelefono(txtTelefono.getText());
            PacienteActual.setEmail(txtEmail.getText());
            PacienteActual.setcelular(txtCelular.getText());

            FuncionesGenerales.setPacienteActual(PacienteActual);
                
            if (PacienteActual.getEsNuevo() == false)
            {
              pac.modificarPaciente(PacienteActual);
            }
          
            ModoLectura();

        }

    }

    /**
     * Método para el comportamiento del boton Editar.
     */
    @FXML
    private void btnEditar_click() {

        ModoEdicion();

    }

    /**
     * Método para el comportamiento del boton Cerrar.
     */
    @FXML
    private void btnCerrar_click() {

        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();

    }

}
