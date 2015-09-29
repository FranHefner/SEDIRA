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
public class Organo {
    private String nombreOrgano; 
    private double organMass; // masa de un organo en particular. 
    private double totalMass; // masa total del cuerpo. 

    /**
     * Constructor para los Organos. 
     * @param nombreOrgano Nombre descriptivo de un organo. Ejemplo: Riñon. 
     * @param organMass Masa del organo en gramos, 
     * @param totalMass Masa del total del cuerpo. Depende de la sumaroria de las masas de los organos corportales y estructura osea.
     */
    public Organo(String nombreOrgano, double organMass, double totalMass) {
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

    public double getOrganMass() {
        return organMass;
    }

    public void setOrganMass(double organMass) {
        this.organMass = organMass;
    }

    public double getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(double totalMass) {
        this.totalMass = totalMass;
    }
    
    
    
}

