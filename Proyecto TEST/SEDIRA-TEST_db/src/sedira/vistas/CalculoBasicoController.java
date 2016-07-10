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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CalculoBasicoController implements Initializable {

    @FXML
    private TabPane tabPaneCalculo;
    @FXML
    private Tab tabPaciente;
    @FXML
    private Pane pnlPaciente;
    @FXML
    private Tab tabPhantom;
    @FXML
    private Pane pnlPhantom;
    @FXML
    private Tab tabOrganoTejido;
    @FXML
    private Pane pnlOrgano;
    @FXML
    private Tab tabRadionuclido;
    @FXML
    private Pane pnlRadionuclido;
    @FXML
    private Tab tabCalcular;
    @FXML
    private AnchorPane pnlCalculo;
    @FXML
    private TextArea txtProceso;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Node NodoPaciente;
            NodoPaciente = (Node) FXMLLoader.load(getClass().getResource("PestañaPaciente.fxml"));
            pnlPaciente.getChildren().setAll(NodoPaciente);
        } catch (IOException ex) {
            Logger.getLogger(CalculoBasicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnAtras_click(ActionEvent event) {
    }

    @FXML
    private void btnSiguiente_click(MouseEvent event) {
    }

    @FXML
    private void btnSiguiente_click(ActionEvent event) {
        
             
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) {
    }
    
}
