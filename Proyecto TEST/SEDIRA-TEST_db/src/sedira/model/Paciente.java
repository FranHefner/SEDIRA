    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.sql.Blob;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

/**
 * Clase Paciente, describe a un paciente dentro del sistema del calculo SEDIRA;
 *
 */
public class Paciente {

    private IntegerProperty idPaciente;
    private StringProperty tipoDoc;
    private IntegerProperty numeroDoc;
    private StringProperty apellido;
    private StringProperty nombre;
    private Date fechaNacimiento;
    private StringProperty direccion;
    private int numeroAsociado;
    private StringProperty email;
    private StringProperty telefono;
    private StringProperty celular;
    private Blob foto;
    private StringProperty sexo;
    private boolean enTratamiento;
    private ProgresoPaciente estadoActual;
    private Boolean esNuevo;
    // private List<ProgresoPaciente> Progreso;

    /**
     * Contructor con parámetros para pacientes.
     *
     * @param idPaciente
     * @param tipoDoc
     * @param numeroDoc
     * @param apellido
     * @param nombre
     * @param fechaNacimiento
     * @param direccion
     * @param numeroAsociado
     * @param email
     * @param telefono
     * @param celular
     * @param sexo
     * @param enTratamiento
     */
    public Paciente(int idPaciente, String tipoDoc, int numeroDoc, String apellido, String nombre, Date fechaNacimiento, String direccion, int numeroAsociado, String email, String telefono, String celular,/* Blob foto,*/ String sexo, boolean enTratamiento, Boolean esNuevo) {
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.tipoDoc = new SimpleStringProperty(tipoDoc);
        this.numeroDoc = new SimpleIntegerProperty(numeroDoc);
        this.apellido = new SimpleStringProperty(apellido);
        this.nombre = new SimpleStringProperty(nombre);
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = new SimpleStringProperty(direccion);
        this.numeroAsociado = numeroAsociado;
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
        this.celular = new SimpleStringProperty(celular);  
        this.sexo = new SimpleStringProperty(sexo);
        this.enTratamiento = enTratamiento;
        this.esNuevo = esNuevo;
        
    }

    /**
     * Contructor sin parámetros para pacientes.
     */
    public Paciente() {
        this.idPaciente = new SimpleIntegerProperty(0);
        this.tipoDoc = new SimpleStringProperty("");
        this.numeroDoc = new SimpleIntegerProperty(0);
        this.apellido = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.fechaNacimiento = null;
        this.direccion = new SimpleStringProperty("");
        this.numeroAsociado = 0;
        this.email = new SimpleStringProperty("");
        this.telefono = new SimpleStringProperty("");
        this.celular = new SimpleStringProperty("");
        this.sexo = new SimpleStringProperty("");
        this.enTratamiento = true;
        this.esNuevo = true;
    }

    public Date getFechaNacimientoDATE() {

        if (fechaNacimiento == null) {
            return null;
        }
        return fechaNacimiento;
    }

    public String getFechaNacimientoString() {

        if (fechaNacimiento == null) {
            return null;
        } else {
            return fechaNacimiento.toString();
        }

    }

    public String getFechaNacimientoDB() {

        Format formatter;

        if (fechaNacimiento == null) {
            return null;
        } else {
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            return formatter.format(fechaNacimiento);

        }

    }

    //idPaciente
    public IntegerProperty getIdPacienteProperty() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente.set(idPaciente);
    }

    public int getIdPaciente() {
        return idPaciente.get();
    }

    //TipoDoc
    public String getTipoDoc() {
        return tipoDoc.get();
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc.set(tipoDoc);
    }

    public StringProperty getTipoDocProperty() {
        return tipoDoc;
    }

    //NumeroDoc
    public int getNumeroDoc() {
        return numeroDoc.get();
    }

    public void setNumeroDoc(int numeroDoc) {
        this.numeroDoc.set(numeroDoc);
    }

    public IntegerProperty getNumeroDocProperty() {
        return numeroDoc;
    }

    //Apellido
    public StringProperty getApellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getApellido() {
        return apellido.get();
    }

    //Nombre
    public StringProperty getNombreProperty() {
        return nombre;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    //FechaNacimiento
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = fechaNacimiento;

        try {

            Date date = formatter.parse(dateInString);
            this.fechaNacimiento = date;

        } catch (ParseException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ingreso de datos inválido");
            alert.setHeaderText("Fecha invalida ");
            alert.setContentText("La fecha que se quiere guardar es inválida");
            alert.showAndWait();
            //e.printStackTrace();
        }

    }

    //Direccion
    public StringProperty getDireccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getDireccion() {
        return direccion.get();
    }

    //Numero asociado
    public int getNumeroAsociado() {
        return numeroAsociado;
    }

    public void setNumeroAsociado(int numeroAsociado) {
        this.numeroAsociado = numeroAsociado;
    }

    //Email
    public StringProperty getEmailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    //Telefono
    public StringProperty getTelefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getTelefono() {
        return telefono.get();
    }

    //Celular
    public StringProperty getCelularProperty() {
        return celular;
    }

    public void setcelular(String celular) {
        this.celular.set(celular);
    }

    public String getcelular() {
        return celular.get();
    }

    //FOTO
    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    //Sexo
    public StringProperty getSexoProperty() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo.set(sexo);
    }

    public String getSexo() {
        return sexo.get();
    }

    //Entratamiento
    public boolean enEnTratamiento() {
        return enTratamiento;
    }

    public void setEnTratamiento(boolean enTratamiento) {
        this.enTratamiento = enTratamiento;
    }

    public ProgresoPaciente getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(ProgresoPaciente EstadoActual) {
        this.estadoActual = EstadoActual;
    }
     public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean EsNuevo) {
        this.esNuevo = EsNuevo;
    }

}
