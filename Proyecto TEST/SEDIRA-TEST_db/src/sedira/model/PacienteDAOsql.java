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
import javafx.scene.control.Alert;
import sedira.CodigosErrorSQL;

/**
 * Clase para el acceso de datos de Pacientes.
 *
 * @author Quelin Pablo, Hefner Francisco
 *
 */
public class PacienteDAOsql implements IPacienteDAO {

    /**
     * Método que agrega un paciente a la base de datos
     *
     * @param paciente a agregar
     * @return 
     */
    @Override
    public boolean agregarPaciente(Paciente paciente) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        int dni = paciente.getNumeroDoc();

        try {
            
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        " INSERT INTO pacientes ("
                        + "tipo_doc"
                        + ",numero_doc"
                        + ",apellido"
                        + ",nombre"
                        + ",fecha_nacimiento"
                        + ",direccion"                      
                        + ",email"
                        + ",telefono"                     
                        + ",sexo"                  
                        + ",celular)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?)"
                );

                /**
                 * CONSULTA COMPLETA "INSERT INTO pacientes (tipo_doc,
                 * numero_doc, apellido, nombre, fecha_nacimiento, direccion" +
                 * "numero_asociado, email, telefono, foto, sexo,
                 * en_tratamiento)" + "VALUES (" + "?,?,?,?,?,?,?,?,?,?,?,?" +
                 * ")"
                 */
                consulta.setString(1, paciente.getTipoDoc());
                consulta.setInt(2, paciente.getNumeroDoc());
                consulta.setString(3, paciente.getApellido());
                consulta.setString(4, paciente.getNombre());
                consulta.setString(5, paciente.getFechaNacimientoDB());
                consulta.setString(6, paciente.getDireccion());           
                consulta.setString(7, paciente.getEmail());
                consulta.setString(8, paciente.getTelefono());              
                consulta.setString(9, paciente.getSexo());               
                consulta.setString(10, paciente.getcelular());
                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText("Agregar paciente");
                alerta.setContentText("El paciente fué agregado.");
                alerta.showAndWait();
            
                return true;
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
               return false;
           // System.out.println("Ocurrió un error en la inserción del paciente " + e.getMessage());
            // JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción del paciente " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método para eliminar un paciente de la base de datos.
     *
     * @param id Numero de paciente a eliminar.
     */
    @Override
    public void eliminarPaciente(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM pacientes WHERE id_paciente = ?");
            consulta.setInt(1, id);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();

            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText("Eliminar Paciente");
            alerta.setContentText("El paciente fué eliminado. ");
            alerta.showAndWait();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al eliminar el paciente \n" + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el paciente \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método que modifica un paciente que ya existe en la base de datos.
     *
     *
     * @return  @param paciente
     */
    @Override
    public boolean modificarPaciente(Paciente paciente) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            //Validar . Estaba con DNI, pero al modificar a un paciente el dni se mantiene. 

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " UPDATE pacientes SET "
                    + "tipo_doc = ?,"
                    + "numero_doc = ?,"
                    + "apellido = ?,"
                    + "nombre = ?,"
                    + "fecha_nacimiento = ?,"
                    + "direccion = ?,"                  
                    + "email = ?,"
                    + "telefono = ?,"              
                    + "sexo = ?,"                 
                    + "celular = ?"
                    + " WHERE id_paciente = ?");
            /**
             * COMPLETAR CONSULTA COMPLETA PARA TODOS LOS ATRIBUTOS.
             */
            consulta.setString(1, paciente.getTipoDoc());
            consulta.setInt(2, paciente.getNumeroDoc());
            consulta.setString(3, paciente.getApellido());
            consulta.setString(4, paciente.getNombre());
            consulta.setString(5, paciente.getFechaNacimientoDB());
            consulta.setString(6, paciente.getDireccion());          
            consulta.setString(7, paciente.getEmail());
            consulta.setString(8, paciente.getTelefono());           
            consulta.setString(9, paciente.getSexo());        
            consulta.setString(10, paciente.getcelular());
            consulta.setInt(11, paciente.getIdPaciente());
            //parametros acotados para simplicidad del test 

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            conexion.desconectar();

            // Mensaje de confirmacion
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText("Modificar paciente");
            alerta.setContentText("El paciente fué modificado.");
            alerta.showAndWait();

            return true;
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);

              return false;
            //System.out.println("Ocurrió un error en la modificación  del paciente " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error en la modificación del paciente " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método que busca si el paciente ya está ingresado por su dni.
     *
     * @param dni
     * @return True si no existe, False si existe.
     */
    @Override
    public boolean buscaDni(int dni) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT numero_doc FROM pacientes "
                    + "WHERE numero_doc = ?");

            consulta.setInt(1, dni);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                return false;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return true;
            }

        } catch (SQLException e) {
           CodigosErrorSQL.analizarExepcion(e);
            // System.out.println(e.getMessage());
           // JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }

    /**
     * Método que retorna la lista completa de pacientes
     *
     * @return Lista de pacientes.
     * @throws SQLException
     */
    @Override
    public ObservableList<Paciente> obtenerPacientes() throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM pacientes");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                //objeto auxiliar
                //Paciente(int idPaciente, String tipoDoc, int numeroDoc, String apellido, String nombre/*, Date FechaNacimiento */) {
                Paciente paciente = new Paciente();
                //obtencion de los datos desde la bd.
                paciente.setIdPaciente(Integer.parseInt(resultado.getString("id_paciente")));
                paciente.setApellido(resultado.getString("apellido"));
                paciente.setDireccion(resultado.getString("direccion"));
                paciente.setEmail(resultado.getString("email"));              
                paciente.setFechaNacimiento(resultado.getDate("fecha_nacimiento"));           
                paciente.setNombre(resultado.getString("nombre"));        
                paciente.setNumeroDoc(Integer.parseInt(resultado.getString("numero_doc")));
                paciente.setSexo(resultado.getString("sexo"));
                paciente.setTelefono(resultado.getString("telefono"));
                paciente.setcelular(resultado.getString("celular"));
                paciente.setTipoDoc(resultado.getString("tipo_doc"));
                //agrego los datos del paciente a la lista 
                pacienteData.add(paciente);

            }
            //Cierre de consulta
            resultado.close();
            consulta.close();
            //Cierre de conexion. 
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);

        }
        return pacienteData;
    }

    /**
     * Método que retorna el último id insertado
     *
     * @return ultimo id
     */
    @Override
    public int getLastId() {
        // aux 
        int insertId = -1;
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "select Table_name, auto_increment from Information_Schema.TABLES where TABLE_NAME = 'pacientes'");

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                insertId = resultado.getInt("auto_increment");
                consulta.close();

            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);

        }
        return insertId;
    }

}
