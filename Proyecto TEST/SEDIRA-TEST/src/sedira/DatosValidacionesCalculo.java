/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import sedira.model.Calculo;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;

/**
 *
 * @author Fran
 */
public class DatosValidacionesCalculo {
    
    
        private Calculo CalculoActual;
        private Paciente PacienteActual;
        private Phantom PhantomActual;
        private Organo OrganoActual;
       
        
        
        private void LimpiarVariables()
        {
            
        }
        
        public boolean setPaciente(Paciente MiPaciente)
        {
            return true;
        }
        
      public boolean setPhantom(Phantom MiPhantom)
      {
          
          return true;
      }       
      public boolean setOrgano(Organo MiOrgano)
      {
          
          return true;
      }  
        
       public void IniciarCalculo()
       {
           LimpiarVariables();
           
           
       }
                        
        
}
