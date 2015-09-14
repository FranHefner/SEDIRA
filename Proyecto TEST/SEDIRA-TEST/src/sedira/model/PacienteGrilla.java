/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fran
 */
public class PacienteGrilla {
    
   /* private int idPaciente; 
    private int tipoDoc; 
    private long numeroDoc;
    private String apellido;
    private String nombre; */
   
    private final IntegerProperty idPaciente;
    private final IntegerProperty tipoDoc;
    private final IntegerProperty numeroDoc;
    private final StringProperty apellido;
    private final StringProperty nombre;
  //  private final ObjectProperty<LocalDate> birthday;
    

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
     public PacienteGrilla (int idPaciente, int tipoDoc, long numeroDoc, String apellido, String nombre)
    {
        
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.tipoDoc = new SimpleIntegerProperty(tipoDoc);
        this.numeroDoc = new SimpleIntegerProperty((int) numeroDoc);
        this.apellido = new SimpleStringProperty(apellido);
        this.nombre = new SimpleStringProperty(nombre);
      /*  
        this.idPaciente = idPaciente;
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
        this.apellido = apellido;
        this.nombre =nombre;*/
        
    }
     
    
    public IntegerProperty getidPaciente() {
        return idPaciente;
    }
      public IntegerProperty getTipoDoc() {
        return tipoDoc;
    }
    public IntegerProperty getNumeroDoc() {
        return numeroDoc;
    }
    public StringProperty getApellido() {
        return apellido;
    }      
    public StringProperty getNombre() {
        return nombre;
    }
    public void setTipoDoc(Integer TipoDoc)
    {
         this.tipoDoc.setValue(TipoDoc);
    }
    public void setNumeroDoc(Integer NumeroDoc)
    {
         this.numeroDoc.setValue(NumeroDoc);
    }        
    public void setNombre(String Nombre)
    {
         this.nombre.setValue(Nombre);
    }
     public void setApellido(String Apellido)
    {
         this.apellido.setValue(Apellido);
    }
   
     
 
}
