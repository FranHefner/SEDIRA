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
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class Calculo_pruebaController implements Initializable {
    @FXML
    private TabPane tabPaneCalculo;
    @FXML
    private Tab tabPaciente;
    @FXML
    private AnchorPane pnlPaciente;
    @FXML
    private Tab tabPhantom;
    @FXML
    private Tab tabOrganoTejido;
    @FXML
    private Tab tabCalcular;
          

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          try {
            // Load person overview.
         /*   FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calculo_pruebaController.class.getResource("Paciente.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();*/

            // Set person overview into the center of root layout.
             Node node;
             node = (Node)FXMLLoader.load(getClass().getResource("Paciente.fxml")); 
           
             pnlPaciente.getChildren().setAll(node); 
            
            //  pnlPaciente = (AnchorPane) FXMLLoader.load("Paciente.fxml");
        //   pnlPaciente.getChildren().setAll(FXMLLoader.load("Paciente.fxml"));
            // Give the controller access to the main app.
         /*   PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }   
    @FXML
    public void btnSiguiente (){
        tabPhantom.setDisable(true);
        
    }
    
}
