/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.SQLException;
import javafx.collections.ObservableList;


/**
 * Interface para ValorDescripcionDAOsql
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface IValorDescripcionDAO {

    public void agregarItem(ValorDescripcion vd, int id, String Tabla) throws SQLException;

    public void modificarItem(ValorDescripcion vd, int id, String Tabla);

    public void eliminarItem(int id);

    public boolean buscaNombre(String propiedad, String tabla, int idEntidad) throws SQLException;
    
    public ObservableList listadoPropiedades(String Entidad);
    
    public int getLastId();

}
