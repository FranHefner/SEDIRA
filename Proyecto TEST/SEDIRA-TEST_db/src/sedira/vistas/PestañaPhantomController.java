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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.Phantom;
import sedira.model.ValorDescripcion;
import sedira.DatosValidacionesCalculo;
import sedira.model.PhantomDAO;

/**
 * Clase controladora del archivo FXML PestañaPhantom. 
 *
 * @author Hefner Francisco, Quelin Pablo 
 */
public class PestañaPhantomController implements Initializable {
       
    //declaración de los componentes de la interfaz. 
    
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
    //Objeto de tipo ValorDescripcopn auxiliar. 
        private ValorDescripcion phantomValorDescripcion;
    //Objeto de tipo Organo auxiliar. 
        private Organo organoActual;
    //Objeto Phantom auxiliar; 
        private Phantom phantomActual; 
    
    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Inicializo la tabla de Organos
        clOrganoNombre.setCellValueFactory(
                cellData -> cellData.getValue().getNombreOrganoProperty());
        clOrganoMasa.setCellValueFactory(
                cellData -> cellData.getValue().getOrganMassProperty().asString());
       
        // Limpieza de los detalles de organos. 
        FuncionesGenerales.mostrarDetalleOrgano(null, griOrgano);
  
        
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
     * Metodo que inicializa la lista de Phantoms dentro del ChoiceBox 
     */
     @FXML
    public void initListaPhantom(){
        //Inicializo la lista de phantoms con la informacion de la base de datos. 
        ListaPhantom = PhantomDAO.obtenerListaPhantom();
        //Lista auxliar para el manejo de los nombres de los phantoms. 
        ObservableList <String> listaStringPhantom = FXCollections.observableArrayList();
                     
        for (Phantom listaPhantom1 : ListaPhantom) {
            listaStringPhantom.add(listaPhantom1.phantomNombreProperty().getValue());
        }
        //Asigno la lista de los nombres de los Phantoms al ChoiceBox
         choicePhantom.setItems(listaStringPhantom);
    }
      
    /**
     * Metodo que se activa al seleccionar un phantom. 
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
                    FuncionesGenerales.mostrarDetalleOrgano(PhantomDAO.obtenerInfoOrgano(phantomActual), griOrgano);
                    //Completo tabla de Info Phantoms
                    FuncionesGenerales.mostrarDetalleTablaValorDescripcion(PhantomDAO.obtenerInfoPhantom(phantomActual), griValorDescripcionPhantom);
                  
                  /* Seleccion Phantom en el cálculo */  
                    DatosValidacionesCalculo.setPhantom(phantomActual);                            
                   /*************/
                }
            });
    }
    
}
