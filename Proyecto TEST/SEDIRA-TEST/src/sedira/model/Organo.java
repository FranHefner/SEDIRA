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
     * @param organMass
     * @param totalMass 
     */
    public Organo(String nombreOrgano, double organMass, double totalMass) {
        this.nombreOrgano = new SimpleStringProperty(nombreOrgano);
        this.organMass = new SimpleDoubleProperty (organMass);
        this.totalMass = new SimpleDoubleProperty (totalMass);
    }

    public StringProperty getNombreOrgano() {
        return nombreOrgano;
    }

    public void setNombreOrgano(StringProperty nombreOrgano) {
        this.nombreOrgano = nombreOrgano;
    }

    public DoubleProperty getOrganMass() {
        return organMass;
    }
    
    public void setOrganMass(DoubleProperty organMass) {
        this.organMass = organMass;
    }

    public DoubleProperty getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(DoubleProperty totalMass) {
        this.totalMass = totalMass;
    }

   
    
    
    
    
}

