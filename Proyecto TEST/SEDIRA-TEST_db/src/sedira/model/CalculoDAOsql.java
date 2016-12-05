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
                    "SELECT  CONCAT(P.apellido,\", \", P.nombre) AS Paciente, "
                    + "R.nombre_radionuclido AS Radionuclido, "
                    + "PH.nombre AS Phantom, "
                    + "C.fecha_calculo AS Fecha, "
                    + "O.nombre AS Organo, "
                    + "C.id_calculo, "
                    + "C.resultado_calculo AS Resultado, "
                    + "C.observaciones AS Observaciones, "
                    + "C.hash_code AS Hash, "
                    + "C.formula_mat AS Formula_Mat, "
                    + "C.formula_tex AS Formula_Tex, "
                    + "P.id_paciente "
                    + "FROM calculos C "
                    + "JOIN radionuclidos R ON C.id_radionuclido = R.id_radionuclido "
                    + "JOIN phantoms PH ON C.id_phantom = PH.id_phantom "
                    + "JOIN organos O ON C.id_organo = O.id_organo "
                    + "JOIN pacientes P ON C.id_paciente = P.id_paciente "
                    + "WHERE P.id_paciente = ?");
            consulta.setInt(1, idPaciente);
            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            //Creación del objeto a devolver.
            while (resultado.next()) {

                CalculoMuestra calculo = new CalculoMuestra();

                calculo.setFecha((Long.parseLong(resultado.getString("Fecha"))));
                calculo.setIdCalculoMuestra(Integer.parseInt(resultado.getString("id_calculo")));
                calculo.setPaciente(resultado.getString("id_paciente"));
                calculo.setPhantom(resultado.getString("Phantom"));
                calculo.setRadionuclido(resultado.getString("Radionuclido"));
                calculo.setOrgano(resultado.getString("Organo"));
                calculo.setFormula(resultado.getString("Formula_Mat"));
                calculo.setFormulaTex(resultado.getString("Formula_Tex"));
                calculo.setResultado(resultado.getString("Resultado"));
                calculo.setHashValidado(resultado.getString("Hash"));
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
    public int setCalculo(Calculo calculo) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        int Id_calculo = -1;
        int Id_formula = -1;
        int Id_propiedad = -1;

        try {

            conexion.getConnection().setAutoCommit(false);

            PreparedStatement consultaObtenerIDFormula = conexion.getConnection().prepareStatement(
                    "SELECT IFNULL(MAX(id_formula) + 1,1) AS SIGUIENTE FROM formulas");

            ResultSet rsIdFormula = consultaObtenerIDFormula.executeQuery();

            while (rsIdFormula.next()) {
                Id_formula = rsIdFormula.getInt("SIGUIENTE");

            }

            PreparedStatement guardarFormula = conexion.getConnection().prepareStatement(
                    " INSERT INTO formulas ( "
                    + "     id_formula "
                    + "    ,formula_mat "
                    + "    ,formula_tex "
                    + "   ) VALUES (  "
                    + "     ?    "
                    + "    ,?  "
                    + "    ,?  "
                    + "   ) ");

            guardarFormula.setInt(1, Id_formula);
            guardarFormula.setString(2, calculo.getFormula());
            guardarFormula.setString(3, calculo.getFormulaTex());

            PreparedStatement consultaObtenerID = conexion.getConnection().prepareStatement(
                    "SELECT IFNULL(MAX(id_calculo) + 1,1) AS SIGUIENTE FROM calculos");

            ResultSet resultado = consultaObtenerID.executeQuery();

            while (resultado.next()) {
                Id_calculo = resultado.getInt("SIGUIENTE");

            }

            // Guardado del cálculo
            PreparedStatement consultaCalculo = conexion.getConnection().prepareStatement(
                    " INSERT INTO calculos ( "
                    + "    id_calculo "
                    + "   ,id_paciente "
                    + "   ,id_radionuclido "
                    + "   ,id_phantom "
                    + "   ,id_organo "
                    + "   ,fecha_calculo "
                    + "   ,resultado_calculo "
                    + "   ,observaciones "
                    + "   ,hash_code "
                    + "   ,id_formula "
                    + " ) VALUES ( "
                    + "    ?  "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? "
                    + "   ,? )");

            consultaCalculo.setInt(1, Id_calculo);
            consultaCalculo.setInt(2, calculo.getIdPaciente());
            consultaCalculo.setInt(3, calculo.getIdRadionuclido());
            consultaCalculo.setInt(4, calculo.getIdPhantom());
            consultaCalculo.setInt(5, calculo.getIdOrgano());

            consultaCalculo.setLong(6, calculo.getFecha());
            consultaCalculo.setString(7, calculo.getResultado());
            consultaCalculo.setString(8, calculo.getObservaciones());
            consultaCalculo.setString(9, calculo.getHashCode());
            consultaCalculo.setInt(10, Id_formula);

                     
            String queryPropiedades = "   INSERT INTO propiedades ("
                    + "  id_propiedad"
                    + " ,nombre"
                    + " ,valor"
                    + " ,variable"
                    + " ) VALUES ";
            
            String queryCalculoPropiedades =  "   INSERT INTO calculospropiedades ("
                        + "         id_calculo"
                        + "        ,id_propiedad"
                        + "      ) VALUES ";

            PreparedStatement consultaObtenerIdPropiedad = conexion.getConnection().prepareStatement(
                    "SELECT IFNULL(MAX(id_propiedad) + 1,1) AS SIGUIENTE FROM propiedades");

            ResultSet rsIdPropiedad = consultaObtenerIdPropiedad.executeQuery();

            while (rsIdPropiedad.next()) {
                Id_propiedad = rsIdPropiedad.getInt("SIGUIENTE");

            }

            List<VariableCalculo> variables = calculo.getVariables();
            String coma = "";
            for (int i = 0; i < variables.size(); i++) {

                if (i == 0) {
                    coma = "";
                } else {
                    coma = ",";
                }

                queryPropiedades = queryPropiedades + coma + ("('" + Id_propiedad
                        + "','" + variables.get(i).getDescripcion()
                        + "','" + variables.get(i).getValor()
                        + "','" + variables.get(i).getVariable() + "')");

                queryCalculoPropiedades = queryCalculoPropiedades + coma + ("('" + Id_calculo                   
                        + "','" +Id_propiedad + "')");

                        
                Id_propiedad++;

            }

            PreparedStatement consultaPropiedades = conexion.getConnection().prepareStatement(queryPropiedades);
            PreparedStatement consultaPropiedadesCalculo = conexion.getConnection().prepareStatement(queryCalculoPropiedades);

            guardarFormula.executeUpdate();
            consultaCalculo.executeUpdate();
            consultaPropiedades.executeUpdate();
            consultaPropiedadesCalculo.executeUpdate();

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
                } catch (SQLException excep) {

                }
            }

        }
        return Id_calculo;
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
            consulta.setString(3, calculo.getResultado());
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

    /**
     * Método que trae los cálculos de la Base de datos. Filtrados por número de
     * cálculo.
     *
     * @param idCalculo
     * @return
     */
    @Override
    public CalculoMuestra getCalculoSeleccionado(int idCalculo) {

        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        CalculoMuestra calculoSeleccionado = new CalculoMuestra();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT  CONCAT(P.apellido,\", \", P.nombre) AS Paciente, "
                    + "R.nombre_radionuclido AS Radionuclido, "
                    + "PH.nombre_phantom AS Phantom, "
                    + "C.fecha_calculo AS Fecha, "
                    + "O.nombre_organo AS Organo, "
                    + "C.id_calculo, "
                    + "C.resultado_calculo AS Resultado, "
                    + "C.observaciones AS Observaciones, "
                    + "C.hash_code AS Hash, "
                    + "C.formula_mat AS Formula_Mat, "
                    + "C.formula_tex AS Formula_Tex, "
                    + "P.id_paciente "
                    + "FROM calculos C "
                    + "JOIN radionuclidos R ON C.id_radionuclido = R.id_radionuclido "
                    + "JOIN phantoms PH ON C.id_phantom = PH.id_phantom "
                    + "JOIN organos O ON C.id_organo = O.id_organo "
                    + "JOIN pacientes P ON C.id_paciente = P.id_paciente "
                    + "WHERE C.id_calculo = ?");
            consulta.setInt(1, idCalculo);
            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            //Creación del objeto a devolver.
            while (resultado.next()) {

                calculoSeleccionado.setFecha((Long.parseLong(resultado.getString("Fecha"))));
                calculoSeleccionado.setIdCalculoMuestra(Integer.parseInt(resultado.getString("id_calculo")));
                calculoSeleccionado.setPaciente(resultado.getString("id_paciente"));
                calculoSeleccionado.setPhantom(resultado.getString("Phantom"));
                calculoSeleccionado.setRadionuclido(resultado.getString("Radionuclido"));
                calculoSeleccionado.setOrgano(resultado.getString("Organo"));
                calculoSeleccionado.setFormula(resultado.getString("Formula_Mat"));
                calculoSeleccionado.setFormulaTex(resultado.getString("Formula_Tex"));
                calculoSeleccionado.setResultado(resultado.getString("Resultado"));
                calculoSeleccionado.setHashValidado(resultado.getString("Hash"));
                calculoSeleccionado.setObservaciones(resultado.getString("Observaciones"));
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
        return calculoSeleccionado;
    }

}
