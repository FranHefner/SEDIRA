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
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 * Clase que define y guarda un cálculo en tiempo de ejecución En la aplicación,
 * está destinada a los "Cientificos". Dependera el tipo de usuario que inicia
 * la app.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class DatosValidacionesCalculo implements IDatosValidaciones {

    /**
     * Elementos necesarios para la realización del cálculo.
     */
    private static Calculo CalculoActual;
    private static Paciente PacienteActual;
    private static Phantom PhantomActual;
    //ItemPhantom es el item que se selecciona de la lista de atributos del phantom. 
    private static ValorDescripcion ItemPhantom;
    //ItemRadNuclido es el item que se selecciona de la lista de atributos del Radionuclido. 
    private static ValorDescripcion ItemRadNuclido;

    private static Organo OrganoActual;
    private static Radionuclido RadionuclidoActual;
    //Variable que se encarga de almacenar información recopilada durante el proceso de cálculo. 
    private static String TextoProgreso;
    private static String Observaciones;
    private static Blob Resultado;
    private static Boolean ProcesoCompleto;

    private static void limpiarVariables() {
        CalculoActual = null;
        PacienteActual = null;
        PhantomActual = null;
        OrganoActual = null;
        RadionuclidoActual = null;
        ItemRadNuclido = null;
        ItemPhantom = null;
    }

    //Implementación de la interface ICalculo. En este caso, utiliza el lenguaje de base de datos MySql. 
    private static final ICalculoDAO cal = new CalculoDAOsql();

    /* Validaciones por cada objeto antes de finalizar el proceso y llenar la entidad cálculo */
    /* 
     1 - Que este en la base de datos el objeto (Paciente, Phantom, Organo, Radionuclido)
     2 - Que el paciente este en tratamiento
     3 - add..
     */
    @Override
    public void setProcesoCompleto(boolean esCompleto) {
        ProcesoCompleto = esCompleto;
    }

    @Override
    public boolean getProcesoCompleto() {
        return ProcesoCompleto;
    }

    @Override
    public boolean finalizarCalculo(Blob resultado) {
        Resultado = resultado;

        return true;
    }

    /**
     * Método que almacena en un cadena de texto toda la información de los
     * items seleccionados en el proceso de cálculo.
     *
     * @return
     */
    @Override
    public String GetTextoProgeso() {
        TextoProgreso = "";

        if (ProcesoCompleto) {
            if (PacienteActual == null) {

            } else {
                TextoProgreso = TextoProgreso + "Paciente: " + getPacienteActual().getApellido() + ", " + getPacienteActual().getNombre();

                if (PhantomActual != null && ItemPhantom != null) {
                    TextoProgreso = TextoProgreso + "\n" + "Phantom: " + getPhantomActual().getPhantomNombre();
                    TextoProgreso = TextoProgreso + "\n" + "Item Phantom: " + getItemPhantom().getDescripcion();

                    if (OrganoActual != null) {
                        TextoProgreso = TextoProgreso + "\n" + "Organo: " + getOrganoActual().getNombreOrgano();

                        if (RadionuclidoActual != null && ItemRadNuclido != null) {

                            TextoProgreso = TextoProgreso + "\n" + "Radionuclido: " + getRadionuClidoActual().getNombreRadNuclido();
                            TextoProgreso = TextoProgreso + "\n" + "Item Radionuclido : " + getItemRadNuclido().getDescripcion();
                        }
                    }

                }

            }
        } else if (PacienteActual == null) {

        } else {
            TextoProgreso = TextoProgreso + "\n" + "Paciente: " + getPacienteActual().getApellido() + ", " + getPacienteActual().getNombre();
        }

        return TextoProgreso;
    }

    /**
     * Método que almacena el resultado del cálculo y todos los componentes en
     * la base de datos
     *
     * @return
     */
    @Override
    public boolean guardarCalculo() {

        Date Ahora = new Date();

        if (Resultado != null) {
            Calculo nuevoCalculo = new Calculo(Ahora.getTime(), PacienteActual.getIdPaciente(), PhantomActual.getIdPhantom(), RadionuclidoActual.getIdRadNuclido(), Observaciones, Resultado);

            /*  Se valida el hash antes de guardar, para luego tomarlo nuevamente de la base de datos y comparalo para ver si son iguales
             la idea es poner un byte array, por otro lado hay que ver si conviene implementar el hash que viene por defecto en netbeans
             hashCode() o usar el que hice que te devuelve un string en vez de un entero.
           
             */
            nuevoCalculo.Validar();

            cal.setCalculo(nuevoCalculo);

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
        PhantomActual = null;
        ItemPhantom = null;
        OrganoActual = null;
        RadionuclidoActual = null;
        ItemRadNuclido = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarPhantom() {
        //ItemPhantom = null;
        OrganoActual = null;
        RadionuclidoActual = null;
        ItemRadNuclido = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarItemPhantom() {

        OrganoActual = null;
        RadionuclidoActual = null;
        ItemRadNuclido = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarOrgano() {

        RadionuclidoActual = null;
        ItemRadNuclido = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validaradionuclidoActual() {
        ItemRadNuclido = null;
        CalculoActual = null;

        return true;
    }

    private static boolean validarItemRadNuclido() {

        CalculoActual = null;

        return true;
    }

    @Override
    public boolean setPaciente(Paciente miPaciente) {
        PacienteActual = miPaciente;
        return validarPaciente();
    }

    /**
     * Método SetTer para el Phantom seleccionado en el proceso de cálculo.
     *
     * @param miPhantom
     * @return
     */
    @Override
    public boolean setPhantom(Phantom miPhantom) {
        PhantomActual = miPhantom;

        return validarPhantom();
    }

    /**
     * Método SetTer para el Item del Phantom seleccionado en el proceso de
     * cálculo.
     *
     * @param miItemPhantom
     * @return
     */
    @Override
    public boolean setItemPhantom(ValorDescripcion miItemPhantom) {
        ItemPhantom = miItemPhantom;
        return validarItemPhantom();
    }

    @Override
    public boolean setItemRadNuclido(ValorDescripcion miItemRadNuclido) {
        ItemRadNuclido = miItemRadNuclido;
        return validarItemRadNuclido();
    }

    /**
     * Método SetTer para el organo seleccionado en el proceso de cálculo.
     *
     * @param miOrgano
     * @return
     */
    @Override
    public boolean setOrgano(Organo miOrgano) {
        OrganoActual = miOrgano;

        return validarOrgano();
    }

    /**
     * Método SetTer para el Radionuclido seleccionado en el proceso de cálculo.
     *
     * @param miRadionuclido
     * @return
     */
    @Override
    public boolean setRadionuclido(Radionuclido miRadionuclido) {

        if (miRadionuclido != null) {
            RadionuclidoActual = miRadionuclido;
        }

        return validaradionuclidoActual();
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
    public Phantom getPhantomActual() {
        if (PhantomActual != null) {
            return PhantomActual;
        } else {
            return null;
        }
    }

    @Override
    public ValorDescripcion getItemPhantom() {
        if (ItemPhantom != null) {
            return ItemPhantom;
        } else {
            return null;
        }
    }

    @Override
    public ValorDescripcion getItemRadNuclido() {
        if (ItemRadNuclido != null) {
            return ItemRadNuclido;
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
    public Organo getOrganoActual() {
        if (OrganoActual != null) {
            return OrganoActual;
        } else {
            return null;
        }
    }

    @Override
    public Radionuclido getRadionuClidoActual() {
        if (RadionuclidoActual != null) {
            return RadionuclidoActual;
        } else {
            return null;
        }
    }

    /**
     * Método que se llama al iniciar el cálculo. Limpia todas las variables
     * para comenzar 0
     */
    @Override
    public void IniciarCalculo() {
        limpiarVariables();
    }

    /**
     * Método para el control del estado en el cual se encuentra el cálculo.
     *
     * @param adelante
     * @param pestañaActual
     * @return
     */
    @Override
    public String getEstadoActual(boolean adelante, String pestañaActual) {
        /* Si adelante esta en falso, retorna el estado anterior al estado actual */

        if (!adelante) {
            /* Si se selecciono algo en la pestaña actual se elimina */
            switch (pestañaActual) {
                case "Paciente":
                    PacienteActual = null;
                    break;
                case "Phantom":
                    PhantomActual = null;
                    ItemPhantom = null;
                    break;
                case "Organo":
                    OrganoActual = null;
                    break;
                case "RadioNuclido":
                    RadionuclidoActual = null;
                    ItemRadNuclido = null;
                    break;
                case "Completo":
                    break;
            }
        }
        /* Se valida el estado real del calculo */
        if (PacienteActual == null) {
            return "Paciente";
        } else if (PhantomActual == null) {
            if (!adelante) {
                validarPaciente();
                return "Paciente";
            }
            return "Phantom";

        } else if (ItemPhantom == null) {
            if (!adelante) {
                validarPaciente();
                return "Paciente";
            }
            return "Phantom";
        } else if (OrganoActual == null) {
            if (!adelante) {
                validarPhantom();
                validarItemPhantom();
                return "Phantom";
            }
            return "Organo";
        } else if (RadionuclidoActual == null) {
            if (!adelante) {
                validarOrgano();
                return "Organo";
            }
            return "RadioNuclido";
        } else if (ItemRadNuclido == null) {
            if (!adelante) {
                validarOrgano();
                return "Organo";
            }
            return "RadioNuclido";
        } else {
            if (!adelante) {
                validaradionuclidoActual();
                validarItemRadNuclido();
                return "RadioNuclido";
            }
            return "Completo";
        }

    }

}
