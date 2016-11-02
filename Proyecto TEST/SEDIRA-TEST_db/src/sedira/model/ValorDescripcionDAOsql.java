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
import javax.swing.JOptionPane;
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
     * @param phantom Si se trata de un phantom que hace la llamada, True
     * @param radionuclido Si se trata de un radionuclido que hace la llamda,
     * False
     * @throws SQLException
     */
    @Override
    public void agregarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String unidad = vd.getUnidad();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO valordescripcion (descripcion, valor, unidad, id_radionuclido,id_phantom) "
                    + "VALUES(?,?,?,?,?)");
            consulta.setString(1, vd.getDescripcion());
            consulta.setString(2, vd.getValor());
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
            conexion.desconectar();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
            
            // Mensaje de confirmacion
           /* Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El item: \n " +vd.getDescripcion() +"\n fué agregado.");
            alerta.showAndWait();*/
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error en la inserción de la propiedad " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción de la propiedad " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método que modifica un ítem para las tablas de Valor Descripcion.
     * Funciona tanto para radionúclidos como para Phantoms
     *
     * @param vd item a modificar
     * @param id Identificacion de la entidad que hace la llamada al metodo.
     * @param phantom
     * @param radionuclido
     */
    @Override
    public void modificarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Identificador del item a modificar.
        int itemId = vd.getId();
        try {
            //if (buscaNombre(vd.getDescripcion())) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE valordescripcion SET descripcion = ?"
                        + ",valor = ?"
                        + ",unidad = ?"
                        + ",id_radionuclido = ?"
                        + ",id_phantom = ? "
                        + "WHERE id_valordescripcion = ?");
                consulta.setString(1, vd.getDescripcion());
                consulta.setString(2, vd.getValor());
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
                conexion.desconectar();

                // Mensaje de confirmacion
               /* Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El item: \n " +vd.getDescripcion() +"\n fué modificado.");
                alerta.showAndWait();*/
                // JOptionPane.showMessageDialog(null, "La propiedad " + vd.getDescripcion() + " fué agregada con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE); 
            //}

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
            
            
            // Mensaje de confirmacion
           /* Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El ítem fué eliminado. ");
            alerta.showAndWait();*/

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            //System.out.print(e);
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
    public ObservableList listadoPropiedades(){
    
          //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        ObservableList listado = FXCollections.observableArrayList();
        

        try {
           PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM lista_propiedades");

            //Ejecucion de la consulta. 
            ResultSet resultado = consulta.executeQuery();
            //obtencion de los datos desde la bd.
            while (resultado.next()) {
                listado.add(   resultado.getString("nombre_propiedad"));
              
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
   
        }
        return listado;
    }
   
    
    
}
