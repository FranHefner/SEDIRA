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
 * Clase que describe a los radionuclidos. 
 * @author Quelin Pablo, Hefner Francisco.
 */
public class Radionuclido {
    private IntegerProperty idRadNuclido; //identificador unico. Para uso en Base de Datos 
    private StringProperty nombreRadNuclido;
    private ObservableList <ValorDescripcion> propiedades; // Otras propiedades. Escalabilidad. 
    
    
    /**
     * Contructor de RadioNuclidos. Contiene una lista de propiedades para mejorar la escalabilidad. 
     * @param idRadNuclido identificador unico para el uso en Base de datos. 
     */
   
    public Radionuclido(int idRadNuclido,String nombreRadNuclido, ObservableList<ValorDescripcion> propiedades) {
        this.idRadNuclido = new SimpleIntegerProperty (idRadNuclido);
        this.nombreRadNuclido = new SimpleStringProperty (nombreRadNuclido);
        this.propiedades = propiedades;
    }

    public IntegerProperty getIdRadNuclidoProperty() {
        return idRadNuclido;
    }
    public int getIdRadNuclido (){
        return idRadNuclido.get();
    }
    public void setIdRadNuclido(int idRadNuclido) {
        this.idRadNuclido.set(idRadNuclido);
    }
    
    
    public  StringProperty getNombreRadNuclidoProperty (){
        return nombreRadNuclido; 
    }
    public String getNombreRadNuclido (){
        return this.nombreRadNuclido.get();
    }
    public void setNombreRadNuclido (String nombreRadNuclido){
        this.nombreRadNuclido.set(nombreRadNuclido);
    }
    
    
    public ObservableList<ValorDescripcion> getPropiedades() {
        return propiedades;
    }
    public void setPropiedades(ObservableList<ValorDescripcion> propiedades) {
        this.propiedades = propiedades;
    }
    
    
    
    
    
}
