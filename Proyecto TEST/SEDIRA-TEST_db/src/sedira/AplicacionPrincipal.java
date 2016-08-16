/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sedira.model.ConexionDB;

/**
 * Clase que se encarga de la aplicación base del sistema. Es un Stage, dentro
 * de esta se iniciaran los distintos Scenes.
 *
 * @author: Hefner Francisco, Quelin Pablo
 *
 */
public class AplicacionPrincipal extends Application {

    private Object stage;
    private Stage primaryStage;
    private AnchorPane menuPrincipal;

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
        if (!conex.getError()){
        conex.desconectar();
        Parent root = FXMLLoader.load(getClass().getResource("vistas/Login.fxml"));

        //Se le pasa el root node
        Scene scene = new Scene(root);
        //Tamaños de ventana
        primaryStage.setMaxWidth(362);
        primaryStage.setMaxHeight(185);
        primaryStage.setMinWidth(362);
        primaryStage.setMinHeight(185);

        primaryStage.setScene(scene);
        primaryStage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Ocurrio un error al iniciar el programa ");
            alert.setContentText("Siga estos pasos: \n"
                    + "Asegúrese que el software SEDIRA está correctamente instalado.\n"
                    + "Revisé la configuración del servidor de base de datos.");
            alert.showAndWait();
            primaryStage.close();
        }
        
    }

    /**
     * @param args argumentos de linea de comando. Llama al Método start
     */
    public static void main(String[] args) {
        launch(args);
    }

}
