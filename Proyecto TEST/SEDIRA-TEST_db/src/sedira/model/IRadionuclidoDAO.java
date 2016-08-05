/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Interface para RadionuclidoDAOsql
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface IRadionuclidoDAO {

    public ObservableList<Radionuclido> obtenerListaRadNuclido();

    public ObservableList<ValorDescripcion> obtenerInfoRadNuclido(Radionuclido radioNuclidoSeleccionado);

    public void agregarRadionuclido(Radionuclido radionuclido) throws SQLException;

    public void eliminarRadionuclido(int idRadionuclido);

    public void modificarNombreRadionuclido(Radionuclido radionuclido);

    public boolean buscaNombre(String nombreRadionuclido) throws SQLException;
}
