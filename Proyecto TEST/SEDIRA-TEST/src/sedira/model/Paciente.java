/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.util.Date;

/**
 * Clase Paciente, describe a un paciente dentro del sistema del calculo SEDIRA; 
 * 
 * 
 */
public class Paciente {
    
    private int idPaciente; 
    private int tipoDoc; 
    private long numeroDoc;
    private String apellido;
    private String nombre; 
    private Date fechaNacimiento; 
    private String direccion; 
    private long numeroAsociado;
    private String email;
    private String telefono; 
    private Blob foto; 
    private String sexo;
    private boolean enTratamiento; 

     /**
    * Contructor con parametros
    */
    public Paciente(int idPaciente, int tipoDoc, long numeroDoc, String apellido, String nombre, Date fechaNacimiento, String direccion, long numeroAsociado, String email, String telefono, Blob foto, String sexo, boolean enTratamiento) {
        this.idPaciente = idPaciente;
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numeroAsociado = numeroAsociado;
        this.email = email;
        this.telefono = telefono;
        this.foto = foto;
        this.sexo = sexo;
        this.enTratamiento = enTratamiento;
    }
    
    /**
    * Métodos GETters
    */
    public int getIdPaciente() {
        return idPaciente;
    }

    public int getTipoDoc() {
        return tipoDoc;
    }

    public long getNumeroDoc() {
        return numeroDoc;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public long getNumeroAsociado() {
        return numeroAsociado;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Blob getFoto() {
        return foto;
    }

    public String getSexo() {
        return sexo;
    }

    public boolean isEnTratamiento() {
        return enTratamiento;
    }

    /**
    * Métodos SETters
    */
    
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public void setNumeroDoc(long numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNumeroAsociado(long numeroAsociado) {
        this.numeroAsociado = numeroAsociado;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEnTratamiento(boolean enTratamiento) {
        this.enTratamiento = enTratamiento;
    }

   
    
    
    
}