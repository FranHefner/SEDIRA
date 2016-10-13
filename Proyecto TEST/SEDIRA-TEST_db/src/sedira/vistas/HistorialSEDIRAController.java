/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sedira.FuncionesGenerales;
import sedira.model.CalculoDAOsql;
import sedira.model.CalculoMuestra;
import sedira.model.ICalculoDAO;
import sedira.model.Paciente;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class HistorialSEDIRAController implements Initializable {

    
    @FXML
    private Label lblPaciente;
    @FXML
    private Label lblPhantom;
    @FXML
    private Label lblOrgano;
    @FXML
    private Label lblRadionuclido;
    
    @FXML
    private TableView<CalculoMuestra> griListaCalculos;
    @FXML
    private TableColumn<CalculoMuestra, Integer> clIdCalculo;
    @FXML
    private TableColumn<CalculoMuestra, Long> clFechaCalculo;
    @FXML
    private TableColumn<CalculoMuestra, String> clPaciente;
    
    
    
    
    
    private Paciente pacienteActual = new Paciente();  
    //private int IdPacienteActual = pacienteActual.getIdPaciente();
    //Instancia de objeto tipo ICalculoDAO. Se inicializa como CalculoDAOsql.  
    private ICalculoDAO cal = new CalculoDAOsql(); 
    
    private ObservableList<CalculoMuestra> calculoData = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pacienteActual = FuncionesGenerales.getPacienteActual();
        //lblPaciente.setText(pacienteActual.getApellido());
        
        calculoData = cal.getCalculos();
            //actualizo el grid
        griListaCalculos.setItems(calculoData);
        
        clIdCalculo.setCellValueFactory(cellData -> cellData.getValue().getIdCalculoMuestraProperty().asObject());
        clFechaCalculo.setCellValueFactory(cellData -> cellData.getValue().getFechaProperty().asObject());
        clPaciente.setCellValueFactory(cellData -> cellData.getValue().getPacienteProperty());

        /*griListaCalculos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionCalculo(newValue));*/
        
        
        

           
        

    }
    
    
  
    
    

}
