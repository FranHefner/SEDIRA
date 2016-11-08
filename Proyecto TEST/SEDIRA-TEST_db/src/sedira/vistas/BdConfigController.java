/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.Security;
import sedira.model.ConexionDB;
import static sedira.model.ConexionDB.bd;

/**
 * FXML Controller class
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class BdConfigController implements Initializable {

    @FXML
    TextField txtNombreBaseDatos;
    @FXML
    TextField txtNombreUsuario;
    @FXML
    TextField txtPass;
    @FXML
    TextField txtUrl;
    @FXML
    Button btnGuardarCambios;
    @FXML
    Button btnTest;
    @FXML
    Button btnCerrar;
    @FXML
    TextArea textLog;

    final static String FILE_NAME = "C:\\dbConfig.txt";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //btnTest.setDisable(true);
        try {
            Scanner s = new Scanner(new File(FILE_NAME)).useDelimiter("\\s+");
            while (s.hasNext()) {
                textLog.appendText("Archivo de configuración encontrado. \n");
                txtNombreBaseDatos.appendText(s.next()); // else read the next token
              
               txtNombreUsuario.appendText(s.next());               
                try {
                    txtPass.appendText( Security.decrypt(s.next()));
                } catch (Exception ex) {
                
                }
                txtUrl.appendText(s.next());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            textLog.appendText("No se encontro el archivo de configuración en c:\\SEDIRA. \n");

        }

    }

    
    
            
    public void getParametros() {
        try {
            Scanner s = new Scanner(new File(FILE_NAME)).useDelimiter("\\s+");
            while (s.hasNext()) {
                ConexionDB.setBd(s.next()); // else read the next token
                ConexionDB.setLogin(s.next());
                try {
                    ConexionDB.setPassword(Security.decrypt(s.next()));
                } catch (Exception ex) {
                   //Error al desencriptar
                }
                ConexionDB.setUrl(s.next());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            textLog.appendText("No se encontro el archivo de configuración en c:\\SEDIRA. \n");
        }
    }

    @FXML
    public void btnCerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnTest() {
            
     
         ConexionDB nuevaConexion = new ConexionDB(txtNombreBaseDatos.getText(), txtNombreUsuario.getText(),txtPass.getText(), txtUrl.getText());
         
        if (!nuevaConexion.getError()) {
            textLog.appendText("CONEXIÓN ESTABLECIDA. \n");
            textLog.appendText("REINICIE LA APLICACIÓN \n");
         //   btnGuardarCambios.setDisable(true);
          //  btnTest.setDisable(true);
        } else {
            textLog.appendText("FALLO LA CONEXIÓN, REVISE LOS PARAMETROS. \n");
        }
        nuevaConexion.desconectar();
    }

    @FXML
    public void btnGuardarConfiguracion() {
     
        String nombreBaseDatos = txtNombreBaseDatos.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String pass = txtPass.getText();
        String url = txtUrl.getText();

        PrintWriter fw = null;
        try {
            fw = new PrintWriter(FILE_NAME);

            //BufferedWriter bw = new BufferedWriter(fw);
            fw.write(nombreBaseDatos);
            fw.write("\n");
            fw.write(nombreUsuario);
            fw.write("\n");
            try {
                fw.write(Security.encrypt(pass));
            } catch (Exception ex) {             
            }
            fw.write("\n");
            fw.write(url);
            fw.close();

            textLog.setText("Los datos se guardaron correctamente. \n");
                    
        } catch (IOException e) {
            e.printStackTrace();
            fw.close();
        }
        ConexionDB.setBd(nombreBaseDatos);
        ConexionDB.setLogin(nombreUsuario);
        ConexionDB.setPassword(pass);
        ConexionDB.setUrl(url);

    }
}
