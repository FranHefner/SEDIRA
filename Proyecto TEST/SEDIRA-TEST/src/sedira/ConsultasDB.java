/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;


import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sedira.model.TipoDocumento;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;
/**
 * Clase de consultas y persistencia de datos.
 * @author Hefner Francisco, Quelin Pablo
 */

public class ConsultasDB {
    public static ObservableList <Phantom> phantomData = FXCollections.observableArrayList(); 
    public static ObservableList <Radionuclido> radionuclidoData = FXCollections.observableArrayList();
    
      
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
      */
     public static void iniciarPhantomsDefecto () //public static ObservableList <Phantom> iniciarPhantomsDefecto ()
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
       
        
          phantomData.add(new Phantom (0,"Adulto Masculino 76kg",listaAtributoPhantom,organosData));
          phantomData.add(new Phantom (1,"Adulto Femenino 56.8kg", listaAtributoPhantomFem, organosDataFem));
 
                       
        //return phantomData;
     }
     
     /**
     * Método para obtener la lista de Phantoms
     * @return Lista de Phantoms
     */
    public static ObservableList <Phantom> ObtenerPhantoms()
    {
        return  phantomData;        
    }
    /**
     * Metodo que guarda un phantom creado/modificado en la lista de Phantoms inicial  
     * @param phantom Objeto phantom a guardar en la lista de Phantoms ya creados. Base datos. 
     */
    public static void AgregarPhantom(Phantom phantom){
        
        phantomData.add(phantom);
        System.out.print(phantomData.size());
                
    }
    
     /**
      * Metodo que inicializa por defecto a un radionuclido. En este caso el Yodo-131. Datos obetenidos de Wikipedia. 
      */
     public static void iniciarRadionuclidosDefecto (){
         ObservableList <ValorDescripcion> infoRadNuclido = FXCollections.observableArrayList(); 
         infoRadNuclido.add( new ValorDescripcion ("Protones", 53, "unidadProton"));
         infoRadNuclido.add( new ValorDescripcion ("Neutrones", 78, "unidadNeutron"));
         infoRadNuclido.add( new ValorDescripcion ("Vida Media", 8.0197, "días"));
         infoRadNuclido.add( new ValorDescripcion ("Masa atómica", 130.9061246, "u")); //Ojo la masa atomica se muestra como 130,9061246(12) . Posible error en los tipos de datos. 
         infoRadNuclido.add( new ValorDescripcion ("Exceso de energía", 971, "KeV"));
         //Creacion de un lista de radionuclidos, Cada radionuclido puede contener una listua de valores que lo describen. 
         radionuclidoData.add(new Radionuclido (0,"Yodo-131",infoRadNuclido));
     }
     /**
      * Metodo que retorna la lista de radionuclidos. 
      * @return radionuclidoData. la lista de radionuclidos. 
      */
     public static ObservableList <Radionuclido> obtenerRadionuclidos (){
         return radionuclidoData;
     }
     
     /**
     * Metodo que guarda un radionuclido creado en la lista de Radionuclido inicial  
     * @param radionuclido Objeto radionuclido a guardar en la lista de radionuclidos ya creados. Base datos. 
     */
    public static void agregarRadionuclido(Radionuclido radionuclido){
        
        radionuclidoData.add(radionuclido);
        //System.out.print(phantomData.size());
                
    }
    /**
     * Metodo que modifica un radionuclido ya almacenado. 
     * @param radionuclido
     * @param index 
     */
    public static void modificarRadionuclido (Radionuclido radionuclido, int index){
        radionuclidoData.set(index, radionuclido);
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
    public static int getNewIdPhantom (){
        //Compara con el tamaño de la lista de Phantoms. Luego le suma 1. 
        int newId = phantomData.size() + 1;
        return newId;
    }
    
     public static int getNewIdRadNuclido (){
        //Compara con el tamaño de la lista de Radionuclidos. Luego le suma 1. 
        int newId = radionuclidoData.size() + 1;
        return newId;
    }
}
