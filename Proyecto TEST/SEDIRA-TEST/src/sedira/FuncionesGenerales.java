/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.ValorDescripcion;

/**
 *
 * @author INVAP
 */
public class FuncionesGenerales {
    
    
    
   /**
    * Funcion que recibe una lista observable de pacientes, una TableView y retorna una lista 
    * de pacientes aplicando un filtro recibido por parametro.
    * @param griListaPacientes
    * @param pacienteData
    * @param txtCampoBusqueda
    * @return SortedList<Paciente>
    */
    
    public static  SortedList<Paciente>  FiltroListaPaciente( TableView<Paciente> griListaPacientes,ObservableList<Paciente> pacienteData, TextField txtCampoBusqueda)
    {
               
     
        FilteredList<Paciente> filteredData = new FilteredList<>(pacienteData, p -> true);

       
        txtCampoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Paciente -> {
               
                if (newValue == null || newValue.isEmpty()) {
                    return false;
                }

               
                String lowerCaseFilter = newValue.toLowerCase();

                if (Paciente.getNombre().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtro por el nombre
                } else if (Paciente.getApellido().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtro por el apellido
                }
                else if (Paciente.getNumeroDoc().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtro por el documento
                }
                return false; 
            });
        });
      
        
        SortedList<Paciente> sortedData = new SortedList<>(filteredData);

        // Enlazo el comparador de la lista filtrada con el comparador de la grilla
        sortedData.comparatorProperty().bind(griListaPacientes.comparatorProperty());

        //Devuelvo la lista filtrada para la grilla
        return sortedData;
        
    }
    /**
     * Funcion que retorna una lista ordenada de los phantoms mendiante un criterio de busqueda. 
     * @param griPhantom
     * @param phantomData
     * @param txtCampoBusqueda
     * @return 
     */
    public static SortedList <Phantom> FiltroListaPhantom (TableView<Phantom> griPhantom ,ObservableList<Phantom> phantomData, TextField txtCampoBusqueda){
    // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Phantom> filteredData = new FilteredList<>(phantomData, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        txtCampoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Phantom -> {
                // Si no se filtra nada, mostrara todos los phantoms
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Phantom.getPhantomNombre().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Busca solo por el Nombre del Phantom. Pensar en agregar que busque por peso. 
                } else {
                    return false; // 
                }
            });
        });
        
        // 3. Warp la  FilteredList con la  SortedList. 
        SortedList<Phantom> sortedData = new SortedList<>(filteredData);
        // 4. Conecta el comparador de SortedList con el comparador de  TableView.
        sortedData.comparatorProperty().bind(griPhantom.comparatorProperty());
        // 5.Agrego la lista Ordenada y Filtrada a la tabla. 
        return sortedData;
        
    }   
        
        
}
  
