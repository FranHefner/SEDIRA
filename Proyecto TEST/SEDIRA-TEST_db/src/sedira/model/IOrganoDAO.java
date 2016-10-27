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

    void eliminarOrgano(int id);

    boolean buscaNombre(String nombreOrgano, int idPhantom) throws SQLException;
    
    public ObservableList listadoOrganos();
    
  
}
