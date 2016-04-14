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
    private int IdPaciente;
    private int  IdPhantom;
    private int IdRadionuclido;
    private ObservableList <ValorDescripcion> DatosEntrada;
    private String observaciones;
    
    
       
    
}
