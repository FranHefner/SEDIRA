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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sedira.ConsultasDB;
import sedira.FuncionesGenerales;
import sedira.model.Organo;
import sedira.model.Phantom;

/**
 * FXML Controller class
 *  Clase controladora para la interfaz de Organo/Tejido del calculo. 
 * @author Quelin Pablo, Hefner Francisco
 */
public class PestañaOrganoController implements Initializable {

    @FXML
    private ChoiceBox choiceOrgano;
    @FXML
    private TextField txtIdOrgano;
    @FXML
    private TextField txtNombreOrgano;
    @FXML 
    private TextField txtMasaOrgano;
    @FXML 
    private TextField txtPhantomSeleccionado;
    
    private Organo organoActual;
    int aux;
     
    
   
    
    /**
     * Inicializa la clase controladora. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        /**
         * OJO. metodo init y el metodo seleccion del choiceBox genera indexOfBounds. 
         * 
         */
    }    
    /**
     * Metodo que inicializa la lista de Organos dentro del ChoiceBox 
     * la lista de organos se tomara a partir del phantom seleccionado.
     */
     @FXML
    public void initListaOrgano(){
        //basicamente el if es un controlador para que se inicialize solo 1 vez, 
        //Esta inicializacion deberia estar en el calculo controller. 
        if (aux == 0){
            ObservableList <String> listaOrganoString = FXCollections.observableArrayList();

            for (int i=0; i<FuncionesGenerales.phantomActual.getOrgano().size();i++){
                listaOrganoString.add(FuncionesGenerales.phantomActual.getOrgano().get(i).getNombreOrgano());
            }
            choiceOrgano.setItems(listaOrganoString);
            txtPhantomSeleccionado.setText(FuncionesGenerales.phantomActual.getPhantomNombre());
            aux = 1;
        }   
        //Asigno la lista de los nombres de los Phantoms al ChoiceBox
        
        
    }
    
    /**
     * Metodo que se activa al seleccionar un phantom. 
     * Este llenara las tablas de organos y de informacion del phantom. 
     */
    @FXML
    public void seleccionarOrgano(){
        
        choiceOrgano.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            public void changed (ObservableValue ov, Number value, Number newValue){
                //Busco el Organo por el Indice del ChoiceBox
                int index = choiceOrgano.getSelectionModel().getSelectedIndex();
                organoActual = ConsultasDB.ObtenerOrganos().get(index);

                //Completo tabla de Organos
                showDetalleOrgano();

            }
        });
    }
    
    @FXML
    public void showDetalleOrgano( ){
        txtNombreOrgano.setText(organoActual.getNombreOrgano());
        txtMasaOrgano.setText(organoActual.getOrganMass().toString());
    }
    
    
}
