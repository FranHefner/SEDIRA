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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.Security;
import sedira.ValidacionesGenerales;
import sedira.model.IUsuarioDAO;
import sedira.model.Usuario;
import sedira.model.UsuarioDAOsql;

/**
 * FXML Controller class Clase controladora para AmbUsuario.FXML
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class AbmUsuarioController implements Initializable {
    //Declaracion de variables FXML

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Label lblContraseña;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblTipoUsuario;

    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtPass;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTipoUsuario;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private ChoiceBox cBtipoUsuario;

    //Objeto Phantom auxiliar. 
    private Usuario usuario;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    private boolean cancelar = false;
    IUsuarioDAO usr = new UsuarioDAOsql();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnGuardar.setDisable(false);
        lblNombreUsuario.setText("");
        lblContraseña.setText("");
        lblDescripcion.setText("");
        lblTipoUsuario.setText("");

        ObservableList<String> strTipoUsuario = FXCollections.observableArrayList();
        cBtipoUsuario.getItems().addAll("Científico", "Médico", "Administrador");
/*
        //Validacion al perder el Focus. 
        txtNombreUsuario.focusedProperty().addListener((arg_User, oldValueUser, newValueUser) -> {
            if (!newValueUser) {//when focus lost
                //Pregunto si toco el boton cancelar. 
                if (!btnCancelar.isPressed()) {
                    try {
                        if (validarUsuario()) {
                            //validacion correcta.
                            
                        } else {
                            //validacion usuario erronea. 
                            //txtNombreUsuario.setText("");
                            //txtNombreUsuario.requestFocus();
                            txtNombreUsuario.positionCaret(txtNombreUsuario.getText().length());
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AbmUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });
        txtPass.focusedProperty().addListener((argPass, oldValuePass, newValuePass) -> {
            if (!newValuePass) {//when focus lost
                if (!btnCancelar.isFocused()) {
                    try {
                        if (validarPass()) {
                            //validacion correcta. 
                        } else {
                            //validacion erronea. 
                            txtPass.setText("");
                            txtPass.requestFocus();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AbmUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        });*/

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        dialogStage.setMinHeight(330);
        dialogStage.setMinWidth(330);
        dialogStage.setMaxHeight(400);
        dialogStage.setMaxWidth(400);

    }

    /**
     * Setea el Usuario a editar dentro del Formulario de edicion. Si el Usuario
     * que viene por parametros tiene el id = -1, significa que el se busca
     * crear un nuevo User.
     *
     * @param usuario a editar.
     * @throws java.lang.Exception
     */
    public void setUsuario(Usuario usuario) throws Exception {
        this.usuario = usuario;
      //  String tipoDeUsuario = usr.obtenerTipoUsuario(usuario.getIdUsuario());
       String tipoDeUsuario = usr.descripcionTipobyID(usuario.getTipoUsuario());
        String nombreUsuario = usuario.getLogin();
        String password = usuario.getPass();

        /**
         * Si el id usuario es -1 significa que fue creado un usuario auxiliar.
         * Esto significa que se traba de nuevo usuario.
         */
        if (usuario.getIdUsuario() != -1) {
            /**
             * Obtiente el Usuario seleccionado en Usuario.fxml.
             */

            txtNombreUsuario.setEditable(true);
            txtNombreUsuario.setText(nombreUsuario);
            txtPass.setEditable(true);
            txtPass.setText(password);
            txtDescripcion.setEditable(true);
            txtDescripcion.setText(usuario.getDescripcion());
            cBtipoUsuario.setValue(String.valueOf(tipoDeUsuario));

            //Comportamiento de botones. 
        } else {
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear Usuario");
            //Apago los TextField
            txtNombreUsuario.setEditable(true);
            //txtIdUsuario.setDisable(true);

        }

    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnGuardarDatos() throws SQLException, Exception {
        // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde el controlador de Usuarios, usuarioController. Editar/Nuevo
        if (validarDatosEntrada()) {
            //Validacion preguntando si esta seguro guardar cambios.
            switch (dialogStage.getTitle()) {
                case "Crear Usuario":
                    usuario.setDescripcion(txtDescripcion.getText());
                    usuario.setLogin(txtNombreUsuario.getText());
                    usuario.setPass(txtPass.getText());
                    usuario.setDescripcion(txtDescripcion.getText());
                   // FuncionesGenerales.setTipoUsuario(cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
                    usuario.seTipoUsuario( cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
                    
                    break;
                case "Modificar Usuario":
                    usuario.setDescripcion(txtDescripcion.getText());
                    usuario.setLogin(txtNombreUsuario.getText());
                    usuario.setPass(txtPass.getText());
                    usuario.setDescripcion(txtDescripcion.getText());
                 //   FuncionesGenerales.setTipoUsuario(cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
                     usuario.seTipoUsuario( cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
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

    public boolean isCancelarClicked() {
        return this.cancelar;
    }

    /**
     * Metodo que se llama al presionar el boton cancelar.
     *
     */
    @FXML
    public void btnCancel_click() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        switch (dialogStage.getTitle()) {
            case "Crear Usuario":
                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del usuario ? ");
                break;
            case "Modificar Usuario":

                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del usuario? ");
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
        txtDescripcion.setText("");
        txtPass.setText("");
        txtNombreUsuario.setText("");
        cBtipoUsuario.getSelectionModel().clearSelection();

    }

    /**
     * Método que valida un usuario en caso de utilizar validacion por
     * LostFocus.
     *
     * @return
     * @throws Exception
     */
    public boolean validarUsuario() throws Exception {
        String mensajeError = "";
        String nombreUsuario = txtNombreUsuario.getText();
        String nombreUsuarioEnc = Security.encrypt(nombreUsuario);

        if (!ValidacionesGenerales.ValidarNombreUsuario(nombreUsuario)) {
            mensajeError = "El nombre de usuario debe contener como minimo 5 caracteres. \n"
                    + "Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.";
            txtNombreUsuario.setText("");

        }
        if (usr.buscaUsuario(nombreUsuarioEnc) == true) {
            mensajeError += "El nombre de usuario ya existe!\n";

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
     * Método para validar el password en caso de utilizar valicación por
     * LostFocus.
     *
     * @return
     * @throws Exception
     */
    public boolean validarPass() throws Exception {
        String mensajeError = "";
        String pass = txtNombreUsuario.getText();

        if (!ValidacionesGenerales.ValidarContrasenaUsuario(pass)) {
            mensajeError = "La contraseña debe contener como minimo 5 caracteres. \n"
                    + "Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.";
            txtPass.setText("");

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
     * Método que se encarga de la validacion de los datos necesarios para la
     * creacion o la modificacion de un usuario.
     *
     * @return True si no existen errores. 0 si se encontraron errores.
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException, Exception {

        String mensajeError = "";
        String nombreUsuario = txtNombreUsuario.getText();

        if ("Crear Usuario".equals(this.dialogStage.getTitle())) {

            //  if (!nombreUsuario.equals(nombreUsuarioDec)) { // verifico que si es modo edicion no entre en error por el nombre que no cambiara
            if (!ValidacionesGenerales.ValidarNombreUsuario(nombreUsuario)) {
                mensajeError += "\nEl nombre de usuario debe contener como minimo 5 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.";
            }
            if (usr.buscaUsuario(Security.encrypt(nombreUsuario)) == true) {
                mensajeError += "\nEl nombre de usuario ya existe!\n";
            }

            if (!ValidacionesGenerales.ValidarContrasenaUsuario(nombreUsuario)) {
                mensajeError += "\nLa contraseña debe contener como minimo 5 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.";

            }
            if (cBtipoUsuario.getSelectionModel().getSelectedItem() == null) {
                mensajeError += "\nDebe seleccionar un tipo de usuario \n";
            }
        }
        if ("Modificar Usuario".equals(this.dialogStage.getTitle())) {
            if (!nombreUsuario.equals(usuario.getLogin()) || nombreUsuario.length() < 5) { // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                if (!ValidacionesGenerales.ValidarNombreUsuario(nombreUsuario)) {
                    mensajeError += "\nEl nombre de usuario debe contener como minimo 5 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.\n";
                }
                if (usr.buscaUsuario(Security.encrypt(nombreUsuario)) == true) {
                    mensajeError += "\nEl nombre de usuario ya existe!\n";
                }
            }
            if (!ValidacionesGenerales.ValidarContrasenaUsuario(nombreUsuario)) {
                mensajeError += "\nLa contraseña debe contener como minimo 5 caracteres: Se aceptan letras mayúsculas, minúsculas, números, puntos y guiones.\n";
            }
            if (cBtipoUsuario.getSelectionModel().getSelectedItem() == null) {
                mensajeError += "\nDebe seleccionar un tipo de usuario \n";
            }
        }

        if (mensajeError.length() == 0) {

            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Se han detectado los siguientes errores que impiden realizar la operación: ");
            alert.setContentText(mensajeError);
            alert.showAndWait();
            return false;
        }

    }

}
