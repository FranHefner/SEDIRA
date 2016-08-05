/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que describe a un órgano. Un phantom esta constituido por una lista de órganos. 
 * @author Hefner Francisco, Quelin Pablo

 */
public class Organo {
     
    private IntegerProperty idOrgano;
    private StringProperty nombreOrgano; 
    private DoubleProperty organMass; // masa de un organo en particular. 
    private DoubleProperty totalMass; // masa total del cuerpo. Sumatoria de todos los organos y tejidos 

    /**
     * @param idOrgano Identificador unico de la clase Organo. 
     * @param nombreOrgano
     * @param organMass masa/peso en gramos del organos. 
     * @param totalMass sumatoria de las masas. Determina el peso total del Humano (Phantom)
     */
    public Organo(int idOrgano, String nombreOrgano, double organMass, double totalMass) {
        this.idOrgano = new SimpleIntegerProperty (idOrgano);
        this.nombreOrgano = new SimpleStringProperty(nombreOrgano);
        this.organMass = new SimpleDoubleProperty (organMass);
        this.totalMass = new SimpleDoubleProperty (totalMass);
    }
    
    //**********************************idOrgano GETters and SETters 
    public void setIdOrgano (int idOrgano){
        this.idOrgano.set(idOrgano); 
    }
    public int getIdOrgano(){
        return idOrgano.get();
    }
    public IntegerProperty getIdOrganoProperty(){
        return idOrgano;
    }
    //**********************************nombreOrgano GETters and SETters 
        
    public String getNombreOrgano() {
        return nombreOrgano.get();
    }
    public void setNombreOrgano(String nombreOrgano) {
        this.nombreOrgano.set(nombreOrgano);
    }
    public StringProperty getNombreOrganoProperty (){
        return nombreOrgano;
    }

    //**********************************organMass GETters and SETters 
    public Double getOrganMass() {
        return organMass.get();
    }
    
    public void setOrganMass(Double organMass) {
        this.organMass.set(organMass);
    }
    public DoubleProperty getOrganMassProperty(){
        return organMass;
    }
    
    //**********************************totalMass GETters and SETters 
    public Double getTotalMass() {
        return totalMass.get();
    }
    public void setTotalMass(Double totalMass) {
        this.totalMass.set(totalMass);
    }
   public DoubleProperty getTotalMassProperty(){
        return totalMass;
    }

   
    
    
    
    
}

