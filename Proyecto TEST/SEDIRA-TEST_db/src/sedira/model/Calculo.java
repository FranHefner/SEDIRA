/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.util.Date;
import javafx.collections.ObservableList;

/**
 * Clase que describe un calculo. 
 * El calculo almacena la informacion de : 
 * Fecha : dia en que se realiza el calculo. 
 * IdPaciente: Identificador del paciente al que se le realizo el calculo. 
 * IdPhantom: Identificador del phantom que se utiliza para el calculo. 
 * IdRadionuclido: Identificador del radionuclido que se utilizo para el calculo . 
 * 
 * @author Hefner Francisco, Quelin Pablo. 
 */
public class Calculo {
    
    private Date Fecha;
    private int idPaciente;
    private int  idPhantom;
    private int idRadionuclido;
   // private ObservableList <ValorDescripcion> DatosEntrada;
    private String observaciones;
    private String resultado;

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdPhantom() {
        return idPhantom;
    }

    public void setIdPhantom(int idPhantom) {
        this.idPhantom = idPhantom;
    }

    public int getIdRadionuclido() {
        return idRadionuclido;
    }

    public void setIdRadionuclido(int idRadionuclido) {
        this.idRadionuclido = idRadionuclido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    
       
    
}
