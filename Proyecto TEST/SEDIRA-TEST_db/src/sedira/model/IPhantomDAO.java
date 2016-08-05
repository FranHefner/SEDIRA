/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Interfaz para Phantom
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface IPhantomDAO {

    ObservableList<Phantom> obtenerListaPhantom();

    ObservableList<Phantom> obtenerListaPhantomCompletos();

    ObservableList<ValorDescripcion> obtenerInfoPhantom(Phantom phantomSeleccionado);

    void agregarPhantom(Phantom phantom);

    void modificarNombrePhantom(Phantom phantom);

    void eliminarPhantom(int id);

    boolean buscaNombre(String nombrePhantom) throws SQLException;

    ObservableList<Organo> obtenerInfoOrgano(Phantom phantomSeleccionado);

}
