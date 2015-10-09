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
import sedira.model.ValorDescripcion;
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
              pacienteData.add(  new Paciente(1, "DNI", 34000001,"Hefner","Fran"));
              pacienteData.add(  new Paciente(2, "DNI", 34000002,"Quelin","Pablo"));
              pacienteData.add(  new Paciente(3, "DNI", 34000003,"Salibar","Roberto"));
              pacienteData.add(  new Paciente(4, "DNI", 34000004,"Argañaras","Pablo"));
              pacienteData.add(  new Paciente(5, "PAS", 34000005,"Perez","Pepe"));
            
            return pacienteData;
     }
     
     /**
      * Metodo que inicializa los tipos de documento. 
      * @return Listado  de los tipos de Documento. 
      */
      public static ObservableList<String> ListaTipoDocumento ()
     {
          ObservableList<TipoDocumento> TipoDocumentosData = FXCollections.observableArrayList();
          TipoDocumentosData.add( new TipoDocumento(1,"DNI","Documento Nacional de Identidad"));
          TipoDocumentosData.add( new TipoDocumento(2,"PAS","Pasaporte"));
         
          
          ObservableList<String> TipoDocumentosDataString = FXCollections.observableArrayList();
          TipoDocumentosDataString.add("DNI");
          TipoDocumentosDataString.add("PAS");
          
          return TipoDocumentosDataString;
          
          
     }
     /**
      * Metodo que inicializa un Phantom por defecto. Corresponde a los siguientes datos datos por el 
      * modelo de Stabin MG y JA Siegel - 2003
      *  
      * @return Objeto Phantom, compuesto arreglo de objetos tipo Valor-Descripcion y arreglo de Organos.  
      */
     public static ObservableList <Phantom> listaPhantom ()
     {
        // Un phantom esta compuesto de varios organos. Un organos puede formar parte de varios Phantoms. 
        // Temporalmente se utiliza un contructor acotado de los organos que forman parte de un Phantom.
       
        //Ejemplo Organos Masculino
         ObservableList<Organo> organosData = FXCollections.observableArrayList();
            organosData.add(  new Organo ("Riñon",299,73700));
            organosData.add(  new Organo ("Tiroide",20.9,73700));
            
        //Ejemplo Organos femenino
        ObservableList<Organo> organosDataFem = FXCollections.observableArrayList();
            organosDataFem.add(  new Organo ("Ovarios",19,56800));
            organosDataFem.add(  new Organo ("Tiroide",17,56800));
            organosDataFem.add(  new Organo ("Riñon",275,56800));
        
        //Ejemplo Phantom masculino    
        ObservableList <ValorDescripcion> listaAtributoPhantom = FXCollections.observableArrayList(); 
            listaAtributoPhantom.add(new ValorDescripcion("Altura",167,"cm"));
            listaAtributoPhantom.add(new ValorDescripcion("Sup Cuerpo",18000,"cm2"));
            listaAtributoPhantom.add(new ValorDescripcion("Peso total",73700,"grs"));
        
        //Ejemplo Phantom Femenino
        ObservableList <ValorDescripcion> listaAtributoPhantomFem = FXCollections.observableArrayList(); 
            listaAtributoPhantomFem.add(new ValorDescripcion("Altura",0,"cm"));
            listaAtributoPhantomFem.add(new ValorDescripcion("Sup Cuerpo",0,"cm2"));
            listaAtributoPhantomFem.add(new ValorDescripcion("Peso total",56800,"grs"));
       
        ObservableList <Phantom> listaPhantom = FXCollections.observableArrayList();
          listaPhantom.add(new Phantom ("Adulto Masculino 76kg",listaAtributoPhantom,organosData));
           listaPhantom.add(new Phantom ("Adulto Femenino 56.8kg", listaAtributoPhantomFem, organosDataFem));
 
                       
        return listaPhantom;
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
    
     public static ObservableList<Organo> ObtenerOrganos()
    {
         //Ejemplo Organos femenino
       ObservableList<Organo> organosData = FXCollections.observableArrayList();
            organosData.add(  new Organo ("Riñon",299,73700));
            organosData.add(  new Organo ("Tiroide",20.9,73700));

          return  organosData;    
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
    
    public static String ObtenerTipoDoc(int ID)
    {
        if (ID == 1 )
        {
            return "DNI";
            
        }
        if (ID == 2 )
        {
            return "PAS";
            
        } 
        return ("No encontrado");
        
    }
    
}
