/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

/**
 *
 * @author Fran
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
      
    public static boolean ValidarNombre(String Nombre)
    {           
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
