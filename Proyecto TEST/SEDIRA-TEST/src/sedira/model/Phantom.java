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
    private int height; //Altura en cm.
    private int bodySurfaceArea; // Superficie del cuerpo en cm2
    private ObservableList <Organo> organo;

    /**
     * Contructor de Clase Phantom. Un phantom contiene una lista de Organos
     * @param panthom
     * @param height
     * @param bodySurfaceArea
     * @param organo 
     */
    public Phantom(String panthom, int height, int bodySurfaceArea, ObservableList<Organo> organo) {
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBodySurfaceArea() {
        return bodySurfaceArea;
    }

    public void setBodySurfaceArea(int bodySurfaceArea) {
        this.bodySurfaceArea = bodySurfaceArea;
    }

    public ObservableList<Organo> getOrgano() {
        return organo;
    }

    public void setOrgano(ObservableList<Organo> organo) {
        this.organo = organo;
    }
    
       
    
    
    
    
    
    
}
