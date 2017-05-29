/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.ConexionDB;

/**
 * FXML Controller class
 *
 * @author Quelin Pablo. Hefner Francisco
 */
public class AyudaController implements Initializable {

    @FXML
    private Button btnCerrar;
    @FXML
    private Label lblInfoTitulo;
    @FXML
    private Label lblVersion;
    @FXML
    private Label lblActualizacion;
    @FXML
    private TextArea txtAreaInfo;
    @FXML
    private TextField txtSystemInfo;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblInfoTitulo.setText("Sistema para estimar la dosis interna de radiación absorbida en tratamientos de medicina nuclear");
        lblVersion.setText("Versión 5.0");
        lblActualizacion.setText("Última actualización: 28/05/2017");

      

        try {
            obtenerInfo();
        } catch (SQLException ex) {
            Logger.getLogger(AyudaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @FXML
    private void btnCerrar_click(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    private void obtenerInfo() throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        DatabaseMetaData meta = conexion.getConnection().getMetaData();
        String nameOS = "os.name";
        String versionOS = "os.version";
        String architectureOS = "os.arch";

        String info = "Versión servidor base de datos: \n" + meta.getDatabaseProductVersion();

        info += "\nProcesadores Disponibles (cores): "
                + Runtime.getRuntime().availableProcessors();
        /* Total amount of free memory available to the JVM */
        info += "\nMemoria libre (bytes): "
                + Runtime.getRuntime().freeMemory();
        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        info += "\nMemoria máxima (bytes): "
                + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory);
        /* Total memory currently available to the JVM */
        info += "\nMemoria total disponible para JVM (bytes): "
                + Runtime.getRuntime().totalMemory();
        info += "\n\nInformación sobre el Sistema Operativo: ";
        info += "\nNombre: "
                + System.getProperty(nameOS);
        info += "\nVersion:  "
                + System.getProperty(versionOS);
        info += "\nArquitectura: "
                + System.getProperty(architectureOS);
        txtAreaInfo.setText(info);

    }

}
