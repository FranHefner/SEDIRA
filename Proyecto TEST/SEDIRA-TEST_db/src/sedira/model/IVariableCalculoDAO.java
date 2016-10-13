/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.collections.ObservableList;

/**
 * Interface para VariableCalculoDAOsql
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface IVariableCalculoDAO {
       
    public ObservableList <VariableCalculo> obtenerVariables (int IdCalculo);
}
