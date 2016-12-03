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
                radionuclido.setNombreRadNuclido(resultado.getString("nombre"));
                radionuclidoData.add(radionuclido);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
          
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
                  + " SELECT"
                  + " * "
                  + " FROM"
                  + " radionuclidos R"
                  + " WHERE"
                  + " EXISTS"
                  + " (SELECT"
                  + "    *"
                  + "  FROM"
                  + "   radionuclidos_valordescripcion RV"
                  + " WHERE"
                  + "   RV.id_radionuclido = R.id_radionuclido)");
                   ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                Radionuclido radionuclido = new Radionuclido(0, "", null);
                
                radionuclido.setIdRadNuclido(Integer.parseInt(resultado.getString("id_radionuclido")));
                radionuclido.setNombreRadNuclido(resultado.getString("nombre"));
                radionuclidoData.add(radionuclido);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
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
                  "  SELECT"
                  + "    V.id_valordescripcion,"
                  + "    V.descripcion,"
                  + "    V.valor,"
                  + "    V.unidad"
                  + "   FROM"
                  + "     radionuclidos R"
                  + "     JOIN radionuclidos_valordescripcion RV"
                  + "      ON R.id_radionuclido ="
                  + "         RV.id_radionuclido"
                  + "     JOIN valordescripcion V"
                  + "      ON RV.id_valordescripcion ="
                  + "          V.id_valordescripcion "
                  + "   WHERE R.id_radionuclido = ?"
            );
            consulta.setInt(1, idRadNuclido);
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
                consulta.executeUpdate("INSERT INTO radionuclidos (nombre) VALUES ('" + nombreRadNuclido + "')");
                consulta.close();
                conexion.desconectar();

                
            } else {
                 /**
                  * Mensaje por error esta detallado en el metodo validarDatosEntrada. ABMRadionuclidos
                  */
           
            }
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
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
            conexion.getConnection().setAutoCommit(false);
            PreparedStatement consultaQueryValorDescripcion = conexion.getConnection().prepareStatement(
                      "DELETE FROM valordescripcion "
                    + "WHERE id_valordescripcion "
                    + "IN (SELECT radionuclidos_valordescripcion.id_valordescripcion " 
                    + "FROM radionuclidos_valordescripcion " 
                    + "WHERE radionuclidos_valordescripcion.id_radionuclido = ?)");
            
            consultaQueryValorDescripcion.setInt(1, idRadionuclido);
          
            
            PreparedStatement consultaQueryRadNuclido = conexion.getConnection().prepareStatement(
                    "DELETE FROM radionuclidos WHERE id_radionuclido = ?");
            consultaQueryRadNuclido.setInt(1, idRadionuclido);
            //System.out.print(id);
            
            consultaQueryValorDescripcion.executeUpdate(); //Ejecucion de la consulta.
            consultaQueryRadNuclido.executeUpdate(); //Ejecucion de la consulta.
            
                      
            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);
            
            consultaQueryRadNuclido.close();
            consultaQueryValorDescripcion.close();
            conexion.desconectar();

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
          
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
                        "UPDATE radionuclidos SET nombre = ? WHERE id_radionuclido = ?");
                //JOptionPane.showMessageDialog(null, "El radionúclido ha sido modificado!", "Información", JOptionPane.INFORMATION_MESSAGE);
                
                //Seteo las variables para la consulta.
                consulta.setString(1, nombreRadionuclido);
                consulta.setInt(2, id);
                //Ejecuto la consulta.
                consulta.executeUpdate();
                consulta.close();
                
                
            } else {
             /**
                  * Mensaje por error esta detallado en el metodo validarDatosEntrada. ABMRadionuclidos
                  */
            }
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            
        }
    }
    /**
     * Método que busca si un radionúclido ya existe.
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
                    "SELECT nombre FROM radionuclidos WHERE nombre = ?");
            consulta.setString(1, nombreRadionuclido);
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                resultado.close();
                conexion.desconectar();
                return false;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return true;
            }
            
        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            return false;
        }
    }
   
    
    
}
