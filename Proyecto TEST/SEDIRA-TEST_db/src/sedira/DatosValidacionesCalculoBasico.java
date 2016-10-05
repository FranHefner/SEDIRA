/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.sql.Blob;
import java.util.Date;
import sedira.model.Calculo;
import sedira.model.CalculoDAOsql;
import sedira.model.ICalculoDAO;
import sedira.model.Organo;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 * Clase que define y guarda un cálculo en tiempo de ejecución. 
 * El cálculo básico está definido para los usuarios que desen obtener una interfaz mas rápida y sencilla. 
 * En la aplicación, está destinada a los "Medicos". 
 * Dependera el tipo de usuario que inicia la app. 
 * @author Hefner Francisco, Quelin Pablo
 */
public class DatosValidacionesCalculoBasico implements IDatosValidaciones {

    private static Paciente PacienteActual;
    private static Calculo CalculoActual;
    public static String TextoProgreso;
    private static String Observaciones;
    private static Blob Resultado;
    private static Boolean ProcesoCompleto;

    private static final ICalculoDAO cal = new CalculoDAOsql();

    private void limpiarVariables() {
        CalculoActual = null;
        PacienteActual = null;

    }

   @Override
    public  String GetTextoProgeso() {
        TextoProgreso = "INFORMACION DEL PROCESO/SELECCION";

        if (PacienteActual != null) {

            TextoProgreso = TextoProgreso + "\n" + "Paciente: " + getPacienteActual().getApellido() + ", " + getPacienteActual().getNombre();
        }

        return TextoProgreso;
    }

    @Override
    public void setProcesoCompleto(boolean esCompleto) {
        ProcesoCompleto = esCompleto;
    }

    @Override
    public boolean getProcesoCompleto() {
        return ProcesoCompleto;
    }

    @Override
    public  boolean finalizarCalculo(Blob resultado, String formula, String formulaTex) {
        Resultado = resultado;

        return true;
    }

    @Override
    public  boolean guardarCalculo() {

        Date Ahora = new Date();

        if (Resultado != null) {
            Calculo nuevoCalculo = new Calculo(Ahora.getTime(), PacienteActual.getIdPaciente(), Observaciones, Resultado);

            /*  Se valida el hash antes de guardar, para luego tomarlo nuevamente de la base de datos y comparalo para ver si son iguales
           la idea es poner un byte array, por otro lado hay que ver si conviene implementar el hash que viene por defecto en netbeans
           hashCode() o usar el que hice que te devuelve un string en vez de un entero.
           
             */
            nuevoCalculo.Validar();

            cal.setCalculoBasico(nuevoCalculo);

        } else {
            return false;
        }

        /* Una vez guardado, obtener el dato y aplicar Hash para ver si coinciden, como forma de asegurarse el resiltado*/
        /* ver en que tipo de dato se guarda, podria ser en binaio para asegurarnos que no van a existir casteos de la db 
        porque pude ser que cambie el motor de db        */
        return true;
    }

    private static boolean validarPaciente() {

        /*Ej: Aplicar hash MD5 al objeto y compararlo con ConsultasDB.Obtener(Paciente)*/
        CalculoActual = null;

        return true;
    }

    @Override
    public  boolean setPaciente(Paciente miPaciente) {
        PacienteActual = miPaciente;
        return validarPaciente();
    }

    @Override
    public Blob getResultado() {
        if (Resultado != null) {
            return Resultado;
        } else {
            return null;
        }
    }
    @Override
    public Paciente getPacienteActual() {
        if (PacienteActual != null) {
            return PacienteActual;
        } else {
            return null;
        }
    }
    @Override
    public void IniciarCalculo() {
        limpiarVariables();
    }
      @Override
    public String getEstadoActual(boolean adelante, String pestañaActual) {
        /* Si adelante esta en falso, retorna el estado anterior al estado actual */

        if (!adelante) {
            /* Si se selecciono algo en la pestaña actual se elimina */
            switch (pestañaActual) {
                case "Paciente":
                    PacienteActual = null;
                    break;               
                case "Completo":
                    break;
            }
        }
        /* Se valida el estado real del calculo */
        if (PacienteActual == null) {
            return "Paciente";
        }else {
           
            return "Completo";
        }
    }

   
    @Override
    public Phantom getPhantomActual() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organo getOrganoActual() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Radionuclido getRadionuClidoActual() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    @Override
    public boolean setPhantom(Phantom miPhantom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setOrgano(Organo miOrgano) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setRadionuclido(Radionuclido miRadionuclido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}
