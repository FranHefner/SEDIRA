/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.nio.ByteBuffer;

/**
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class ValidacionesGenerales {
    
    public static String DejarSoloNumeros(String Texto) {
        String Resultado = "";
        Resultado = Texto;
        for (int i = 0; i < Texto.length(); i++) {
            if (!Texto.substring(i, i + 1).matches("[0-9]")) {
                Resultado = Resultado.replace(Resultado.substring(i, i + 1), "*");
            }
        }
       
        return  Resultado.replace("*", "");

    }

    public static String DejarSoloLetras(String Texto) {
        String Resultado = "";
        Resultado = Texto;
        for (int i = 0; i < Texto.length(); i++) {
            if (!Texto.substring(i, i + 1).toLowerCase().matches("[a-z]")) {
                Resultado = Resultado.replace(Resultado.substring(i, i + 1), "*");
            }
        }
        return  Resultado.replace("*", "");

    }
    public static boolean ValidarNumero(String Numero)
    {    
      
      for (int i=0;i<Numero.length();i++)
        {
          if ( !Numero.substring(i, i+1).matches("[0-9]"))
          {
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

    public static boolean ValidarNombre(String Nombre) {
        for (int i=0;i<Nombre.length();i++)
        {
          if ( !Nombre.substring(i, i+1).toLowerCase().matches("[a-z]"))
          {
              return false;
          }
        }       
        return true;
    }
      
    
}
