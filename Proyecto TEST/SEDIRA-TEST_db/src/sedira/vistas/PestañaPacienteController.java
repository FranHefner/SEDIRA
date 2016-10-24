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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.model.Paciente;
import sedira.DatosValidacionesCalculo;
import sedira.DatosValidacionesCalculoBasico;
import sedira.IDatosValidaciones;
import sedira.model.IPacienteDAO;
import sedira.model.PacienteDAOsql;

/**
 * FXML Controller class
 * Clase controladora de la interfaz gráfica "Pestaña Paciente" que pertenece a un calculo determinado. 
 * @author Hefner Francisco, Quelin Pablo
 */
public class PestañaPacienteController implements Initializable {
     
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IPacienteDAO pac = new PacienteDAOsql(); 
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
    private Label cantCaracteres;

    /**
     *
     */
    @FXML
    private TextArea txtObservaciones;
        
    private Paciente pacienteActual;
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
    private IDatosValidaciones dValidaciones;
    
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
     //   if( MenuPrincipalController.TipoUsuario == "Cientifico")
        //{
           dValidaciones = new DatosValidacionesCalculo();
        //}
       // if (MenuPrincipalController.TipoUsuario == "Medico")
       // {
         //  dValidaciones = new DatosValidacionesCalculoBasico();
       // }
        /****************************************************************************/
        
        clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());         
        clTipoDoc.setCellValueFactory(cellData ->  cellData.getValue().getTipoDocProperty());          
        clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDocProperty().asObject());
         
         griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));
        try {
           
            //Carga los pacientes desde la base de datos .
            pacienteData = pac.obtenerPacientes();
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
            txtObservaciones.setText("");
            txtNombrePaciente.setDisable(false);
            dValidaciones.setPaciente(PacienteActual);
         
        } else {
            txtNombrePaciente.setText("");
            txtObservaciones.setText("");
            btnHistorialSEDIRA.setDisable(true);
            txtObservaciones.setDisable(true);
            txtNombrePaciente.setDisable(true);
             
        }
    }
    
    /**
     * Comportamiento para el botón Historial SEDIRA. 
     * @throws IOException 
     */
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
   
    /**
     * Método que controla el ingreso de texto en el 
     */
   @FXML
   private void getTextoObservaciones(){
       int count = 499-txtObservaciones.getText().length();
       
       String text=txtObservaciones.getText();
      
       if (txtObservaciones.getText().length()<500){
           
       dValidaciones.setObservaciones(txtObservaciones.getText());
       cantCaracteres.setText(String.valueOf(count));
       } else{
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado!");
            alert.setHeaderText("Se excedió el limite de caracteres para el campo Observaciones. ");
            alert.setContentText("Se han ingresado demasiados caracteres.");

            alert.showAndWait();
       }
       
   }
    
  
    
}
