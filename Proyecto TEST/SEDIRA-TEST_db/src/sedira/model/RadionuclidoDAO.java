/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;


/**
 * Clase de acceso a datos para Radionuclidos. 
 * @author Quelin Pablo, Hefner Francisco.
 */
public class RadionuclidoDAO {
   
    
    /**
    * Metodo que retorna la lista completa de radionuclidos almacenados en la base de datos. 
    * @return 
    */
    public  static ObservableList <Radionuclido> obtenerListaRadNuclido (){
        //Creo una lista auxiliar
        ObservableList <Radionuclido> radionuclidoData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion= new ConexionDB();
        
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM radionuclido");
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                //objeto auxiliar
                Radionuclido radionuclido= new Radionuclido(0,"",null);
                
                radionuclido.setIdRadNuclido(Integer.parseInt(resultado.getString("id_radionuclido")));
                radionuclido.setNombreRadNuclido(resultado.getString("nombre_radionuclido"));
                radionuclidoData.add(radionuclido);
            }
            resultado.close();
                consulta.close();
                conexion.desconectar();

           } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar el radionuclido /n"+e);
            System.out.print(e);
           }
           
          
        return radionuclidoData;
    }
    
    /**
    * Metodo que retorna la la lista de informacion para un radionuclido seleccionado. 
    * @return 
    */
    public  static ObservableList <ValorDescripcion> obtenerInfoRadNuclido (Radionuclido radioNuclidoSeleccionado ){
        //Creo una lista auxiliar
        ObservableList <ValorDescripcion> infoRadNuclidoData = FXCollections.observableArrayList();
                
        //Instancia de conexion
        ConexionDB conexion= new ConexionDB();
        //objeto auxiliar
        
        int idRadNuclido = radioNuclidoSeleccionado.getIdRadNuclido(); 
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                        "SELECT * FROM radionuclido "
                      + "INNER JOIN valordescripcion "
                      + "ON radionuclido.id_radionuclido = valordescripcion.id_radionuclido "
                      + "WHERE radionuclido.id_radionuclido ="+idRadNuclido+";"
            );
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                //Ojeto Aux de tipo ValorDescripcion. 
                ValorDescripcion infoRadNuclido = new ValorDescripcion ("",0.0,"");
                //Completo el aux con la informacion obtenida de la BD
                infoRadNuclido.setDescripcion(resultado.getString("descripcion"));
                infoRadNuclido.setValor(Double.parseDouble(resultado.getString("valor")));
                //infoRadNuclido.setUnidad(resultado.getString("unidad"));
                
                //agregro al arreglo de propiedades la nueva propiedad parseada               
                infoRadNuclidoData.add(infoRadNuclido);
                //agrego al radionuclido seleccionado la lista de propiedades
                radioNuclidoSeleccionado.setPropiedades(infoRadNuclidoData);
                
            }
            resultado.close();
                consulta.close();
                conexion.desconectar();

           } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar la informacion del radionuclido /n"+e);
            System.out.print(e);
           }
           
          
        return infoRadNuclidoData;
    }
    
    
    
}

