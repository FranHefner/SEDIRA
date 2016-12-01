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
     * Método que modifica un ítem para las tablas de Valor Descripcion.
     * Funciona tanto para radionúclidos como para Phantoms
     *
     * @param vd item a agregar
     * @param id Identificador de la entidad que hace la llamada al metodo
     * @throws SQLException
     */
    @Override
    public void agregarItem(ValorDescripcion vd, int id, String Tabla) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String unidad = vd.getUnidad();
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
                        "   INSERT INTO organos_valordescripcion (id_organo,id_valordescripcion) "
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
            //System.out.println("Ocurrió un error en la inserción de la propiedad " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción de la propiedad " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método que modifica un ítem para las tablas de Valor-Descripcion.
     * Funciona tanto para radionúclidos como para Phantoms
     *
     * @param vd item a modificar
     * @param id Identificador del item a modificar. .
     
     */
    @Override
    public void modificarItem(ValorDescripcion vd, int id, String Tabla) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Identificador del item a modificar.
        int itemId = vd.getId();
        try {
            //if (buscaNombre(vd.getDescripcion())) {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "  UPDATE valordescripcion SET descripcion = ?,"
                    + "valor = ?,"
                    + "unidad = ?"
                    + "WHERE id_valordescripcion = ?");
            

            consulta.setString(1, vd.getDescripcion());
            consulta.setString(2, vd.getValor());
            consulta.setString(3, vd.getUnidad());
            consulta.setInt(4, itemId);
            
            //System.out.print(id);
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
     * @return True si no hay coincidencias. False si el nombre existe.
     * @throws java.sql.SQLException
     */
    @Override
    public boolean buscaNombre(String propiedad) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT descripcion FROM valordescripcion WHERE descripcion = ?");
            consulta.setString(1, propiedad);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                //JOptionPane.showMessageDialog(null, "El radionúclido que desea insertar ya existe","Información",JOptionPane.INFORMATION_MESSAGE);
                //System.out.println();
                resultado.close();
                conexion.desconectar();

                return false;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return true;
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
                  + "   "+ Entidad+ "_valordescripcion "
                  + "   JOIN "
                  + "   valordescripcion "
                  + "   ON "+ Entidad+ "_valordescripcion.id_valordescripcion = "
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
