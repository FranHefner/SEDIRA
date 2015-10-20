/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que describe a un organo. Un phantom esta contituido por una lista de organos. 
 * @author Hefner Francisco, Quelin Pablo

 */
public class Organo {
    private StringProperty nombreOrgano; 
    private DoubleProperty organMass; // masa de un organo en particular. 
    private DoubleProperty totalMass; // masa total del cuerpo. 

    /**
     * 
     * @param nombreOrgano
     * @param organMass masa/peso en gramos del organos. 
     * @param totalMass sumatoria de las masas. Determina el peso total del Humano (Phantom)
     */
    public Organo(String nombreOrgano, double organMass, double totalMass) {
        this.nombreOrgano = new SimpleStringProperty(nombreOrgano);
        this.organMass = new SimpleDoubleProperty (organMass);
        this.totalMass = new SimpleDoubleProperty (totalMass);
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

    //**********************************irganMass GETters and SETters 
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

