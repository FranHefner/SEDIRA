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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sedira.DatosValidacionesCalculo;
import sedira.IDatosValidaciones;

import sedira.model.Organo;

/**
 * FXML Controller class Clase controladora para la interfaz de Organo/Tejido
 * del calculo.
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class PestañaOrganoController implements Initializable {

    @FXML
    private ChoiceBox choiceOrgano;
    @FXML
    private TextField txtIdOrgano;
    @FXML
    private TextField txtNombreOrgano;
    @FXML
    private TextField txtMasaOrgano;
    @FXML
    private TextField txtPhantomSeleccionado;

    // Declaracion de variables. 
    private Organo organoActual;
    int aux;

    private IDatosValidaciones dValidaciones;

    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
     //   if( MenuPrincipalController.TipoUsuario == "Cientifico")
        //  {
        dValidaciones = new DatosValidacionesCalculo();
      //  }
        // if (MenuPrincipalController.TipoUsuario == "Medico")
        //{
        //   dValidaciones = new DatosValidacionesCalculoBasico();
        //}
        /**
         * *************************************************************************
         */
        /**
         * OJO. metodo init y el metodo seleccion del choiceBox genera
         * indexOfBounds.
         *
         */
        initListaOrgano();

    }

    /**
     * Metodo que inicializa la lista de Organos dentro del ChoiceBox la lista
     * de organos se tomara a partir del phantom seleccionado.
     */
    @FXML
    public void initListaOrgano() {

        if (aux == 0) {
            ObservableList<String> listaOrganoString = FXCollections.observableArrayList();

            for (int i = 0; i < dValidaciones.getPhantomActual().getOrgano().size(); i++) {
                listaOrganoString.add(dValidaciones.getPhantomActual().getOrgano().get(i).getNombreOrgano());
            }
            choiceOrgano.setItems(listaOrganoString);
            txtPhantomSeleccionado.setText(dValidaciones.getPhantomActual().getPhantomNombre());
            aux = 1;
        }

    }

    /**
     * Metodo que se activa al seleccionar un phantom. Este llenara las tablas
     * de organos y de informacion del phantom.
     */
    @FXML
    public void seleccionarOrgano() {

        choiceOrgano.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue ov, Number value, Number newValue) {
                //Busco el Organo por el Indice del ChoiceBox
                int index = choiceOrgano.getSelectionModel().getSelectedIndex();
                //organoActual = ConsultasDB.ObtenerOrganos().get(index);
                //organoActual = FuncionesGenerales.phantomActual.getOrgano().get(index);
                organoActual = dValidaciones.getPhantomActual().getOrgano().get(index);
                //Al seleccionar el organo, se debe guardar el id en datosValidacionesCalculo.setOrgano
                //Completo tabla de Organos
                mostrarDetalleSeleccion(organoActual, txtNombreOrgano, txtMasaOrgano, txtIdOrgano);

                /* Selección del organo para el cálculo */
                dValidaciones.setOrgano(organoActual);
                /**
                 * *
                 */

            }
        });
    }

    /**
     * Este metodo setea en los textFields la informacion que el usuario
     * selecciona de la tabla de organos.
     *
     * @param organo es el organo seleccionado desde la tabla.
     * @param organoMasa Textfield a completar con la masa del organo.
     * @param organoNombre Texfield a completar con el nombre del organo.
     */
    @FXML
    public void mostrarDetalleSeleccion(Organo organo, TextField organoNombre, TextField organoMasa, TextField organoId) {
        //btnQuitarOrgano.setDisable(false);
        //btnQuitar.setDisable(false);
        if (organo != null) {
            //organoId.setText(String.valueOf(organo.getIdOrgano()));
            organoNombre.setText(organo.getNombreOrgano());
            organoMasa.setText(organo.getOrganMass().toString());

        } else {
            organoNombre.setText("");
            organoMasa.setText("");

        }
    }

}
