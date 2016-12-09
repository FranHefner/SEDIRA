/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

/**
 * Clase que contiene métodos de validación varios.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class ValidacionesGenerales {

    /**
     * Método que solo deja los números de una cadena de caracteres pasada por
     * parametros.
     *
     * @param Texto
     * @return
     */
    public static String DejarSoloNumeros(String Texto) {
        String Resultado = "";
        Resultado = Texto;
        for (int i = 0; i < Texto.length(); i++) {
            if (!Texto.substring(i, i + 1).matches("[0-9]")) {
                Resultado = Resultado.replace(Resultado.substring(i, i + 1), "*");
            }
        }

        return Resultado.replace("*", "");

    }

    /**
     * Método que solo deja letras de una cadena de caracteres pasada por
     * parametros.
     *
     * @param Texto
     * @return
     */
    public static String DejarSoloLetras(String Texto) {
        String Resultado = "";
        Resultado = Texto;
        for (int i = 0; i < Texto.length(); i++) {
            if (!Texto.substring(i, i + 1).toLowerCase().matches("[a-z]")) {
                Resultado = Resultado.replace(Resultado.substring(i, i + 1), "*");
            }
        }
        return Resultado.replace("*", "");

    }

    /**
     * Método que valida si una cadena de caracteres pasada por parametros es un
     * número.
     *
     * @param Numero
     * @return
     */
    public static boolean ValidarNumero(String Numero) {

        for (int i = 0; i < Numero.length(); i++) {
            if (!Numero.substring(i, i + 1).matches("[0-9]")) {
                return false;
            }

        }
        return true;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /*

     public static String bytesToHex(byte[] bytes) {
     char[] hexChars = new char[bytes.length * 2];
     for (int j = 0; j < bytes.length; j++) {
     int v = bytes[j] & 0xFF;
     hexChars[j * 2] = hexArray[v >>> 4];
     hexChars[j * 2 + 1] = hexArray[v & 0x0F];
     }
     return new String(hexChars);
     }*/
    /**
     * Método que controla si un nombre contiene los caracteres permitidos.
     *
     * @param Nombre
     * @return True si acepta. False si no
     */
    public static boolean ValidarNombre(String Nombre) {
        for (int i = 0; i < Nombre.length(); i++) {
            //^[\\p{L} .'-]+$

            if (!Nombre.substring(i, i + 1).toLowerCase().matches("[a-z]")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que controla si un nombre es aceptado. En este caso acepta
     * espacios entre caracteres. Utiliza expresiones regulares.
     *
     * @param Nombre
     * @return True si acepta. False si no
     */
    public static boolean ValidarNombreConEspacios(String Nombre) {
        return Nombre.matches("^[\\p{L} '-]+$");

    }

    public static boolean ValidarNumericoFloat(String Numero) {
        return Numero.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean ValidarNombreRadNuclido(String Numero) {
        return Numero.matches("[a-z,A-Z]*-[0-9]*");
    }

    /**
     * Método para validar los números de telefono
     * @param phoneNo
     * @return 
     */
    public static boolean validarNumeroTelefono(String phoneNo) {
        //Solo numero separados por guiones
        if (phoneNo.matches("([0-9]|-)*")) {
            return true;
        }
        else {
            return false;
        }
    }

    // regex para email 
        // ^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$  
    }
