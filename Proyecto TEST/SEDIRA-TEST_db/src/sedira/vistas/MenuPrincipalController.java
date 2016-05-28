/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;



/**
 * FXML Menu principal Controller class
 *  Controlador para el comportamiento del menu principal
 * @author Hefner Francisco, Quelin Pablo
 */

public class MenuPrincipalController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    /**
     * Declaración del menu
     */       
    @FXML
    private MenuBar mnbPrincipal;
    /**
     * Funcion que habilita o deshabilita el menu principal    
     */
    private void HabilitacionMenu(boolean Habilitado)
    {
        mnbPrincipal.setDisable(!Habilitado);
        
    }
    /**
     * Comportamiento para Item de menu, administrar paciente. 
     * @throws IOException 
     */
    @FXML
    private void mniPacienteAdministrar_click() throws IOException
    { 
      
             
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Paciente.fxml"));
        Scene scene = new Scene(root);
        stage.setMaxWidth(700);        
        stage.setMaxHeight(639);
        stage.setMinWidth(700);        
        stage.setMinHeight(639);
        stage.setScene(scene);
         stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Administrar Pacientes");
        stage.show();   
        
     
      
    }
    /**
     * Comportamiento para el Item de menu, Iniciar Calculo. 
     * @throws IOException 
     */
    @FXML
    private void mniIniciarCalculo_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Calculo.fxml"));
        Scene scene = new Scene(root);         
       /*
        stage.setMaxWidth(682);        
        stage.setMaxHeight(671);
        stage.setMinWidth(682);        
        stage.setMinHeight(671);*/
          stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Calcular Dosis Administrada");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.show();          
        
    }
    /**
     * Comportamiento para el Item de menu, Administrar Phantoms
     * @throws IOException 
     */
    @FXML
    private void mniPhantomAdministrar_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Phantom.fxml"));
        Scene scene = new Scene(root);        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Administrar Phantoms");
        stage.show();          

    }
    
    /**
     * Comportamiento para el Item de menu, Administrar Radionuclidos
     * @throws IOException 
     */
    @FXML
    private void mniRadionuclidosAdministrar_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Radionuclido.fxml"));
        Scene scene = new Scene(root);        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Administrar Radionuclidos");
        stage.show();          

    }
    
  
 
}
