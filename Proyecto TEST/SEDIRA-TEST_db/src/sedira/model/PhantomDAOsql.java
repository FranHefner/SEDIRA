/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import sedira.CodigosErrorSQL;

/**
 * Clase de Acceso de datos para objetos de tipo Phamtonm
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class PhantomDAOsql implements IPhantomDAO {

    /**
     * Metodo que retorna la lista completa de phantoms de la base de datos
     *
     * @return phantomData
     */
    @Override
    public ObservableList<Phantom> obtenerListaPhantom() {
        //Creo una lista auxiliar
        ObservableList<Phantom> phantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM phantoms");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                // parametros de inicializacion del contructor (id, nombre, listaPropiedades, listaOrganos)
                Phantom phantom = new Phantom(0, "", 0, null, null);
                //obtencion de los datos desde la bd.
                phantom.setIdPhantom(Integer.parseInt(resultado.getString("id_phantom")));
                phantom.setPhantomNombre(resultado.getString("nombre_phantom"));
                //agrego el objeto a la lista de phantom. Esta se retornada para completar la vista.
                phantomData.add(phantom);

            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (SQLException | NumberFormatException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return phantomData;
    }

    /**
     * Método que retorna la lista de phantoms que tienen organos asignados
     *
     * @return phantomData
     */
    @Override
    public ObservableList<Phantom> obtenerListaPhantomCompletos() {
        //Creo una lista auxiliar
        ObservableList<Phantom> phantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM phantoms "
                    + "INNER JOIN organos "
                    + "ON phantoms.id_phantom = organos.id_phantom "
                    + "INNER JOIN valordescripcion "
                    + "ON phantoms.id_phantom = valordescripcion.id_phantom "
                    + "GROUP BY phantoms.id_phantom");

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                // parametros de inicializacion del contructor (id, nombre, listaPropiedades, listaOrganos)
                Phantom phantom = new Phantom(0, "", 0, null, null);
                //obtencion de los datos desde la bd.
                phantom.setIdPhantom(Integer.parseInt(resultado.getString("id_phantom")));
                phantom.setPhantomNombre(resultado.getString("nombre_phantom"));
                //agrego el objeto a la lista de phantom. Esta se retornada para completar la vista.
                phantomData.add(phantom);

            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return phantomData;
    }

    /**
     * Método que retorna la información completa de tipo valor descripción que
     * contiene un phantom.
     *
     * @param phantomSeleccionado
     * @return
     */
    @Override
    public ObservableList<ValorDescripcion> obtenerInfoPhantom(Phantom phantomSeleccionado) {
        //Creo una lista auxiliar
        ObservableList<ValorDescripcion> infoPhantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //objeto auxiliar
        int idPhantom = phantomSeleccionado.getIdPhantom();
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM phantoms "
                    + "INNER JOIN valordescripcion "
                    + "ON phantoms.id_phantom = valordescripcion.id_phantom "
                    + "WHERE phantoms.id_phantom =" + idPhantom + ";"
            );
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //Ojeto Aux de tipo ValorDescripcion.
                ValorDescripcion infoPhantom = new ValorDescripcion(-1, "", "", "");

                //Completo el aux con la informacion obtenida de la BD
                infoPhantom.setId(Integer.parseInt(resultado.getString("id_valordescripcion")));
                infoPhantom.setDescripcion(resultado.getString("descripcion"));
                infoPhantom.setValor(resultado.getString("valor"));
                infoPhantom.setUnidad(resultado.getString("unidad"));

                //agregro al arreglo de propiedades la nueva propiedad parseada
                infoPhantomData.add(infoPhantom);
                //agrego al phantom seleccionado la lista de propiedades
                phantomSeleccionado.setPropiedades(infoPhantomData);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return infoPhantomData;
    }

    /**
     * Metodo que agrega un Phantom a la base da datos
     *
     * @param phantom que se insertara en la base de datos
     */
    @Override
    public void agregarPhantom(Phantom phantom) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombrePhantom = phantom.getPhantomNombre();
        try {
            //Antes de insertar corrobora que no exista el nombre
            if (buscaNombre(nombrePhantom)) {
                Statement consulta = conexion.getConnection().createStatement();
                consulta.executeUpdate("INSERT INTO phantoms (nombre_phantom) VALUES ('" + nombrePhantom + "')");
                consulta.close();
                conexion.desconectar();
                //Mensaje de confirmacion
                /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                 alerta.setTitle("Confirmación");
                 alerta.setHeaderText(null);
                 alerta.setContentText("El phantom fué " + nombrePhantom + " agregado.");
                 alerta.showAndWait();*/

            } else {
                //Las validaciones se encuentran en ABMphantomController 
                //JOptionPane.showMessageDialog(null, "El phantom " + nombrePhantom + " ya existe!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

    }

    /**
     * Metodo que modifica el nombre de un phantom existente.
     *
     * @param phantom a modificar
     */
    @Override
    public void modificarNombrePhantom(Phantom phantom) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombrePhantom = phantom.getPhantomNombre();
        int id = phantom.getIdPhantom();
        try {
            //Antes de insertar corrobora que no exista el nombre
            if (buscaNombre(nombrePhantom)) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE phantoms SET nombre_phantom = ?"
                        + "WHERE id_phantom=?");

                consulta.setString(1, nombrePhantom);
                consulta.setInt(2, id);
                //Ejecuto la consulta
                consulta.executeUpdate();
                consulta.close();
                conexion.desconectar();

                //Mensaje de confirmacion
                /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                 alerta.setTitle("Confirmación");
                 alerta.setHeaderText(null);
                 alerta.setContentText("El phantom fué modificado");
                 alerta.showAndWait();*/
            } else {
                //Las validaciones se encuentran en ABMphantomController 
                //JOptionPane.showMessageDialog(null, "El phantom " + nombrePhantom + " ya existe!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

    }

    /**
     * Método que elimina un phanton completo. Organos y propiedades tambien
     * seran eliminados
     *
     * @param id Identificador del phantom a eliminar.
     */
    @Override
    public void eliminarPhantom(int id) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM phantoms WHERE id_phantom = ?");
            consulta.setInt(1, id);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();

            // Mensaje de confirmacion
            /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
             alerta.setTitle("Confirmación");
             alerta.setHeaderText(null);
             alerta.setContentText("El phantom fué eliminado. ");
             alerta.showAndWait();*/
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

    }

    /**
     * Método que busca en la base de datos si el nombre del Phantom ya existe.
     *
     * @param nombrePhantom
     * @return
     * @throws SQLException
     */
    @Override
    public boolean buscaNombre(String nombrePhantom) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT nombre_phantom FROM phantoms WHERE nombre_phantom = ?");
            consulta.setString(1, nombrePhantom);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
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

    /**
     * Método que retorna la lista de los órganos que contiene un phantom.
     *
     * @param phantomSeleccionado
     * @return
     */
    @Override
    public ObservableList<Organo> obtenerInfoOrgano(Phantom phantomSeleccionado) {
        //Creo una lista auxiliar de tipo organo
        ObservableList<Organo> organoPhantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //objeto auxiliar
        int idPhantom = phantomSeleccionado.getIdPhantom();
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM phantoms "
                    + "INNER JOIN organos "
                    + "ON phantoms.id_phantom = organos.id_phantom "
                    + "WHERE phantoms.id_phantom =" + idPhantom + ";"
            );
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //Ojeto Aux de tipo ValorDescripcion.
                Organo organoPhantom = new Organo(-1, "", 0.0, 0.0);

                //Completo el aux con la informacion obtenida de la BD
                organoPhantom.setIdOrgano(Integer.parseInt(resultado.getString("id_organo")));
                organoPhantom.setNombreOrgano(resultado.getString("nombre_organo"));
                organoPhantom.setOrganMass(Double.parseDouble(resultado.getString("masa_organo")));
                organoPhantom.setTotalMass(Double.parseDouble(resultado.getString("masa_total")));

                //agregro al arreglo de propiedades la nueva propiedad parseada
                organoPhantomData.add(organoPhantom);
                //agrego al phantom seleccionado la lista de propiedades
                phantomSeleccionado.setOrgano(organoPhantomData);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
        }

        return organoPhantomData;
    }

}
