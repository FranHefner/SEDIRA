/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public boolean setFormula(String Nombre, String Formula_mat, int Id_historial) {

        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " INSERT INTO formulas(nombre, formula_mat, id_historial)"
                    + "VALUES(?,?,?)");
            consulta.setString(1, Nombre);
            consulta.setString(2, Formula_mat);
            consulta.setInt(3, Id_historial);

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

    @Override
    public void eliminarFormula(String Formula)
    {
        
           //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " DELETE FROM formulas WHERE nombre = ?");
                  
            consulta.setString(1, Formula);
         

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            conexion.desconectar();

            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("La fórmula fué eliminada con éxito.");
            alerta.showAndWait();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al guardar el cálculo " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el cálculo " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }
    @Override
    public List<Formula> getFormulas() {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        List<Formula> formulas = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM formulas");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                //  public Formula(int pId_formula, String pNombre, String pFormula_mat, int pId_calculo)
                 Formula f = new Formula(resultado.getInt("id_historial"),resultado.getString("nombre"),resultado.getString( "formula_mat"),resultado.getInt("id_historial"));
                formulas.add(f);
            }
            //Cierre de consulta
            resultado.close();
            consulta.close();
            //Cierre de conexion. 
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            System.out.println(e.getMessage());

        }
        return formulas;

    }
    @Override
    public ObservableList<VariableCalculo> getPropiedadesFormula(int Id_historial, boolean ConValores) {
     
                
                     //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<VariableCalculo>  propiedadesCalculo = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM historialcalculo WHERE id_historial = ?");

            
               consulta.setInt(1, Id_historial);
            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                //  public Formula(int pId_formula, String pNombre, String pFormula_mat, int pId_calculo
          //       public VariableCalculo(int id, String descripcion, double valor, String variable) {
                  VariableCalculo variable =null;
                if (ConValores)
                {
                      variable = new VariableCalculo(resultado.getInt("id_historial"), resultado.getString("propiedad"), resultado.getString("valor"), resultado.getString("variable"));
               
                }else
                {
                     variable = new VariableCalculo(resultado.getInt("id_historial"), resultado.getString("propiedad"), "", resultado.getString("variable"));
               
                }
                
                 propiedadesCalculo.add(variable);
            }
            //Cierre de consulta
            resultado.close();
            consulta.close();
            //Cierre de conexion. 
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            System.out.println(e.getMessage());

        }
        return propiedadesCalculo;
        
    }

}
