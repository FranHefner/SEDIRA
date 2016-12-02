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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.FuncionesGenerales;
import sedira.ValidacionesGenerales;
import sedira.model.IOrganoDAO;
import sedira.model.IValorDescripcionDAO;
import sedira.model.Organo;
import sedira.model.OrganoDAOsql;

import sedira.model.ValorDescripcion;
import sedira.model.ValorDescripcionDAOsql;

/**
 * FXML Controller class Clase controladora para el Abm de items de organos.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class AbmItemOrganoController implements Initializable {

    @FXML
    private Button btnLimpiarValores;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtPropiedad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtUnidad;
    @FXML
    private TextField txtNombreOrgano;
    @FXML
    private TitledPane titledEdicion;

    @FXML
    private VBox boxControles;

    //Objeto Organo auxiliar. 
    private Organo organoActual = FuncionesGenerales.getOrganoActual();
    // Stage aux
    private Stage dialogStage;
    // Valor Descripcion aux para items de Organo 
    private ValorDescripcion itemOrgano;
    // boleano para controlar cuando el usuario clickea ok 
    private boolean guardarDatos = false;
    //Lista Observable para el manejo de Organos
    private ObservableList<ValorDescripcion> listaAtributoOrgano = FXCollections.observableArrayList();

    private IOrganoDAO org = new OrganoDAOsql();
    private IValorDescripcionDAO vd = new ValorDescripcionDAOsql();

    private static int LIMIT_NOMBRE = 45;
    private static int LIMIT_VALOR = 14;
    private static int LIMIT_UNIDAD = 255;
    

    ObservableList<String> data;
    ListView listaSugerida = new ListView();
    FilteredList<String> filteredData;
    ObservableList<String> dataFiltrada = FXCollections.observableArrayList();
    boolean bandera = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLimpiarValores.setDisable(false);
        //Listener para la cantidad de caracteres en el nombre en las propiedades
        txtPropiedad.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtPropiedad.getText().length() >= LIMIT_NOMBRE) {

                        txtPropiedad.setText(txtPropiedad.getText().substring(0, LIMIT_NOMBRE));
                    }
                }
            }
        });

        //Listener para la cantidad de caracteres en el nombre en el valor 
        txtValor.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtValor.getText().length() >= LIMIT_VALOR) {

                        txtValor.setText(txtValor.getText().substring(0, LIMIT_VALOR));
                    }
                }
            }
        });

        //Listener para la cantidad de caracteres en el nombre en el campo unidad 
        txtUnidad.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (txtUnidad.getText().length() >= LIMIT_UNIDAD) {

                        txtUnidad.setText(txtUnidad.getText().substring(0, LIMIT_UNIDAD));
                    }
                }
            }
        });

    }

    /**
     * Setea el Stage para este Formulario o Dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        data = vd.listadoPropiedades("organos");
        listaSugerida.setItems(data);

        txtPropiedad.textProperty().addListener(
                (observable, oldValue, newValue) -> actualizarListaSugerida(newValue));

        boxControles.getChildren().addAll(txtPropiedad, listaSugerida);

        listaSugerida.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                bandera = true;
                if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {

                } else {
                    seleccionarItem(newValue);
                }
                bandera = false;

            }
        });
    }

    public void seleccionarItem(String itemSeleccionado) {
        txtPropiedad.setText(itemSeleccionado);

    }

    /**
     * Método que actualiza la lista de propiedades al completar el textField
     *
     * @param filtro
     */
    private void actualizarListaSugerida(String filtro) {
        listaSugerida.getSelectionModel().clearSelection();
        //Comportamiento de la lista sugerida. 
        if (listaSugerida.getSelectionModel().isEmpty()) {
            listaSugerida.setFocusTraversable(false);

        } else {
            listaSugerida.setFocusTraversable(true);

        }
        //FIn Comportamiento de la lista sugerida.

        if (bandera == false) {
            if (filtro == null || filtro.length() == 0) {
                listaSugerida.setItems(data);
                listaSugerida.setVisible(true);
            } else {

                dataFiltrada = data.filtered(s -> s.toLowerCase().contains(filtro.toLowerCase()));
                if (dataFiltrada.size() == 0) {
                    listaSugerida.setVisible(false);
                } else {
                    listaSugerida.setItems(dataFiltrada);
                    listaSugerida.setVisible(true);
                }

            }
        }
    }

    /**
     * Setea los Items en el órgano
     *
     * @param itemOrgano
     */
    public void setItemOrgano(ValorDescripcion itemOrgano) {
        Organo organoActual = FuncionesGenerales.getOrganoActual();
        this.itemOrgano = itemOrgano;
        txtNombreOrgano.setFocusTraversable(false);
        txtNombreOrgano.setEditable(false);
        txtNombreOrgano.setText(organoActual.getNombreOrgano());
        txtPropiedad.setText(this.itemOrgano.getDescripcion());
        txtValor.setText(String.valueOf(this.itemOrgano.getValor()));
        txtUnidad.setText(this.itemOrgano.getUnidad());
    }

    /**
     * Método llamado al momento de que el usuario presiona Guardar datos .
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void btnGuardarDatos() throws SQLException {

        String NombrePropiedad;

        // Me fijo si hay una propiedad seleccionada o es un nuevo
        if (listaSugerida.getSelectionModel().getSelectedIndex() == -1) {
            NombrePropiedad = txtPropiedad.getText();
        } else {
            NombrePropiedad = listaSugerida.getSelectionModel().getSelectedItem().toString();
        }

        // TODO: VALIDACIONES.  
        // La llamada a la base de datos se realiza desde PhantomController. Editar/Nuevo
        if (validarDatosEntrada()) {
            //Validacion preguntando si esta seguro guardar cambios.
            switch (dialogStage.getTitle()) {

                case "Modificar Items":
                    itemOrgano.setDescripcion(NombrePropiedad);
                    itemOrgano.setUnidad(txtUnidad.getText());
                    itemOrgano.setValor(txtValor.getText());
                    break;
            }
            // Si las validaciones son correctas se guardan los datos. 

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
     * Metodo que se llama al presionar el boton cancelar.
     */
    @FXML
    public void btnCancel_click() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        switch (dialogStage.getTitle()) {

            case "Modificar Items":
                alert.setTitle("Cancelar modificación");
                alert.setHeaderText("Atención!");
                alert.setContentText("Está seguro de cancelar la modificación del órgano?");
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dialogStage.close();
        } else {

        }
    }

    /**
     * metodo para el control del Boton Limpiar Valores. limpia los datos
     * agregados en los textFields del formulario.
     */
    @FXML
    public void btnLimpiarValores_click() {

        switch (dialogStage.getTitle()) {

            case "Modificar Items":
                txtUnidad.setText("");
                txtPropiedad.setText("");
                txtValor.setText("");
                break;
        }
    }

    /**
     * Método que se encarga de la validación de los datos ingresados
     *
     * @return
     * @throws SQLException
     */
    public boolean validarDatosEntrada() throws SQLException {

        String mensajeError = "";
        String valor = txtValor.getText();
        String propiedad = txtPropiedad.getText();
        String unidad = txtUnidad.getText();
        
            
            // Validacion propiedad
            if (propiedad == null || propiedad.length() == 0) {
                mensajeError += "El campo Propiedad no puede estar vacio. \n";
            } else {
                    if (vd.buscaNombre(propiedad, "organos",organoActual.getIdOrgano())){
                    mensajeError += "La nombre de la propiedad ya existe. \n";
                    } 
                }
            
            //Validacion Valor
            if (valor == null || valor.length() == 0) {
                mensajeError += "El campo Valor no puede estar vacio. \n";
            } else {
                try {
                    int i = Integer.parseInt(valor);
                    //int routine
                    //Si puede se pasa el entero a Double. 
                } catch (NumberFormatException e) {
                    if (ValidacionesGenerales.ValidarNumericoFloat(valor)) {
                        double d = Double.parseDouble(valor);
                        if (d == 0.0) {
                            mensajeError += "El campo Valor no debe ser 0.0 \n";
                        }
                        //double routine

                    } else {
                        mensajeError += "El campo Valor debe ser númerico separado por . (punto) "
                                + "  Ej: 12.30 \n";
                        //throw new IllegalArgumentException();
                    }
                }

            } //fin else valor

            //Validacion Unidad. 
            // Al no saber con ciencia cierta lo que el usuario seleccionara como unidad. Este campo solo valida que el 
            // no este vacio o sin caracteres. 
            if (unidad == null || unidad.length() == 0) {
                mensajeError += "El campo Unidad es inválido! \n";
            }
        

        if (mensajeError.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Existe un error en los siguientes campos:");
            alert.setContentText(mensajeError);
            alert.showAndWait();
            return false;
        }

    }

}
