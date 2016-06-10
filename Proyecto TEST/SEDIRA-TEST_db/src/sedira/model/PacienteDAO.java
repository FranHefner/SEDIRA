/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 * Clase para el acceso de datos de Pacientes. 
 * @author Quelin Pablo, Hefner Francisco
 * 
 */
public class PacienteDAO {
    
    public static void agregarPaciente (Paciente paciente){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        int dni = paciente.getNumeroDoc();
        
        try {
            if (buscaDni(dni)) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "INSERT INTO pacientes (tipo_doc, numero_doc, apellido, nombre)"
                        + "VALUES ("
                                + "?,?,?,?"
                                + ")"
                );
                
                /**
                 * CONSULTA COMPLETA
                 * "INSERT INTO pacientes (tipo_doc, numero_doc, apellido, nombre, fecha_nacimiento, direccion"
                        + "numero_asociado, email, telefono, foto, sexo, en_tratamiento)"
                        + "VALUES ("
                                + "?,?,?,?,?,?,?,?,?,?,?,?"
                                + ")"
                 */
                consulta.setString(1, paciente.getTipoDoc());
                consulta.setInt(2,paciente.getNumeroDoc());
                consulta.setString(3,paciente.getApellido());
                consulta.setString(4,paciente.getNombre());
                //parametros acotados para simplicidad del test 
                
                //consulta.setDate(5, (Date) paciente.getFechaNacimientoDATE());
                //consulta.setString(6, paciente.getDireccion());
                //consulta.setInt(7, paciente.getNumeroAsociado());
                //consulta.setString(8,paciente.getEmail());
                //consulta.setString(9,paciente.getTelefono());
                //consulta.setBlob(10,paciente.getFoto());
                //consulta.setBoolean(11, paciente.isEnTratamiento());
                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El paciente fué agregado.");
                alerta.showAndWait();
            } else {

            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la inserción del paciente " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción del paciente " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void eliminarPaciente(int id) {
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
            alerta.setHeaderText(null);
            alerta.setContentText("El paciente fué eliminado. ");
            alerta.showAndWait();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al eliminar el paciente \n" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el paciente \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void modificarPaciente(Paciente paciente) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        int dni= paciente.getNumeroDoc();
        int id=paciente.getIdPaciente();

        try {
            //Validar . Estaba con DNI, pero al modificar a un paciente el dni se mantiene. 
            
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE  pacientes SET tipo_doc = ?,"
                                + "numero_doc=?,"
                                + "apellido=?,"
                                + "nombre=?,"
                                + "fecha_nacimiento=?"
                        + " WHERE id_paciente = ?"
                );

                /**
                 * COMPLETAR CONSULTA COMPLETA PARA TODOS LOS ATRIBUTOS. 
                 */
                consulta.setString(1, paciente.getTipoDoc());
                consulta.setInt(2, paciente.getNumeroDoc());
                consulta.setString(3, paciente.getApellido());
                consulta.setString(4, paciente.getNombre());
                consulta.setString(5, paciente.getFechaNacimientoDB());                
                consulta.setInt(6,id);
                //parametros acotados para simplicidad del test 

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
                conexion.desconectar();

                // Mensaje de confirmacion
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El paciente fué modificado.");
                alerta.showAndWait();
            
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la modificación  del paciente " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la modificación del paciente " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    /**
     * Método que busca si el paciente ya está ingresado por su dni. 
     * @param dni
     * @return True si no existe, False si existe. 
     */
    public static boolean buscaDni(int dni) {
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
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }
    /**
     * Método que retorna la lista completa de pacientes 
     * @return Lista de pacientes. 
     * @throws SQLException 
     */
    public static ObservableList <Paciente> obtenerPacientes () throws SQLException{
          //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Creo una lista auxiliar
        ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM pacientes ");
   
            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            
            while (resultado.next()) {
                //objeto auxiliar
                //Paciente(int idPaciente, String tipoDoc, int numeroDoc, String apellido, String nombre/*, Date FechaNacimiento */) {
                Paciente paciente = new Paciente(0,"",0,"","");
                 //obtencion de los datos desde la bd.
                paciente.setIdPaciente(Integer.parseInt(resultado.getString("id_paciente")));
                paciente.setApellido(resultado.getString("apellido"));
          //      paciente.setDireccion(resultado.getString("direccion"));
             //   paciente.setEmail(resultado.getString("email"));
                paciente.setEnTratamiento(resultado.getBoolean("en_tratamiento"));
                paciente.setFechaNacimiento(resultado.getDate("fecha_nacimiento"));
                //paciente.setFoto(resultado.getBlob("foto"));
                paciente.setNombre(resultado.getString("nombre"));
                //paciente.setNumeroAsociado(Integer.parseInt(resultado.getString("numero_asociado")));
                paciente.setNumeroDoc(Integer.parseInt(resultado.getString("numero_doc")));
                //paciente.setSexo(resultado.getString("sexo"));
                paciente.setTelefono(resultado.getString("telefono"));
                paciente.setcelular(resultado.getString("telefono"));
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
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            
        }
        return pacienteData;
    }
    
    /**
     * Método que retorna el último id insertado
     *
     * @return ultimo id
     */
    public static int getLastId() {
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
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);

        }
        return insertId;
    }
}
