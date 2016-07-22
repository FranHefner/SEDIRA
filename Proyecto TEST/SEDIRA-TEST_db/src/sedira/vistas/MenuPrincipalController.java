/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

/**
 * FXML Menu principal Controller class Controlador para el comportamiento del
 * menu principal
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    Menu menuDosis;
    @FXML
    Menu menuPacientes;
    @FXML
    Menu menuPhantoms;
    @FXML
    Menu menuRadionuclidos;
    @FXML
    Menu menuUsuarios;
    @FXML
    Menu menuInicio;
    @FXML
    Menu menuAyuda;

    /**
     * Initializes the controller class.
     */
    // Variable para definir el comportamiento del menu segun el tipo de usuario.
    public static String TipoUsuario = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdministrarMenu(TipoUsuario);
    }
    
    /**
     * Declaración del menu
     */
    @FXML
    private MenuBar mnbPrincipal;

    /**
     * Funcion que habilita o deshabilita el menu principal
     */
    private void HabilitacionMenu(boolean Habilitado) {
        mnbPrincipal.setDisable(!Habilitado);

    }

    /**
     * Comportamiento para Item de menu, administrar paciente.
     *
     * @throws IOException
     */
    @FXML
    private void mniPacienteAdministrar_click() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Paciente.fxml"));
        Scene scene = new Scene(root);
        stage.setMaxWidth(700);
        stage.setMaxHeight(639);
        stage.setMinWidth(700);
        stage.setMinHeight(639);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Administrar Pacientes");
        stage.show();

    }

    /**
     * Comportamiento para el Item de menu, Iniciar Calculo.
     *
     * @throws IOException
     */
    @FXML
    private void mniIniciarCalculo_click() throws IOException {
        if ("Cientifico".equals(TipoUsuario) ||"Admin".equals(TipoUsuario)  ) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Calculo.fxml"));
            Scene scene = new Scene(root);
            /*
             stage.setMaxWidth(682);        
             stage.setMaxHeight(671);
             stage.setMinWidth(682);        
             stage.setMinHeight(671);*/
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Calcular Dosis Administrada");
            stage.initModality(Modality.APPLICATION_MODAL);
            //  stage.setAlwaysOnTop(true);
            stage.show();
        }
        if ("Medico" == TipoUsuario) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("CalculoBasico.fxml"));
            Scene scene = new Scene(root);
            /*
             stage.setMaxWidth(682);        
             stage.setMaxHeight(671);
             stage.setMinWidth(682);        
             stage.setMinHeight(671);*/
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Calcular Dosis Administrada");
            stage.initModality(Modality.APPLICATION_MODAL);
            //  stage.setAlwaysOnTop(true);
            stage.show();
        }

    }

    /**
     * Comportamiento para el Item de menu, Administrar Phantoms
     *
     * @throws IOException
     */
    @FXML
    private void mniPhantomAdministrar_click() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Phantom.fxml"));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Administrar Phantoms");
        stage.show();

    }

    /**
     * Comportamiento para el Item de menu, Administrar Radionuclidos
     *
     * @throws IOException
     */
    @FXML
    private void mniRadionuclidosAdministrar_click() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Radionuclido.fxml"));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Administrar Radionuclidos");
        stage.show();

    }

    /**
     * Comportamiento para el Item de menu, Administrar Usuarios
     *
     * @throws IOException
     */
    @FXML
    private void mniUsuarioAdministrar_click() throws IOException {
        if ("Administrador".equals(TipoUsuario)) {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Usuario.fxml"));
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Administrar Usuarios");
            stage.show();
        }
        //TODO SUPERUSER
    }

    private void AdministrarMenu(String TipoUsuario) {
        if (TipoUsuario.equals("Cientifico")) {
            menuInicio.setDisable(false);
            menuDosis.setDisable(false);
            menuPacientes.setDisable(false);
            menuPhantoms.setDisable(false);
            menuRadionuclidos.setDisable(false);
            menuAyuda.setDisable(false);
            menuUsuarios.setDisable(true);
        }
        if (TipoUsuario.equals("Medico")) {
            menuInicio.setDisable(false);
            menuDosis.setDisable(false);
            menuPacientes.setDisable(true);
            menuPhantoms.setDisable(true);
            menuRadionuclidos.setDisable(true);
            menuAyuda.setDisable(true);
            menuUsuarios.setDisable(true);

        }
        if (TipoUsuario.equals("Administrador")) {
            //Controla todo + los usuarios
            menuInicio.setDisable(false);
            menuDosis.setDisable(false);
            menuPacientes.setDisable(false);
            menuPhantoms.setDisable(false);
            menuRadionuclidos.setDisable(false);
            menuAyuda.setDisable(false);
            menuUsuarios.setDisable(false);

        }
    }
    @FXML
    private void ayudaClick () throws IOException{
        Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ayuda.fxml"));
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Ayuda");
            stage.show();
    }

    }
