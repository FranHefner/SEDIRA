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

    public void agregarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido) throws SQLException;

    public void modificarItem(ValorDescripcion vd, int id, boolean phantom, boolean radionuclido);

    public void eliminarItem(int id);

    public boolean buscaNombre(String propiedad) throws SQLException;
    
    public ObservableList listadoPropiedadesPhantom();
    public ObservableList listadoPropiedadesRadionuclido();

}
