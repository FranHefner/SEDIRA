/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import sedira.model.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    private StringProperty radNombre;  // Nombre: ejemplo Yodo-123
    private IntegerProperty protones; // Cantidad de protones 
    private IntegerProperty neutrones; //Cantidad de neutrones
    private DoubleProperty vidaMedia; //desintegracion
    private ObservableList <ValorDescripcion> propiedades; // Otras propiedades. Escalabilidad. 
    
    
    /**
     * Contructor de RadioNuclidos. Contiene una lista de propiedades para mejorar la escalabilidad. 
     * @param idRadNuclido identificador unico para el uso en Base de datos. 
     * @param radNombre Nombre, ejemplo: Yodo-131
     * @param protones Cantidad de protones
     * @param neutrones Cantidad de neutrones 
     * @param vidaMedia Tiempo de  vida media, ej: 12.33 horas 
     //* @param propiedades  Otras propiedades. 
     */
    public Radionuclido(int idRadNuclido, String radNombre, int protones, int neutrones, Double vidaMedia , ObservableList<ValorDescripcion> propiedades) {
        this.idRadNuclido = new SimpleIntegerProperty (idRadNuclido);
        this.radNombre = new SimpleStringProperty (radNombre);
        this.protones = new SimpleIntegerProperty (protones);
        this.neutrones = new SimpleIntegerProperty (neutrones);
        this.vidaMedia = new SimpleDoubleProperty (vidaMedia);
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
    
    

    public StringProperty getRadNombreProperty() {
        return radNombre;
    }
    public String getRadNombre (){
        return radNombre.get();
    }
    public void setRadNombre(String radNombre) {
        this.radNombre.set(radNombre);
    }

    
    public IntegerProperty getProtonesProperty() {
        return protones;
    }
    public int getProtones (){
        return protones.get();
    }
    public void setProtones(int protones) {
        this.protones.set(protones);
    }

    
    public IntegerProperty getNeutronesProperty() {
        return neutrones;
    }
    public int getNeutrones(){
        return neutrones.get();
    }
    public void setNeutrones(int neutrones) {
        this.neutrones.set(neutrones);
    }

    
    public DoubleProperty getVidaMediaProperty() {
        return vidaMedia;
    }
    public Double getVidaMedia(){
        return vidaMedia.get();
    }
    public void setVidaMedia(Double vidaMedia) {
        this.vidaMedia.set(vidaMedia);
    }
     
    public ObservableList<ValorDescripcion> getPropiedades() {
        return propiedades;
    }
    public void setPropiedades(ObservableList<ValorDescripcion> propiedades) {
        this.propiedades = propiedades;
    }
    
    
    
    
    
}
