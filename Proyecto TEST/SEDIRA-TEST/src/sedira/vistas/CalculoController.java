/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sedira.DatosValidacionesCalculo;

/**
 * FXML Controller class Clase controladora para el proceso de Calculo.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CalculoController implements Initializable {

    @FXML
    private TabPane tabPaneCalculo;

    @FXML
    private TableView<?> griListaPacientes;
    @FXML
    private TableColumn<?, ?> clTipoDoc;
    @FXML
    private TableColumn<?, ?> clNumeroDoc;
    @FXML
    private TableColumn<?, ?> clNombre;
    @FXML
    private TableColumn<?, ?> clApellido;
    @FXML
    private Tab tabPaciente;
    @FXML
    private Tab tabPhantom;
    @FXML
    private Tab tabOrganoTejido;
    @FXML
    private Tab tabRadionuclido;
    @FXML
    private Tab tabCalcular;
    @FXML
    private Pane pnlPhantom;
    @FXML
    private Pane pnlPaciente;
    @FXML
    private Pane pnlRadionuclido;
    @FXML
    private Pane pnlOrgano;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnCancelar;

    private PestañaOrganoController organoController;
    
    public static String pestañaActual;

    /**
     * Initializes the controller class.
     */
    // DatosValidacionesCalculo NuevoCalculo = new DatosValidacionesCalculo();
    SingleSelectionModel<Tab> listaTABS;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            /* SE INICIA EL CÁLCULO, SE CARGAN LAS PESTAÑAS Y SE CARGA LA LISTA DE TABS */
            // NuevoCalculo.IniciarCalculo();
            DatosValidacionesCalculo.IniciarCalculo();
            /**
             * ***********************************
             */

            Node NodoPhantom;
            NodoPhantom = (Node) FXMLLoader.load(getClass().getResource("PestañaPhantom.fxml"));
            pnlPhantom.getChildren().setAll(NodoPhantom);

            Node NodoPaciente;
            NodoPaciente = (Node) FXMLLoader.load(getClass().getResource("PestañaPaciente.fxml"));
            pnlPaciente.getChildren().setAll(NodoPaciente);

            Node NodoRadionuclido;
            NodoRadionuclido = (Node) FXMLLoader.load(getClass().getResource("PestañaRadionuclido.fxml"));
            pnlRadionuclido.getChildren().setAll(NodoRadionuclido);

            Node NodoOrgano;
            NodoOrgano = (Node) FXMLLoader.load(getClass().getResource("PestañaOrgano.fxml"));
            pnlOrgano.getChildren().setAll(NodoOrgano);

            listaTABS = tabPaneCalculo.getSelectionModel();

        } catch (IOException ex) {
            Logger.getLogger(CalculoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SeleccionPestaña(boolean adelante) {
        //  String EstadoActual = NuevoCalculo.EstadoActual();
        
       
        String EstadoActual = DatosValidacionesCalculo.EstadoActual(adelante,pestañaActual );
               
        if (EstadoActual.equals("Paciente")) {
            tabPaciente.setDisable(false);
            listaTABS.select(tabPaciente);
            pestañaActual = "Paciente";
            
           

            tabPhantom.setDisable(true);
            tabOrganoTejido.setDisable(true);
            tabRadionuclido.setDisable(true);
            tabCalcular.setDisable(true);

        }
        if (EstadoActual.equals("Phantom")) {
            tabPhantom.setDisable(false);
            listaTABS.select(tabPhantom);
              pestañaActual = "Phantom";

            tabPaciente.setDisable(true);
            tabOrganoTejido.setDisable(true);
            tabRadionuclido.setDisable(true);
            tabCalcular.setDisable(true);
                        
        }
        if (EstadoActual.equals("Organo")) {
            tabOrganoTejido.setDisable(false);
            listaTABS.select(tabOrganoTejido);
              pestañaActual = "Organo";

            tabPaciente.setDisable(true);
            tabPhantom.setDisable(true);
            tabRadionuclido.setDisable(true);
            tabCalcular.setDisable(true);
        }
        if (EstadoActual.equals("RadioNuclido")) {
            tabRadionuclido.setDisable(false);
            listaTABS.select(tabRadionuclido);
            pestañaActual = "RadioNuclido";

            tabPaciente.setDisable(true);
            tabPhantom.setDisable(true);
            tabOrganoTejido.setDisable(true);
            tabCalcular.setDisable(true);
        }
        if (EstadoActual.equals("Completo")) {
            tabCalcular.setDisable(false);
            listaTABS.select(tabCalcular);
             pestañaActual = "Completo";

            tabRadionuclido.setDisable(true);
            tabPaciente.setDisable(true);
            tabPhantom.setDisable(true);
            tabOrganoTejido.setDisable(true);
        }

        /* Logica de botones */
        if (EstadoActual.equals("Paciente")) {
            btnAtras.setDisable(true);
            btnSiguiente.setDisable(false);

        } else {
            btnAtras.setDisable(false);
            if (EstadoActual.equals("Completo")) {
                btnSiguiente.setDisable(true);
            }
        }

    }

    @FXML
    private void btnSiguiente_click() throws IOException {
        SeleccionPestaña(true);
    }

    @FXML
    private void btnAtras_click() throws IOException {
        SeleccionPestaña(false);
    }

    @FXML
    private void btnCancelar_click() throws IOException {

    }

}
