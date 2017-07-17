/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Interface para OrganoDAO
 * @author Quelin Pablo, Hefner Franscisco
 */
public interface IOrganoDAO {
    
    void agregarOrgano(Organo organo, int idPhantom);

    void modificarOrgano(Organo organo, int idPhantom);

    void eliminarOrgano(int idOrgano, int idPhantom);
     
    boolean buscaNombre(String nombreOrgano) throws SQLException;
       
    public ObservableList listadoOrganos();
    
    boolean buscarReferenciaOrgano(int idOrgano) throws SQLException ;
    
    public ObservableList<ValorDescripcion> obtenerInfoOrgano(Organo organoSeleccionado, int Id_Phantom);
   
    public int obtenerIdOrganoPhantom(int idOrgano);
   
    public boolean buscarOrganoPhantom(int idPhantom, String nombreOrgano)throws SQLException;
     
}
