/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;


import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sedira.model.Calculo;
import sedira.model.TipoDocumento;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;
/**
 * Clase de consultas y persistencia de datos.
 * @author Hefner Francisco, Quelin Pablo
 */

public class ConsultasDB {
       /* Agregar la administracion de la conexion con la base de datos */     

    /**
     * Metodo para obtener los datos basicos de los pacientes
     * @return Lista de Pacientes
     */
     public static ObservableList<Paciente> ListaPacientes ()
     {
         
         // Se usa un costructor de pacientes mas acotado para cargar la grilla.
            ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
              pacienteData.add(  new Paciente(1, 1, 34000001,"Hefner","Fran"));
              pacienteData.add(  new Paciente(2, 1, 34000002,"Quelin","Pablo"));
              pacienteData.add(  new Paciente(3, 1, 34000003,"Salibar","Roberto"));
              pacienteData.add(  new Paciente(4, 2, 34000004,"Argañaras","Pablo"));
              pacienteData.add(  new Paciente(5, 2, 34000005,"Perez","Pepe"));
            
            return pacienteData;
     }
     /**
      * Método para actualizar un paciente
      * @param IdPaciente Identificación del paciente
      * @param PacienteActualizado Paciente para actualizar
      * @return Booleano que informa el éxito del proceso.
      */
     public static boolean ActualizarPaciente (int IdPaciente, Paciente PacienteActualizado)
     {
         
         return true;
     }
   /**
     * Método para obtener la lista de órganos
     * @return Lista de órganos
     */
    public static ArrayList<Organo> ObtenerOrganos()
    {
        ArrayList<Organo> listaOrgano = null ;

          return  listaOrgano;        
    }
     /**
     * Método para obtener la lista de Phantoms
     * @return Lista de Phantoms
     */
    public static ArrayList<Phantom> ObtenerPhantoms()
    {
        ArrayList<Phantom> ListaPhantom = null ;

          return  ListaPhantom;        
    }
    
    public static ArrayList<TipoDocumento> ObtenerTiposDoc()
    {
        
        TipoDocumento TipoDoc1 = new TipoDocumento(1,"DNI","Documento Nacional de Identidad");
        TipoDocumento TipoDoc2 = new TipoDocumento(2,"PAS","Pasaporte");
     
       ArrayList <TipoDocumento> ListadoTipoDoc= new ArrayList <> (); 
       ListadoTipoDoc.add(TipoDoc1);
       ListadoTipoDoc.add(TipoDoc2);
       
        return ListadoTipoDoc;
    }
    
    
    
}
