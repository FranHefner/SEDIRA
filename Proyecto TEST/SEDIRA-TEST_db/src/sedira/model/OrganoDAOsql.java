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

        boolean nuevoOrgano = false;
        int Id_organo = 1;

        String nombreOrgano = organo.getNombreOrgano();

        try {
            conexion.getConnection().setAutoCommit(false);

            PreparedStatement sqlOrgano = conexion.getConnection().prepareStatement(
                    "  INSERT INTO organos ( id_organo, nombre ) "
                    + "  VALUES ( ?,? )");

            if (buscaNombre(nombreOrgano) == false) {

                nuevoOrgano = true;

                PreparedStatement consultaObtenerIdOrgano = conexion.getConnection().prepareStatement(
                        "SELECT IFNULL(MAX(id_organo) + 1,1) AS SIGUIENTE FROM organos");

                ResultSet rsIdOrgano = consultaObtenerIdOrgano.executeQuery();

                while (rsIdOrgano.next()) {
                    Id_organo = rsIdOrgano.getInt("SIGUIENTE");

                }

                sqlOrgano.setInt(1, Id_organo);
                sqlOrgano.setString(2, organo.getNombreOrgano());

                sqlOrgano.executeUpdate();
            } else {
                PreparedStatement buscarId = conexion.getConnection().prepareStatement(
                        " SELECT id_organo FROM organos "
                        + " WHERE nombre = ?");

                buscarId.setString(1, organo.getNombreOrgano());

                ResultSet IdOrganoExistente = buscarId.executeQuery();

                while (IdOrganoExistente.next()) {
                    Id_organo = IdOrganoExistente.getInt("id_organo");

                }

            }

            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            PreparedStatement sqlOrganoPhantom = conexion.getConnection().prepareStatement(
                    " INSERT INTO organos_phantoms ( "                  
                    + " id_organo "
                    + " ,id_phantom "
                    + "  ,masa_organo "
                    + " ) VALUES ( "
                    + " ?  "
                    + " ,?  "
                    + " ,?  "
                    + " )");

            sqlOrganoPhantom.setInt(1, Id_organo);
            sqlOrganoPhantom.setInt(2, idPhantom);
            sqlOrganoPhantom.setDouble(3, organo.getOrganMass());

            sqlOrganoPhantom.executeUpdate();

            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);

            if (nuevoOrgano) {
                sqlOrgano.close();
            }
            sqlOrganoPhantom.close();

            conexion.desconectar();

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

            conexion.getConnection().setAutoCommit(false);

            PreparedStatement sqlOrgano = conexion.getConnection().prepareStatement(
                    "  UPDATE organos SET  "
                    + " nombre = ?"
                    + " WHERE  id_organo = ?");

            sqlOrgano.setString(1, organo.getNombreOrgano());
            sqlOrgano.setInt(2, organo.getIdOrgano());

            PreparedStatement sqlOrganoPhantom = conexion.getConnection().prepareStatement(
                    "  UPDATE organos_phantoms SET  "
                    + "  masa_organo = ? "
                    + "  WHERE id_phantom = ? AND  id_organo = ? ");

            sqlOrganoPhantom.setDouble(1, organo.getOrganMass());
            sqlOrganoPhantom.setInt(2, idPhantom);
            sqlOrganoPhantom.setInt(3, organo.getIdOrgano());

            sqlOrgano.executeUpdate();
            sqlOrganoPhantom.executeUpdate();

            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);

            sqlOrgano.close();
            sqlOrganoPhantom.close();

            conexion.desconectar();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);

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
    public void eliminarOrgano(int idOrgano, int idPhantom) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        boolean OrganoEliminado = false;
        try {

            conexion.getConnection().setAutoCommit(false);

            PreparedStatement sqlOrganoPhantom = conexion.getConnection().prepareStatement(
                    "DELETE FROM organos_phantoms WHERE id_organo = ? AND id_phantom = ? ");
            sqlOrganoPhantom.setInt(1, idOrgano);
            sqlOrganoPhantom.setInt(2, idPhantom);

            PreparedStatement sqlOrgano = conexion.getConnection().prepareStatement(
                    "DELETE FROM organos WHERE id_organo = ?");
            sqlOrgano.setInt(1, idOrgano);

            if (buscarReferenciaOrgano(idOrgano)) {

                // Si existe referencias no se elimina el organo
            } else {
                OrganoEliminado = true;
                sqlOrgano.executeUpdate();

            }

            sqlOrganoPhantom.executeUpdate();

            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);

            if (OrganoEliminado) {
                sqlOrgano.close();
            }

            sqlOrganoPhantom.close();

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
    public boolean buscaNombre(String nombreOrgano) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT nombre FROM organos "
                    + "WHERE nombre = ?");

            consulta.setString(1, nombreOrgano);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                //JOptionPane.showMessageDialog(null, "El radionúclido que desea insertar ya existe","Información",JOptionPane.INFORMATION_MESSAGE);
                //System.out.println();
                return true;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return false;
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }

    @Override
    public boolean buscarReferenciaOrgano(int idOrgano) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " SELECT * FROM organos_phantoms  "
                    + " WHERE id_organo = ?");

            consulta.setInt(1, idOrgano);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                //JOptionPane.showMessageDialog(null, "El radionúclido que desea insertar ya existe","Información",JOptionPane.INFORMATION_MESSAGE);
                //System.out.println();
                return true;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return false;
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }

    @Override
    public ObservableList listadoOrganos() {

        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        ObservableList listado = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM organos");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            //obtencion de los datos desde la bd.
            while (resultado.next()) {
                listado.add(resultado.getString("nombre"));

            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);

        }
        return listado;
    }
    
    @Override
    public ObservableList<ValorDescripcion> obtenerInfoOrgano(Organo organoSeleccionado) {
        //Creo una lista auxiliar
        ObservableList<ValorDescripcion> infoOrganoData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //objeto auxiliar
        int idOrgano = organoSeleccionado.getIdOrgano();
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                     "   SELECT  "
                     + "    VD.id_valordescripcion, "
                     + "    VD.descripcion,"
                     + "    VD.valor,"
                     + "    VD.unidad"
                     + " FROM "
                     + "    organos_phantoms OP"
                     + " JOIN organos_valordescripcion OVD"
                     + "   ON OP.id_organo_phantom = OVD.id_organo_phantom "
                     + " JOIN valordescripcion VD "
                     + "   ON VD.id_valordescripcion = OVD.id_valordescripcion "
                     + " WHERE OP.id_organo = " + idOrgano + ";"     );           
        
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //Ojeto Aux de tipo ValorDescripcion.
                ValorDescripcion infoOrgano = new ValorDescripcion(-1, "", "", "");

                //Completo el aux con la informacion obtenida de la BD
                infoOrgano.setId(Integer.parseInt(resultado.getString("id_valordescripcion")));
                infoOrgano.setDescripcion(resultado.getString("descripcion"));
                infoOrgano.setValor(resultado.getString("valor"));
                infoOrgano.setUnidad(resultado.getString("unidad"));

                //agregro al arreglo de propiedades la nueva propiedad parseada
                infoOrganoData.add(infoOrgano);
                //agrego al phantom seleccionado la lista de propiedades
                organoSeleccionado.setPropiedades(infoOrganoData);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return infoOrganoData;
    }

}
        

