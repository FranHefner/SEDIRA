/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IRadionuclidoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Radionuclido;
import sedira.model.RadionuclidoDAOsql;
import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class Clase controladora de la interfaz Editar/Nuevo
 * Radionuclido.
 *
 * @author Quelin Pablo, Hefner Francisco.
 */
public class AbmRadionuclidoController implements Initializable {

    @FXML
    private TextField txtRadNuclidoNombre;
    @FXML
    private TextField txtIdRadNuclido;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;

    @FXML
    private Button btnLimpiarValores;

    //******************** variables 
    //Objeto Lista de radionuclidos  auxiliar. 
    private ObservableList<ValorDescripcion> listaAtributoRadNuclido = FXCollections.observableArrayList();
    //Objeto radionuclido auxiliar. 
    private Radionuclido radionuclido;
    private ValorDescripcion itemRadionuclido;
    // Stage aux
    private Stage dialogStage;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Instancia de objeto tipo IPacienteDAO. Se inicializa como PacienteDAOsql.  
    private IRadionuclidoDAO rad = new RadionuclidoDAOsql();
    //Instancia de objeto tipo IValorDescripcionDAO. Se inicializa como ValorDescripcionDAOsql.  
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();

    /**
     * Inicializa la clase initialize del controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Setea el Radionúclido a editar.
     *
     * @param radionuclido a editar.
     */
    public void setRadionuclido(Radionuclido radionuclido) {
        this.radionuclido = radionuclido;

        if (radionuclido.getIdRadNuclido() != -1) {
            /**
             * Obtiente el Radionuclido seleccionado en la busqueda del
             * formulario radionuclido.fxml
             */
            txtIdRadNuclido.setText(String.valueOf(radionuclido.getIdRadNuclido()));
            txtRadNuclidoNombre.setText(radionuclido.getNombreRadNuclido());
            btnLimpiarValores.setDisable(true);
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Prendo los botones

        } else {
            btnLimpiarValores.setDisable(true);
            txtIdRadNuclido.setDisable(true);
            //Cambio Nombre en el formulario. 
            this.dialogStage.setTitle("Crear un Radionúclido");
            //apago los textFields.
            txtPropiedad.setDisable(true);
            txtValor.setDisable(true);
            txtUnidad.setDisable(true);
            //Prendo los botones. 

        }
    }

    /**
     * Método SetTer para el item de radionúclido que se selecciono.
     *
     * @param itemRadionuclido
     */
    public void setItemRadionuclido(ValorDescripcion itemRadionuclido) {
        Radionuclido radionuclidoActual = FuncionesGenerales.getRadioNuclidoActual();
        this.itemRadionuclido = itemRadionuclido;
        txtIdRadNuclido.setText(String.valueOf(radionuclidoActual.getIdRadNuclido()));
        txtIdRadNuclido.setDisable(true);
        txtRadNuclidoNombre.setText(radionuclidoActual.getNombreRadNuclido());
        txtRadNuclidoNombre.setEditable(false);
        txtPropiedad.setText(itemRadionuclido.getDescripcion());
        txtValor.setText(itemRadionuclido.getValor().toString());
        txtUnidad.setText(itemRadionuclido.getUnidad());
    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     */
    @FXML
    public void btnGuardarDatos() throws SQLException {
        // TODO: VALIDACIONES.  

        if (validarDatosEntrada()) {
            switch (dialogStage.getTitle()) {
                case "Crear un Radionúclido":
                    radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                    radionuclido.setPropiedades(listaAtributoRadNuclido);
                    break;
                case "Modificar nombre del Radionúclido":
                    radionuclido.setNombreRadNuclido(txtRadNuclidoNombre.getText());
                    break;
                case "Modificar Items":
                    itemRadionuclido.setDescripcion(txtPropiedad.getText());
                    itemRadionuclido.setUnidad(txtUnidad.getText());
                    itemRadionuclido.setValor(Double.parseDouble(txtValor.getText()));
                    break;
            }
            //Si las validaciones son correctas se guardan los datos
            guardarDatos = true;
            dialogStage.close();

        }

    }

    /**
     * Método que retorna si el usuario presiono el boton Guardar Datos.
     *
     * @return guardarDatos
     */
    public boolean isGuardarDatosClicked() {
        return this.guardarDatos;
    }

    /**
     * Método que se llama al presionar el boton cancelar.
     */
    @FXML
    public void btnCancel_click() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        switch (dialogStage.getTitle()) {
            case "Crear un Radionúclido":

                alert.setTitle("Cancelar creación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la creación del radionúclido? ");
                break;
            case "Modificar nombre del Radionúclido":

                alert.setTitle("Cancelar modificación del radionúclido");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del radionúclido? ");
                break;
            case "Modificar Items":
                alert.setTitle("Cancelar modificación del radionúclido");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del radionúclido? ");
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dialogStage.close();
        } else {

        }

    }

    /**
     * Método para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {
        txtUnidad.setText("");
        txtPropiedad.setText("");
        txtValor.setText("");
    }

    /**
     * Método que se encarga de la validación de los datos ingresados por el
     * usuario.
     *
     * @return
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {
        String mensajeError = "Existe un error en los siguientes campos: \n";
        String valor = txtValor.getText();
        String propiedad = txtPropiedad.getText();
        String unidad = txtUnidad.getText();
        String nombreRadNuclido = txtRadNuclidoNombre.getText();

        if ("Crear un Radionúclido".equals(this.dialogStage.getTitle()) || "Modificar nombre del Radionúclido".equals(this.dialogStage.getTitle())) {
            // Solo valido
            // campo en NULL y Campo con logitud 0
            if (txtRadNuclidoNombre.getText() == null || txtRadNuclidoNombre.getText().length() == 0) {
                mensajeError += "Debe agregar un nombre para el radionúclido";
                if (!ValidacionesGenerales.ValidarNombreConEspacios(nombreRadNuclido)) {
                    mensajeError += "Nombre del radionúclido inválido";
                } else if (rad.buscaNombre(nombreRadNuclido) == false) {
                    mensajeError += "El nombre del radionúclido ya existe!";
                }
            }
            
        } else {
            /*
             Debido  a la utilización del mismo formulario para el abm de radionúclido. 
             Cuando se modifica el nombre los campos de unidad, propiedad y valor están desactivados. 
             Por eso se pregunta si estan prendidos los textfields
             */
            if (txtPropiedad.isDisable() == false) {

                if (propiedad == null || propiedad.length() == 0) {
                    mensajeError += "El campo Propiedad inválido! \n";
                }
                /*if (vd.buscaNombre(txtPropiedad.getText()) == false) {
                 mensajeError += "El nombre de la propiedad que desea insertar ya existe\n";
                    
                 }*/

            }

            if (txtValor.isDisable() == false) {
                if (valor == null || valor.length() == 0) {
                    mensajeError += "Valor inválido! \n";
                } else {
                    try {
                        int i = Integer.parseInt(valor);
                        //int routine
                        //Si puede se pasa el entero a Double. 
                    } catch (NumberFormatException e) {
                        if (ValidacionesGenerales.ValidarNumericoFloat(valor)) {
                            double d = Double.parseDouble(valor);
                            if (d == 0.0) {
                                mensajeError += "El campo Valor no debe ser 0.0 !\n";
                            }
                            //double routine

                        } else {
                            mensajeError += "El campo Valor debe ser númerico separado por . "
                                    + "  Ej: 12.30 \n";
                            //throw new IllegalArgumentException();
                        }
                    }
                }
            }
            //Validacion Unidad. 
            // Al no saber con ciencia cierta lo que el usuario seleccionara como unidad. Este campo solo valida que el 
            // no este vacio o sin caracteres. 
            if (txtUnidad.isDisable() == false) {
                if (txtUnidad.getText() == null || txtUnidad.getText().length() == 0) {
                    mensajeError += "El campo Unidad inválido! \n";
                }
            }
        }

        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error al guardar el radionúclido");
            alert.setContentText(mensajeError);

            alert.showAndWait();
            return false;
        }

    }

}
