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
        
        
        String nombreOrgano = organo.getNombreOrgano();

        try {
            
            conexion.getConnection().setAutoCommit(false);
          
           PreparedStatement sqlOrgano = conexion.getConnection().prepareStatement(
                          "  INSERT INTO organos (  nombre ) "
                          + "  VALUES ( ? )");
                  
            if (buscaNombre(nombreOrgano)) {
                
                nuevoOrgano = true;
            
  
                sqlOrgano.setString(1, organo.getNombreOrgano());
                sqlOrgano.setDouble(2, organo.getOrganMass());
                //Se calcula
             //   consulta.setDouble(3, organo.getTotalMass());
                sqlOrgano.setInt(4, idPhantom);
                

            }
                
          
                // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);
           

             PreparedStatement sqlOrganoPhantom = conexion.getConnection().prepareStatement(             
                     " INSERT INTO organos_phantoms ( "
                        + " id_organo_phantom "
                       + " ,id_organo "
                       + " ,id_phantom "
                       + "  ,masa_organo "
                       + " ) VALUES ( "                   
                       + " ?  "
                       + " ,?  "
                       + " ,?  "
                       + " )");

            sqlOrganoPhantom.setInt(1, organo.getIdOrgano());
            sqlOrganoPhantom.setInt(2, idPhantom);
            sqlOrganoPhantom.setDouble(3, organo.getOrganMass());
               
            
            if (nuevoOrgano)
            {
             sqlOrgano.executeUpdate(); 
            }
          
            
            sqlOrganoPhantom.executeUpdate();      
            
            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);
            
             if (nuevoOrgano)
            {
                sqlOrgano.close();                
                sqlOrganoPhantom.close();
            }
             
         
            
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
                   + " WHERE  id_organo = ?" );            
            
            sqlOrgano.setString(1, organo.getNombreOrgano());            
            sqlOrgano.setInt(2, organo.getIdOrgano());
            
            
            PreparedStatement sqlOrganoPhantom = conexion.getConnection().prepareStatement(  
                 "  UPDATE organos_phantoms SET  " 
                 + "  masa_organo = ? "
                 + "  WHERE id_phantom = ? AND  id_organo = ? " ); 
        
            
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
    public void eliminarOrgano( int idOrgano, int idPhantom) {
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
                     
            
              if ( buscarReferenciaOrgano(idOrgano)) {
                  
                    // Si existe referencias no se elimina el organo
              }else                  
              {                    
                       OrganoEliminado = true;
                       sqlOrgano.executeUpdate(); 
                       
              }
              
             
            sqlOrganoPhantom.executeUpdate();      
            
            conexion.getConnection().commit();
            conexion.getConnection().setAutoCommit(true);
            
            if(OrganoEliminado)
            {
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
    public ObservableList listadoOrganos(){
    
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
                listado.add(   resultado.getString("nombre"));
              
            }

        } catch (SQLException e) {
            CodigosErrorSQL.analizarExepcion(e);
            //System.out.println(e.getMessage());
            //JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
   
        }
        return listado;
    }
}
