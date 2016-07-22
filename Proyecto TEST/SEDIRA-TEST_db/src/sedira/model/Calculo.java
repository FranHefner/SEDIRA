/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import sedira.Security;
import sedira.ValidacionesGenerales;

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
    
    private long Fecha;
    private int idPaciente;
    private int  idPhantom;
    private int idRadionuclido;
   // private ObservableList <ValorDescripcion> DatosEntrada;
    private String observaciones;
    private Blob resultado;
    private String HashValidado;

    
    
    public Calculo(long pfecha, int pidPaciente, int pidPhantom, int pidRadionuclido, String pobservaciones, Blob presultado) {
        Fecha = pfecha;
        idPaciente = pidPaciente;
        idPhantom = pidPhantom;
        idRadionuclido = pidRadionuclido;
        observaciones = pobservaciones;
        resultado = presultado;   
    }
        
    /**
     *
     * Método contructor Cálculo Básico   
     * @param pfecha
     * @param pidPaciente
     * @param pobservaciones
     * @param presultado      
     */
    public Calculo(long pfecha, int pidPaciente, String pobservaciones, Blob presultado) {
        Fecha = pfecha;
        idPaciente = pidPaciente;      
        observaciones = pobservaciones;
        resultado = presultado;   
    }
    
    
    
    public boolean Validar()
    {  
 
        String Resultado =   String.valueOf(Fecha) + idPaciente + idPhantom + idRadionuclido + observaciones + resultado.hashCode();
     
     
        try {
          
            /* ver si conviene el hash code de java o el que hice que devuelve string*/
              System.out.println(Resultado.hashCode());
                System.out.println(Security.md5( Resultado) );
            
            HashValidado =  Security.md5( Resultado);    
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Calculo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
    /* Valida que el hash del parametro sea igual al actual*/
    public boolean ValidacionHash(String hash)
    { 
        return HashValidado.equals(hash);
    }
    public String getHashCode()
    {
        return HashValidado;
    }
    public long getFecha() {
        return Fecha;
        
        
    }

    public void setFecha(long Fecha) {
        this.Fecha = Fecha;
    }
    
    
     public String getFechaDB() {

        Format formatter;

        if (Fecha != 0) {
            return null;
        } else {
            formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            return formatter.format(Fecha);

        }
        
                 
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

    public Blob getResultado() {
        return resultado;
    }

    public void setResultado(Blob resultado) {
        this.resultado = resultado;
    }
    
    
       
    
}
