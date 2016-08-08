/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.Phantom;
import sedira.model.ValorDescripcion;
import sedira.DatosValidacionesCalculo;
import sedira.DatosValidacionesCalculoBasico;
import sedira.IDatosValidaciones;
import sedira.model.IPhantomDAO;
import sedira.model.PhantomDAOsql;

/**
 * Clase controladora del archivo FXML PestañaPhantom. 
 *
 * @author Hefner Francisco, Quelin Pablo 
 */
public class PestañaPhantomController implements Initializable {
       
    //declaración de los componentes de la interfaz. 
    @FXML
    private TextField txtPesoTotal;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private ChoiceBox choicePhantom;
    @FXML
    private TableView <Organo> griOrgano;
    @FXML
    private TableColumn <Organo, String> clOrganoNombre;
    @FXML
    private TableColumn <Organo, String> clOrganoMasa;
    @FXML
    private TableView <ValorDescripcion> griValorDescripcionPhantom;
    @FXML
    private TableColumn <ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidad;
    @FXML 
    private ComboBox cbUnidad;
    
    //Declaracion de variables
    
    //Lista de Organos
         public static  ObservableList <Organo> listaOrgano = FXCollections.observableArrayList();
    //Lista de Phantom
         public static  ObservableList <Phantom> ListaPhantom = FXCollections.observableArrayList();
    //Objeto de tipo ValorDescripcion auxiliar. 
        private ValorDescripcion phantomValorDescripcion;
    //Objeto Phantom auxiliar; 
        private Phantom phantomActual; 
    //Objeto interfaz Phantom
        IPhantomDAO ph = new PhantomDAOsql();
        
        
    private IDatosValidaciones dValidaciones;
    /**
     * Inicializa la clase controladora.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
        if( "Cientifico".equals(MenuPrincipalController.TipoUsuario))
        {
           dValidaciones = new DatosValidacionesCalculo();
        }
        if ("Medico".equals(MenuPrincipalController.TipoUsuario))
        {
           dValidaciones = new DatosValidacionesCalculoBasico();
        }
        
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());
       
        // Limpieza de los detalles de organos. 
        FuncionesGenerales.mostrarDetalleOrgano(null, griOrgano);
        
        //Desactivo la seleccion en la lista de organos 
        griOrgano.setEditable(false);
        
  
        
       //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValor.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griValorDescripcionPhantom);
        
        //Inicializo la lista de Phantoms para el ChoiceBox
        initListaPhantom();
        choicePhantom.setTooltip(new Tooltip("Seleccione el Phantom"));                
        
    }   
    /**
     * Método que inicializa la lista de Phantoms dentro del ChoiceBox 
     */
     @FXML
    public void initListaPhantom(){
        //Inicializo la lista de phantoms con la informacion de la base de datos. 
        ListaPhantom = ph.obtenerListaPhantomCompletos();
        //Lista auxliar para el manejo de los nombres de los phantoms. 
        ObservableList <String> listaStringPhantom = FXCollections.observableArrayList();
                     
        for (Phantom listaPhantom1 : ListaPhantom) {
            listaStringPhantom.add(listaPhantom1.phantomNombreProperty().getValue());
        }
        //Asigno la lista de los nombres de los Phantoms al ChoiceBox
         choicePhantom.setItems(listaStringPhantom);
    }
      
    /**
     * Método que se activa al seleccionar un phantom. 
     * Este llenara las tablas de organos y de informacion del phantom. 
     */
    @FXML
    public void seleccionarPhantom(){
        
        choicePhantom.getSelectionModel().selectedIndexProperty().addListener(new
            ChangeListener<Number>(){
               
                @Override
                public void changed (ObservableValue ov, Number value, Number newValue){
                    //Busco el Phantom por el Indice del ChoiceBox
                    
                    int index = choicePhantom.getSelectionModel().getSelectedIndex();
                    phantomActual = ListaPhantom.get(index);
                    //Completo tabla de Organos
                    
                    FuncionesGenerales.mostrarDetalleOrgano(ph.obtenerInfoOrgano(phantomActual), griOrgano);
                    //Completo tabla de Info Phantoms
                    FuncionesGenerales.mostrarDetalleTablaValorDescripcion(ph.obtenerInfoPhantom(phantomActual), griValorDescripcionPhantom);
                    // Completo el textfield pesoTotal
                    txtPesoTotal.setText(String.valueOf(phantomActual.getPesoTotal()));
                  /* Seleccion Phantom en el cálculo */  
                    dValidaciones.setPhantom(phantomActual);                            
                   /*************/
                    
                }
            });
    }
    /**
     * Método que se activa al seleccionar un item de la tabla de informacion del phantom.  
     */
    @FXML
    public void seleccionInfoPhantom (){
        phantomValorDescripcion = griValorDescripcionPhantom.getSelectionModel().getSelectedItem();
        txtPropiedad.setText(phantomValorDescripcion.getDescripcion());
        txtValor.setText(String.valueOf(phantomValorDescripcion.getValor()));
        txtUnidad.setText(phantomValorDescripcion.getUnidad());
        dValidaciones.setItemPhantom(phantomValorDescripcion);
    }
    
}
