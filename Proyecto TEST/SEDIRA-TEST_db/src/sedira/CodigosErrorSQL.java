/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase para el control de las excepciones de la base de datos.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CodigosErrorSQL {

    public static void analizarExepcion(SQLException e) {
        String mensajeError = "";
        Boolean errorConocido = false;
        String titulo = "Información";

        switch (e.getErrorCode()) {
            case 1062:
                mensajeError = "El registro que se intenta agregar/modificar ya encuentra en la base de datos \n "
                        + "Error SQL: \n" + e.getMessage();
                break;
            case 1451:
                mensajeError = "El registro que se intenta eliminar esta siendo referenciado. "
                        + "No puede ser eliminado mientras exista dicha referencia.  \n ";
                        //muy largo el mensaje. 
                        //+ "Error SQL: \n" + e.getMessage();
                break;

        }
        /*if (errorConocido == false) {
         mensajeError = "Ocurrió un error al realizar la operación. Por favor, intente nuevamente";
         }*/

        JOptionPane.showMessageDialog(null, mensajeError, titulo, JOptionPane.INFORMATION_MESSAGE);

    }

}
