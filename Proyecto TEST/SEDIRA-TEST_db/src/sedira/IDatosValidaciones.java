/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.Blob;
import sedira.model.Organo;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 * Interface para DatosValidaciones. 
 * define lo metodos abtractos para que sean implementados en las clases que lo requieran 
 * @author Hefner Francisco, Quelin Pablo
 */
public interface IDatosValidaciones {
    
      void IniciarCalculo();
      boolean finalizarCalculo(Blob resultado);
      boolean guardarCalculo();
      
      Blob    getResultado();
      Phantom getPhantomActual();   
      Paciente getPacienteActual();
      String  GetTextoProgeso();
      Organo  getOrganoActual();      
      Radionuclido getRadionuClidoActual();   
      String getEstadoActual(boolean adelante, String pestañaActual);
      boolean getProcesoCompleto();      
      
      boolean setPaciente(Paciente miPaciente);      
      boolean setPhantom(Phantom miPhantom);  
      boolean setOrgano(Organo miOrgano);
      boolean setRadionuclido(Radionuclido miRadionuclido);
      void setProcesoCompleto(boolean esCompleto);
 
    
}
