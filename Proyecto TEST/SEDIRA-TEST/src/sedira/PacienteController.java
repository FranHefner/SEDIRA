/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.model.Paciente;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PacienteController implements Initializable {
    @FXML
    private TextField txtCampoBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Paciente> griListaPacientes;
    @FXML
    private TableColumn<?, ?> clTipoDoc;
    @FXML
    private TableColumn<?, ?> clNumerOdOC;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txtNumeroDoc;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;   
      
    /**
     * Inicializacion de la clase Controlador.
     */
    
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
    
     public ObservableList<Paciente> getPacienteData() {
        return pacienteData;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         

         
    }   
    
     @FXML
     private void btnBuscar_click() 
    {         
         // Paciente PacienteTEST = new Paciente(1,1,34000000,"ApellidoTest", "NombreTest", '2015-09-09',"asd",1234,"asd",4411,,"m",true);
            
        //  public Paciente(int idPaciente, int tipoDoc, long numeroDoc, String apellido, String nombre, Date fechaNacimiento, String direccion, long numeroAsociado, String email, String telefono, Blob foto, String sexo, boolean enTratamiento) {
            final ObservableList<Paciente> DataPacientes = FXCollections.observableArrayList(
               new Paciente(1, 1, 34000001,"NombrePersona1", "ApellidoPersona1"),
               new Paciente(2, 1, 34000002,"NombrePersona1", "ApellidoPersona1"),
               new Paciente(3, 1, 34000003,"NombrePersona1", "ApellidoPersona1"),
               new Paciente(4, 2, 34000004,"NombrePersona1", "ApellidoPersona1"),
               new Paciente(5, 2, 34000005,"NombrePersona1", "ApellidoPersona1")
           );
        
         
            griListaPacientes.setItems(DataPacientes);
        Paciente PacienteTEST = new Paciente();
        
        PacienteTEST.setTipoDoc(1);
        PacienteTEST.setNumeroDoc(34663986);        
         PacienteTEST.setNombre("Francisco");
         PacienteTEST.setApellido("Hefner");
       txtNombre.setText(PacienteTEST.getNombre());
       txtApellido.setText(PacienteTEST.getApellido());
      
    }
    @FXML
    private void btnBuscar2click() 
    {  
     //sad
    }
     
     
}
