/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class CodigosErrorSQL {
    
    
    private void analizarExepcion(SQLException e)
    {
        String mensajeError ="";
        Boolean errorConocido = false;
        String titulo ="Información";
        
        
        
          if (e.getErrorCode() == 1062)
            {
                mensajeError = "El registro que se intenta agregar/modificar ya encuentra en la base de datos " + e.getMessage();
            }
            if (errorConocido == false)
            {
                 mensajeError = "Ocurrió un error al realizar la operación. Por favor, intente nuevamente";           
            }    
            
            JOptionPane.showMessageDialog(null, mensajeError, titulo, JOptionPane.INFORMATION_MESSAGE);
           
    }
    
}
