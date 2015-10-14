/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Clase que describe un Phantom.
 * @author Hefner Francisco, Quelin Pablo
 * 
 */
public class Phantom {
    private IntegerProperty idPhantom;
    private StringProperty phantomNombre;
    private ObservableList <ValorDescripcion> propiedades; 
    private ObservableList <Organo> organo;

   /**
    * Constructor de Phantoms, Contiene una lista de valor-descripcion. Para la mejorar la escalabilidad. 
    * @param phantomNombre
    * @param propiedades
    * @param organo 
    * @param idPhantom identificador unico para el uso con Base de datos 
    */
    public Phantom(int idPhantom, String phantomNombre, ObservableList<ValorDescripcion> propiedades, ObservableList<Organo> organo) {
        this.idPhantom = new SimpleIntegerProperty (idPhantom);
        this.phantomNombre = new SimpleStringProperty (phantomNombre);
        this.propiedades = propiedades;
        this.organo = organo;
    }

    public int getIdPhantom() {
        return idPhantom.get();
    }

    public void setIdPhantom(int idPhantom) {
        this.idPhantom.set(idPhantom);
    }
    
    public IntegerProperty idPhantomProperty (){
        return idPhantom;
    }
        
    public String getPhantomNombre() {
        return phantomNombre.get();
    }
    
    public void setPhantom(String phantomNombre) {
        this.phantomNombre.set(phantomNombre);
    }
    
    public StringProperty phantomNombreProperty (){
        return phantomNombre;
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
