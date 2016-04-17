/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.model.Paciente;
import sedira.DatosValidacionesCalculo;
import sedira.model.PacienteDAO;

/**
 * FXML Controller class
 * Clase controladora de la interfaz gráfica "Pestaña Paciente" que pertenece a un calculo determinado. 
 * @author Fran
 */
public class PestañaPacienteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Paciente> griListaPacientes;
    @FXML
    private TextField txtNombrePaciente;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableColumn<Paciente, String> clTipoDoc;
    @FXML
    private TableColumn<Paciente, Integer> clNumeroDoc;
    @FXML
    private TableColumn<Paciente, String> clNombre;
    @FXML
    private TableColumn<Paciente, String> clApellido;
    @FXML
    private Button btnHistorialSEDIRA;
    @FXML
    private TextArea txtObservaciones;
        
    private Paciente pacienteActual;
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());         
        clTipoDoc.setCellValueFactory(cellData ->  cellData.getValue().getTipoDocProperty());          
        clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDocProperty().asObject());
         
         griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));
        try {
            //pacienteData =  ConsultasDB.ListaPacientes();
            //Carga los pacientes desde la base de datos .
            pacienteData = PacienteDAO.obtenerPacientes();
        } catch (SQLException ex) {
            Logger.getLogger(PestañaPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        griListaPacientes.setItems(pacienteData);
    }    
    
    public ObservableList<Paciente> getPacienteData() {
        return pacienteData;
    }     
    @FXML
     private void btnBuscar_click() 
    {         
        griListaPacientes.setItems(FuncionesGenerales.FiltroListaPaciente(griListaPacientes, pacienteData, txtBusqueda));
    }
    private void SeleccionPaciente(Paciente PacienteActual) 
    {  
    
        if (PacienteActual != null) {
            
            this.pacienteActual = PacienteActual;
            
            txtNombrePaciente.setText(String.valueOf(PacienteActual.getApellido())+", "+ String.valueOf(PacienteActual.getNombre()));
            btnHistorialSEDIRA.setDisable(false);
            txtObservaciones.setDisable(false);
            txtNombrePaciente.setDisable(false);
            
            DatosValidacionesCalculo.setPaciente(PacienteActual);
            
            
        } else {
            txtNombrePaciente.setText("");
            txtObservaciones.setText("");
            btnHistorialSEDIRA.setDisable(true);
            txtObservaciones.setDisable(true);
            txtNombrePaciente.setDisable(true);
             
        }
    }
    
    @FXML
    private void btnHistorialSEDIRA_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ProgresoPaciente.fxml"));
        Scene scene = new Scene(root);
              stage.setScene(scene);
        
        stage.setTitle("Progreso Paciente");
        stage.show();   
    }
    
  
    
}
