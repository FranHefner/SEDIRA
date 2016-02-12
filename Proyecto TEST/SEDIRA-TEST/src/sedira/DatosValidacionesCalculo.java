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

    private static Calculo CalculoActual;
    private static Paciente PacienteActual;
    private static Phantom PhantomActual;
    private static Organo OrganoActual;
    private static Radionuclido RadionuclidoActual;

    private static void limpiarVariables() {
        CalculoActual = null;
        PacienteActual = null;
        PhantomActual = null;
        OrganoActual = null;
        RadionuclidoActual = null;
    }

    /* Validaciones por cada objeto antes de finalizar el proceso y llenar la entidad cálculo */
    /* 1 - Que este en la base de datos el objeto (Paciente, Phantom, Organo, Radionuclido)
     2 - Que el paciente este en tratamiento
     3 - add..
     */
    private static boolean validarPaciente() {

        /*Ej: Aplicar hash MD5 al objeto y compararlo con ConsultasDB.Obtener(Paciente)*/
        PhantomActual = null;
        OrganoActual = null;
        RadionuclidoActual = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarPhantom() {

        OrganoActual = null;
        RadionuclidoActual = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarOrgano() {

        RadionuclidoActual = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validaradionuclidoActual() {

        CalculoActual = null;

        return true;
    }

    public static boolean setPaciente(Paciente miPaciente) {
        PacienteActual = miPaciente;
        return validarPaciente();
    }

    public static boolean setPhantom(Phantom miPhantom) {
        PhantomActual = miPhantom;

        return validarPhantom();
    }

    public static boolean setOrgano(Organo miOrgano) {
        OrganoActual = miOrgano;

        return validarOrgano();
    }

    public static boolean setRadionuclido(Radionuclido miRadionuclido) {
        RadionuclidoActual = miRadionuclido;

        return validaradionuclidoActual();
    }

    public static void IniciarCalculo() {
        limpiarVariables();
    }

    public static String EstadoActual(boolean adelante, String pestañaActual) {
        /* Si adelante esta en falso, retorna el estado anterior al estado actual */

        if (!adelante) {            
            /* Si se selecciono algo en la pestaña actual se elimina */
            switch (pestañaActual) {
                case "Paciente":
                    PacienteActual = null;                    
                    break;
                case "Phantom":
                    PhantomActual = null;
                    break;
                case "Organo":
                    OrganoActual = null;
                    break;
                case "RadioNuclido":
                    RadionuclidoActual = null;
                    break;                   
                case "Completo":
                    break;
            }
        }
        /* Se valida el estado real del calculo */
            if (PacienteActual == null) {
                return "Paciente";
            } else {
                if (PhantomActual == null) {
                    if (!adelante) {
                        validarPaciente();
                        return "Paciente";
                    }
                    return "Phantom";

                } else {
                    if (OrganoActual == null) {
                        if (!adelante) {
                            validarPhantom();
                            return "Phantom";
                        }
                        return "Organo";
                    } else {
                        if (RadionuclidoActual == null) {
                            if (!adelante) {
                                validarOrgano();
                                return "Organo";
                            }
                            return "RadioNuclido";
                        } else {
                            if (!adelante) {
                                validaradionuclidoActual();
                                return "RadioNuclido";
                            }
                            return "Completo";
                        }
                    }
                }
            }

        }

    }
