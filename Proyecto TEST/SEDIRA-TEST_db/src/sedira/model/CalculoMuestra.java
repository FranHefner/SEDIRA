/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.text.Format;
import java.text.SimpleDateFormat;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hefner Francisco
 */
public class CalculoMuestra {
    private IntegerProperty idCalculoMuestra;
    private LongProperty Fecha;
    private StringProperty Paciente;
    private StringProperty Phantom;
    private StringProperty Radionuclido;
    private StringProperty Observaciones;
    private Blob Resultado;
    private StringProperty HashValidado;
    private StringProperty Formula;
    private StringProperty FormulaTex;

    /**
     * Constructor para la clase CalculoMuestra.
     *
     * @param pfecha
     * @param ppaciente
     * @param pphantom
     * @param pobservaciones
     * @param pradionuclido
     * @param presultado
     * @param pformula
     * @param pformulaTex
     */
    public CalculoMuestra(int idCalculoM, long pfecha, String ppaciente,  String pphantom, String pradionuclido, String pobservaciones, Blob presultado, String pformula, String pformulaTex) {
        this.idCalculoMuestra = new SimpleIntegerProperty(idCalculoM);
        this.Fecha = new SimpleLongProperty (pfecha);
        this.Paciente = new SimpleStringProperty (ppaciente);
        this.Phantom = new SimpleStringProperty (pphantom);
        this.Radionuclido = new SimpleStringProperty (pradionuclido);
        this.Observaciones = new SimpleStringProperty (pobservaciones);
        this.Resultado = presultado;
        this.Formula = new SimpleStringProperty (pformula);
        this.FormulaTex = new SimpleStringProperty (pformulaTex);
        
    }

    public CalculoMuestra() {
        this.idCalculoMuestra = new SimpleIntegerProperty(0);
        this.Fecha = new SimpleLongProperty (0);
        this.Paciente = new SimpleStringProperty ("");
        this.Phantom = new SimpleStringProperty ("");
        this.Radionuclido = new SimpleStringProperty ("");
        this.Observaciones = new SimpleStringProperty ("");
        this.Resultado = Resultado;
        this.Formula = new SimpleStringProperty ("");
        this.FormulaTex = new SimpleStringProperty ("");
    }

    public IntegerProperty getIdCalculoMuestraProperty() {
        return idCalculoMuestra;
    }
    public int getIdCalculoMuestra(){
        return idCalculoMuestra.get();
    }
    public void setIdCalculoMuestra(int idCalculoMuestra) {
        this.idCalculoMuestra.set(idCalculoMuestra);
    }
    
     public StringProperty getPacienteProperty() {
        return Paciente;
    }
    public String getPaciente(){
        return Paciente.get();
    }
    public void setPaciente(String Paciente) {
        this.Paciente.set(Paciente);
    }
    
       
    public LongProperty getFechaProperty() {
        return Fecha;
    }
    public Long getFecha(){
        return Fecha.get();
    }
    public void setFecha(Long Fecha) {
        this.Fecha.set(Fecha);
    }
    
    
    

   
  
}
