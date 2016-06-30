/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 * Clase de acceso de datos para Cálculo
 * @author Quelin Pablo, Francisco Hefner
 */
public abstract class CalculoDAO {
    
    public static void getCalculo(int idPaciente){
        
    }
    public static void setCalculo(Calculo calculo){
         //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
                
        try {
            
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "INSERT INTO calculos (id_paciente, id_radionuclido, id_phantom, fecha_calculo, "
                                + "resultado_calculo, observaciones) "
                                 + "VALUES(?,?,?,?,?,?)");
                consulta.setInt(1,calculo.getIdPaciente());
                consulta.setInt(2,calculo.getIdRadionuclido());
                consulta.setInt(3,calculo.getIdPhantom());
                  
                consulta.setDate(4, (Date) calculo.getFecha());
                consulta.setString(5, calculo.getResultado());
                consulta.setString (6,calculo.getObservaciones());

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El cálculo fué guardado con éxito.");
                alerta.showAndWait();
             
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al guardar el cálculo " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el cálculo " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
