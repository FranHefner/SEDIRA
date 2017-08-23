/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pablo Quelin, Hefner Francisco
 */
public class VariableCalculo {
    
      IntegerProperty vdId; 
      StringProperty descripcion;
      StringProperty valor;
      StringProperty variable;          
    /**
     * 
     * @param id identidicador para el uso en la base de datos. 
     * @param descripcion define el nombre de un nuevo atributo.
     * @param valor el valor que tomara ese nuevo atributo. 
     * @param variable se especifica la variable. Debido a la pluralidad de elementos que existen.
     */
      
    public VariableCalculo(int id, String descripcion, String valor, String variable) {
        this.vdId = new SimpleIntegerProperty (id);
        this.descripcion = new SimpleStringProperty (descripcion);
        this.valor = new SimpleStringProperty(valor);
        this.variable = new SimpleStringProperty (variable);
    }
    public VariableCalculo(){
        this.vdId = new SimpleIntegerProperty (-1);
        this.descripcion = new SimpleStringProperty ("");
        this.valor = new SimpleStringProperty("");
        this.variable = new SimpleStringProperty ("");
    }
    
    public int getId (){
        return vdId.get();
    }
    public void setId (int id){
        this.vdId.set(id);
    }
    public IntegerProperty idProperty(){
        return vdId;
    }
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    public StringProperty  descripcionProperty (){
        return descripcion;
    }
    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }
    public StringProperty valorProperty (){
        return valor;
    }

    public String getVariable() {
        return variable.get();
    }

    public void setVariable(String variable) {
        this.variable.set(variable);
    }
    public StringProperty variableProperty(){
        return variable; 
    }

}
