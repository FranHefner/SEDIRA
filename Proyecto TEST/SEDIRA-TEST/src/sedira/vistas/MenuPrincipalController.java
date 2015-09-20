/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */

public class MenuPrincipalController implements Initializable {
    
    @FXML
    private MenuBar mnbPrincipal;
    @FXML
    private MenuItem mniPacienteAdministrar;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
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
        
        stage.setTitle("Administrar Pacientes");
        stage.show();   
    }
    @FXML
    private void mniIniciarCalculo_click() throws IOException
    { 
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Calculo.fxml"));
        Scene scene = new Scene(root);        
        stage.setMaxWidth(700);        
        stage.setMaxHeight(600);
        stage.setMinWidth(700);        
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.setTitle("Calcular Dosis Administrada");
        stage.show();          

    }
    
  
 
}
