/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

/**
 *
 * @author Pablo Quelin, Francisco Hefner
 */
public class Formula {
    
    int Id_formula;
    String Nombre;
    String Formula_mat;

    
    
    
     public Formula(){}
     public Formula(int pId_formula, String pNombre, String pFormula_mat)
     {
         this.Id_formula= pId_formula;
         this.Nombre = pNombre;
         this.Formula_mat= pFormula_mat;              
     }
     
     public String getNombre()
     {
         return Nombre;
     }
      public String getFormula_mat()
     {
         return Formula_mat;
     }
      public int getId_Formula()
     {
         return Id_formula;
     }
    
}
