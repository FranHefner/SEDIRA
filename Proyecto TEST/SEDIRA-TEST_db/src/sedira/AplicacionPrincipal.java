/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sedira.model.ConexionDB;

/**
 * Clase que se encarga de la aplicación base del sistema. Es un Stage, dentro
 * de esta se iniciaran los distintos Scenes.
 *
 * @author: Hefner Francisco, Quelin Pablo
 *
 */
public class AplicacionPrincipal extends Application {

     /**
     * Método Start para el inicio del Scene.
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Conecta al servidor primero.
        //Detecta error del servidor no encontrado. 
        ConexionDB conex = new ConexionDB();
        if (!conex.getError()) {
            //  conex.desconectar();
            Parent root = FXMLLoader.load(getClass().getResource("vistas/Login.fxml"));

            //Se le pasa el root node
            Scene scene = new Scene(root);
            //Tamaños de ventana
            primaryStage.setMaxWidth(362);
            primaryStage.setMaxHeight(185);
            primaryStage.setMinWidth(362);
            primaryStage.setMinHeight(185);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Iniciar Sesión");
            primaryStage.setResizable(false);
          
            primaryStage.show();
        } else {
      //      if (CodigosErrorSQL.getErrorCode() == 1045/* || CodigosErrorSQL.getErrorCode() == 0  */) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Iniciar configuración");
                alert.setHeaderText("Atención!");
                alert.setContentText("¿Desea iniciar el asistente de configuración de base de datos?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(getClass().getResource("vistas/BdConfig.fxml"));

                    //Se le pasa el root node
                    Scene scene = new Scene(root);
                    //Tamaños de ventana
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Configuración");
                    primaryStage.setResizable(false);
                    primaryStage.show();
                }
  //          }
        }

    }

    /**
     * @param args argumentos de linea de comando. Llama al Método start
     */
    public static void main(String[] args) {
        launch(args);
    }

}
