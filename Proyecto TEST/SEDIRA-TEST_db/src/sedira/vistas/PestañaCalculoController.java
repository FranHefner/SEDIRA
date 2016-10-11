/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

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
import sedira.DatosValidacionesCalculoBasico;
import sedira.IDatosValidaciones;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JLabel;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import sedira.FuncionesGenerales;
import sedira.MathJS;
import sedira.model.ValorDescripcion;
import sedira.model.VariableCalculo;
import sedira.model.MyCanvas;

/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PestañaCalculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea txtEntrada;
    @FXML
    private Button btnCalcular;
    @FXML

    private TextField txtResult;
    @FXML
    private Label lblOrgano;
    @FXML
    private TextField txtOrganoMasa;

    private IDatosValidaciones dValidaciones;
    @FXML
    private TableView<ValorDescripcion> griValorDescripcionPhantom;
    @FXML
    private TableView<ValorDescripcion> griValorDescripcionRadionuclido;
    @FXML
    private TableView<VariableCalculo> griVariables;

    @FXML
    private Pane pnFuncion;
    @FXML
    private TableColumn<ValorDescripcion, Double> clVdValorPhantom;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcionPhantom;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidadPhantom;

    @FXML
    private TableColumn<ValorDescripcion, Double> clVdValorRadionuclido;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcionRadionuclido;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidadRadionuclido;

    @FXML
    private TableColumn<VariableCalculo, String> clDescripcionVariable;
    @FXML
    private TableColumn<VariableCalculo, Double> clValorVariable;
    @FXML
    private TableColumn<VariableCalculo, String> clLetraVariable;
    @FXML
    private Button btnGuardar;

    private String formulEnTex;
    private String formulaCalculo;

    boolean graficaOK = false;
    boolean calculoOK = false;

    private ValorDescripcion variableSeleccionada = null;
    private boolean grillaSeleccionada = false;
    private char letra = 'A';
    private ObservableList<VariableCalculo> listaVariables = FXCollections.observableArrayList();
    private MathJS math = MenuPrincipalController.math;
    private MyCanvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Se activa la funcion para realizar la vista previa;
        txtEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
            RealizarCalculo(newValue);
        });
        // Se realiza una pequeña pre-carga para cargar archivos a memoria
        txtEntrada.setText("0");
        txtEntrada.setText("");


        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
        if (MenuPrincipalController.TipoUsuario == "Cientifico") {
            dValidaciones = new DatosValidacionesCalculo();
        }
        if (MenuPrincipalController.TipoUsuario == "Medico") {
            dValidaciones = new DatosValidacionesCalculoBasico();
        }
        /**
         * *************************************************************************
         */

        //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValorPhantom.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcionPhantom.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidadPhantom.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
        // FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griValorDescripcionPhantom);

        // Muestro las propiedades del phantom selecionado
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(dValidaciones.getPhantomActual().getPropiedades(), griValorDescripcionPhantom);
        // Muestro el nombre del órgano Seleccionado

        lblOrgano.setText(dValidaciones.getOrganoActual().getNombreOrgano());

        griValorDescripcionPhantom.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionPropiedadPhantom(newValue));

        // Muestro las propiedades del radionuclido seleccionado
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValorRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcionRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidadRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());

        //Completo tabla de Info Radionuclido
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion(dValidaciones.getRadionuClidoActual().getPropiedades(), griValorDescripcionRadionuclido);

        griValorDescripcionRadionuclido.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionPropiedadRadionuclido(newValue));

        //Inicializo la tabla de Variables, correspondiente a la informacion de las variables . 
        clDescripcionVariable.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());

        clValorVariable.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());

        clLetraVariable.setCellValueFactory(
                cellData -> cellData.getValue().variableProperty());

        txtOrganoMasa.setText(dValidaciones.getOrganoActual().getOrganMass().toString());

    }

    @FXML
    public void seleccionPropiedadPhantom(ValorDescripcion datoSeleccionado) {

        variableSeleccionada = datoSeleccionado;

    }

    @FXML
    public void seleccionPropiedadRadionuclido(ValorDescripcion datoSeleccionado) {

        variableSeleccionada = datoSeleccionado;

    }

    @FXML
    public void eliminarVariable() {

        if (griVariables.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Falta selección");
            alert.setHeaderText("Atención!");
            alert.setContentText("Falta seleccionar la propiedad que se desea eliminar");
            alert.show();
        } else {
            griVariables.getItems().remove(griVariables.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    public void seleccionMasa() {

        variableSeleccionada = new ValorDescripcion(0, "Masa órgano/tejido", dValidaciones.getOrganoActual().getOrganMass(), "grs");

    }

    @FXML
    public void agregarVariables() {

        if (variableSeleccionada == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Falta selección");
            alert.setHeaderText("Atención!");
            alert.setContentText("Falta seleccionar la propiedad que se desea agregar");
            alert.show();

        } else if (letra == '[') {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Límite Variables");
            alert.setHeaderText("Atención!");
            alert.setContentText("No es posible agregar más variables al cálculo");
            alert.show();
        } else {
            VariableCalculo nuevaVariable = new VariableCalculo(variableSeleccionada.getId(), variableSeleccionada.getDescripcion(), variableSeleccionada.getValor(), String.valueOf(letra++));

            listaVariables.add(nuevaVariable);

            //griVariables.refresh();
            griVariables.setItems(listaVariables);

            griValorDescripcionRadionuclido.getSelectionModel().clearSelection();
            griValorDescripcionPhantom.getSelectionModel().clearSelection();
            variableSeleccionada = null;
        }

    }

    @FXML
    public void GuardarResultado() {

        if (calculoOK == true) {
            /* Se convierte el RESULTADO A BLOB */
            try {

                float resultado_temp_float = Float.valueOf(txtResult.getText());

                int bits = Float.floatToIntBits(resultado_temp_float);
                byte[] bytes = new byte[4];
                bytes[0] = (byte) (bits & 0xff);
                bytes[1] = (byte) ((bits >> 8) & 0xff);
                bytes[2] = (byte) ((bits >> 16) & 0xff);
                bytes[3] = (byte) ((bits >> 24) & 0xff);

                Blob resultado_temp_blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                dValidaciones.finalizarCalculo(resultado_temp_blob, formulaCalculo, formulEnTex, listaVariables);

                // DE UN FLOAT COMO RESULTADO DEL CALCULO PASA A UN BLOB Y DESPUES A UN FLOAT 
                float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();

                String ResultString = String.valueOf(f);
                //txtResult.setText(ResultString);

            } catch (SQLException ex) {
                Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("No se pudo convertir a blob");
            }
        }

        dValidaciones.guardarCalculo();

    }

    @FXML
    public void RealizarCalculo(String NuevaFormula) {

        txtResult.setText("");
        pnFuncion.getChildren().clear();
        btnGuardar.setDisable(true);

        //Si no es vacio, continua
        if (!"".equals(NuevaFormula)) {
            try {
                /* SE GRAFICA LA FÓRMULA */
                formulaCalculo = NuevaFormula;

                for (VariableCalculo Variable : listaVariables) {

                    formulaCalculo = formulaCalculo.replace(Variable.getvariable(), Variable.getValor().toString());

                }

                formulEnTex = math.Ejecutar(" math.parse('" + formulaCalculo + "').toTex() ");

                System.out.println(formulEnTex);

                canvas = new MyCanvas(formulEnTex);

                canvas.widthProperty().bind(pnFuncion.widthProperty());
                canvas.heightProperty().bind(pnFuncion.heightProperty());

                pnFuncion.getChildren().add(canvas);
                graficaOK = true;

            } catch (ScriptException ex) {
                System.out.println("No se pudo graficar la fórmula");
                txtResult.setText("Fórmula incompleta o errónea");;
                graficaOK = false;
            }

            if (graficaOK == true) {
                /* SE CALCULA LA FÓRMULA */
                try {

                    txtResult.setText(math.eval(formulaCalculo));

                    calculoOK = true;
                    btnGuardar.setDisable(false);
                } catch (ScriptException ex) {
                    // System.out.println("No se pudo calcular la fórmula");
                    txtResult.setText("Fórmula no soportada o faltan definir variables.");
                    calculoOK = false;
                }
            }
        } else {
        }

    }
}
