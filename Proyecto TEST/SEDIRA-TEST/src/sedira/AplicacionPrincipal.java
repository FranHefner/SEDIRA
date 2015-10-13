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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sedira.model.Phantom;
import sedira.vistas.AbmPhantomController;



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
        
       
        Parent root = FXMLLoader.load(getClass().getResource("vistas/MenuPrincipal.fxml"));
        
        //Se le pasa el root node
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    
        
    }
    
    /**
     * @param args argumentos de linea de comando. Llama al Metodo start
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Abre el formulario de AbmPhantom.FXml con los datos correspondientes al phantom seleccionado.
     * Si el usuario presiona guardar datos, los cambios son guardados dentro del phantom y returna
     * true. 
     * @param phantom para ser editado
     * @return true si el usuario clickea Guardar datos o retorna falso en caso contrario
     */
    public boolean mostrarPhantomEditDialog (Phantom phantom){
        // cargo el nuevo FXML para crear un ventana tipo PopUp
        try {
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AplicacionPrincipal.class.getResource("AbmPhantom.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Creo el Stage para el Dialogo Editar. 
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Phantom");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

                // Set the person into the controller.
            AbmPhantomController controladorAbmPhantom = loader.getController();
            controladorAbmPhantom.setDialogStage(dialogStage);
            controladorAbmPhantom.setPhantom(phantom);

            // Muestra el formulario y espera hasta que el usuario lo cierre. 
            dialogStage.showAndWait();

        return controladorAbmPhantom.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
                return false;
            }
        }
}
