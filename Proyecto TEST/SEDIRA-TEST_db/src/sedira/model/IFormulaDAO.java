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
    
     boolean setFormula(int id_formula, String Formula_mat, String formula_tex) ;
     List<Formula>  getFormulasPlantillas();
     public ObservableList<VariableCalculo> getPropiedadesFormulaCalculo(int id_formula, int id_calculo, boolean ConValores);
     public void eliminarFormula(String Formula);
      public boolean SetFormulaPlantilla( int Id_formula, String Nombre);
}
