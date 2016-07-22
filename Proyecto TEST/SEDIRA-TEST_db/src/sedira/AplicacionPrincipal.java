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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



/**
 * Esta clase se encarga de la aplicacion base del sistema. Es un Stage, dentro de esta se iniciaran los distintos Scenes. 
 * 
 * @author: Hefner Francisco, Quelin Pablo 
 * 
 */
public class AplicacionPrincipal extends Application {
    private Object stage;
    private Stage primaryStage;
    private AnchorPane menuPrincipal;
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
       
     /*   Parent root = FXMLLoader.load(getClass().getResource("vistas/MenuPrincipal.fxml"));
        
        //Se le pasa el root node
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        */
         Parent root = FXMLLoader.load(getClass().getResource("vistas/Login.fxml"));
        
        //Se le pasa el root node
        Scene scene = new Scene(root);
        
            primaryStage.setMaxWidth(362);        
        primaryStage.setMaxHeight(185);
        primaryStage.setMinWidth(362);        
        primaryStage.setMinHeight(185);
   
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    /**
     * @param args argumentos de linea de comando. Llama al Metodo start
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
