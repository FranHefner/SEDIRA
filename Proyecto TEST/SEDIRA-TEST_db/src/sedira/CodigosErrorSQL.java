/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 * Clase para el control de las excepciones de la base de datos.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CodigosErrorSQL {
    public  static int errorCode;
    
    public static void analizarExepcion(SQLException e) {
        String mensajeError = "";
        Boolean errorConocido = false;
        String titulo = "Error!";

        switch (e.getErrorCode()) {
            case 1045: 
                mensajeError= "Ocurrio un error en el acceso a la base de datos. Por favor revise los datos de acceso";
                setErrorCode(1045);
                break;
            case 1062:
                mensajeError = "El registro que se intenta agregar/modificar ya encuentra en la base de datos. \n Por favor, verifique los datos ingresados.";
                break;
            case 1451:
                mensajeError = "El registro que se intenta eliminar está siendo referenciado. "
                        + "No puede ser eliminado mientras exista dicha referencia.  \n "
                        + "Es probable que el ítem a eliminar haya sido utilizado en un proceso de cálculo";
                        //muy largo el mensaje. 
                //+ "Error SQL: \n" + e.getMessage();
                break;
            case 0:
                mensajeError = "Ocurrio un error en la conexión con el servidor de base de datos.  "
                        + "Por favor revise que el servicio de MySql se encuentre corriendo y encendido.  \n"
                        + "POR FAVOR, REINICIE LA APLICACIÓN ";
                        //muy largo el mensaje. 
                //+ "Error SQL: \n" + e.getMessage();
                System.out.print(e);
                
                break;
            default: 
                mensajeError = "Ocurrio un error! \n"
                        + "POR FAVOR REINICE LA APLICACIÓN";
                System.out.print(e);

        }
        /*if (errorConocido == false) {
         mensajeError = "Ocurrió un error al realizar la operación. Por favor, intente nuevamente";
         }*/
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(mensajeError);
        //alert.setContentText(String.valueOf(e));
        alert.showAndWait();

        //JOptionPane.showMessageDialog(null, mensajeError, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int getErrorCode() {
        return errorCode;
    }

    public static void setErrorCode(int errorCode) {
        CodigosErrorSQL.errorCode = errorCode;
    }
    
    

}
