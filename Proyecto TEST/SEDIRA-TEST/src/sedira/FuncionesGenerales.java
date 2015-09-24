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
}
