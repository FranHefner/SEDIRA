/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase de acceso a datos para Objetos de tipo ValorDescripcion.
 *
 *  * @author Quelin Pablo, Hefner Francisco
 */
public class ValorDescripcionDAO {

    /**
     * Metodo que modifica un item para las tablas de Valor Descripcion.
     * Funciona tanto para radionuclidos como para Phantoms
     *
     * @param vd item a agregar
     * @param id Identificador de la entidad que hace la llamada al metodo
     * @param phantom Si se trata de un phantom que hace la llamada, True
     * @param radionuclido Si se trata de un radionuclido que hace la llamda,
     * False
     * @throws SQLException
     */
    public static void agregarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String unidad = vd.getUnidad();
        System.out.print(vd.getDescripcion());
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO valordescripcion (descripcion, valor, unidad, id_radionuclido,id_phantom) "
                    + "VALUES(?,?,?,?,?)");
            consulta.setString(1, vd.getDescripcion());
            consulta.setDouble(2, vd.getValor());
            consulta.setString(3, vd.getUnidad());

            if (phantom) { //Agrega item a un phantom
                consulta.setNull(4, java.sql.Types.NULL);
                consulta.setInt(5, id);
            }
            if (radionuclido) { //Agrega item a un radionuclido
                consulta.setInt(4, id);
                consulta.setNull(5, java.sql.Types.NULL);

            }

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la inserción de la propiedad " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción de la propiedad " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metodo que modifica un item para las tablas de Valor Descripcion.
     * Funciona tanto para radionuclidos como para Phantoms
     *
     * @param vd item a modificar
     * @param id Identificacion de la entidad que hace la llamada al metodo.
     * @param phantom
     * @param radionuclido
     */
    public static void modificarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Identificador del item a modificar.
        int itemId = vd.getId();
        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "UPDATE valordescripcion SET descripcion = ?"
                    + ",valor = ?"
                    + ",unidad = ?"
                    + ",id_radionuclido = ?"
                    + ",id_phantom = ? "
                    + "WHERE id_valordescripcion = ?");
            consulta.setString(1, vd.getDescripcion());
            consulta.setDouble(2, vd.getValor());
            consulta.setString(3, vd.getUnidad());

            if (phantom) { //Modifica item a un phantom
                consulta.setNull(4, java.sql.Types.NULL);
                consulta.setInt(5, id);
                consulta.setInt(6, itemId);
            }
            if (radionuclido) { //Modifica item a un radionuclido
                consulta.setInt(4, id);
                consulta.setNull(5, java.sql.Types.NULL);
                consulta.setInt(6, itemId);

            }

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
           // JOptionPane.showMessageDialog(null, "La propiedad " + vd.getDescripcion() + " fué agregada con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la modificación de la propiedad \n" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la modificación de la propiedad \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metodo que elimina un item de una tabla de tipo ValorDescripcion.
     *
     * @param id identificador del item a eliminar.
     * @return true si pudo eliminar o false si fallo la eliminacion.
     */
    public static void eliminarItem(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {

            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM valordescripcion WHERE id_valordescripcion = ?");
            consulta.setInt(1, id);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al eliminar el item \n" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el item \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
