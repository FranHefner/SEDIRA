/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.model.Paciente;
import sedira.model.PacienteGrilla;

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
    private TableView<PacienteGrilla> griListaPacientes;
    @FXML
    private TableColumn<PacienteGrilla, Integer> clTipoDoc;
    @FXML
    private TableColumn<PacienteGrilla, Integer> clNumeroDoc;
    @FXML
    private TableColumn<PacienteGrilla, String> clNombre;
    @FXML
    private TableColumn<PacienteGrilla, String> clApellido;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txtIdPaciente;
    @FXML
    private TextField txtNumeroDoc;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido; 
    @FXML
    private TextField txtTipoDoc; 
     @FXML
    private TextField txtNumeroAsociado; 
    
    private PacienteGrilla PacienteActual;
      
    /**
     * Inicializacion de la clase Controlador.
     */
    
    private ObservableList<PacienteGrilla> pacienteData = FXCollections.observableArrayList();
    
     public ObservableList<PacienteGrilla> getPacienteData() {
        return pacienteData;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
         clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellido());         
         clTipoDoc.setCellValueFactory(cellData -> cellData.getValue().getTipoDoc().asObject());          
         clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDoc().asObject());
          
          griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));
      
            
     }   
    
     @FXML
     private void btnBuscar_click() 
    {         
         // Paciente PacienteTEST = new Paciente(1,1,34000000,"ApellidoTest", "NombreTest", '2015-09-09',"asd",1234,"asd",4411,,"m",true);
            
        //  public Paciente(int idPaciente, int tipoDoc, long numeroDoc, String apellido, String nombre, Date fechaNacimiento, String direccion, long numeroAsociado, String email, String telefono, Blob foto, String sexo, boolean enTratamiento) {
    
            if (griListaPacientes.getItems().size() > 5 )
            {
             
            }else
            {
           //    final ObservableList<PacienteGrilla> pacienteData = FXCollections.observableArrayList(
              pacienteData.add(  new PacienteGrilla(1, 1, 34000001,"Fran", "Hefner"));
              pacienteData.add(  new PacienteGrilla(2, 1, 34000002,"Pablo", "Quelin"));
              pacienteData.add(  new PacienteGrilla(3, 1, 34000003,"Roberto", "Salibar"));
              pacienteData.add(  new PacienteGrilla(4, 2, 34000004,"Pablo", "Argañaras"));
              pacienteData.add(  new PacienteGrilla(5, 2, 34000005,"Pepe", "Perez"));
            //   );                            
            }
                       
        griListaPacientes.setItems(pacienteData);
    
      
    }
    @FXML
    private void SeleccionPaciente(PacienteGrilla PacienteActual) 
    {  
    
        if (PacienteActual != null) {
            
            this.PacienteActual = PacienteActual;
            txtIdPaciente.setText(String.valueOf( PacienteActual.getidPaciente().getValue() ) );
            txtNombre.setText(String.valueOf( PacienteActual.getNombre().getValue() ) );
            txtApellido.setText(String.valueOf(PacienteActual.getApellido().getValue()));
            txtNumeroDoc.setText(String.valueOf(PacienteActual.getNumeroDoc().getValue()));
            txtTipoDoc.setText(String.valueOf(PacienteActual.getTipoDoc().getValue()));
           // txtNumeroAsociado.setText(PacienteActual.getStreet());
  
        } else {
            txtIdPaciente.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtNumeroDoc.setText("");
            txtTipoDoc.setText("");
      
        }
    }
    
     @FXML
     private void btnNuevo_click() 
     {
            txtNombre.setText("");
            txtApellido.setText("");
            txtNumeroDoc.setText("");
            txtTipoDoc.setText("");
            
            txtIdPaciente.setText( String.valueOf( griListaPacientes.getItems().size()+1 ));
            
            txtNombre.setEditable(true);
            txtApellido.setEditable(true);
            txtNumeroDoc.setEditable(true);
            txtTipoDoc.setEditable(true);
       
     }
     
      @FXML
     private void btnAceptar_click() 
     {      
            
        if (Integer.valueOf( txtIdPaciente.getText()) <= griListaPacientes.getItems().size() )
        {
                  PacienteActual.setApellido(txtApellido.getText());
                  PacienteActual.setNombre(txtNombre.getText());
                  PacienteActual.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
                  PacienteActual.setTipoDoc(Integer.valueOf( txtTipoDoc.getText() ));
                  
                  txtNombre.setEditable(false);
                  txtApellido.setEditable(false);
                  txtNumeroDoc.setEditable(false);
                  txtTipoDoc.setEditable(false);
        }
        else           
        {
            PacienteGrilla PacienteTemp =  new PacienteGrilla( Integer.valueOf( txtIdPaciente.getText()) , Integer.valueOf( txtTipoDoc.getText() ) , Integer.valueOf( txtNumeroDoc.getText() ),txtNombre.getText(), txtApellido.getText());
            pacienteData.add(PacienteTemp);
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtNumeroDoc.setEditable(false);
            txtTipoDoc.setEditable(false);
         }       
        
           
     }
      @FXML
     private void btnEditar_click() 
     {                            
            txtNombre.setEditable(true);
            txtApellido.setEditable(true);
            txtNumeroDoc.setEditable(true);
            txtTipoDoc.setEditable(true);            
     }
     
     
      

     
     
}
