/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 *  Clase de Acceso de datos para objetos de tipo Phamtonm
 * @author Quelin Pablo, Hefner Francisco
 */
public class PhantomDAO {

    public static ObservableList<Phantom> obtenerListaPhantom() {
        //Creo una lista auxiliar
        ObservableList<Phantom> phantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement("SELECT * FROM phantoms");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //objeto auxiliar
                // parametros de inicializacion del contructor (id, nombre, listaPropiedades, listaOrganos)
                Phantom phantom = new Phantom(0, "", null, null);
                //obtencion de los datos desde la bd.
                phantom.setIdPhantom(Integer.parseInt(resultado.getString("id_phantom")));
                phantom.setPhantomNombre(resultado.getString("nombre_phantom"));
                //agrego el objeto a la lista de phantom. Esta se retornada para completar la vista.
                phantomData.add(phantom);

            }
            resultado.close();
            consulta.close();
            conexion.desconectar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar el phantom /n" + e);
            System.out.print(e);
        }

        return phantomData;
    }
    
    public static ObservableList<ValorDescripcion> obtenerInfoPhantom(Phantom phantomSeleccionado) {
        //Creo una lista auxiliar
        ObservableList<ValorDescripcion> infoPhantomData = FXCollections.observableArrayList();
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        //objeto auxiliar
        int idPhantom = phantomSeleccionado.getIdPhantom();
        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT * FROM phantoms "
                            + "INNER JOIN valordescripcion "
                            + "ON phantoms.id_phantom = valordescripcion.id_phantom "
                            + "WHERE phantoms.id_phantom =" + idPhantom + ";"
            );
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                //Ojeto Aux de tipo ValorDescripcion.
                ValorDescripcion infoPhantom = new ValorDescripcion(-1, "", 0.0, "");
                
                //Completo el aux con la informacion obtenida de la BD
                infoPhantom.setId(Integer.parseInt(resultado.getString("id_valordescripcion")));
                infoPhantom.setDescripcion(resultado.getString("descripcion"));
                infoPhantom.setValor(Double.parseDouble(resultado.getString("valor")));
                infoPhantom.setUnidad(resultado.getString("unidad"));
                
                //agregro al arreglo de propiedades la nueva propiedad parseada
                infoPhantomData.add(infoPhantom);
                //agrego al phantom seleccionado la lista de propiedades
                phantomSeleccionado.setPropiedades(infoPhantomData);
            }
            resultado.close();
            consulta.close();
            conexion.desconectar();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se pudo consultar la información del phantom /n" + e);
            System.out.print(e);
        }
        
        return infoPhantomData;
    }
    
    
    
    public static void agregarPhantom (Phantom phantom){
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();
        String nombrePhantom = phantom.getPhantomNombre();
        try {
            //Antes de insertar corrobora que no exista el nombre
            if (buscaNombre(nombrePhantom)) {
                Statement consulta = conexion.getConnection().createStatement();
                consulta.executeUpdate("INSERT INTO phantoms (nombre_phantom) VALUES ('" + nombrePhantom+ "')");
                
            } else {
                
                //JOptionPane.showMessageDialog(null, "El phantom " + nombrePhantom + " ya existe!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la creación");
        }
        
    }
    public static void modificarPhantom(){
        
    }
    public static void eliminarPhantom(){
        
    }
    
    public static boolean buscaNombre(String nombrePhantom) throws SQLException {
        //Instancia de conexion
        ConexionDB conexion = new ConexionDB();

        try {
            PreparedStatement consulta = conexion.getConnection().prepareStatement(
                    "SELECT nombre_phantom FROM phantoms WHERE nombre_phantom = ?");
            consulta.setString(1, nombrePhantom);
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                consulta.close();
                
                return false;
            } else {
                //Si no hay coincidencias. o sea, la cantidad de tuplas es 0 entonces EL nombre no existe
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error! " + e);
            return false;
        }
    }
    
}
