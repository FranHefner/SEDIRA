/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sedira.model.TipoDocumento;
import sedira.model.Paciente;
import sedira.model.Phantom;
import sedira.model.Organo;
import sedira.model.Radionuclido;
import sedira.model.ValorDescripcion;
import java.sql.*;

/**
 * Clase de consultas y persistencia de datos.
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class ConsultasDB {

    public static ObservableList<Phantom> phantomData = FXCollections.observableArrayList();
    public static ObservableList<Radionuclido> radionuclidoData = FXCollections.observableArrayList();
    public static ObservableList<Organo> organoData = FXCollections.observableArrayList();

    /* Variables globales para la utilizacion de los Id sin motor de base de datos*/
    public static int phantomId = phantomData.size();
    public static int radNuclidoId = radionuclidoData.size();
    public static int organoId = organoData.size();

    /* Conexion con la base de datos */
    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public class mySQLBD {

        private Connection conexion;
        
        
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String BaseDeDatos = "jdbc:mysql://localhost/test?user=usuario&password=123";
            setConexion(DriverManager.getConnection(BaseDeDatos));
            if (getConexion() != null) {
                System.out.println("Conexion Exitosa!");
            } else {
                System.out.println("Conexion Fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ******************************
     */
    /**
     * Metodo para obtener los datos basicos de los pacientes
     *
     * @return Lista de Pacientes
     */
    public static ObservableList<Paciente> ListaPacientes() {

        // Se usa un costructor de pacientes mas acotado para cargar la grilla.
        ObservableList<Paciente> pacienteData = FXCollections.observableArrayList();
        pacienteData.add(new Paciente(1, "DNI", 34000001, "Hefner", "Fran"));
        pacienteData.add(new Paciente(2, "DNI", 34000002, "Quelin", "Pablo"));
        pacienteData.add(new Paciente(3, "DNI", 34000003, "Salibar", "Roberto"));
        pacienteData.add(new Paciente(4, "DNI", 34000004, "Argañaras", "Pablo"));
        pacienteData.add(new Paciente(5, "PAS", 34000005, "Perez", "Pepe"));

        return pacienteData;
    }

    /**
     * Metodo que inicializa los tipos de documento.
     *
     * @return Listado de los tipos de Documento.
     */
    public static ObservableList<String> ListaTipoDocumento() {
        ObservableList<TipoDocumento> TipoDocumentosData = FXCollections.observableArrayList();
        TipoDocumentosData.add(new TipoDocumento(1, "DNI", "Documento Nacional de Identidad"));
        TipoDocumentosData.add(new TipoDocumento(2, "PAS", "Pasaporte"));

        ObservableList<String> TipoDocumentosDataString = FXCollections.observableArrayList();
        TipoDocumentosDataString.add("DNI");
        TipoDocumentosDataString.add("PAS");

        return TipoDocumentosDataString;

    }

    /**
     * Metodo que inicializa un Phantom por defecto. Corresponde a los
     * siguientes datos datos por el modelo de Stabin MG y JA Siegel - 2003
     *
     */
    public static void iniciarPhantomsDefecto() //public static ObservableList <Phantom> iniciarPhantomsDefecto ()
    {
        // Un phantom esta compuesto de varios organos. 
        // Temporalmente se utiliza un contructor acotado de los organos que forman parte de un Phantom.

        //Ejemplo Organos Masculino Id harcodeados
        ObservableList<Organo> organosDataMasc = FXCollections.observableArrayList();
        organosDataMasc.add(new Organo(0, "Riñon", 299, 73700));
        organosDataMasc.add(new Organo(1, "Tiroide", 20.9, 73700));

        //Ejemplo Organos femenino Id harcodeados
        ObservableList<Organo> organosDataFem = FXCollections.observableArrayList();
        organosDataFem.add(new Organo(2, "Ovarios", 19, 56800));
        organosDataFem.add(new Organo(3, "Tiroide", 17, 56800));
        organosDataFem.add(new Organo(4, "Riñon", 275, 56800));

        //Ejemplo Phantom masculino    
        ObservableList<ValorDescripcion> listaAtributoPhantom = FXCollections.observableArrayList();
        listaAtributoPhantom.add(new ValorDescripcion("Altura", 167, "cm"));
        listaAtributoPhantom.add(new ValorDescripcion("Sup Cuerpo", 18000, "cm2"));
        listaAtributoPhantom.add(new ValorDescripcion("Peso total", 73700, "grs"));
        phantomData.add(new Phantom(0, "Adulto Masculino 76kg", listaAtributoPhantom, organosDataMasc));

        //Ejemplo Phantom Femenino
        ObservableList<ValorDescripcion> listaAtributoPhantomFem = FXCollections.observableArrayList();
        listaAtributoPhantomFem.add(new ValorDescripcion("Altura", 0, "cm"));
        listaAtributoPhantomFem.add(new ValorDescripcion("Sup Cuerpo", 0, "cm2"));
        listaAtributoPhantomFem.add(new ValorDescripcion("Peso total", 56800, "grs"));
        phantomData.add(new Phantom(1, "Adulto Femenino 56.8kg", listaAtributoPhantomFem, organosDataFem));

        // La separacion de los los organos fue necesarios para iniciar por defecto los phantoms. 
        //Una vez implementada la base de datos no será necesario. 
        organoData.addAll(organosDataMasc);
        organoData.addAll(organosDataFem);

        //return phantomData;
    }

    /**
     * Método para obtener la lista de Phantoms
     *
     * @return Lista de Phantoms
     */
    public static ObservableList<Phantom> ObtenerPhantoms() {
        return phantomData;
    }

    /**
     * Metodo que guarda un phantom creado/modificado en la lista de Phantoms
     * inicial
     *
     * @param phantom Objeto phantom a guardar en la lista de Phantoms ya
     * creados. Base datos.
     */
    public static void agregarPhantom(Phantom phantom) {

        phantomData.add(phantom);
        //System.out.print(phantomData.size());

    }

    /**
     * Metodo que inicializa por defecto a un radionuclido. En este caso el
     * Yodo-131. Datos obetenidos de Wikipedia.
     */
    public static void iniciarRadionuclidosDefecto() {
        ObservableList<ValorDescripcion> infoRadNuclido = FXCollections.observableArrayList();
        infoRadNuclido.add(new ValorDescripcion("Protones", 53, "unidadProton"));
        infoRadNuclido.add(new ValorDescripcion("Neutrones", 78, "unidadNeutron"));
        infoRadNuclido.add(new ValorDescripcion("Vida Media", 8.0197, "días"));
        infoRadNuclido.add(new ValorDescripcion("Masa atómica", 130.9061246, "u")); //Ojo la masa atomica se muestra como 130,9061246(12) . Posible error en los tipos de datos. 
        infoRadNuclido.add(new ValorDescripcion("Exceso de energía", 971, "KeV"));
        //Creacion de un lista de radionuclidos, Cada radionuclido puede contener una listua de valores que lo describen. 
        radionuclidoData.add(new Radionuclido(0, "Yodo-131", infoRadNuclido));
    }

    /**
     * Metodo que retorna la lista de radionuclidos.
     *
     * @return radionuclidoData. la lista de radionuclidos.
     */
    public static ObservableList<Radionuclido> obtenerRadionuclidos() {
        return radionuclidoData;
    }

    /**
     * Metodo que guarda un radionuclido creado en la lista de Radionuclido
     * inicial
     *
     * @param radionuclido Objeto radionuclido a guardar en la lista de
     * radionuclidos ya creados. Base datos.
     */
    public static void agregarRadionuclido(Radionuclido radionuclido) {

        radionuclidoData.add(radionuclido);
        //System.out.print(phantomData.size());

    }

    /**
     * Metodo que modifica un radionuclido ya almacenado.
     *
     * @param radionuclido a modificar
     * @param index indice del phantom a modificar.
     */
    public static void modificarRadionuclido(Radionuclido radionuclido, int index) {
        radionuclidoData.set(index, radionuclido);
    }

    /**
     * Metodo que modifica un Phantom ya almacenado
     *
     * @param phantom a modificar
     * @param index indice del phantom a modificar.
     */
    public static void modificarPhantom(Phantom phantom, int index) {
        phantomData.set(index, phantom);

    }

    /**
     * Método para actualizar un paciente
     *
     * @param IdPaciente Identificación del paciente
     * @param PacienteActualizado Paciente para actualizar
     * @return Booleano que informa el éxito del proceso.
     */
    public static boolean ActualizarPaciente(int IdPaciente, Paciente PacienteActualizado) {

        return true;
    }

    /**
     * Método para obtener la lista de órganos utilizados en Phantoms masculinos
     *
     * @return Lista de órganos
     *
     *
     * public static ObservableList<Organo> ObtenerOrganosMasc() { //Ejemplo
     * Organos Masculinos return organosDataMasc; } /** Método para obtener la
     * lista de órganos utilizados en Phantoms Femeninos
     * @return Lista de órganos
     *
     *
     * public static ObservableList<Organo> ObtenerOrganosFem() { //Ejemplo
     * Organos Femeninos return organosDataFem; }
     */
    public static ArrayList<TipoDocumento> ObtenerTiposDoc() {

        TipoDocumento TipoDoc1 = new TipoDocumento(1, "DNI", "Documento Nacional de Identidad");
        TipoDocumento TipoDoc2 = new TipoDocumento(2, "PAS", "Pasaporte");

        ArrayList<TipoDocumento> ListadoTipoDoc = new ArrayList<>();
        ListadoTipoDoc.add(TipoDoc1);
        ListadoTipoDoc.add(TipoDoc2);

        return ListadoTipoDoc;
    }

    public static String ObtenerTipoDoc(int ID) {
        if (ID == 1) {
            return "DNI";

        }
        if (ID == 2) {
            return "PAS";

        }
        return ("No encontrado");

    }

    public static int getNewIdPhantom() {
        phantomId = phantomId + 1;
        return phantomId;
    }

    public static int getNewIdRadNuclido() {
        radNuclidoId = radNuclidoId + 1;
        return radNuclidoId;
    }

    public static int getNewIdOrgano() {
        organoId = organoId + 1;
        return organoId;
    }
}
