
package sedira;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sedira.model.Paciente;

/**
 * Clase Controller para el calculo 
 * @author Hefner Francisco, Quelin Pablo
 */

public class CalculoController {
    @FXML
    private TabPane tabPaneCalculo;
    @FXML
    private Tab tabPaciente;
    @FXML
    private Tab tabPhantom;
    @FXML
    private Tab tabOrganoTejido;
    @FXML
    private Tab tabCalcular;
    @FXML
    private AnchorPane tabAnchorPanePaciente;
    @FXML
    private TableView<Paciente> tablaPaciente;
    @FXML
    private TableColumn  idPaciente;
    @FXML
    private TableColumn numeroDocPaciente;
    @FXML
    private TableColumn<Paciente, String> nombrePaciente;
    @FXML
    private TableColumn<Paciente, String> ApellidoPaciente;
    @FXML
    private TextField txtFldTipoDocPaciente;
    @FXML
    private TextField txtFldDocPaciente;
    @FXML
    private TextField txtFldNombrePaciente;
    @FXML
    private TextField txtFldApellidoPaciente;
    @FXML
    private TextField txtFldBuscar;
    @FXML
    private TextField txtFldNroAsociado;
    @FXML
    private TextField txtFldFechaRegistro;
       
    
    
    @FXML
     private void MostrarMenuPaciente() {
         
     }
       
}
