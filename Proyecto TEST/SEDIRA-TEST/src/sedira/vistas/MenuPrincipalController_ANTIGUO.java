/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Fran
 */
public class MenuPrincipalController_ANTIGUO implements Initializable {
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
    private void mniPacienteAdministrar_CLICK(ActionEvent event) throws IOException {
            
        Parent PacienteVista = FXMLLoader.load(getClass().getResource("vistas/Paciente.fxml"));
        
        Scene scene = new Scene(PacienteVista);     
        Stage EscenarioPaciente = new Stage();
        
        EscenarioPaciente.setScene(scene);
        EscenarioPaciente.show();
        
    }
    
}
