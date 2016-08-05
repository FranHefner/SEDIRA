/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

/**
 * Clase para el almacenaje de tipos de documentos. 
 * Los tipos vienen instalados con la app. 
 * @author Hefner Francisco, Quelin Pablo
 */
public class TipoDocumento {
    
    int IdTipoDoc;
    String TipoDoc; //Ej: DNI , PAS
    String Descripcion;  // TipoDocumento Nacional de Identidad,  Pasaporte
    
    
   public TipoDocumento(int IdTipoDoc, String TipoDoc, String Descripcion)
   {
       this.IdTipoDoc = IdTipoDoc;
       this.TipoDoc = TipoDoc;
       this.Descripcion = Descripcion;
   }
}
