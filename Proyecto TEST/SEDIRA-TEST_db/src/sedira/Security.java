/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.security.MessageDigest;

/**
 * Clase auxiliar. Ofrece los mecanismos de segurirar para el control de
 * usuarios.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class Security {

    private static final String AESencrp = "AES";
    private static final byte[] fOmJ19yWN
            = new byte[]{'p', 'j', 'S', '9', '%', 's', '+',
                '6', 'F', '#', 'b', 'w', '&', 'x', 'w', '7'};

    /**
     * Método para encriptar un dato pasado por parámetros.
     *
     * @param Data
     * @return
     * @throws Exception
     */
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(AESencrp);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    /**
     * Método para desencriptar un dato pasado por parámetros.
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(AESencrp);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
      //  String decryptedValue = new String(decValue);
        return new String (decValue);
    }
 /**
  * 
c.init(Cipher.DECRYPT_MODE, key)
val decodedValue = new Base64().decode(encryptedValue.getBytes())
val decryptedVal = c.doFinal(decodedValue)
return new String(decryptedVal)
  */
    /**
     * Método para generar el Key Utilizado en los métodos de encriptación y
     * desencriptación.
     *
     * @return
     * @throws Exception
     */
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(fOmJ19yWN, AESencrp);
        return key;
    }

    /**
     * Método para aplicar el algoritmo de reducción criptografico.
     *
     * @param clear
     * @return
     * @throws Exception
     */
    public static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());

        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }

}
