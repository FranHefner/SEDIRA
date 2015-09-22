    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sedira.ConsultasDB;
import sedira.model.ProgresoPaciente;

/**
 * Clase Paciente, describe a un paciente dentro del sistema del calculo SEDIRA; 
 * 
 * 
 */
public class Paciente {
    
     
    
   
    private  IntegerProperty idPaciente;
    private  StringProperty tipoDoc;
    private  IntegerProperty numeroDoc;
    private  StringProperty apellido;
    private  StringProperty nombre;
  
    private Date fechaNacimiento; 
    private StringProperty  direccion; 
    private long numeroAsociado;
    private StringProperty  email;
    private StringProperty  telefono; 
    private Blob foto; 
    private StringProperty  sexo;
    private boolean enTratamiento; 
    private ProgresoPaciente EstadoActual;
   // private List<ProgresoPaciente> Progreso;
    

   
   
     /**
    * Contructor con parametros
    */
    public Paciente(int idPaciente, String tipoDoc, long numeroDoc, String apellido, String nombre,/* Date fechaNacimiento, */String direccion, long numeroAsociado, String email, String telefono,/* Blob foto,*/ String sexo, boolean enTratamiento) {
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.tipoDoc = new SimpleStringProperty(tipoDoc);
        this.numeroDoc = new SimpleIntegerProperty((int) numeroDoc);
        this.apellido = new SimpleStringProperty(apellido);
        this.nombre = new SimpleStringProperty(nombre);
      //  this.fechaNacimiento = fechaNacimiento;
        this.direccion = new SimpleStringProperty(direccion);
        this.numeroAsociado = numeroAsociado;
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
    //    this.foto = foto;
        this.sexo = new SimpleStringProperty(sexo);
        this.enTratamiento = enTratamiento;
    }

    public Paciente(int idPaciente, String tipoDoc, long numeroDoc, String apellido, String nombre) {
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.tipoDoc = new SimpleStringProperty(tipoDoc);
        this.numeroDoc = new SimpleIntegerProperty((int) numeroDoc);
        this.apellido = new SimpleStringProperty(apellido);
        this.nombre = new SimpleStringProperty(nombre);
    }
  
       
    /**
    * Métodos GETters
    */
   public IntegerProperty getidPaciente() {
        return idPaciente;
    }
      public StringProperty getTipoDoc() {
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
   
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public StringProperty getDireccion() {
        return direccion;
    }

    public long getNumeroAsociado() {
        return numeroAsociado;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getTelefono() {
        return telefono;
    }

    public Blob getFoto() {
        return foto;
    }

    public StringProperty getSexo() {
        return sexo;
    }

    public boolean isEnTratamiento() {
        return enTratamiento;
    }

    /**
    * Métodos SETters
    */
    
   public void setTipoDoc(String TipoDoc)
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

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion.setValue(direccion);
    }

    public void setNumeroAsociado(long numeroAsociado) {
        this.numeroAsociado = numeroAsociado;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setTelefono(String telefono) {
        this.telefono.setValue(telefono);
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public void setSexo(String sexo) {
        this.sexo.setValue(sexo);
    }

    public void setEnTratamiento(boolean enTratamiento) {
        this.enTratamiento = enTratamiento;
    }

   
    
    
    
}