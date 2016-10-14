/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Pablo Quelin, Francisco Hefner
 */
public interface IFormulaDAO {
    
     boolean setFormula(String Formula, String Nombre, int Id_calculo);
    List<Formula>  getFormulas();
     public ObservableList<VariableCalculo> getPropiedadesFormula(int Id_calculo);
     
    
}
