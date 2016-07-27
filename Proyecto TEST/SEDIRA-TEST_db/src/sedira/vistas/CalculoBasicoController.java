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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sedira.DatosValidacionesCalculoBasico;
import sedira.IDatosValidaciones;
import static sedira.vistas.CalculoController.pestañaActual;

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
    private Tab tabCalcular;
   
    @FXML
    private Pane pnlPaciente;
  
    @FXML
    private Pane pnlCalculo;

    @FXML
    private Button btnSiguiente;
    @FXML
    private TextArea txtProceso;

    @FXML
    private Button btnAtras;


    /**
     * Initializes the controller class.
     */
     SingleSelectionModel<Tab> listaTABS;
     /****** Inicializamos si vamos a realizar un proceso completo (Cientifico) o de Medico (Basico) ***/

     private IDatosValidaciones dValidaciones = new DatosValidacionesCalculoBasico();
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
        try {
            
            dValidaciones.setProcesoCompleto(false);
             
            Node NodoPaciente;
            NodoPaciente = (Node) FXMLLoader.load(getClass().getResource("PestañaPaciente.fxml"));
            pnlPaciente.getChildren().setAll(NodoPaciente);
            
               listaTABS = tabPaneCalculo.getSelectionModel();
        } catch (IOException ex) {
            Logger.getLogger(CalculoBasicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    


        public void SeleccionPestaña(boolean adelante) throws IOException {
        //  String EstadoActual = NuevoCalculo.EstadoActual();

        String EstadoActual = dValidaciones.getEstadoActual(adelante, pestañaActual);

        if (EstadoActual.equals("Paciente")) {
            tabPaciente.setDisable(false);
            listaTABS.select(tabPaciente);
            pestañaActual = "Paciente";        
            tabCalcular.setDisable(true);
            txtProceso.setText(dValidaciones.GetTextoProgeso());

        }
    
       
        if (EstadoActual.equals("Completo")) {
            tabCalcular.setDisable(false);
            listaTABS.select(tabCalcular);
            pestañaActual = "Completo";
            tabPaciente.setDisable(true);

            if (adelante) {

                Node NodoCalculo;
                NodoCalculo = (Node) FXMLLoader.load(getClass().getResource("PestañaCalculo.fxml"));
                pnlCalculo.getChildren().setAll(NodoCalculo);
            }

            txtProceso.setText(dValidaciones.GetTextoProgeso());

        }

        /* Logica de botones */
        if (EstadoActual.equals("Paciente")) {
            btnAtras.setDisable(true);
            btnSiguiente.setDisable(false);

        } else {
            btnAtras.setDisable(false);
            if (EstadoActual.equals("Completo")) {
                btnSiguiente.setText("Finalizar");
              
            } else {
                btnSiguiente.setText("Siguiente");
                btnSiguiente.setDisable(false);
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
    private void btnCancelar_click(ActionEvent event) {
        
        
    }
   
    
}
