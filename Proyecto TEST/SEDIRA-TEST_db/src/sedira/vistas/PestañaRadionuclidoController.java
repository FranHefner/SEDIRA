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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;
import sedira.DatosValidacionesCalculo;
import sedira.DatosValidacionesCalculoBasico;
import sedira.FuncionesGenerales;
import sedira.IDatosValidaciones;
import sedira.model.IRadionuclidoDAO;
import sedira.model.RadionuclidoDAOsql;

/**
 * FXML Controller class Clase controladora de la interfaz de Radionuclidos
 * dentro del proceso de calculo. En esta instancia, solo es posible seleccionar
 * entre los radionuclidos creados con anterioridad en el Administrador de
 * radionuclidos.
 *
 * @author Quelin Pablo, Hefner Francisco.
 */
public class PestañaRadionuclidoController implements Initializable {

    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TableView<ValorDescripcion> griInfoRadNuclido;
    @FXML
    private TableColumn<ValorDescripcion, Double> clVdValor;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidad;

    @FXML
    private ChoiceBox choiceRadionuclido;

    //Objeto de tipo Radionuclido auxiliar. 
    private Radionuclido radionuclidoActual;
    //Objeto Lista Radionuclidos auxiliar. 
    public static ObservableList<Radionuclido> ListaRadionuclido = FXCollections.observableArrayList();
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IRadionuclidoDAO rad = new RadionuclidoDAOsql();
    //Objeto de tipo ValorDescripcion auxiliar. 
    private ValorDescripcion itemRadNuclido;
    
    private IDatosValidaciones dValidaciones;

    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
        if (MenuPrincipalController.TipoUsuario == "Cientifico") {
            dValidaciones = new DatosValidacionesCalculo();
        }
        if (MenuPrincipalController.TipoUsuario == "Medico") {
            dValidaciones = new DatosValidacionesCalculoBasico();
        }

        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValor.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidad.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        // Limpieza de los detalles de Radionuclido. 
        showDetalleRadionuclido(null);

        //Inicializo la lista de Radionuclidos  para el ChoiceBox
        initListaRadNuclido();
        choiceRadionuclido.setTooltip(new Tooltip("Seleccione el Radionuclido"));
    }

    /**
     * Metodo que inicializa la lista de Radionuclidos dentro del ChoiceBox
     */
    @FXML
    public void initListaRadNuclido() {
        //Inicializo la lista de radionuclidos con la informacion contenida en la base de datos. 
        ListaRadionuclido = rad.obtenerListaRadNuclidoCompletos();
        //Lista auxiliar para el manejo de los nombres de los radionuclidos. 
        ObservableList<String> listaStringRadionuclido = FXCollections.observableArrayList();

        for (Radionuclido listaRadNuclido1 : ListaRadionuclido) {
            listaStringRadionuclido.add(listaRadNuclido1.getNombreRadNuclidoProperty().getValue());
        }
        //Asigno la lista de los nombres de los Radionuclidos al ChoiceBox
        choiceRadionuclido.setItems(listaStringRadionuclido);
    }

    /**
     * Metodo que muestra el detalle de los radionuclidos en la tabla tipo
     * Valor-Descripcion.
     *
     * @param infoRadionuclido lista de todos los atributos del radionuclido.
     */
    @FXML
    public void showDetalleRadionuclido(ObservableList<ValorDescripcion> infoRadionuclido) {
        //Aca se utiliza la tabla Descripcion - Valor. 
        griInfoRadNuclido.setItems(infoRadionuclido);

    }

    /**
     * Metodo que se activa al seleccionar un phantom. Este llenara las tablas
     * de organos y de informacion del phantom.
     */
    @FXML
    public void seleccionarRadionuclido() {

        choiceRadionuclido.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue ov, Number value, Number newValue) {
                //Busco el Radionuclido por el Indice del ChoiceBox
                int index = choiceRadionuclido.getSelectionModel().getSelectedIndex();
                //System.out.print(index);
                radionuclidoActual = ListaRadionuclido.get(index);
                //Completo tabla de Info Radionuclido
                FuncionesGenerales.mostrarDetalleTablaValorDescripcion(rad.obtenerInfoRadNuclido(radionuclidoActual), griInfoRadNuclido);
                /*Asigna radionuclido al calculo */
                dValidaciones.setRadionuclido(radionuclidoActual);
                /**
                 * **********************
                 */

            }
        });

    }

    /**
     * Método que se activa al seleccionar un item de la tabla de informacion
     * del radionuclido.
     */
    @FXML
    public void seleccionInfoRadNuclido() {
        itemRadNuclido = griInfoRadNuclido.getSelectionModel().getSelectedItem();
        txtPropiedad.setText(itemRadNuclido.getDescripcion());
        txtValor.setText(String.valueOf(itemRadNuclido.getValor()));
        txtUnidad.setText(itemRadNuclido.getUnidad());    
        dValidaciones.setRadionuclido(radionuclidoActual);
    }

}
