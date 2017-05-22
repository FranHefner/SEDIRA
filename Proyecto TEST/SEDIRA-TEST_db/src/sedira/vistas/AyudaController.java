/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
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
    private Label lblTitulo;
    @FXML
    private Label lblInfoTitulo;
    @FXML
    private Label lblVersion;
    @FXML
    private TextField txtDbInfo;
    @FXML
    private TextField txtSystemInfo;
    Hyperlink hyperlink = new Hyperlink("Departamento de Mecánica Computacional");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lblInfoTitulo.setText("Sistema para estimar la dosis interna de radiación absorbida en tratamientos de medicina nuclear");
        lblVersion.setText("Versión 5.0");
        try {
            obtenerInfoDb();
        } catch (SQLException ex) {
            Logger.getLogger(AyudaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCerrar_click(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();

        stage.close();
    }

    private void obtenerInfoDb() throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        DatabaseMetaData meta = conexion.getConnection().getMetaData();
        String Version = "Versión: "+meta.getDatabaseProductVersion();
        txtDbInfo.setText(Version);

    }
     private void obtenerSystemInfo() throws SQLException {
        

    }
    
    

}
