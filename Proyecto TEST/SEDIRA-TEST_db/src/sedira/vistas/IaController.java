/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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

    //Instancia del perceptron
    SimplePerceptron p = new SimplePerceptron();

    //Entradas X1,X2 y X3 (X3 = valor del umbral = -1
    private double[][] entradas = {
        {1, 1, -1},
        {1, -1, -1},
        {-1, 1, -1},
        {-1, -1, -1}
    };

    /**
     * Tabla de valores para AND lógico. {-1, -1, -1}, {-1, 1, -1}, {1, -1, -1},
     * {1, 1, -1}
     */
    @FXML
    private TextArea txtInfo;
    @FXML
    private TextArea txtEntradas;
    @FXML
    private TextArea txtObjetivos;
    @FXML
    private TextArea txtPesosInicio;
    @FXML
    private TextArea txtPesosFinales;

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
    @FXML
    private Slider SliTasa;
    @FXML
    private TextField txtTasa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtTasa.setText(String.valueOf(SliTasa.getValue()));
        txtInfo.setText(null);
        btnRecalcular.setDisable(true);
        
       
        //Salidas esperadas
        /**
         * Los objetivos son las salidas esperadas. En este caso para poder
         * validar correctamente el comportamiento, se utilizarán las salidas
         * correspondientes para la entrada del OR Lógico
         */
        double[] objetivos = {1, 1, 1, -1}; //O logico

        /**
         * Es posible agregar en las entradas los objetos tomados para un
         * cálculo realizado en SEDIRA. Para ellos es necesario contar con los
         * objetivos verdaderos para la fórmula matemática que se encarga de
         * realizar el cálculo.
         *
         */
        p.setEntradas(entradas);
        llenarTxtEntrada();

        p.setObjetivos(objetivos);
        llenarTxtVector(p.getObjetivos(), txtObjetivos);

        p.inicializarPesos();

        llenarTxtVector(p.getPesos(), txtPesosInicio);
        SliTasa.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed (ObservableValue<? extends Number> observable, 
                    Number oldValue, Number newValue){
                txtTasa.setText(String.valueOf(newValue));
            }
        });
    }

    /**
     * Método que completa el TextArea de las entradas.
     */
    private void llenarTxtEntrada() {
        String texto = "";
        for (int i = 0; i < entradas.length; i++) {
            for (int j = 0; j < entradas[0].length; j++) {
                texto += entradas[i][j] + "\t";
            }
            texto += "\n";
        }
        txtEntradas.setText(texto);

    }

    /**
     * Método que completa los TxtArea . Este metodo es genérico para text areas
     * y arreglos unidimensionales.
     *
     * @param vector
     * @param txtDestino
     */
    private void llenarTxtVector(double vector[], TextArea txtDestino) {
        String texto = "";
        for (int i = 0; i < vector.length; i++) {
            texto += vector[i] + "\n";
        }
        txtDestino.setText(texto);
    }

    /**
     * Método para el evento del botón cerrar.
     *
     * @param event
     */
    @FXML
    public void btnCerrar_click(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método para el comportamiento del botón Nuevos Pesos.
     *
     * @param event
     */
    @FXML
    public void btnNuevosPesos(ActionEvent event) {
        p.inicializarPesos();
        llenarTxtVector(p.getPesos(), txtPesosInicio);
        btnCalcular.setDisable(false);
    }

    /**
     * Método para el comportamiento del botón Calcular.
     *
     * @param event
     */
    @FXML
    public void btnCalcular(ActionEvent event) {
        p.setTextoResultado("");
        p.entrenar();
        p.setTASA_APRENDIZAJE(SliTasa.getValue());
        llenarTxtVector(p.getPesos(), txtPesosFinales);
        //llenarTxtVector(p.getPesos(),txtPesosInicio);
        txtInfo.setText(p.getTextoResultado());
        txtPasos.setText(String.valueOf(p.getPasos()));
        btnRecalcular.setDisable(false);
        btnCalcular.setDisable(true);

    }

    /**
     * Método para el comportamiento del botón Recalcular.
     *
     * @param event
     */
    @FXML
    public void btnReCalcular(ActionEvent event) {
       // txtInfo.setText("");

        llenarTxtVector(p.getPesos(), txtPesosInicio);

        llenarTxtVector(p.getPesos(), txtPesosFinales);
        txtInfo.setText(p.getTextoResultado());
        txtPasos.setText(String.valueOf(p.getPasos()));
        btnCalcular.setDisable(false);
        btnRecalcular.setDisable(true);
    }

}
