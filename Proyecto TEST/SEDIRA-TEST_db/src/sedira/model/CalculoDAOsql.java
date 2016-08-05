/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 * Clase de acceso de datos para Cálculo. Implementada en MySql.
 *
 * @author Quelin Pablo, Francisco Hefner
 */
public class CalculoDAOsql implements ICalculoDAO {

    /**
     * Método que obtiene los calculos realizados a un paciente determinado.
     *
     * @param idPaciente Identificador de paciente
     */
    @Override
    public void getCalculo(int idPaciente) {

    }

    /**
     * Método que almacena un cálculo en la base de datos.
     *
     * @param calculo
     */
    @Override
    public void setCalculo(Calculo calculo) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO calculos (id_paciente, id_radionuclido, id_phantom, fecha_calculo, "
                    + "resultado_calculo, observaciones, hash_code) "
                    + "VALUES(?,?,?,?,?,?,?)");
            consulta.setInt(1, calculo.getIdPaciente());
            consulta.setInt(2, calculo.getIdRadionuclido());
            consulta.setInt(3, calculo.getIdPhantom());

            consulta.setLong(4, calculo.getFecha());
            consulta.setBlob(5, calculo.getResultado());
            consulta.setString(6, calculo.getObservaciones());
            consulta.setString(7, calculo.getHashCode());

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

    /**
     * Método que almacena un cálculo con menos datos en la base de datos.
     *
     * @param calculo
     */
    @Override
    public void setCalculoBasico(Calculo calculo) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO calculos (id_paciente,fecha_calculo, "
                    + "resultado_calculo, observaciones, hash_code) "
                    + "VALUES(?,?,?,?,?)");
            consulta.setInt(1, calculo.getIdPaciente());
            consulta.setLong(2, calculo.getFecha());
            consulta.setBlob(3, calculo.getResultado());
            consulta.setString(4, calculo.getObservaciones());
            consulta.setString(5, calculo.getHashCode());

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
