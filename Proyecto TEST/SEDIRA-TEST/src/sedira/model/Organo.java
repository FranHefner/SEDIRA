/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

/**
 * Clase que describe a un organo. Un phantom esta contituido por una lista de organos. 
 * @author Hefner Francisco, Quelin Pablo

 */
class Organo {
    private String nombreOrgano; 
    private int organMass; // masa de un organo en particular. 
    private int totalMass; // masa total del cuerpo. 

    /**
     * Constructor para los Organos. 
     * @param nombreOrgano
     * @param organMass
     * @param totalMass 
     */
    public Organo(String nombreOrgano, int organMass, int totalMass) {
        this.nombreOrgano = nombreOrgano;
        this.organMass = organMass;
        this.totalMass = totalMass;
    }

    public String getNombreOrgano() {
        return nombreOrgano;
    }

    public void setNombreOrgano(String nombreOrgano) {
        this.nombreOrgano = nombreOrgano;
    }

    public int getOrganMass() {
        return organMass;
    }

    public void setOrganMass(int organMass) {
        this.organMass = organMass;
    }

    public int getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(int totalMass) {
        this.totalMass = totalMass;
    }
    
    
    
}

