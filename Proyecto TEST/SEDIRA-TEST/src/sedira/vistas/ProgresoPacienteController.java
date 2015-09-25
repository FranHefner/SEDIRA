/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import com.sun.javaws.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * FXML Controller class
 *
 * @author Fran
 */
public class ProgresoPacienteController implements Initializable {
    @FXML 
    private Pane pnlImagen;
    @FXML
    private LineChart lchProgreso;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
       final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
       
                          
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Pepe Perez");
        
        series1.getData().add(new XYChart.Data("01/09/2015", 0));
        series1.getData().add(new XYChart.Data("05/09/2015", 20));
        series1.getData().add(new XYChart.Data("10/09/2015", 50));
        series1.getData().add(new XYChart.Data("20/09/2015", 30));
        series1.getData().add(new XYChart.Data("01/10/2015", 60));
        series1.getData().add(new XYChart.Data("05/10/2015", 65));
        series1.getData().add(new XYChart.Data("07/10/2015", 65));
        
        
         XYChart.Series series2 = new XYChart.Series();
        series2.setName("Juan Martinez");
        
        series2.getData().add(new XYChart.Data("01/09/2015", 0));
        series2.getData().add(new XYChart.Data("05/09/2015", -10));
        series2.getData().add(new XYChart.Data("10/09/2015", -15));
        series2.getData().add(new XYChart.Data("20/09/2015", -5));
        series2.getData().add(new XYChart.Data("01/10/2015", 0));
        series2.getData().add(new XYChart.Data("05/10/2015", 5));
        series2.getData().add(new XYChart.Data("07/10/2015", 8));
        
        
        
        
      lchProgreso.getData().addAll(series1,series2);
     
     
    }   
   
   
    
}
