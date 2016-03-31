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
 *  Clase de acceso a datos para Organos. 
 * @author Quelin Pablo , Hefner Francisco
 */
public class OrganoDAO {
    
    public static void  agregarOrgano (Organo organo,int idPhantom){
         //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombreOrgano = organo.getNombreOrgano();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "INSERT INTO organos (nombre_organo, masa_organo, masa_total,id_phantom) "
                    + "VALUES(?,?,?,?)");
            consulta.setString(1, organo.getNombreOrgano());
            consulta.setDouble(2, organo.getOrganMass());
            consulta.setDouble(3, organo.getTotalMass());
            consulta.setInt(4,idPhantom);

            consulta.executeUpdate(); //Ejecucion de la consulta
            consulta.close();
            // JOptionPane.showMessageDialog(null, "La propiedad "+vd.getDescripcion()+ " fué agregada con éxito!","Información",JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la inserción de la propiedad " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error en la inserción del órgano " + e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    
    public static void modificarOrgano (){
        
    }
    public static void eliminarOrgano (){
        
    }
}
