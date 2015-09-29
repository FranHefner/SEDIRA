/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.collections.ObservableList;

/**
 * Clase que describe un Phantom.
 * @author Hefner Francisco, Quelin Pablo
 * 
 */
public class Phantom {
    
    private String panthom;
    private double height; //Altura en cm.
    private double bodySurfaceArea; // Superficie del cuerpo en cm2
    private ObservableList <Organo> organo;

    /**
     * Contructor de Clase Phantom. Un phantom contiene una lista de Organos
     * @param panthom Nombre del Phantom
     * @param height Altura
     * @param bodySurfaceArea Superficie del cuerpo. Dada en cm2
     * @param organo Lista de organos que componen un Phantom. 
     */
    public Phantom(String panthom, double height, double bodySurfaceArea, ObservableList<Organo> organo) {
        this.panthom = panthom;
        this.height = height;
        this.bodySurfaceArea = bodySurfaceArea;
        this.organo = organo;
    }

    public String getPanthom() {
        return panthom;
    }

    public void setPanthom(String panthom) {
        this.panthom = panthom;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBodySurfaceArea() {
        return bodySurfaceArea;
    }

    public void setBodySurfaceArea(double bodySurfaceArea) {
        this.bodySurfaceArea = bodySurfaceArea;
    }

    public ObservableList<Organo> getOrgano() {
        return organo;
    }

    public void setOrgano(ObservableList<Organo> organo) {
        this.organo = organo;
    }
    
       
    
    
    
    
    
    
}
