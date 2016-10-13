/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import sedira.CodigosErrorSQL;

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
     * @return 
     */
    @Override
    public ObservableList<CalculoMuestra> getCalculoPaciente(int idPaciente) {
         
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<CalculoMuestra> calculosData = FXCollections.observableArrayList();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM calculos "
                            + "WHERE id_paciente = ?");
            consulta.setInt(1, idPaciente);
            //Ejecucion de la consulta. 
           
            /*SELECT  CONCAT(P.apellido,", ", P.nombre) AS Paciente,
            R.nombre_radionuclido AS Radionuclido,
             PH.nombre_phantom AS Phantom,
             C.fecha_calculo AS Fecha,
             C.resultado_calculo AS Resultado,        
             C.observaciones AS Observaciones,
             C.hash_code AS Hash,
             C.formula_mat AS Formula_Mat,
             C.formula_tex AS Formula_Tex       
             FROM calculos C
             JOIN radionuclidos R ON C.id_radionuclido = R.id_radionuclido
             JOIN phantoms PH ON C.id_phantom = PH.id_phantom
             JOIN pacientes P ON C.id_paciente = P.id_paciente*/
            
            ResultSet resultado = consulta.executeQuery();
           
            while (resultado.next()) {
              
                CalculoMuestra calculo = new CalculoMuestra();
                    
                    calculo.setFecha((Long.parseLong(resultado.getString("fecha_calculo"))));
                    calculo.setIdCalculoMuestra(Integer.parseInt(resultado.getString("id_calculo")));
                    calculo.setPaciente(resultado.getString("id_paciente"));
                    
                 
                calculosData.add(calculo);

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
        return calculosData;

    }

    /**
     * Método que obtiene los cálculos realizados
     *
     * @return
     */
    @Override
    public ObservableList<CalculoMuestra> getCalculos() {

        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<CalculoMuestra> calculosData = FXCollections.observableArrayList();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM calculos");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
           
            while (resultado.next()) {
              
                CalculoMuestra calculo = new CalculoMuestra();
                    
                    calculo.setFecha((Long.parseLong(resultado.getString("fecha_calculo"))));
                    calculo.setIdCalculoMuestra(Integer.parseInt(resultado.getString("id_calculo")));
                    calculo.setPaciente(resultado.getString("id_paciente"));
                    
                 
                calculosData.add(calculo);

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
        return calculosData;

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
        
        int Id_calculo = -1;
          
        try {
                
               conexion.getConnection().setAutoCommit(false);
            
          
                PreparedStatement consultaObtenerID= conexion.getConnection().prepareStatement(
                     "SELECT MAX(id_calculo) + 1 AS SIGUIENTE FROM calculos");
             
            
                     
            ResultSet resultado = consultaObtenerID.executeQuery();
           
            while (resultado.next()) {
             Id_calculo =  resultado.getInt("SIGUIENTE");
           
            }
            
                     
               
            // Guardado del cálculo
             PreparedStatement consultaCalculo = conexion.getConnection().prepareStatement(
                    "INSERT INTO calculos (id_calculo, id_paciente, id_radionuclido, id_phantom, fecha_calculo, "
                    + "resultado_calculo, observaciones, hash_code, formula_mat, formula_tex, id_organo) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            
            consultaCalculo.setInt(1, Id_calculo);
            consultaCalculo.setInt(2, calculo.getIdPaciente());
            consultaCalculo.setInt(3, calculo.getIdRadionuclido());
            consultaCalculo.setInt(4, calculo.getIdPhantom());

            consultaCalculo.setLong(5, calculo.getFecha());
            consultaCalculo.setBlob(6, calculo.getResultado());
            consultaCalculo.setString(7, calculo.getObservaciones());
            consultaCalculo.setString(8, calculo.getHashCode());
            consultaCalculo.setString(9, calculo.getFormula());
            consultaCalculo.setString(10, calculo.getFormulaTex());
            consultaCalculo.setInt(11, calculo.getIdOrgano());

            consultaCalculo.executeUpdate(); //Ejecucion de la consulta
            
            //Obtengo el id del calculo
           
           
           
               String queryVariables = "INSERT INTO historialcalculo (propiedad, valor, variable, id_calculo) VALUES";
               
            List<VariableCalculo> variables  = calculo.getVariables();
            for (int i=0;i<variables.size();i++) {
                
               if (i==0)
               {
                         queryVariables = queryVariables+ ("('" + variables.get(i).getDescripcion()+
                                                 "','" + variables.get(i).getValor()    +
                                                 "','" + variables.get(i).getVariable() +
                                                 "','" + Id_calculo+"')");
               }else
               {
                     queryVariables = queryVariables+ (",('" + variables.get(i).getDescripcion()+
                                                 "','" + variables.get(i).getValor()    +
                                                 "','" + variables.get(i).getVariable() +
                                                 "','" + Id_calculo+"')");
               }
             
            }
            
            
            PreparedStatement consultaVariables = conexion.getConnection().prepareStatement(queryVariables);
                  
            consultaVariables.executeUpdate();
                     
             conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);
            
             consultaCalculo.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            conexion.desconectar();
            
  

            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El cálculo fué guardado con éxito.");
            alerta.showAndWait();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
       
             
         if (conexion != null) {
            try {
                System.err.print("Transaction is being rolled back");
                conexion.getConnection().rollback();
            } catch(SQLException excep) {
               
               
            }
        }
        
            //System.out.println("Ocurrió un error al guardar el cálculo " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el cálculo " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
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
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al guardar el cálculo " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el cálculo " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
