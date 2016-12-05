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
    public boolean setFormula(int id_formula, String Formula_mat, String formula_tex) {

        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                            "   INSERT INTO formulas ("
                            + "   id_formula"
                            + "  ,formula_mat"
                            + "  ,formula_tex"
                            + " ) VALUES ("
                            + "   ? "
                            + "  ,? "
                            + "  ,? ");

            consulta.setInt(1, id_formula);
            consulta.setString(2, Formula_mat);
            consulta.setString(3, formula_tex);

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
     public boolean SetFormulaPlantilla( int Id_formula, String Nombre)
     {
             ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(                  
            "  INSERT INTO plantillas ("       
                + " nombre"
                + " ,id_formula "
                + " ) VALUES ("        
                + "  ?  "
                + "  ,?  )");
  

            consulta.setString(1, Nombre);
            consulta.setInt(2, Id_formula);
      
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
    public List<Formula> getFormulasPlantillas() {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        List<Formula> formulas = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                  " SELECT "
                  + " F.id_formula, "
                  + " P.nombre, "
                  + " F.formula_mat "
                  + " FROM "
                  + " plantillas P JOIN formulas F ON P.id_formula = F.id_formula ");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                //  public Formula(int pId_formula, String pNombre, String pFormula_mat, int pId_calculo)
                 Formula f = new Formula(resultado.getInt("id_formula"),resultado.getString("nombre"),resultado.getString( "formula_mat"));
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
    public ObservableList<VariableCalculo> getPropiedadesFormula(int id_formula, boolean ConValores) {
     
                
                     //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<VariableCalculo>  propiedadesCalculo = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM formulasPropiedades WHERE id_formula =  ?");

            
               consulta.setInt(1, id_formula);
            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
             
                  VariableCalculo variable =null;
                if (ConValores)
                {
                      variable = new VariableCalculo(resultado.getInt("id_propiedad"), resultado.getString("nombre"), resultado.getString("valor"), resultado.getString("variable"));
               
                }else
                {
                     variable = new VariableCalculo(resultado.getInt("id_propiedad"), resultado.getString("nombre"), "", resultado.getString("variable"));
               
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
