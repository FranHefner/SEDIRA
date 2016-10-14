/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sedira.FuncionesGenerales;

/**
 *
 * @author Hefner Francisco
 */
public class CalculoMuestra {

    private IntegerProperty idCalculoMuestra;
    private LongProperty Fecha;
    private StringProperty Paciente;
    private StringProperty Organo;
    private StringProperty Phantom;
    private StringProperty Radionuclido;
    private StringProperty Observaciones;
    private StringProperty Resultado;
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
    public CalculoMuestra(int idCalculoM, long pfecha, String ppaciente, String pphantom, String porgano, String pradionuclido, String pobservaciones, String presultado, String pformula, String pformulaTex, String phashValidado) {
        this.idCalculoMuestra = new SimpleIntegerProperty(idCalculoM);
        this.Fecha = new SimpleLongProperty(pfecha);
        this.Paciente = new SimpleStringProperty(ppaciente);
        this.Phantom = new SimpleStringProperty(pphantom);
        this.Organo = new SimpleStringProperty(porgano);
        this.Radionuclido = new SimpleStringProperty(pradionuclido);
        this.Observaciones = new SimpleStringProperty(pobservaciones);
        this.Resultado = new SimpleStringProperty(presultado);
        this.Formula = new SimpleStringProperty(pformula);
        this.FormulaTex = new SimpleStringProperty(pformulaTex);
        this.HashValidado = new SimpleStringProperty(phashValidado);

    }

    public CalculoMuestra() {
        this.idCalculoMuestra = new SimpleIntegerProperty(0);
        this.Fecha = new SimpleLongProperty(0);
        this.Paciente = new SimpleStringProperty("");
        this.Phantom = new SimpleStringProperty("");
        this.Organo = new SimpleStringProperty("");
        this.Radionuclido = new SimpleStringProperty("");
        this.Observaciones = new SimpleStringProperty("");
        this.Resultado = new SimpleStringProperty("");
        this.Formula = new SimpleStringProperty("");
        this.FormulaTex = new SimpleStringProperty("");
        this.HashValidado = new SimpleStringProperty("");
    }

    public IntegerProperty getIdCalculoMuestraProperty() {
        return idCalculoMuestra;
    }

    public int getIdCalculoMuestra() {
        return idCalculoMuestra.get();
    }

    public void setIdCalculoMuestra(int idCalculoMuestra) {
        this.idCalculoMuestra.set(idCalculoMuestra);
    }

    public StringProperty getPacienteProperty() {
        return Paciente;
    }

    public String getPaciente() {
        return Paciente.get();
    }

    public void setPaciente(String Paciente) {
        this.Paciente.set(Paciente);
    }

    public LongProperty getFechaProperty() {
        return Fecha;
    }
    public StringProperty getFechaSringProperty()
    {
        StringProperty fecha = new SimpleStringProperty("");
        fecha.set(FuncionesGenerales.LongToDateString(Fecha.getValue()));
        return fecha;
        
    }
    public Long getFecha() {
        return Fecha.get();
    }

    public void setFecha(Long Fecha) {
        this.Fecha.set(Fecha);
    }

    /*public StringProperty getFechaDB() {
        
     Format formatter;

     if ((Fecha == 0)) {
     return null;
     } else {
     formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
     a=formatter.format(Fecha);
     return a;

     }

     }*/
    public StringProperty getPhantomProperty() {
        return Phantom;
    }

    public String getPhantom() {
        return Phantom.get();
    }

    public void setPhantom(String Phantom) {
        this.Phantom.set(Phantom);
    }

    public StringProperty getOrganoProperty() {
        return Organo;
    }

    public String getOrgano() {
        return Organo.get();
    }

    public void setOrgano(String Organo) {
        this.Organo.set(Organo);
    }

    public StringProperty getRadionuclidoProperty() {
        return Radionuclido;
    }

    public String getRadionuclido() {
        return Radionuclido.get();
    }

    public void setRadionuclido(String Radionuclido) {
        this.Radionuclido.set(Radionuclido);
    }

    public StringProperty getObservacionesProperty() {
        return Observaciones;
    }

    public String getObservaciones() {
        return Observaciones.get();
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones.set(Observaciones);
    }

    public String getResultado() {
        return Resultado.get();
    }

    public void setResultado(String Resultado) {
        this.Resultado.set( Resultado );
    }

    public StringProperty getHashValidadoProperty() {
        return HashValidado;
    }

    public String getHashValidado() {
        return HashValidado.get();
    }

    public void setHashValidado(String HashValidado) {
        this.HashValidado.set(HashValidado);
    }

    public StringProperty getFormulaProperty() {
        return Formula;
    }

    public String getFormula() {
        return Formula.get();
    }

    public void setFormula(String Formula) {
        this.Formula.set(Formula);
    }

    public StringProperty getFormulaTexProperty() {
        return FormulaTex;
    }

    public String getFormulaTex() {
        return FormulaTex.get();
    }

    public void setFormulaTex(String FormulaTex) {
        this.FormulaTex.set(FormulaTex);
    }

}
