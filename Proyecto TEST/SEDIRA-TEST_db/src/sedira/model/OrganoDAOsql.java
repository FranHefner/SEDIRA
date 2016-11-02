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
 * Clase de acceso a datos para Organos. Escrita en codigo SQL. Para motor
 * MySQL.
 *
 * @author Quelin Pablo , Hefner Francisco
 */
public class OrganoDAOsql implements IOrganoDAO {

    /**
     * Método que agrega un órgano a la base de datos
     *
     * @param organo
     * @param idPhantom
     */
    @Override
    public void agregarOrgano(Organo organo, int idPhantom) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreOrgano = organo.getNombreOrgano();

        try {
            if (buscaNombre(nombreOrgano, idPhantom)) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "INSERT INTO organos (nombre_organo, masa_organo, masa_total,id_phantom) "
                        + "VALUES(?,?,?,?)");
                consulta.setString(1, organo.getNombreOrgano());
                consulta.setDouble(2, organo.getOrganMass());
                consulta.setDouble(3, organo.getTotalMass());
                consulta.setInt(4, idPhantom);

                consulta.executeUpdate(); //Ejecucion de la consulta
                consulta.close();
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
                conexion.desconectar();

                // Mensaje de confirmacion
               /* Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El órgano fué agregado.");
                alerta.showAndWait();*/
            } else {

            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error en la inserción de la propiedad " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción del órgano " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método que modifica un órgano existente en la base de datos
     *
     * @param organo
     * @param idPhantom
     */
    @Override
    public void modificarOrgano(Organo organo, int idPhantom) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreOrgano = organo.getNombreOrgano();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "UPDATE organos SET nombre_organo = ?"
                    + ",masa_organo = ?"
                    + ", masa_total=?"
                    + ",id_phantom=? "
                    + "WHERE id_organo = ?");

            consulta.setString(1, organo.getNombreOrgano());
            consulta.setDouble(2, organo.getOrganMass());
            consulta.setDouble(3, organo.getTotalMass());
            consulta.setInt(4, idPhantom);
            consulta.setInt(5, organo.getIdOrgano());

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            // System.out.println("Ocurrió un error en la modificación del órgano " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error en la modificación del órgano " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método que elimina un órgano de la base de datos.
     *
     * @param id identificador del órgano a eliminar.
     */
    @Override
    public void eliminarOrgano(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM organos WHERE id_organo = ?");
            consulta.setInt(1, id);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();

            // Mensaje de confirmacion
            /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El órgano fué eliminado. ");
            alerta.showAndWait();*/

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al eliminar el órgano \n" + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el organo \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Método para buscar en la base de datos el nombre de un órgano
     *
     * @param nombreOrgano
     * @param idPhantom
     * @return True si el nombre no existe. False si el nombre existe
     * @throws SQLException
     */
    @Override
    public boolean buscaNombre(String nombreOrgano, int idPhantom) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT nombre_organo FROM organos "
                    + "WHERE nombre_organo = ?"
                    + "AND id_phantom = ?");
            consulta.setString(1, nombreOrgano);
            consulta.setInt(2, idPhantom);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                //JOptionPane.showMessageDialog(null, "El radionúclido que desea insertar ya existe","Información",JOptionPane.INFORMATION_MESSAGE);
                //System.out.println();
                return false;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return true;
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }
    @Override
    public ObservableList listadoOrganos(){
    
          //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        ObservableList listado = FXCollections.observableArrayList();
        

        try {
           PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM lista_organos");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            //obtencion de los datos desde la bd.
            while (resultado.next()) {
                listado.add(   resultado.getString("nombre_organo"));
              
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
   
        }
        return listado;
    }
}
