/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import sedira.model.CalculoDAOsql;
import sedira.model.CalculoMuestra;
import sedira.model.ICalculoDAO;
import sedira.model.IPacienteDAO;
import sedira.model.PacienteDAOsql;

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
    //@FXML
    // private TextField txtIdPaciente;
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
    private ComboBox cbSexo;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker txtFechaNacimiento;
    @FXML
    private Label lblModo;
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

    boolean editarClicked = false;
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IPacienteDAO pac = new PacienteDAOsql();
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
    private ObservableList<String> DocumentosData = FXCollections.observableArrayList();
    

    public ObservableList<Paciente> getPacienteData() {
        return pacienteData;
    }
    
    boolean IgnorarValidacion = false;
    @FXML
    public void IgnorarValidacion() {

        System.out.println("IgnoraValidacion");
         IgnorarValidacion = true;
    }
    @FXML
    public void RetornarValidacion() {

       System.out.println("RetornaValidacion");
        IgnorarValidacion = false;
    }

    int UltimoFoco = 0;



    private int IdPacienteActual;
    private Paciente pacienteActual;
    // Paciente PacienteActual = new Paciente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Control de botones. 
        
          
           
        
        
        FuncionesGenerales.pacienteActual = null;
        btnEditar.setDisable(true);
        try {
            //Obtengo la lista de pacientes desde la base de datos. 

            pacienteData = pac.obtenerPacientes();
            //actualizo el grid
            griListaPacientes.setItems(pacienteData);
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ModoLectura();
        cbTipoDoc.getItems().addAll("DNI", "PAS");
        cbSexo.getItems().addAll("F", "M", "O");

        clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
        clTipoDoc.setCellValueFactory(cellData -> cellData.getValue().getTipoDocProperty());
        clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDocProperty().asObject());

        griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));

        txtNumeroDoc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtNumeroDoc.isEditable()) {
                    if (!ValidacionesGenerales.ValidarNumero(txtNumeroDoc.getText())) {

                        //  txtNumeroDoc.setText(txtNumeroDoc.getText().substring(0, txtNumeroDoc.getText().length() - 1));
                        txtNumeroDoc.setText(ValidacionesGenerales.DejarSoloNumeros(txtNumeroDoc.getText()));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ingreso de datos inválido");
                        alert.setHeaderText("Solo se permiten números ");
                        alert.setContentText("El carácter ingresado fué borrado");
                        alert.showAndWait();
                        txtNumeroDoc.positionCaret(txtNumeroDoc.getText().length());
                    }

                }
            }
        });

        // TextField txtNumeroDoc = new TextField();
        txtNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (txtNombre.isEditable()) {
                    if (!ValidacionesGenerales.ValidarNombre(txtNombre.getText())) {

                        //  txtNombre.setText(txtNombre.getText().substring(0, txtNombre.getText().length() - 1));
                        txtNombre.setText(ValidacionesGenerales.SacarNumeros(txtNombre.getText()));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ingreso de datos inválido");
                        alert.setHeaderText("Solo se permiten letras ");
                        alert.setContentText("El carácter ingresado fué borrado");
                        alert.showAndWait();
                        txtNombre.positionCaret(txtNombre.getText().length());
                    }
                }

            }
        });
        txtApellido.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (txtApellido.isEditable()) {
                    if (!ValidacionesGenerales.ValidarNombre(txtApellido.getText())) {

                        // txtApellido.setText(txtApellido.getText().substring(0, txtApellido.getText().length() - 1));
                        txtApellido.setText(ValidacionesGenerales.SacarNumeros(txtApellido.getText()));

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ingreso de datos inválido");
                        alert.setHeaderText("No se permiten números ");
                        alert.setContentText("El carácter ingresado fué borrado");
                        alert.showAndWait();

                        txtApellido.positionCaret(txtApellido.getText().length());
                    }
                }
            }
        });

        /**
         * ********************* Validaciones lost focus***************************************************
         */
        txtNumeroDoc.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                   UltimoFoco =1;
                     
                if (!newPropertyValue && txtNumeroDoc.isEditable() && txtNumeroDoc.getText().length() > 0 && IgnorarValidacion == false ) {

                     System.out.println("entro a validacion");
                    boolean ValidacionOK = true;
                    String Error = "";

                    /**
                     * *************************
                     */
                    if (txtNumeroDoc.getText().length() > 9) {
                        Error += "\n El número de documento ingresado excede la cantidad de dígitos establecida.";
                        ValidacionOK = false;
                    }
                    if (txtNumeroDoc.getText().length() < 6) {
                        Error += "\n El número de documento ingresado es menor a la cantidad mínima establecida.";
                        ValidacionOK = false;
                    }

                    /**
                     * **************************
                     */
                    if (ValidacionOK == false) {
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Validación");
                        alert.setHeaderText("El dato ingreso es incorrecto, por favor ingrese uno válido");
                        alert.setContentText(Error);
                        alert.showAndWait();
                        txtNumeroDoc.requestFocus();
                    }
                }
            }
        });
            
        txtNombre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                   UltimoFoco =2;
                if (!newPropertyValue && txtNombre.isEditable() && txtNombre.getText().length() > 0  && IgnorarValidacion == false) {
                    boolean ValidacionOK = true;
                    String Error = "";

                    /**
                     * *************************
                     */
                    char variableAnterior = '0';
                    int contadorCharRepetido = 0;
                    boolean nombreIncorrecto = false;
                    for (int x = 0; x < txtNombre.getText().length(); x++) {
                        if (txtNombre.getText().charAt(x) == variableAnterior) {
                            contadorCharRepetido++;
                        } else {
                            variableAnterior = txtNombre.getText().charAt(x);
                            contadorCharRepetido = 0;
                        }
                        if (contadorCharRepetido > 2) {
                            ValidacionOK = false;
                            nombreIncorrecto = true;
                        }
                    }
                    if (nombreIncorrecto = true) {
                        Error += "\n El nombre ingresado es incorrecto. Se repiten caracteres.";
                    }

                    /**
                     * **************************
                     */
                    if (ValidacionOK == false) {
                    
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Validación");
                        alert.setHeaderText("El dato ingreso es incorrecto, por favor ingrese uno válido");
                        alert.setContentText(Error);
                        alert.showAndWait();
                        txtNombre.requestFocus();
                    }
                }
            }
        });

        txtApellido.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                
                   UltimoFoco =3;
                if (!newPropertyValue  && txtApellido.isEditable() && txtApellido.getText().length() > 0 && IgnorarValidacion == false) {
                    boolean ValidacionOK = true;
                    String Error = "";

                    /**
                     * *************************
                     */
                    char variableAnterior = '0';
                    int contadorCharRepetido = 0;
                    boolean apellidoIncorrecto = false;
                    for (int x = 0; x < txtApellido.getText().length(); x++) {
                        if (txtApellido.getText().charAt(x) == variableAnterior) {
                            contadorCharRepetido++;
                        } else {
                            variableAnterior = txtApellido.getText().charAt(x);
                            contadorCharRepetido = 0;
                        }
                        if (contadorCharRepetido > 2) {
                            apellidoIncorrecto = true;
                            ValidacionOK = false;
                        }
                    }

                    if (apellidoIncorrecto = true) {
                        Error += "\n El apellido ingresado es incorrecto. Se repiten caracteres.";
                    }
                    /**
                     * **************************
                     */

                    if (ValidacionOK == false) {
                     
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Validación");
                        alert.setHeaderText("El dato ingreso es incorrecto, por favor ingrese uno válido");
                        alert.setContentText(Error);
                        alert.showAndWait();
                        txtApellido.requestFocus();
                    }
                }
            }
        });

        txtFechaNacimiento.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                   UltimoFoco =4;
                if (!newPropertyValue && txtFechaNacimiento.isEditable()&& txtFechaNacimiento.getValue() != null && IgnorarValidacion == false) {
                    Date fechaActual = new Date();
                    Date fechaIngresada = new Date();

                    boolean ValidacionOK = true;
                    String Error = "";

                    /**
                     * *************************
                     */
                    if (txtFechaNacimiento.getValue() == null) {
                        Error += "\n Se debe seleccionar una fecha de nacimiento válida.";
                        ValidacionOK = false;
                    } else {
                        fechaIngresada = Date.from(txtFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }
                    if (fechaActual.before(fechaIngresada)) {
                        Error += "\n La fecha de nacimiento debe ser menor o igual a la fecha actual.";
                        ValidacionOK = false;
                    }

                    /**
                     * **************************
                     */
                    if (ValidacionOK == false) {                  
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Validación");
                        alert.setHeaderText("El dato ingreso es incorrecto, por favor ingrese uno válido");
                        alert.setContentText(Error);
                        alert.showAndWait();
                        txtFechaNacimiento.requestFocus();
                    }
                }
            }
        });
        /**
         * *************************************************************************
         */
    }

    /**
     * Método que se activa al escribir un texto en el campo de busqueda.
     */
    private boolean validarCampos() {
        boolean ValidacionOK = true;
        String Error = "";

        Date fechaActual = new Date();
        Date fechaIngresada = new Date();

        if (cbTipoDoc.getSelectionModel().getSelectedIndex() == -1) {
            Error += "\n Se debe seleccionar el tipo de documento.";
            ValidacionOK = false;

        }      
      /*  if (txtNumeroDoc.getText().length() > 9) {
            Error += "\n El número de documento ingresado excede la cantidad de dígitos establecida.";
            ValidacionOK = false;
        }
        if (txtNumeroDoc.getText().length() < 6) {
            Error += "\n El número de documento ingresado es menor a la cantidad mínima establecida.";
            ValidacionOK = false;
        }*/
        if (txtNumeroDoc.getText().length() == 0) {
            Error += "\n No se ingresó el número de documento";
            ValidacionOK = false;
        }
        if (txtNombre.getText().length() == 0) {
            Error += "\n No se ingresó el nombre del paciente";
            ValidacionOK = false;
        }
/*
        char variableAnterior = '0';
        int contadorCharRepetido = 0;
        boolean nombreIncorrecto = false;
        for (int x = 0; x < txtNombre.getText().length(); x++) {
            if (txtNombre.getText().charAt(x) == variableAnterior) {
                contadorCharRepetido++;
            } else {
                variableAnterior = txtNombre.getText().charAt(x);
                contadorCharRepetido = 0;
            }
            if (contadorCharRepetido > 2) {
                ValidacionOK = false;
                nombreIncorrecto = true;
            }
        }
        if (nombreIncorrecto = true) {
            Error += "\n El nombre ingresado es incorrecto. Se repiten caracteres.";
        }*/
        if (txtApellido.getText().length() == 0) {
            Error += "\n No se ingresó el apellido del paciente";
            ValidacionOK = false;
        }

      /*  variableAnterior = '0';
        contadorCharRepetido = 0;
        boolean apellidoIncorrecto = false;
        for (int x = 0; x < txtApellido.getText().length(); x++) {
            if (txtApellido.getText().charAt(x) == variableAnterior) {
                contadorCharRepetido++;
            } else {
                variableAnterior = txtApellido.getText().charAt(x);
                contadorCharRepetido = 0;
            }
            if (contadorCharRepetido > 2) {
                apellidoIncorrecto = true;
                ValidacionOK = false;
            }
        }

        if (apellidoIncorrecto = true) {
            Error += "\n El apellido ingresado es incorrecto. Se repiten caracteres.";
        }*/
         if (txtFechaNacimiento.getValue() == null) {
            Error += "\n Se debe seleccionar una fecha de nacimiento válida.";
            ValidacionOK = false;
        } else {
            fechaIngresada = Date.from(txtFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
      /*  if (fechaActual.before(fechaIngresada)) {
            Error += "\n La fecha de nacimiento debe ser menor o igual a la fecha actual.";
            ValidacionOK = false;
        }*/
        if (cbSexo.getSelectionModel().getSelectedIndex() == -1) {
            Error += "\n No se selecciono el sexo del paciente.";
            ValidacionOK = false;
        }
        if (ValidacionOK == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validación");
            alert.setHeaderText("Se han detectado los siguientes errores que impiden realizar la operación: ");
            alert.setContentText(Error);
            alert.showAndWait();
        }
        return ValidacionOK;

    }

    @FXML
    private void btnBuscar_click() {
        griListaPacientes.setItems(FuncionesGenerales.FiltroListaPaciente(griListaPacientes, pacienteData, txtCampoBusqueda));
    }

    private void ModoLectura() {

        griListaPacientes.setFocusTraversable(true);
        griListaPacientes.setDisable(false);

        btnCancelar.setDisable(true);
        btnContacto.setDisable(true);
        btnAceptar.setDisable(true);
        btnNuevo.setDisable(false);

        cbTipoDoc.setDisable(true);
        cbSexo.setDisable(true);
        txtFechaNacimiento.setDisable(true);

        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtNumeroDoc.setEditable(false);
        txtFechaNacimiento.setEditable(false);
        griListaPacientes.setDisable(false);

         btnCerrar.setDisable(false);
         
        if (FuncionesGenerales.pacienteActual != null) {
            btnMediciones.setDisable(false);
            btnHistorialSEDIRA.setDisable(false);
            btnContacto.setDisable(false);
            btnEditar.setDisable(false);
        } else {
            btnEditar.setDisable(true);
            btnMediciones.setDisable(true);
            btnHistorialSEDIRA.setDisable(true);
            btnContacto.setDisable(true);
        }
    }

    private void ModoEdicion() {
        lblModo.setText("Modo edición");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cbTipoDoc.requestFocus();
            }
        });

        cbTipoDoc.requestFocus();
        griListaPacientes.setFocusTraversable(false);
        griListaPacientes.setDisable(true);

        txtCampoBusqueda.setDisable(true);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtNumeroDoc.setEditable(true);
        txtFechaNacimiento.setEditable(true);
        txtFechaNacimiento.setDisable(false);
        cbTipoDoc.setDisable(false);
        cbSexo.setDisable(false);
        btnCancelar.setDisable(false);
        btnAceptar.setDisable(false);
        btnEditar.setDisable(true);
        btnContacto.setDisable(true);

        //btnMediciones.setDisable(true);
        btnHistorialSEDIRA.setDisable(true);
        btnNuevo.setDisable(true);
        
        btnCerrar.setDisable(true);

    }

    private void SeleccionPaciente(Paciente pacienteSeleccionado) {
        this.pacienteActual = pacienteSeleccionado;
        if (pacienteActual != null) {
            //Control de botones.  

            FuncionesGenerales.setPacienteActual(pacienteActual);

            IdPacienteActual = pacienteActual.getIdPaciente();
            txtNombre.setText(String.valueOf(pacienteActual.getNombre()));
            txtApellido.setText(String.valueOf(pacienteActual.getApellido()));
            txtNumeroDoc.setText(String.valueOf(pacienteActual.getNumeroDoc()));
            cbTipoDoc.setValue(String.valueOf(pacienteActual.getTipoDoc()));
            cbSexo.setValue(String.valueOf(pacienteActual.getSexo()));
            txtFechaNacimiento.setValue(FuncionesGenerales.DateToLocalDate(pacienteActual.getFechaNacimientoDATE()));

            //System.out.print(pacienteSeleccionado.getIdPaciente());
            //System.out.print(pacienteSeleccionado.getApellido());
            FuncionesGenerales.pacienteActual.setEsNuevo(false);
            ModoLectura();

        } else {
            IdPacienteActual = -1;
            txtNombre.setText("");
            //txtNombre.setDisable(true);
            txtApellido.setText("");
            //txtApellido.setDisable(true);
            txtNumeroDoc.setText("");
            //txtNumeroDoc.setDisable(true);
            //txtFechaNacimiento.setDisable(true);
            txtFechaNacimiento.setValue(null);
            cbTipoDoc.setValue(null);
            //cbTipoDoc.setDisable(true);
            cbSexo.setValue(null);
            //cbSexo.setDisable(true);

            FuncionesGenerales.pacienteActual = pacienteActual;
            //  ModoLectura();
        }

    }

    /**
     * Método para el comportamiento del boton Nuevo.
     */
    @FXML
    private void btnNuevo_click() {

        //Validar si los atributos estan vacios. 
        //prendo boton aceptar y cancelar.     
        editarClicked = false;
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumeroDoc.setText("");
        txtFechaNacimiento.setValue(null);
        IdPacienteActual = pac.getLastId();
        cbTipoDoc.setValue(null);
        cbSexo.setValue(null);
        btnContacto.setDisable(false);

        // txtIdPaciente.setText(String.valueOf(pac.getLastId()));
        //  FuncionesGenerales.pacienteActual.setEsNuevo(true);
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
                + "Los cambios sin guardar se decartarán ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
              IgnorarValidacion = true;
            btnNuevo.setDisable(false);

            txtCampoBusqueda.setDisable(false);
            //Cuando no existen pacientes- Primer uso. 
            if (FuncionesGenerales.getPacienteActual() != null) {
                if (FuncionesGenerales.pacienteActual.getIdPaciente() == 0) {

                    SeleccionPaciente(null);
                } else {
                    SeleccionPaciente(FuncionesGenerales.pacienteActual);
                }

            } else {
                txtNombre.setText("");
                txtApellido.setText("");
                txtNumeroDoc.setText("");

                txtFechaNacimiento.setValue(null);
                cbTipoDoc.setValue(null);
                cbSexo.setValue(null);
            }
            ModoLectura();
        }else
        { 
              if (   UltimoFoco == 1){ txtNumeroDoc.requestFocus();}
              if (   UltimoFoco == 2){ txtNombre.requestFocus();}
              if (   UltimoFoco == 3){ txtApellido.requestFocus();}
              if (   UltimoFoco == 4){ txtFechaNacimiento.requestFocus();}  
        }

    }

    /**
     * Método para el comportamiento del boton Aceptar.
     */
    @FXML
    private void btnAceptar_click() throws SQLException {
        //para editar. 

        if (validarCampos()) {
            Paciente PacienteActual = pacienteActual;
            if (editarClicked) {

                PacienteActual.setIdPaciente(IdPacienteActual);
                PacienteActual.setApellido(txtApellido.getText());
                PacienteActual.setNombre(txtNombre.getText());
                PacienteActual.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
                PacienteActual.setTipoDoc(cbTipoDoc.getValue().toString());
                PacienteActual.setSexo(cbSexo.getValue().toString());
                PacienteActual.setFechaNacimiento(txtFechaNacimiento.getValue().toString());
                //Llamada a la clase de acceso de datos de pacientes. PacienteDAO. 

                if (pac.modificarPaciente(PacienteActual)) {
                    //Actualiza la informacion de pacientes
                    pacienteData = pac.obtenerPacientes();
                    //Actualiza la grilla. 
                    griListaPacientes.setItems(pacienteData);

                    // Se carga los datos nuevamente  
                    SeleccionPaciente(PacienteActual);
                    //     ModoLectura();
                } else {
                    // Ocurrio un error al actualizar
                }

            } else {
                // Nuevo Pacientes  

                Paciente PacienteTemp = new Paciente();
                PacienteTemp.setIdPaciente(IdPacienteActual);
                PacienteTemp.setTipoDoc(cbTipoDoc.getValue().toString());
                PacienteTemp.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
                PacienteTemp.setApellido(txtApellido.getText());
                PacienteTemp.setNombre(txtNombre.getText());
                PacienteTemp.setFechaNacimiento(Date.from(txtFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                PacienteTemp.setDireccion("");
                PacienteTemp.setTelefono("");
                PacienteTemp.setEmail("");
                PacienteTemp.setcelular("");
                PacienteTemp.setSexo(cbSexo.getValue().toString());
                PacienteTemp.setEsNuevo(true);

                //Llamada a Control de acceso de datos de paciente. PacienteDAO
                //pacienteData.add(PacienteTemp);
                if (pac.agregarPaciente(PacienteTemp)) {
                    pacienteData = pac.obtenerPacientes();
                    griListaPacientes.setItems(pacienteData);

                    txtCampoBusqueda.setDisable(false);
                    SeleccionPaciente(PacienteTemp);
                } else {
                    //Ocurrio un error al guardar
                }

            }
        }

    }

    /**
     * Método para el comportamiento del boton Editar.
     */
    @FXML
    private void btnEditar_click() {
        //Paciente seleccionado. 
        Paciente paciente = this.pacienteActual;
        //Control de boton. 
        editarClicked = true;

        // Id paciente. 
        IdPacienteActual = paciente.getIdPaciente();
        //  txtIdPaciente.setText(String.valueOf(paciente.getIdPaciente()));
        //SeleccionPaciente(paciente);

        ModoEdicion();

    }

    /**
     * Método para el comportamiento del boton Cerrar.
     */
    @FXML
    private void btnCerrar_click() {
       
        if (btnCancelar.isDisable() ==false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Ventana");
            alert.setHeaderText("Atención!");
            alert.setContentText("Está seguro que desea cerrar? \n"
                    + "Los cambios sin guardar se decartarán ");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) btnCerrar.getScene().getWindow();
                stage.close();
            } else {            
                    if (   UltimoFoco == 1){ txtNumeroDoc.requestFocus();}
                    if (   UltimoFoco == 2){ txtNombre.requestFocus();}
                    if (   UltimoFoco == 3){ txtApellido.requestFocus();}
                    if (   UltimoFoco == 4){ txtFechaNacimiento.requestFocus();}               
            }
        } else {

            Stage stage = (Stage) btnCerrar.getScene().getWindow();

            stage.close();
           
             
        }

    }

    /**
     * Método para el comportamiento del boton Historial.
     *
     * @throws IOException
     */
    @FXML
    private void btnHistorialSEDIRA_click() throws IOException {

        ObservableList<CalculoMuestra> calculoData = FXCollections.observableArrayList();
        ICalculoDAO cal = new CalculoDAOsql();
        calculoData = cal.getCalculoPaciente(IdPacienteActual);
        if (calculoData.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cálculos realizados = 0");
            alert.setHeaderText("Paciente - Cálculos");
            alert.setContentText("El paciente seleccionado no posee cálculos asignados");
            alert.showAndWait();
        } else {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("HistorialSEDIRA.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cálculos SEDIRA");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        }
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
        stage.initModality(Modality.APPLICATION_MODAL);

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
                alert.setContentText("El carácter ingresado fué borrado");
                alert.showAndWait();
                txtNumeroDoc.positionCaret(txtNumeroDoc.getText().length());
            }

        }

    }

    @FXML
    private void txtNombre_KeyPress() throws IOException {
        if (txtNombre.isEditable()) {
            if (!ValidacionesGenerales.ValidarNombreConEspacios(txtNombre.getText())) {
                txtNombre.setText(ValidacionesGenerales.DejarSoloLetras(txtNombre.getText()));
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingreso de datos inválido");
                alert.setHeaderText("Solo se permiten letras ");
                alert.setContentText("El carácter ingresado fué borrado");
                alert.showAndWait();
                txtNombre.positionCaret(txtNombre.getText().length());
            }
        }
    }

    @FXML
    private void txtApellido_KeyPress() throws IOException {
        if (txtApellido.isEditable()) {
            if (!ValidacionesGenerales.ValidarNombreConEspacios(txtApellido.getText())) {

                // txtApellido.setText(txtApellido.getText().substring(0, txtApellido.getText().length() - 1));
                txtApellido.setText(ValidacionesGenerales.DejarSoloLetras(txtApellido.getText()));

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingreso de datos inválido");
                alert.setHeaderText("Solo se permiten letras ");
                alert.setContentText("El carácter ingresado fué borrado");
                alert.showAndWait();

                txtApellido.positionCaret(txtApellido.getText().length());
            }
        }

    }
}
