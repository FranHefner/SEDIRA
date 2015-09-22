/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sedira.model.Paciente;
import sedira.ConsultasDB;
import sedira.model.TipoDocumento;


/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PacienteController implements Initializable {
    @FXML
    private TableView<Paciente> griListaPacientes;
    @FXML
    private TableColumn<Paciente, String> clTipoDoc;
    @FXML
    private TableColumn<Paciente, Integer> clNumeroDoc;
    @FXML
    private TableColumn<Paciente, String> clNombre;
    @FXML
    private TableColumn<Paciente, String> clApellido;
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
    private TextField txtCampoBusqueda;    
    @FXML 
    private ImageView imgPaciente;
    @FXML 
    private ComboBox cbTipoDoc;
            
            
    private Paciente PacienteActual;
      
    /**
     * Inicializacion de la clase Controlador.
     */
    
    private ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
    private ObservableList<String> DocumentosData = FXCollections.observableArrayList();
    
     public ObservableList<Paciente> getPacienteData() {
        return pacienteData;
    }     
    // public ObservableList<TipoDocumento> getDocumentosData() {
    //    return DocumentosData;
   // }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         clNombre.setCellValueFactory(cellData -> cellData.getValue().getNombre());
         clApellido.setCellValueFactory(cellData -> cellData.getValue().getApellido());         
         clTipoDoc.setCellValueFactory(cellData ->  cellData.getValue().getTipoDoc());          
         clNumeroDoc.setCellValueFactory(cellData -> cellData.getValue().getNumeroDoc().asObject());
          
          griListaPacientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionPaciente(newValue));

      
          
       pacienteData =  ConsultasDB.ListaPacientes();
      DocumentosData = ConsultasDB.ListaTipoDocumento();
        cbTipoDoc.setItems(DocumentosData);
       
       
     }   
    @FXML
     private void btnBuscar_click() 
    {         
         // Paciente PacienteTEST = new Paciente(1,1,34000000,"ApellidoTest", "NombreTest", '2015-09-09',"asd",1234,"asd",4411,,"m",true);
            
        //  public Paciente(int idPaciente, int tipoDoc, long numeroDoc, String apellido, String nombre, Date fechaNacimiento, String direccion, long numeroAsociado, String email, String telefono, Blob foto, String sexo, boolean enTratamiento) {
               
     // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Paciente> filteredData = new FilteredList<>(pacienteData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txtCampoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Paciente -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Paciente.getNombre().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (Paciente.getApellido().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
      
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Paciente> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(griListaPacientes.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        griListaPacientes.setItems(sortedData);
      
    }
     @FXML
    private void SeleccionPaciente(Paciente PacienteActual) 
    {  
    
        if (PacienteActual != null) {
            
            this.PacienteActual = PacienteActual;
            txtIdPaciente.setText(String.valueOf( PacienteActual.getidPaciente().getValue() ) );
            txtNombre.setText(String.valueOf( PacienteActual.getNombre().getValue() ) );
            txtApellido.setText(String.valueOf(PacienteActual.getApellido().getValue()));
            txtNumeroDoc.setText(String.valueOf(PacienteActual.getNumeroDoc().getValue()));
              cbTipoDoc.setValue(String.valueOf(PacienteActual.getTipoDoc().getValue())  );
//   txtTipoDoc.setText(String.valueOf(PacienteActual.getTipoDoc().getValue()));
           // txtNumeroAsociado.setText(PacienteActual.getStreet());
  
        } else {
            txtIdPaciente.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtNumeroDoc.setText("");
        
      
        }
    }
    @FXML
     private void btnNuevo_click() 
     {
            txtNombre.setText("");
            txtApellido.setText("");
            txtNumeroDoc.setText("");
         //   txtTipoDoc.setText("");
            
            txtIdPaciente.setText( String.valueOf( pacienteData.size() +1  ));
            
            txtNombre.setEditable(true);
            txtApellido.setEditable(true);
            txtNumeroDoc.setEditable(true);
         //   txtTipoDoc.setEditable(true);
             cbTipoDoc.setDisable(false);
       
     }
     @FXML
     private void btnAceptar_click() 
     {      
            
        if (Integer.valueOf( txtIdPaciente.getText()) <= pacienteData.size() )
        {
                  PacienteActual.setApellido(txtApellido.getText());
                  PacienteActual.setNombre(txtNombre.getText());
                  PacienteActual.setNumeroDoc(Integer.valueOf(txtNumeroDoc.getText()));
                  PacienteActual.setTipoDoc(cbTipoDoc.getValue().toString());
                  
                  txtNombre.setEditable(false);
                  txtApellido.setEditable(false);
                  txtNumeroDoc.setEditable(false);
             //     txtTipoDoc.setEditable(false);
                  cbTipoDoc.setDisable(true);
        }
        else           
        {
       //    Paciente PacienteTemp =  new Paciente(Integer.valueOf( txtIdPaciente.getText()) , cbTipoDoc.getValue().toString() , Integer.valueOf( txtNumeroDoc.getText() ),txtApellido.getText(), txtNombre.getText(),new );
          Paciente PacienteTemp = new Paciente(Integer.valueOf( txtIdPaciente.getText()),  cbTipoDoc.getValue().toString(), Integer.valueOf( txtNumeroDoc.getText() ),txtApellido.getText(),txtNombre.getText());
            // Date fechaNacimiento, String direccion, long numeroAsociado, String email, String telefono, Blob foto, String sexo, boolean enTratamiento) {
    
           pacienteData.add(PacienteTemp);
            txtNombre.setEditable(false);
            txtApellido.setEditable(false);
            txtNumeroDoc.setEditable(false);
            cbTipoDoc.setDisable(true);
         }       
        
           
     }
     @FXML
     private void btnEditar_click() 
     {                            
            txtNombre.setEditable(true);
            txtApellido.setEditable(true);
            txtNumeroDoc.setEditable(true);
         //   txtTipoDoc.setEditable(true); 
            cbTipoDoc.setDisable(false);
            
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
     @FXML
    private void btnContacto_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ContactoPaciente.fxml"));
        Scene scene = new Scene(root);
              stage.setScene(scene);
        
        stage.setTitle("Contacto Paciente");
        stage.show();   
    }
      

     
     
}
