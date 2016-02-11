/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import sedira.model.Calculo;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;
import sedira.model.Radionuclido;

/**
 * Clase que define y guarda un calculo en tiempo de ejecucion
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class DatosValidacionesCalculo {

    private Calculo CalculoActual;
    private Paciente PacienteActual;
    private Phantom PhantomActual;
    private Organo OrganoActual;
    private Radionuclido RadionuclidoActual;

    private void limpiarVariables() {
        this.CalculoActual = null;
        this.PacienteActual = null;
        this.PhantomActual = null;
        this.OrganoActual = null;
        this.RadionuclidoActual = null;
    }

    /* Validaciones por cada objeto antes de finalizar el proceso y llenar la entidad cálculo */
    /* 1 - Que este en la base de datos el objeto (Paciente, Phantom, Organo, Radionuclido)
     2 - Que el paciente este en tratamiento
     3 - add..
     */
    private boolean validarPaciente() {

        /*Ej: Aplicar hash MD5 al objeto y compararlo con ConsultasDB.Obtener(Paciente)*/
        this.PhantomActual = null;
        this.OrganoActual = null;
        this.RadionuclidoActual = null;
        this.CalculoActual = null;

        return true;
    }

    private boolean validarPhantom() {

        this.OrganoActual = null;
        this.RadionuclidoActual = null;
        this.CalculoActual = null;

        return true;
    }

    private boolean validarOrgano() {

        this.RadionuclidoActual = null;
        this.CalculoActual = null;

        return true;
    }

    private boolean validaradionuclidoActual() {

        this.CalculoActual = null;

        return true;
    }

    public boolean setPaciente(Paciente miPaciente) {
        this.PacienteActual = miPaciente;
        return validarPaciente();
    }

    public boolean setPhantom(Phantom miPhantom) {
        this.PhantomActual = miPhantom;

        return validarPhantom();
    }

    public boolean setOrgano(Organo miOrgano) {
        this.OrganoActual = miOrgano;

        return validarOrgano();
    }

    public boolean setRadionuclido(Radionuclido miRadionuclido) {
        this.RadionuclidoActual = miRadionuclido;

        return validaradionuclidoActual();
    }

    public void IniciarCalculo() {
        limpiarVariables();
    }

    public String EstadoActual() {
        if (this.PacienteActual == null) {
            return "Paciente";
        } else {
            if (this.PhantomActual == null) {
                return "Phantom";
            } else {
                if (this.OrganoActual == null) {
                    return "Organo";
                } else {
                    if (this.RadionuclidoActual == null) {
                        return "RadioNuclido";
                    } else {
                        return "Completo";
                    }
                }
            }
        }

    }

}
