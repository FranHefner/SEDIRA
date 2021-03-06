    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sedira.Security;

/**
 * Clase que describe un cálculo. El cálculo almacena la información de : Fecha
 * : dia en que se realiza el cálculo. IdPaciente: Identificador del paciente al
 * que se le realizó el cálculo. IdPhantom: Identificador del phantom que se
 * utilizó para el cálculo. IdRadionuclido: Identificador del radionuclido que
 * se utilizó para el cálculo .
 *
 * @author Hefner Francisco, Quelin Pablo.
 */
public class Calculo {

    private long Fecha;
    private int idPaciente;
    private int idPhantom;
    private int idRadionuclido;
    private int idOrgano;
    // private ObservableList <ValorDescripcion> DatosEntrada;
    private String observaciones;
    private String resultado;
    private String HashValidado;
    private String TipoCalculo;
    private String Formula;
    private String FormulaTex;
    private  List<VariableCalculo> VariablesCalculo;

    /**
     * Constructor para la clase Calculo.
     *
     * @param pfecha
     * @param pidPaciente
     * @param pidPhantom
     * @param pidRadionuclido
     * @param pobservaciones
     * @param presultado
     * @param variablesCalculo
     */
    public Calculo(long pfecha, int pidPaciente, int pidPhantom, int pidRadionuclido, int pidOrgano, String pobservaciones, String presultado, String formula, String formulaTex, List<VariableCalculo> variablesCalculo) {
        Fecha = pfecha;
        idPaciente = pidPaciente;
        idPhantom = pidPhantom;
        idRadionuclido = pidRadionuclido;
        idOrgano = pidOrgano;
        observaciones = pobservaciones;
        resultado = presultado;
        TipoCalculo = "Completo";
        Formula = formula;
        FormulaTex = formulaTex;
        VariablesCalculo = variablesCalculo;
        
    }

    /**
     *
     * Método contructor Cálculo Básico
     *
     * @param pfecha
     * @param pidPaciente
     * @param pobservaciones
     * @param presultado
     */
    public Calculo(long pfecha, int pidPaciente, String pobservaciones, String presultado) {
        Fecha = pfecha;
        idPaciente = pidPaciente;
        observaciones = pobservaciones;
        resultado = presultado;
        TipoCalculo = "Basico";

    }

    /**
     * Método que valida un cálculo realizado. Utiliza Hash para hacer la
     * comparación.
     *
     * @return
     */
    public boolean Validar() {

        String Resultado = Formula + FormulaTex + resultado ;

        try {
       
            System.out.println(Resultado.hashCode());
            System.out.println(Security.md5(Resultado));

            HashValidado = Security.md5(Resultado);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Calculo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
     * Método GetTer para el atributo idOrgano.
     *
     * @return
     */
    public int getIdOrgano() {
        return idOrgano;
    }
    
    
    /**
     * Método GetTer para el atributo idPaciente.
     *
     * @return
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * Método GetTer para el atributo idPhantom.
     *
     * @return
     */
    public int getIdPhantom() {
        return idPhantom;
    }

    /**
     * Método GetTer para el atributo idRadionuclido.
     *
     * @return
     */
    public int getIdRadionuclido() {
        return idRadionuclido;
    }

    /**
     * Método GetTer para el atributo resultado.
     *
     * @return el resultado en tipo BLOB
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Método GetTer para el atributo observaciones.
     *
     * @return
     */
    public String getObservaciones() {
        return observaciones;
    }
    
    public  List<VariableCalculo> getVariables()
    {
        return  VariablesCalculo;
    }

    /* Por ahora no se necesitan */
    /*
     public void setIdPaciente(int idPaciente) {
     this.idPaciente = idPaciente;
     }

     public void setIdRadionuclido(int idRadionuclido) {
     this.idRadionuclido = idRadionuclido;
     }

     public void setIdPhantom(int idPhantom) {
     this.idPhantom = idPhantom;
     }

     public void setObservaciones(String observaciones) {
     this.observaciones = observaciones;
     }

     public void setResultado(Blob resultado) {
     this.resultado = resultado;
     }
     */
}
