/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sedira.model.Usuario;
import sedira.model.UsuarioDAO;

/**
 * FXML Controller class
 * Clase controladora del FXML de usuarios. 
 * @author Hefner Francisco, Quelin Pablo   
 */
public class UsuarioController implements Initializable {
    
    //Declaracion de variables FXML
    @FXML
    private TableView<Usuario> griInfoUser;
    @FXML
    private TableColumn<Usuario, Integer > clIdUsuario;
    @FXML
    private TableColumn<Usuario, String > clUser;
    @FXML
    private TableColumn<Usuario, String > clPass;
    @FXML
    private TableColumn<Usuario, String > clDescripcion;
    
    @FXML
    private Button btnModificarUsuario;
    @FXML
    private Button btnInsertarUsuario;
    @FXML
    private Button btnEliminarUsuario;
    
    
     //Lista Observable para el manejo de phantoms
    private ObservableList <Usuario> userData = UsuarioDAO.obtenerUsuarios();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Inicializo la tabla de Usuarios
        clUser.setCellValueFactory(
                cellData -> cellData.getValue().getLoginProperty());
        clPass.setCellValueFactory(
                cellData -> cellData.getValue().getPassProperty());
        clDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().getPassProperty());
        clIdUsuario.setCellValueFactory(
                cellData -> cellData.getValue().getIdUsuarioProperty().asObject());
        //Limpieza de los detalles del usuario
        mostrarDetalleUsuario(userData, griInfoUser);
        
        griInfoUser.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionUsuario(newValue));     
        
  
                    
        
    }
    
    /**
     * Método que setea los items en la tabla de usuario
     * @param infoObjeto
     * @param tabla 
     */
    private void mostrarDetalleUsuario(ObservableList<Usuario> infoObjeto, TableView<Usuario> tabla) {
        tabla.setItems(infoObjeto);
    }
    
    /**
     * Metodo que muestra los detalles del phantom seleccionado. 
     * @param usuarioActual 
     */
    public void seleccionUsuario(Usuario usuarioActual) {
        //Se setea el usuario seleccionado como usuario actual. 
        
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
    
}
