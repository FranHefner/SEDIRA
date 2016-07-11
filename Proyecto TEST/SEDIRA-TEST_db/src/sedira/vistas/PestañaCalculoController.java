/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sedira.DatosValidacionesCalculo;
import sedira.vistas.CalculoController;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import sedira.ValidacionesGenerales;


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
    @FXML
    private TextField txtResult;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       txtEntradasCalculo.setText( DatosValidacionesCalculo.GetTextoProgeso());
     
    }    
    
     @FXML
    public void GuardarResultado()
    {
  
        DatosValidacionesCalculo.guardarCalculo();
    }
    @FXML
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
      
      /***************************************************/
       
      /* Se conviene el RESULTADO A BLOB */ 
        try {
          
            float resultado_temp_float = 489238;
            
      
            int bits = Float.floatToIntBits(resultado_temp_float);
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (bits & 0xff);
            bytes[1] = (byte) ((bits >> 8) & 0xff);
            bytes[2] = (byte) ((bits >> 16) & 0xff);
            bytes[3] = (byte) ((bits >> 24) & 0xff);

            Blob resultado_temp_blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            DatosValidacionesCalculo.finalizarCalculo(resultado_temp_blob);
          
            /* DE UN FLOAT COMO RESULTADO DEL CALCULO PASA A UN BLOB Y DESPUES A UN FLOAT */
        float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        
           String ResultString = String.valueOf(f);
            txtResult.setText(ResultString);
            
      
            
        } catch (SQLException ex) {
            Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
  
              
    }
    
}
