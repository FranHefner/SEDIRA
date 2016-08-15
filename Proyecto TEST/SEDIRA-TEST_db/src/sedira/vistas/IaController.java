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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.SimplePerceptron;

/**
 * FXML Controller class Clase controladora para el test de Inteligencia
 * Articifial.
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class IaController implements Initializable {

    @FXML
    private TextArea txtInfo;
    @FXML
    private TableView<Double> tblEntradas;
    @FXML
    private TableColumn<SimplePerceptron, Double> tbcX1;
    @FXML
    private TableColumn<SimplePerceptron, Double> tbcX2;
    @FXML
    private TableColumn<SimplePerceptron, Double> tbcX3;

     @FXML
     private TableView<Double> tblObjetivos;
     @FXML
     private TableColumn<SimplePerceptron, Double> tbcObjetivo;
     @FXML
     private TableView<Double> tblPeso;
     @FXML
     private TableColumn<SimplePerceptron, Double> tbcPeso;
     /*@FXML
     private TableView tblPesoFinal;
     @FXML
     private TableColumn<SimplePerceptron, Double> tbcPesoFinal;*/

    @FXML
    private TextField txtPasos;
    @FXML
    private Button btnNuevosPesos;
    @FXML
    private Button btnRecalcular;
    @FXML
    private Button btnCalcular;
    @FXML
    private Button btnCerrar;

   
    //instancia del perceptron
    SimplePerceptron p = new SimplePerceptron();
    //Entradas X1,X2 y X3 (X3 = valor del umbral = -1
        double[][] entradas = {
            {1, 1, -1}, {1, -1, -1}, {-1, 1, -1}, {-1, -1, -1}
        };
    //Lista Observable para el manejo de Variables de entrada
    private ObservableList<Double> pesosData = FXCollections.observableArrayList();
      
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtInfo.setText(null);
       
        
        //Salidas esperadas
        /**
         * Los objetivos son las salidas esperadas. En este caso para poder
         * validar correctamente el comportamiento, se utilizará el OR Lógico
         */
        double[] objetivos = {1, 1, 1, -1}; //O logico
        
        /**
         * Tabla de valores para AND lógico. {-1, -1, -1}, {-1, 1, -1}, {1, -1,
         * -1}, {1, 1, -1}
         */

        
        
        /**
         * Es posible agregar en las entradas los objetos tomados para un
         * cálculo realizado en SEDIRA. Para ellos es necesario contar con los
         * objetivos verdaderos para la fórmula matemática que se encarga de
         * realziar el cálculo.
         *
         */
        p.setEntradas(entradas);
        p.setObjetivos(objetivos);
        p.inicializarPesos();
        p.entrenar();
        txtInfo.setText(p.getTextoResultado());

        p.imprimirPesos();
        txtPasos.setText(String.valueOf(p.getPasos()));
       
    }

    

  

    @FXML
    public void btnCerrar_click(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

}
