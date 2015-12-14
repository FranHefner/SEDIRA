/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sedira.model.Organo;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;

/**
 *  Funciones generales se trata de una encapsulacion de metodos y herramientas. 
 * @author Hefner Francisco , Quelin Pablo
 */
public class FuncionesGenerales {
    //Objeto de tipo Phantom auxiliar. 
        public static Phantom phantomActual;
        public static Radionuclido radionuclidoActual;
        public static int indice;

        public static String pattern = "dd-MM-yyyy";
         public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    public static  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public static int getIndice() {
        return indice;
    }

    public static void setIndice(int indice) {
        FuncionesGenerales.indice = indice;
    }
    
        
    /**
     * Metodo SetTer para el radionuclido actual. 
     * @param radionuclido 
     */
    public static void setRadioNuclidoActual (Radionuclido radionuclido){
        radionuclidoActual = radionuclido;
    }
    /**
     * Metodo GetTer para el radionuclido Actual. 
     * @return 
     */
    public static Radionuclido getRadioNuclidoActual (){
        return radionuclidoActual;
    }
    /**
     * Metodo GetTer para phantomActual
     * @return phantom actual
     */
    public static Phantom getPhantomActual() {
        return phantomActual;
    }
    /**
     * Metodo SetTer para phantomActual.
     */
    public static void setPhantomActual(Phantom phantom) {
        phantomActual = phantom;
    }
    
    
    
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

                if (Phantom.getPhantomNombre().toLowerCase().contains(lowerCaseFilter)) {
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
    
    
    public static SortedList <Radionuclido> FiltroListaRadNuclido(TableView <Radionuclido> griRadionuclido ,ObservableList<Radionuclido> radionuclidoData, TextField txtCampoBusqueda){
    // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Radionuclido> filteredData = new FilteredList<>(radionuclidoData, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        txtCampoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Radionuclido -> {
                // Si no se filtra nada, mostrara todos los phantoms
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Radionuclido.getNombreRadNuclido().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Busca solo por el Nombre del radionuclido. Pensar en agregar que busque por demas atributos. 
                } else {
                    return false; // 
                }
            });
        });
        
        // 3. Warp la  FilteredList con la  SortedList. 
        SortedList<Radionuclido> sortedData = new SortedList<>(filteredData);
        // 4. Conecta el comparador de SortedList con el comparador de  TableView.
        sortedData.comparatorProperty().bind(griRadionuclido.comparatorProperty());
        // 5.Agrego la lista Ordenada y Filtrada a la tabla. 
        return sortedData;
        
    }   
  
    /**
     * Muestra el detalle en una tabla Propiedad Valor. 
     * @param infoObjeto - Lista Observable con la del objeto que se insertara en la tabla 
     * @param tabla  - TableView para detallar la informacion de infoObjeto 
     */
    public static void mostrarDetalleTablaValorDescripcion (ObservableList<ValorDescripcion> infoObjeto, TableView <ValorDescripcion> tabla){
        tabla.setItems(infoObjeto);
    }
    
      /**
     * Muestra el detalle de los Organos pertenecientes al Phantom encontrado en la busqueda. 
     * @param organo  Lista observable con la informacion de los organos que contiene un phantom. 
     * @param tablaOrgano TableView para el detalle de los organos. 
     */
    @FXML
    public static void mostrarDetalleOrgano(ObservableList<Organo> organo, TableView tablaOrgano) {
        tablaOrgano.setItems(organo);
       
    }
   
    
    public static String DateLocalToString(LocalDate date) {
         if (date != null) {
             return dateFormatter.format(date);
         } else {
             return "";
         }
     }
    
    public static LocalDate StringToDateLocal(String string) {
         if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, dateFormatter);
         } else {
             return null;
         }
     }
    
    public static Date StringToDate(String Fecha) throws ParseException
    {  

       Date date = dateFormat.parse(Fecha);      
       

       return date;

    }
    public static LocalDate DateToLocalDate(Date Fecha)
    {
       
        LocalDate date = Fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return date;
    }
    
  /*  public static Date LocalDateToDate(LocalDate Fecha) {
        Date date = Date.from(Fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }  */
        
}
  
