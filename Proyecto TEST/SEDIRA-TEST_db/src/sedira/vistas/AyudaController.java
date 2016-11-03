/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class AyudaController implements Initializable {
    
    @FXML
    WebView navegador = new WebView();
    @FXML
    private Button btnCerrar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        WebEngine engine = navegador.getEngine();
        //url = getClass().getResource("file:///D:/Documents/NetBeansProjects/SEDIRA/Proyecto TEST/SEDIRA-TEST_db/dist/javadoc/index.html");
          //  navegador.getEngine().load(url.toExternalForm());
        //Descomentar esta linea con la ruta local del javadoc. 
        //engine.load("file:///D:/Documents/NetBeansProjects/SEDIRA/Proyecto%20TEST/SEDIRA-TEST_db/dist/javadoc/index.htmla");
        
    }     

    @FXML
    private void btnCerrar_click(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        
        stage.close();
    }
   
   
    
}
