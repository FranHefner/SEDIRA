/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.model.IUsuarioDAO;
import sedira.model.Usuario;
import sedira.model.UsuarioDAOsql;

/**
 * FXML Controller class Clase controladora del FXML de usuarios.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class UsuarioController implements Initializable {

    //Declaracion de variables FXML
    @FXML
    private TableView<Usuario> griInfoUser;
    @FXML
    private TableColumn<Usuario, Integer> clIdUsuario;
    @FXML
    private TableColumn<Usuario, String> clUser;
    @FXML
    private TableColumn<Usuario, String> clPass;
    @FXML
    private TableColumn<Usuario, String> clDescripcion;

    @FXML
    private Button btnModificarUsuario;
    @FXML
    private Button btnInsertarUsuario;
    @FXML
    private Button btnEliminarUsuario;

    IUsuarioDAO usr = new UsuarioDAOsql();
    //Lista Observable para el manejo de phantoms
    private ObservableList<Usuario> userData = usr.obtenerUsuarios();
    //Auxiliar para setear el stage 
    private Stage primaryStage;
    //Auxiliar usuario para usuario actual . 
    private Usuario usuarioActual = FuncionesGenerales.getUsuarioActual();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnInsertarUsuario.setDisable(false);
        // Inicializo la tabla de Usuarios
        clUser.setCellValueFactory(
                cellData -> cellData.getValue().getLoginProperty());
        clPass.setCellValueFactory(
                cellData -> cellData.getValue().getPassProperty());
        clDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().getDescripcionPropery());
        clIdUsuario.setCellValueFactory(
                cellData -> cellData.getValue().getIdUsuarioProperty().asObject());
        //Limpieza de los detalles del usuario
        mostrarDetalleUsuario(userData, griInfoUser);

        griInfoUser.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionUsuario(newValue));

    }

    /**
     * Método que setea los items en la tabla de usuario
     *
     * @param infoObjeto
     * @param tabla
     */
    private void mostrarDetalleUsuario(ObservableList<Usuario> infoObjeto, TableView<Usuario> tabla) {

        tabla.setItems(infoObjeto);
    }

    /**
     * Metodo que muestra los detalles del phantom seleccionado.
     *
     * @param usuarioActual
     */
    public void seleccionUsuario(Usuario usuarioActual) {
        //Se setea el usuario seleccionado como usuario actual. 

        FuncionesGenerales.setUsuarioActual(usuarioActual);

        if (usuarioActual != null) {

            //Prendo el boton de Editar Usuario
            btnModificarUsuario.setDisable(false);
            //Prendo boton de Eliminar Usuario
            btnEliminarUsuario.setDisable(false);
        } else {
            //Todo si no se selecciona ningun usuario de la lista
            //Apago los botones.
            btnModificarUsuario.setDisable(true);
            btnEliminarUsuario.setDisable(true);
        }
    }

    /**
     * Método que abre el formulario para la edicion/creacion de un usuario.
     *
     * @param usuario
     * @return
     */
    public boolean mostrarUsuarioEditDialog(Usuario usuario) throws Exception {

        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhantomController.class.getResource("AbmUsuario.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Modificar Usuario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // setea el usuario dentro del controlador AbmUsuarioController. .
            AbmUsuarioController controladorAbmUsuario = loader.getController();
            controladorAbmUsuario.setDialogStage(dialogStage);
            controladorAbmUsuario.setUsuario(usuario);
            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

            //Return
            return controladorAbmUsuario.isGuardarDatosClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Metodo para el comportamiento del boton editar. Abre un dialogo para la
     * edicion del Usuario.
     *
     * @throws java.io.IOException
     */
    @FXML
    public void btnEditarUsuario() throws IOException, Exception {
        Usuario usuario = FuncionesGenerales.getUsuarioActual();
        boolean guardarCambiosClicked = mostrarUsuarioEditDialog(usuario);
         
        if (guardarCambiosClicked) {
            //Llamada a la Clase de acceso a datos de Usuario. 
            // Pasa el tipo de usuario seleccionado del ComboBox. 
          
            usr.modificarUsuario(usuario);
            userData = usr.obtenerUsuarios();
            //Actualiza el GridView de los usuarios 
            griInfoUser.setItems(userData);
            //Comportamiento de botones 
            btnModificarUsuario.setDisable(true);
        }

    }

    /**
     * Método para el comportamiento del boton NUEVO.
     *
     * @throws java.lang.Exception
     */
    @FXML
    public void btnCrearUsuario() throws Exception {

        Usuario tempUsuario = new Usuario(-1, "", "", "");

        boolean guardarCambiosClicked = mostrarUsuarioEditDialog(tempUsuario);

        if (guardarCambiosClicked) {
            //Harcodeado el tipo de usuario.
            int tipoUsuario = FuncionesGenerales.getTipoUsuario();
            usr.agregarUsuario(tempUsuario);//.agregarUsuario(tempUsuario, 2);
            //Actualizo el GridView de Phantoms.
            userData = usr.obtenerUsuarios();
            griInfoUser.setItems(userData);

        }
    }

    /**
     * Método para el comportamiento del boton Eliminar.
     * @throws java.lang.Exception
     */
    @FXML
    public void btnEliminar() throws Exception {
        usuarioActual = FuncionesGenerales.getUsuarioActual();
        int idUsuario = usuarioActual.getIdUsuario();
        if (usuarioActual != null) {
            String mensaje = usuarioActual.getDescripcion();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Usuario");
            alert.setHeaderText("Atención!");
            alert.setContentText("Está seguro que desea eliminar el usuario seleccionado? \n" + mensaje);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                usr.eliminarUsuario(idUsuario);

                ///Actualizo el GridView de Usuarios.
                userData = usr.obtenerUsuarios();
                griInfoUser.setItems(userData);
            } else {

            }

        } else {
            // No se selecciono ningun item. 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("No hay ítems para eliminar");

            alert.showAndWait();
        }

    }

}
