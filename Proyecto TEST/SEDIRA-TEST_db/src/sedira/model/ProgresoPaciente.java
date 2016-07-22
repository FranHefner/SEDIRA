/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.util.Date;

/**
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class ProgresoPaciente {
    
    Date FechaMedicion;
    double Peso;
    int EstadoGeneral;
    private String Descripcion;
    private Blob Imagen;    
   
    
}
