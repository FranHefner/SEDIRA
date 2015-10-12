/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Clase que describe un Phantom.
 * @author Hefner Francisco, Quelin Pablo
 * 
 */
public class Phantom {
    
   private StringProperty phantomNombre;
    /*private double height; //Altura en cm.
    private double bodySurfaceArea; // Superficie del cuerpo en cm2*/
    private ObservableList <ValorDescripcion> propiedades; 
    private ObservableList <Organo> organo;

   /**
    * Constructor de Phantoms, Contiend una lista de valor-descripcion. Para la mejor escalabilidad. 
    * @param phantom
    * @param propiedades
    * @param organo 
    */
    public Phantom(String phantom, ObservableList<ValorDescripcion> propiedades, ObservableList<Organo> organo) {
        this.phantomNombre = new SimpleStringProperty (phantom);
        this.propiedades = propiedades;
        this.organo = organo;
    }
    
    public StringProperty getPhantomNombre() {
        return phantomNombre;
    }
    
    public void setPhantom(StringProperty phantom) {
        this.phantomNombre = phantom;
    }
   
   
    public ObservableList<ValorDescripcion> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ObservableList<ValorDescripcion> propiedades) {
        this.propiedades = propiedades;
    }

    public ObservableList<Organo> getOrgano() {
        return organo;
    }

    public void setOrgano(ObservableList<Organo> organo) {
        this.organo = organo;
    }
    

   
    
       
    
    
    
    
    
    
}
