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
import sedira.CodigosErrorSQL;

/**
 * Clase de acceso a datos para Objetos de tipo ValorDescripcion.
 *
 *  * @author Quelin Pablo, Hefner Francisco
 */
public class ValorDescripcionDAOsql implements IValorDescripcionDAO {

    /**
     * Método que agrega un ítem para las tablas de valordescripcion
     * organos_valordescripcion phantoms_valordescripcion
     * radionuclidos_valordescripcion
     *
     * @param vd item a agregar
     * @param id Identificador de la entidad que hace la llamada al metodo
     * @param Tabla
     * @throws SQLException
     */
    @Override
    public void agregarItem(ValorDescripcion vd, int id, String Tabla) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        int itemId = getLastId();

        conexion.getConnection().setAutoCommit(false);

        try {
            PreparedStatement sqlValorDescripcion = conexion.getConnection().prepareStatement(
                    "  INSERT INTO valordescripcion ("
                    + "descripcion ,valor,unidad) "
                    + "VALUES (?,?,?)");

            //sqlValorDescripcion.setInt(1, itemId);
            sqlValorDescripcion.setString(1, vd.getDescripcion());
            sqlValorDescripcion.setString(2, vd.getValor());
            sqlValorDescripcion.setString(3, vd.getUnidad());

            PreparedStatement sqlTabla_valordescripcion = conexion.getConnection().prepareStatement("");

            if (Tabla.equals("organos")) {
                sqlTabla_valordescripcion = conexion.getConnection().prepareStatement(
                        "   INSERT INTO organos_valordescripcion (id_organo_phantom,id_valordescripcion) "
                        + "VALUES (?,?)");

                sqlTabla_valordescripcion.setInt(1, id);
                sqlTabla_valordescripcion.setInt(2, itemId);

            }
            if (Tabla.equals("phantoms")) {
                sqlTabla_valordescripcion = conexion.getConnection().prepareStatement(
                        "  INSERT INTO phantoms_valordescripcion (id_phantom,id_valordescripcion) "
                        + "VALUES (?,?)");

                sqlTabla_valordescripcion.setInt(1, id);
                sqlTabla_valordescripcion.setInt(2, itemId);

            }
            if (Tabla.equals("radionuclidos")) {
                sqlTabla_valordescripcion = conexion.getConnection().prepareStatement(
                        "   INSERT INTO radionuclidos_valordescripcion (id_radionuclido,id_valordescripcion) "
                        + "VALUES (?,?)");

                sqlTabla_valordescripcion.setInt(1, id);
                sqlTabla_valordescripcion.setInt(2, itemId);

            }

            sqlValorDescripcion.executeUpdate();
            sqlTabla_valordescripcion.executeUpdate();

            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);

            sqlValorDescripcion.close();
            sqlTabla_valordescripcion.close();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
        }
    }

    /**
     * Método que modifica un ítem para las tablas valordescripcion
     * organos_valordescripcion phantoms_valordescripcion
     * radionuclidos_valordescripcion
     *
     * @param vd item a modificar
     * @param id Identificador del item a modificar. .
     * @param Tabla Relacion que hace el llamado de modificacion. *
     */
    @Override
    public void modificarItem(ValorDescripcion vd, int id, String Tabla) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Identificador del item a modificar.

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "  UPDATE valordescripcion "
                    + "SET descripcion = ?,"
                    + "valor = ?,"
                    + "unidad = ?"
                    + "WHERE id_valordescripcion = ?");

            consulta.setString(1, vd.getDescripcion());
            consulta.setString(2, vd.getValor());
            consulta.setString(3, vd.getUnidad());
            consulta.setInt(4, id);

            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }
    }

    /**
     * Método que elimina un ítem de una tabla de tipo ValorDescripcion.
     *
     * @param id identificador del item a eliminar.
     */
    @Override
    public void eliminarItem(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM valordescripcion WHERE id_valordescripcion = ?");
            consulta.setInt(1, id);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);

        }
    }

    /**
     * Método que busca si una propiedad ya existe.
     *
     * @param propiedad
     * @param Entidad
     * @param idEntidad
     * @return True si el nombre exixte. False si el nombre no existe.
     * @throws java.sql.SQLException
     */
    @Override
    public boolean buscaNombre(String propiedad, String Entidad, int idEntidad) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("");
            //IF (Entidad = radionuclidos)
            // if (Entidad  = phantoms) 
            if (Entidad.equals("radionuclidos")) {
                consulta = conexion.getConnection().prepareStatement(
                        "SELECT valordescripcion.descripcion "
                        + "FROM radionuclidos_valordescripcion "
                        + "JOIN valordescripcion "
                        + "ON valordescripcion.id_valordescripcion = radionuclidos_valordescripcion.id_valordescripcion "
                        + "JOIN radionuclidos "
                        + "ON radionuclidos.id_radionuclido = radionuclidos_valordescripcion.id_radionuclido "
                        + "WHERE valordescripcion.descripcion = ? "
                        + "AND radionuclidos.id_radionuclido = ?");

                consulta.setString(1, propiedad);
                consulta.setInt(2, idEntidad);

            }
            if (Entidad.equals("phantoms")) {
                consulta = conexion.getConnection().prepareStatement(
                        "SELECT valordescripcion.descripcion "
                        + "FROM phantoms_valordescripcion "
                        + "JOIN valordescripcion "
                        + "ON valordescripcion.id_valordescripcion = phantoms_valordescripcion.id_valordescripcion "
                        + "JOIN phantoms "
                        + "ON phantoms.id_phantom = phantoms_valordescripcion.id_phantom "
                        + "WHERE valordescripcion.descripcion = ? "
                        + "AND phantoms.id_phantom = ?");

                consulta.setString(1, propiedad);
                consulta.setInt(2, idEntidad);

            }
            if (Entidad.equals("organos")) {
                consulta = conexion.getConnection().prepareStatement(
                        "SELECT organos.id_organo, valordescripcion.descripcion "
                        + "FROM organos_valordescripcion "
                        + "JOIN valordescripcion "
                        + "ON valordescripcion.id_valordescripcion = organos_valordescripcion.id_valordescripcion "
                        + "JOIN organos_phantoms "
                        + "ON organos_phantoms.id_organo_phantom = organos_valordescripcion.id_organo_phantom "
                        + "JOIN organos "
                        + "ON organos.id_organo = organos_phantoms.id_organo "
                        + "WHERE organos.id_organo = ? "
                        + "AND valordescripcion.descripcion = ?");

                
                consulta.setInt(1, idEntidad);
                consulta.setString(2, propiedad);

            }

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                resultado.close();
                conexion.desconectar();

                return true;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return false;
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);

            return false;
        }
    }

    @Override
    public ObservableList listadoPropiedades(String Entidad) {

        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        ObservableList listado = FXCollections.observableArrayList();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    " SELECT "
                    + "   valordescripcion.descripcion  AS nombre"
                    + "  FROM "
                    + "   " + Entidad + "_valordescripcion "
                    + "   JOIN "
                    + "   valordescripcion "
                    + "   ON " + Entidad + "_valordescripcion.id_valordescripcion = "
                    + "       valordescripcion.id_valordescripcion "
                    + "  GROUP BY descripcion  ");

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
    public int getLastId() {
        // aux 
        int insertId = -1;
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "select Table_name, auto_increment from Information_Schema.TABLES where TABLE_NAME = 'valordescripcion'");

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
