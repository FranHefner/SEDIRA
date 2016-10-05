/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import sedira.Security;

/**
 *
 * @author Hefner Francisco
 */
public class CalculoMuestra {
    private long Fecha;
    private String Paciente;
    private String Phantom;
    private String Radionuclido;
    private String Observaciones;
    private Blob Resultado;
    private String HashValidado;
    private String Formula;
    private String FormulaTex;

    /**
     * Constructor para la clase Calculo.
     *
     * @param pfecha
     * @param pidPaciente
     * @param pidPhantom
     * @param pidRadionuclido
     * @param pobservaciones
     * @param presultado
     */
    public CalculoMuestra(long pfecha, String ppaciente,  String pphantom, String pradionuclido, String pobservaciones, Blob presultado, String pformula, String pformulaTex) {
        Fecha = pfecha;
        Paciente = ppaciente;
        Phantom = pphantom;
        Radionuclido = pradionuclido;
        Observaciones = pobservaciones;
        Resultado = presultado;
        Formula = pformula;
        FormulaTex = pformulaTex;
        
    }

   


    /* Valida que el hash del parametro sea igual al actual*/
    /**
     * Compara el hash del cálculo guardado con el cálculo leido.
     *
     * @param hash
     * @return
     */
    public boolean ValidacionHash(String hash) {
        return HashValidado.equals(hash);
    }

    /**
     * Método para obtener el HashCode
     *
     * @return
     */
    public String getHashCode() {
        return HashValidado;
    }

    /**
     * Método GetTer para el atributo fecha.
     *
     * @return
     */
    public long getFecha() {
        return Fecha;

    }

    /**
     * Método SetTer para el atributo fecha.
     *
     * @param Fecha
     */
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

    /**
     * Método GetTer para el atributo formula.
     *
     * @return
     */
    public String getFormula() {
        return Formula;
    }
     /**
     * Método GetTer para el atributo formula.
     *
     * @return
     */
    public String getFormulaTex() {
        return FormulaTex;
    }
    
    
    /**
     * Método GetTer para el atributo formulaTex.
     *
     * @return
     */
    public String getPaciente() {
        return Paciente;
    }

    /**
     * Método GetTer para el atributo idPhantom.
     *
     * @return
     */
    public String getPhantom() {
        return Phantom;
    }

    /**
     * Método GetTer para el atributo idRadionuclido.
     *
     * @return
     */
    public String getRadionuclido() {
        return Radionuclido;
    }

    /**
     * Método GetTer para el atributo resultado.
     *
     * @return el resultado en tipo BLOB
     */
    public Blob getResultado() {
        return Resultado;
    }

    /**
     * Método GetTer para el atributo observaciones.
     *
     * @return
     */
    public String getObservaciones() {
        return Observaciones;
    }

  
}
