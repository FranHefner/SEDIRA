/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class HistorialSEDIRAController implements Initializable {
      @FXML
    private ScatterChart schHistorialCalculos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
               
         final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0,24,1);
       final ScatterChart<String,Number> lineChart = 
                new ScatterChart<String,Number>(xAxis,yAxis);
       
       
        
         
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Martin Martinez");
        
        series1.getData().add(new XYChart.Data("01/09/2015", 0));
        series1.getData().add(new XYChart.Data("05/09/2015", 1));
        series1.getData().add(new XYChart.Data("10/09/2015", 5));       
        series1.getData().add(new XYChart.Data("01/10/2015", 16));
        series1.getData().add(new XYChart.Data("05/10/2015", 23));
   
                
        
      schHistorialCalculos.getData().addAll(series1);
    }    
    
}
