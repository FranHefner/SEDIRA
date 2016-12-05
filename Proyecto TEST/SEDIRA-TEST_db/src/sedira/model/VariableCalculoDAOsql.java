/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import sedira.CodigosErrorSQL;

/**
 * Clase de acceso a datos para Objetos de tipo VariableCalculo.
 *
 *  * @author Quelin Pablo, Hefner Francisco
 */
public class VariableCalculoDAOsql implements IVariableCalculoDAO {
    
    @Override
    public ObservableList <VariableCalculo> obtenerVariables (int IdCalculo){
         
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<VariableCalculo> varCalculosData = FXCollections.observableArrayList();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "  SELECT calculos.id_calculo, historialcalculo.id_calculo, historialcalculo.id_historial, "
                   + "      historialcalculo.propiedad, historialcalculo.valor, historialcalculo.variable from calculos "
                   + " INNER JOIN historialcalculo "
                   + "       ON calculos.id_calculo = historialcalculo.id_calculo "
                   + " WHERE calculos.id_calculo = ?");
            
            // Parametros de la consulta. 
            consulta.setInt(1, IdCalculo);
            //Ejecucion de la consulta. 
           
            ResultSet resultado = consulta.executeQuery();
           
            while (resultado.next()) {
                VariableCalculo var = new VariableCalculo();
                var.setDescripcion(resultado.getString("propiedad"));
                var.setValor(resultado.getString("valor"));
                var.setVariable(resultado.getString("variable"));
                var.setId(Integer.parseInt(resultado.getString("id_historial")));
                 
                varCalculosData.add(var);

            }
            //Cierre de consulta
            resultado.close();
            consulta.close();
            //Cierre de conexion. 
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);

        }
        return varCalculosData;

    }
}
