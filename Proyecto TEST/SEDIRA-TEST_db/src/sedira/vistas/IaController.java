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
import javafx.scene.control.TextArea;
import sedira.model.SimplePerceptron;

/**
 * FXML Controller class Clase controladora para el test de Inteligencia
 * Articifial.
 *
 * @author pablo
 */
public class IaController implements Initializable {

    @FXML
    TextArea txtInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtInfo.setText(null);
        SimplePerceptron p = new SimplePerceptron();
        //Salidas esperadas
        /**
         * Los objetivos son las salidas esperadas. En este caso para poder
         * validar correctamente el comportamiento, se utilizará el OR Lógico
         */
        double[] objetivos = {1, 1, 1, -1}; //O logico
        // Entradas X1,X2 y X3 (X3 = valor del umbral = -1
        double[][] entradas = {
            {1, 1, -1}, {1, -1, -1}, {-1, 1, -1}, {-1, -1, -1}
                /**
                 * {-1, -1, -1}, {-1, 1, -1}, {1, -1, -1}, {1, 1, -1}
                 */
        };

        p.setEntradas(entradas);
        p.setObjetivos(objetivos);
        p.inicializarPesos();
        p.entrenar();
        txtInfo.setText(p.getTextoResultado());

        p.imprimirPesos();
        //txtInfo.appendText(p.getTextoResultado());
    }

}
