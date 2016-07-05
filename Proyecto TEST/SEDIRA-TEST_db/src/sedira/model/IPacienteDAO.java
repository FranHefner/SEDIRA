/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Interface para PacientesDAOsql   
 * @author pablo
 */
public interface IPacienteDAO {
    void agregarPaciente(Paciente paciente);
    void eliminarPaciente(int id);
    void modificarPaciente(Paciente paciente);
    boolean buscaDni(int dni);
    ObservableList<Paciente> obtenerPacientes() throws SQLException;
    int getLastId();
}
