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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sedira.DatosValidacionesCalculo;
import sedira.IDatosValidaciones;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javax.script.ScriptException;
import sedira.FuncionesGenerales;
import sedira.MathJS;
import sedira.ValidacionesGenerales;
import sedira.model.Formula;
import sedira.model.FormulaDAOsql;
import sedira.model.IFormulaDAO;
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
    @FXML
    private TableView<ValorDescripcion> griValorDescripcionPhantom;
    @FXML
    private TableView<ValorDescripcion> griValorDescripcionRadionuclido;
    @FXML
    private TableView<VariableCalculo> griVariables;

    @FXML
    private Pane pnFuncion;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdValorPhantom;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcionPhantom;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidadPhantom;

    @FXML
    private TableColumn<ValorDescripcion, String> clVdValorRadionuclido;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdDescripcionRadionuclido;
    @FXML
    private TableColumn<ValorDescripcion, String> clVdUnidadRadionuclido;

    @FXML
    private TableColumn<VariableCalculo, String> clDescripcionVariable;
    @FXML
    private TableColumn<VariableCalculo, String> clValorVariable;
    @FXML
    private TableColumn<VariableCalculo, String> clLetraVariable;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnGuardarFormula;
    @FXML
    private Button btnAgregarPropiedad;
    @FXML
    private Button btnSacarPropiedad;
    @FXML
    private ComboBox cbFormulas;
    @FXML
    private Button btnEliminarFormula;

    private String formulEnTex;
    private String formulaOriginal;
    private String formulaCalculo;
    private String resultadoCalculo;

    boolean graficaOK = false;
    boolean calculoOK = false;
    boolean guardadoOK = false;

    private ValorDescripcion variableSeleccionada = null;
    private IDatosValidaciones dValidaciones;
    private IFormulaDAO iFormulas;
    private boolean grillaSeleccionada = false;
    List<Formula> FormulasActuales;
    public char letra = 'A';
    private ObservableList<VariableCalculo> listaVariables = FXCollections.observableArrayList();
    private MathJS math = MenuPrincipalController.math;
    private MyCanvas canvas;
    private IFormulaDAO formu = new FormulaDAOsql();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Se activa la funcion para realizar la vista previa;
        txtEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
            RealizarCalculo(newValue);
        });
        // Se realiza una pequeña pre-carga para cargar archivos a memoria
        ReiniciarTextoEntrada();

        /*intFormulas = new FormulaDAOsql();
         FormulasActuales = intFormulas.getFormulas();

         for (int i = 0; i < FormulasActuales.size(); i++) {
         cbFormulas.getItems().add(FormulasActuales.get(i).getNombre());

         }*/
        llenarFormulas();
        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */

        dValidaciones = new DatosValidacionesCalculo();

        if (MenuPrincipalController.TipoUsuario == "Cientifico") {

            cbFormulas.setDisable(false);
            btnEliminarFormula.setDisable(false);
        }
        if (MenuPrincipalController.TipoUsuario == "Medico") {

            btnSacarPropiedad.setDisable(true);
            btnAgregarPropiedad.setDisable(true);
            btnEliminarFormula.setDisable(true);
        }
        /**
         * *************************************************************************
         */

        //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValorPhantom.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty());
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
                cellData -> cellData.getValue().valorProperty());
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
                cellData -> cellData.getValue().valorProperty());

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

    public void ReiniciarTextoEntrada() {
        String TextoOriginal = txtEntrada.getText();
        txtEntrada.setText("0");
        txtEntrada.setText("");
        txtEntrada.setText(TextoOriginal);

    }

    @FXML
    public void seleccionFormula() {

        int IndiceFormula = -1;

        if (cbFormulas.getSelectionModel().isEmpty()) {
            // NO SE SELECCIONO UNA FORMULA;
        } else {
            boolean FaltanPropiedades = false;
            boolean FaltanDefinirPropiedades = false;
            String formulaSelecionada = cbFormulas.getValue().toString();

            for (int i = 0; i < FormulasActuales.size(); i++) {
                if (formulaSelecionada.equals(FormulasActuales.get(i).getNombre())) {
                    listaVariables = iFormulas.getPropiedadesFormula(FormulasActuales.get(i).getId_Historial(), false);
                    IndiceFormula = i;
                }

            }

            List<String> ListaValores = new ArrayList<String>();
            int indexVariables = -1;
            for (VariableCalculo vc : listaVariables) {
                indexVariables++;
                ListaValores.clear();
                for (ValorDescripcion variablePhantom : dValidaciones.getPhantomActual().getPropiedades()) {
                    if (vc.getDescripcion().equals(variablePhantom.getDescripcion())) {

                        ListaValores.add(variablePhantom.getValor());
                    }
                }
                for (ValorDescripcion variableRadionuclido : dValidaciones.getRadionuClidoActual().getPropiedades()) {
                    if (vc.getDescripcion().equals(variableRadionuclido.getDescripcion())) {

                        ListaValores.add(variableRadionuclido.getValor());
                    }
                }

                if (ListaValores.size() > 1) {

                    TextInputDialog dialog = new TextInputDialog("");

                    String MuestraVariables = "";
                    for (int i = 1; i < ListaValores.size() + 1; i++) {
                        MuestraVariables += "" + i + ": " + ListaValores.get(i - 1) + "\n";
                    }

                    dialog.setTitle("Confirmar Valor");
                    dialog.setHeaderText("Se encontró más de un valor para la propiedad '" + vc.getDescripcion() + "'. Seleccione el valor deseado \n" + MuestraVariables);

                    dialog.setContentText("Opción deseada:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {

                        if (ValidacionesGenerales.ValidarNumero(result.get())) {
                            int index = Integer.parseInt(result.get());

                            listaVariables.get(indexVariables).setValor(ListaValores.get(index - 1));
                        } else {
                            FaltanDefinirPropiedades = true;
                        }

                    } else {
                        FaltanDefinirPropiedades = true;

                    }

                } else if (ListaValores.size() == 1) {
                    listaVariables.get(indexVariables).setValor(ListaValores.get(0));
                } else {

                    FaltanPropiedades = true;
                }
            }

            if (FaltanPropiedades == true) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Faltan propiedades");
                alert.setHeaderText("Atención!");
                alert.setContentText("La fórmula seleccionada requiere propiedades que no están disponibles en los elementos seleccionados. \n"
                        + "Por favor, seleccione otra fórmula...  ");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                }
                cbFormulas.getSelectionModel().clearSelection();
                listaVariables.clear();
               
                  griVariables.setItems(listaVariables);
                

            } else if (FaltanDefinirPropiedades == true) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Faltan definir propiedades");
                alert.setHeaderText("Atención!");
                alert.setContentText("La fórmula seleccionada encontró más de una propiedad con un mismo nombre entre los elementos seleccionados \n"
                        + "Por favor, seleccione nuevamente la fórmula y elija un valor para dicha propiedad ");

                
                 cbFormulas.getSelectionModel().clearSelection();
                 listaVariables.clear();
                 
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                }

               
            } else {
                if (IndiceFormula != -1) {
                    griVariables.setItems(listaVariables);
                    txtEntrada.setText(FormulasActuales.get(IndiceFormula).getFormula_mat());
                } else {
                    cbFormulas.getSelectionModel().clearSelection();
                }

                ReiniciarTextoEntrada();
            }

        }

    }

    
    @FXML
    public void eliminarFormula() {
        
        if (cbFormulas.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Falta selección");
            alert.setHeaderText("Atención!");
            alert.setContentText("Falta seleccionar la fórmula que se desea eliminar");
            alert.show();
        } else {
            formu.eliminarFormula(cbFormulas.getSelectionModel().getSelectedItem().toString());
            cbFormulas.getItems().clear();
            llenarFormulas();
      
        }

        
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
        ReiniciarTextoEntrada();
    }

    @FXML
    public void seleccionMasa() {

        variableSeleccionada = new ValorDescripcion(0, "Masa órgano/tejido", dValidaciones.getOrganoActual().getOrganMass().toString(), "grs");

    }

    @FXML
    public void GuardarFormula() {

        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Guardado de Fórmula");
        dialog.setHeaderText("La fórmula se usará como plantilla para nuevos cálculos.\n¿Qué nombre desea utilizar?");
        dialog.setContentText("Por favor, ingrese el nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {

            formu.setFormula(result.get(), txtEntrada.getText(), dValidaciones.getIdCalgulo());
        }
        cbFormulas.getItems().clear();
        llenarFormulas();

    }

    void encontrarVariableLibre() {
        letra = 'A';
        boolean encontrada = true;
        int cantidad = 0;
        boolean variableLibre = false;

        for (int i = 0; i < griVariables.getItems().size(); i++) {
            if (variableLibre == false) {
                encontrada = false;

                for (int j = 0; j < griVariables.getItems().size(); j++) {

                    if (griVariables.getItems().get(j).getVariable().equals(String.valueOf(letra))) {
                        encontrada = true;

                    }

                }
                if (encontrada == true) {
                    letra++;
                } else {
                    variableLibre = true;
                }
            }

        }

    }

    @FXML
    public void agregarVariables() {

        encontrarVariableLibre();

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
            ReiniciarTextoEntrada();
        }

    }

    @FXML
    public void GuardarResultado() throws IOException {

        dValidaciones.finalizarCalculo(resultadoCalculo, formulaOriginal, formulEnTex, listaVariables);

        guardadoOK = dValidaciones.guardarCalculo();

        if (guardadoOK) {

            if (MenuPrincipalController.TipoUsuario.equals("Cientifico")) {
                btnGuardarFormula.setDisable(false);
            }

        } else {
            btnGuardarFormula.setDisable(true);
        }

    }

    @FXML
    public void RealizarCalculo(String NuevaFormula) {

        btnGuardarFormula.setDisable(true);
        txtResult.setText("");
        pnFuncion.getChildren().clear();
        btnGuardar.setDisable(true);

        //Si no es vacio, continua
        if (!"".equals(NuevaFormula)) {
            try {
                /* SE GRAFICA LA FÓRMULA */
                formulaOriginal = NuevaFormula;
                formulaCalculo = NuevaFormula;

                for (VariableCalculo Variable : listaVariables) {

                    formulaCalculo = formulaCalculo.replace(Variable.getVariable(), Variable.getValor());

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

                    resultadoCalculo = math.eval(formulaCalculo);
                    txtResult.setText(resultadoCalculo);

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

    private void llenarFormulas() {

        iFormulas = new FormulaDAOsql();
        FormulasActuales = iFormulas.getFormulas();

        for (int i = 0; i < FormulasActuales.size(); i++) {
            cbFormulas.getItems().add(FormulasActuales.get(i).getNombre());

        }
    }
}
