/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sedira.DatosValidacionesCalculo;
import sedira.vistas.CalculoController;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;


/**
 * FXML Controller class
 *
 * @author Fran
 */
public class PestañaCalculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TextArea txtEntradasCalculo;
     @FXML
    private Button btnCalcular;
     
        @FXML
    private ProgressBar BarraProgreso;
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       txtEntradasCalculo.setText( DatosValidacionesCalculo.GetTextoProgeso());
     
    }    
    
    public void RealizarCalculo()            
    {
     
      BarraProgreso.setProgress(0.5);
      
      double Progreso = 0;
      
      boolean bandera = false;
      while (bandera == false)
      {
          try {
              Thread.sleep(100);
            Progreso  = Progreso+ 0.1;
              BarraProgreso.setProgress(Progreso);
              if (Progreso >= 1)
              {
                  bandera = true;
              }
          } catch (InterruptedException ex) {
              Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }
    
}
