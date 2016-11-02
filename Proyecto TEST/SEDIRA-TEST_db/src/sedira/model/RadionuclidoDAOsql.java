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
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
import sedira.CodigosErrorSQL;

/**
 * Clase de acceso a datos para Radionúclidos.
 *
 * @author Quelin Pablo, Hefner Francisco.
 */
public class RadionuclidoDAOsql implements IRadionuclidoDAO {
    
    /**
     * Método que retorna la lista completa de radionúclidos almacenados en la
     * base de datos.
     *
     * @return
     */
    @Override
    public ObservableList<Radionuclido> obtenerListaRadNuclido() {
        //Creo una lista auxiliar
        ObservableList<Radionuclido> radionuclidoData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM radionuclidos");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                Radionuclido radionuclido = new Radionuclido(0, "", null);
                
                radionuclido.setIdRadNuclido(Integer.parseInt(resultado.getString("id_radionuclido")));
                radionuclido.setNombreRadNuclido(resultado.getString("nombre_radionuclido"));
                radionuclidoData.add(radionuclido);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el radionuclido /n" + e);
            //System.out.print(e);
        }
        
        return radionuclidoData;
    }
    
    /**
     * Metodo que retorna la lista de radionuclidos que tienen propiedades asociadas
     * @return 
     */
    @Override
    public ObservableList<Radionuclido> obtenerListaRadNuclidoCompletos() {
        //Creo una lista auxiliar
        ObservableList<Radionuclido> radionuclidoData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(""
                    + "SELECT * FROM radionuclidos "
                    + "INNER JOIN valordescripcion "
                    + "ON radionuclidos.id_radionuclido = valordescripcion.id_radionuclido "
                    + "GROUP BY radionuclidos.id_radionuclido");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                Radionuclido radionuclido = new Radionuclido(0, "", null);
                
                radionuclido.setIdRadNuclido(Integer.parseInt(resultado.getString("id_radionuclido")));
                radionuclido.setNombreRadNuclido(resultado.getString("nombre_radionuclido"));
                radionuclidoData.add(radionuclido);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //JOptionPane.showMessageDialog(null, "no se pudo consultar el radionuclido /n" + e);
            //System.out.print(e);
        }
        
        return radionuclidoData;
    }
    /**
     * Método que retorna la la lista de información para un radionúclido
     * seleccionado.
     *
     * @param radioNuclidoSeleccionado
     * @return
     */
    @Override
    public ObservableList<ValorDescripcion> obtenerInfoRadNuclido(Radionuclido radioNuclidoSeleccionado) {
        //Creo una lista auxiliar
        ObservableList<ValorDescripcion> infoRadNuclidoData = FXCollections.observableArrayList();
        
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //objeto auxiliar
        
        int idRadNuclido = radioNuclidoSeleccionado.getIdRadNuclido();
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM radionuclidos "
                            + "INNER JOIN valordescripcion "
                            + "ON radionuclidos.id_radionuclido = valordescripcion.id_radionuclido "
                            + "WHERE radionuclidos.id_radionuclido =" + idRadNuclido + ";"
            );
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //Ojeto Aux de tipo ValorDescripcion.
                ValorDescripcion infoRadNuclido = new ValorDescripcion(-1, "", "", "");
                //Completo el aux con la informacion obtenida de la BD
                infoRadNuclido.setId(Integer.parseInt(resultado.getString("id_valordescripcion")));
                infoRadNuclido.setDescripcion(resultado.getString("descripcion"));
                infoRadNuclido.setValor(resultado.getString("valor"));
                infoRadNuclido.setUnidad(resultado.getString("unidad"));
                
                //agregro al arreglo de propiedades la nueva propiedad parseada
                infoRadNuclidoData.add(infoRadNuclido);
                //agrego al radionuclido seleccionado la lista de propiedades
                radioNuclidoSeleccionado.setPropiedades(infoRadNuclidoData);
                
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (SQLException | NumberFormatException e) {
            CodigosErrorSQL.analizarExepcion((SQLException) e);
            JOptionPane.showMessageDialog(null, "no se pudo consultar la información del radionúclido /n" + e);
            System.out.print(e);
        }
        
        return infoRadNuclidoData;
    }
    /**
     * Método para agregar un radionúclido nuevo. Registra un nuevo radionúclido
     * a la base de datos. Pero previamente corrobora si existe el radionúclido
     * ya ingresado en la base de datos
     *
     * @param radionuclido 
     * @throws java.sql.SQLException
     */
    @Override
    public void agregarRadionuclido(Radionuclido radionuclido) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreRadNuclido = radionuclido.getNombreRadNuclido();
        try {
            //Antes de insertar corrobora que no exista el nombre
            if (buscaNombre(radionuclido.getNombreRadNuclido())) {
                Statement consulta = conexion.getConnection().createStatement();
                consulta.executeUpdate("INSERT INTO radionuclidos (nombre_radionuclido) VALUES ('" + radionuclido.getNombreRadNuclido() + "')");
                consulta.close();
                conexion.desconectar();

                // Mensaje de confirmacion
               /* Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El radionúclido "
                        + ""+nombreRadNuclido+" "
                        + "fué agregado.");
                alerta.showAndWait();*/
            } else {
                 /**
                  * Mensaje por error esta detallado en el metodo validarDatosEntrada. ABMRadionuclidos
                  */
           
            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error en la creación");
            
        }
    }
    /**
     * Método que elimina un radionúclido y todos sus órganos. 
     * @param idRadionuclido Identificador del radionuclido a eliminar. 
     */
    @Override
    public void eliminarRadionuclido (int idRadionuclido){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "DELETE FROM radionuclidos WHERE id_radionuclido = ?");
            consulta.setInt(1, idRadionuclido);
            //System.out.print(id);
            consulta.executeUpdate(); //Ejecucion de la consulta.
            consulta.close();
            conexion.desconectar();
            
            
            // Mensaje de confirmacion
            /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("El radionúclido fué eliminado. ");
            alerta.showAndWait();*/

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println("Ocurrió un error al eliminar el radionúclido \n" + e.getErrorCode());
            //JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar el radionúclido \n" + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    /**
     * Método para modificar el nombre de un radionúclido existente. 
     * @param radionuclido a modificar. 
     */
    @Override
    public void modificarNombreRadionuclido(Radionuclido radionuclido) {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //Auxiliar que contiene el nombre del radionuclido modificado.
        String nombreRadionuclido = radionuclido.getNombreRadNuclido();
        //Identificador del radionuclido. Solo para mecanismo de control.
        int id = radionuclido.getIdRadNuclido();
        
        try {
            //verificacion de la existencia.
            if (buscaNombre(radionuclido.getNombreRadNuclido())) {
                PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "UPDATE radionuclidos SET nombre_radionuclido = ? WHERE id_radionuclido = ?");
                //JOptionPane.showMessageDialog(null, "El radionúclido ha sido modificado!", "Información", JOptionPane.INFORMATION_MESSAGE);
                
                //Seteo las variables para la consulta.
                consulta.setString(1, nombreRadionuclido);
                consulta.setInt(2, id);
                //Ejecuto la consulta.
                consulta.executeUpdate();
                consulta.close();
                
                //Mensaje de confirmacion.
                /*Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Confirmación");
                alerta.setHeaderText(null);
                alerta.setContentText("El radionúclido fué modificado.");
                alerta.showAndWait();*/
                
            } else {
             /**
                  * Mensaje por error esta detallado en el metodo validarDatosEntrada. ABMRadionuclidos
                  */
            }
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            
            
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error en la modificación, " + e);
        }
    }
    /**
     * Método que busca valida si un radionúclido ya existe.
     *
     * @param nombreRadionuclido
     * @return True si no hay coincidencias. False si el nombre existe. 
     */
    @Override
    public boolean buscaNombre(String nombreRadionuclido) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT nombre_radionuclido FROM radionuclidos WHERE nombre_radionuclido = ?");
            consulta.setString(1, nombreRadionuclido);
            
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
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }
   
    
    
}
