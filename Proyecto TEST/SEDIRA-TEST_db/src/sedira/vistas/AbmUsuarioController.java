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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.Security;
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

    IUsuarioDAO usr = new UsuarioDAOsql();
    int tipoUsuario = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> strTipoUsuario = FXCollections.observableArrayList();

        strTipoUsuario.add(0, "Científico");
        strTipoUsuario.add(1, "Médico");
        strTipoUsuario.add(2, "Administrador");

        cBtipoUsuario.setItems(strTipoUsuario);

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        //dialogStage.setResizable(false);
        dialogStage.setMinHeight(330);
        dialogStage.setMinWidth(330);
        dialogStage.setMaxHeight(400);
        dialogStage.setMaxWidth(400);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        if (usuario.getIdUsuario() != -1) {
            /**
             * Obtiente el Usuario seleccionado en Usuario.fxml.
             */
            //Atributos de nombre y id. 
            txtIdUsuario.setText(String.valueOf(usuario.getIdUsuario()));
            txtNombreUsuario.setEditable(true);
            txtNombreUsuario.setText(Security.decrypt(usuario.getLogin()));

            txtPass.setEditable(true);
            txtPass.setText(Security.decrypt(usuario.getPass()));

            txtDescripcion.setEditable(true);
            txtDescripcion.setText(usuario.getDescripcion());

            //Comportamiento de botones. 
        } else {

            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear Usuario");
            //Apago los TextField
            txtNombreUsuario.setEditable(true);
            txtIdUsuario.setDisable(true);

            //Prendo los botones. 
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
                    FuncionesGenerales.setTipoUsuario(cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
                    break;
                case "Modificar Usuario":
                    usuario.setDescripcion(txtDescripcion.getText());
                    usuario.setLogin(txtNombreUsuario.getText());
                    usuario.setPass(txtPass.getText());
                    usuario.setDescripcion(txtDescripcion.getText());
                    FuncionesGenerales.setTipoUsuario(cBtipoUsuario.getSelectionModel().getSelectedIndex() + 1);
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

    /**
     * Metodo que se llama al presionar el boton cancelar.
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
     * metodo para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {
        txtDescripcion.setText("");
        txtPass.setText("");
        txtNombreUsuario.setText("");

    }

    /**
     * Método que se encarga de la validacion de los datos necesarios para la
     * creacion o la modificacion de un usuario.
     *
     * @return True si no existen errores. 0 si se encontraron errores.
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException, Exception {
        // CUIDADO CON LA ENCRIPTACION. 

        String mensajeError = "";
        String nombreUsuario = txtNombreUsuario.getText();

        if ("Crear Usuario".equals(this.dialogStage.getTitle()) || "Modificar Usuario".equals(this.dialogStage.getTitle())) {
            if (!nombreUsuario.equals(Security.decrypt(this.usuario.getLogin()))) { // verifico que si es modo edicion no entre en error por el nombre que no cambiara
                if (txtNombreUsuario.getText() == null || txtNombreUsuario.getText().length() == 0) {
                    mensajeError += "Nombre de usuario inválido!\n";
                }
                if (usr.buscaUsuario(Security.encrypt(nombreUsuario)) == true) {
                    mensajeError += "El nombre de usuario ya existe!\n";
                }
                if (txtPass.getText() == null || txtPass.getText().length() == 0) {
                //mas validaciones con resperto al pass. 
                    // O llamar a funcion que verifique, cantidad de caracteres , largo y demas 
                    mensajeError += "La contraseña es invalida\n";
                }
                if (cBtipoUsuario.getSelectionModel().getSelectedItem() == null) {
                    mensajeError += "Debe seleccionar un tipo de usuario \n";
                }
            }
        }
        // TODO validacion Unidad. 

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
