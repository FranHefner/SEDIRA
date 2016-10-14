/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sedira.CodigosErrorSQL;

/**
 *
 * @author Pablo Quelin, Francisco Hefner
 */
public class FormulaDAOsql implements IFormulaDAO {
    
    /**
     * Método para guardar la fómula
     *
     * @param Formula Formula matemtica
     * @param Nombre Nombre de la´fórmula
     * @return 
     */
    
    @Override
    public boolean setFormula( String Nombre, String Formula_mat,int Id_calculo){
      
         //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(               
                    
                   " INSERT INTO formulas(nombre, formula_mat, id_calculo)"             
                    + "VALUES(?,?,?)");
            consulta.setString(1,Nombre );
            consulta.setString(2,Formula_mat);
            consulta.setInt(3,Id_calculo);
       
            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            conexion.desconectar();

            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("La fórmula fué guardada con éxito.");
            alerta.showAndWait();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al guardar el cálculo " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el cálculo " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        

          return true;
      }
              
              
    
}
