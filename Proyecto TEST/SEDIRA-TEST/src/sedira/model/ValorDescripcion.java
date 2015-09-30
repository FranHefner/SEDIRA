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
 *Clase que describe a tablas de tipo Valor - Descripcion. Utilizado para mejorar la escalabilidad del proyecto. 
 * @author Hefner Francisco, Quelin Pablo
*/
public class ValorDescripcion {
    
       StringProperty descripcion;
       DoubleProperty valor;
       StringProperty unidad;
       
    /**
     * 
     * @param descripcion
     * @param valor
     * @param unidad 
     */
    public ValorDescripcion(String descripcion, double valor, String unidad) {
        this.descripcion = new SimpleStringProperty (descripcion);
        this.valor = new SimpleDoubleProperty(valor);
        this.unidad = new SimpleStringProperty (unidad);
    }

    public StringProperty getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringProperty descripcion) {
        this.descripcion = descripcion;
    }

    public DoubleProperty getValor() {
        return valor;
    }

    public void setValor(DoubleProperty valor) {
        this.valor = valor;
    }

    public StringProperty getUnidad() {
        return unidad;
    }

    public void setUnidad(StringProperty unidad) {
        this.unidad = unidad;
    }

   
    
  
       
}
