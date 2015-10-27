/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sedira.model.Phantom;

/**
 * FXML Controller class
 *  Clase controladora para la interfaz de Organo/Tejido del calculo. 
 * @author Quelin Pablo, Hefner Francisco
 */
public class PestañaOrganoTejidoController implements Initializable {

    @FXML
    private ChoiceBox choiceOrgano;
    @FXML
    private TextField idOrgano;
    @FXML
    private TextField nombreOrgano;
    @FXML 
    private TextField masaOrgano;
    @FXML 
    private TextField phantomSeleccionado;
    
    
    private Phantom phantomAux = PestañaPhantomController.phantomActual;
    /**
     * Inicializa la clase controladora. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se crea una phantom auxilicar con la informacion del phantom seleccionado en la pestaña 
        //de phantom, esto es porque los organos estan contenidos dentro de los Phantoms. 
        
        
        initListaOrgano();
        
        
    }    
    /**
     * Metodo que inicializa la lista de Organos dentro del ChoiceBox 
     * la lista de organos se tomara a partir del phantom seleccionado.
     */
     @FXML
    public void initListaOrgano(){
        //Lista auxliar para el manejo de los nombres de los Organos. 
        ObservableList <String> listaOrganoString = FXCollections.observableArrayList();
        
        for (int i=0; i<phantomAux.getOrgano().size();i++){
            listaOrganoString.add(phantomAux.getOrgano().get(i).getNombreOrgano());
        }
            
        //Asigno la lista de los nombres de los Phantoms al ChoiceBox
         choiceOrgano.setItems(listaOrganoString);
    }
    
    
}
