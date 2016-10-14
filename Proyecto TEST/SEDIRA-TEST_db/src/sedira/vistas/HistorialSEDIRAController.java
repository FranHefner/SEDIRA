/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.sql.rowset.serial.SerialBlob;
import sedira.FuncionesGenerales;
import sedira.Security;
import sedira.model.CalculoDAOsql;
import sedira.model.CalculoMuestra;
import sedira.model.ICalculoDAO;
import sedira.model.IVariableCalculoDAO;
import sedira.model.MyCanvas;
import sedira.model.Paciente;
import sedira.model.VariableCalculo;
import sedira.model.VariableCalculoDAOsql;
import static sedira.vistas.MenuPrincipalController.math;



/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class HistorialSEDIRAController implements Initializable {

    @FXML
    private Label lblPaciente;
    @FXML
    private TextField txtFormula;
    @FXML
    private TextField txtResultado;
    @FXML
    private Label lblPhantom;
    @FXML
    private Label lblOrgano;
    @FXML
    private Label lblRadionuclido;
    @FXML
    private Pane pnFuncion;
    @FXML
    private Label lblhash;
      

    @FXML
    private TableView<CalculoMuestra> griListaCalculos;
    @FXML
    private TableColumn<CalculoMuestra, Integer> clIdCalculo;
    @FXML
    private TableColumn<CalculoMuestra, String> clFechaCalculo;
    @FXML
    private TableColumn<CalculoMuestra, String> clPaciente;

    @FXML
    private TableView<VariableCalculo> griVariableCalculo;
    @FXML
    private TableColumn<VariableCalculo, String> clPropiedad;
    @FXML
    private TableColumn<VariableCalculo, String> clValor;
    @FXML
    private TableColumn<VariableCalculo, String> clVariable;

    private Paciente pacienteActual = new Paciente();
    private MyCanvas canvas;
    
    
   
   
    //Instancia de objeto tipo ICalculoDAO. Se inicializa como CalculoDAOsql.  
    private ICalculoDAO cal = new CalculoDAOsql();
     //Instancia de objeto tipo IVariableCalculoDAO. Se inicializa como VariableCalculoDAOsql.  
    private IVariableCalculoDAO var = new VariableCalculoDAOsql();
    
    //Arreglos de calculos realizados a una persona. 
    private ObservableList<CalculoMuestra> calculoData = FXCollections.observableArrayList();
    //Arreglo de variables que contiene un calculo. 
    private ObservableList<VariableCalculo> varCalculoData = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pacienteActual = FuncionesGenerales.getPacienteActual();
        int idPaciente = pacienteActual.getIdPaciente();
        
        //Obtengo los calculos realizados a un paciente determinado 
        calculoData = cal.getCalculoPaciente(idPaciente);

        //actualizo el grid
        griListaCalculos.setItems(calculoData);

        clIdCalculo.setCellValueFactory(cellData -> cellData.getValue().getIdCalculoMuestraProperty().asObject());
        clFechaCalculo.setCellValueFactory(cellData -> cellData.getValue().getFechaSringProperty());
        clPaciente.setCellValueFactory(cellData -> cellData.getValue().getPacienteProperty());

        griListaCalculos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SeleccionCalculo(newValue));

        clPropiedad.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        clValor.setCellValueFactory(cellData -> cellData.getValue().valorProperty());
        clVariable.setCellValueFactory(cellData -> cellData.getValue().variableProperty());

    }
    /**
     * Método que se activa al seleccionar un cálculo de la lista de cálculos
     * @param calculoSeleccionado 
     */
    private void SeleccionCalculo(CalculoMuestra calculoSeleccionado) {
       
       String HashValidado="";
        pnFuncion.getChildren().clear();
                
        //Objeto aux de tipo calculo. 
        CalculoMuestra detalleItem = new CalculoMuestra();
        
        //Obtengo los detalles de un calculo en particular. 
        detalleItem = cal.getCalculoSeleccionado(calculoSeleccionado.getIdCalculoMuestra());

        if (calculoSeleccionado != null) {
            //Completo la grilla de variables del calculo. 
            varCalculoData = var.obtenerVariables(calculoSeleccionado.getIdCalculoMuestra());
            griVariableCalculo.setItems(varCalculoData);

            
            //Completo la informacion de los labels. 
            lblPaciente.setText(pacienteActual.getApellido() + " " + pacienteActual.getNombre());
            lblOrgano.setText(detalleItem.getOrgano());
            lblPhantom.setText(detalleItem.getPhantom());
            lblRadionuclido.setText(detalleItem.getRadionuclido());
            txtFormula.setText(detalleItem.getFormula());            
            txtResultado.setText(detalleItem.getResultado());   
            
            
                            
                  
                canvas = new MyCanvas(detalleItem.getFormulaTex());

                canvas.widthProperty().bind(pnFuncion.widthProperty());
                canvas.heightProperty().bind(pnFuncion.heightProperty());

                pnFuncion.getChildren().add(canvas);
                
            try {
               HashValidado=Security.md5(txtFormula.getText() + detalleItem.getFormulaTex()+ detalleItem.getResultado());
            } catch (Exception ex) {
             
            }
            if (HashValidado.equals(detalleItem.getHashValidado() ))
            {
                lblhash.setText(HashValidado + " - Validación: OK");
            }else                
            {
               lblhash.setText(HashValidado + " - Validación: No pasó la validación ");
            }
          
            

        } else {

        }

    }

}
