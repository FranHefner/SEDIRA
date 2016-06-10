/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que describe a un usuario en el sistema
 * @author Quelin Pablo, Hefner Francisco   
 */
public class Usuario {
    private IntegerProperty idUsuario;
    private StringProperty descripcion;
    private StringProperty login; //nombre de usuario
    private StringProperty pass; 
    
    /**
     * Contructor de usuarios
     * @param idUsuario
     * @param descripcion
     * @param login
     * @param pass 
     */
    public Usuario(int idUsuario, String descripcion, String login, String pass) {
        this.idUsuario = new SimpleIntegerProperty (idUsuario);
        this.descripcion = new SimpleStringProperty (descripcion);
        this.login = new SimpleStringProperty (login);
        this.pass = new SimpleStringProperty (pass);
    }
    
//**********************************idUsuario GETters and SETters 
    public IntegerProperty getIdUsuarioProperty() {
        return idUsuario;
    }
    public int getIdUsuario (){
        return idUsuario.get();
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario.set(idUsuario);
    }
    
//**********************************descripcion GETters and SETters 

    public StringProperty getDescripcionPropery() {
        return descripcion;
    }
    public String getDescripcion (){
        return descripcion.get();
    }
    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    
//**********************************login GETters and SETters 

    public StringProperty getLoginProperty() {
        return login;
    }
    public String getLogin(){
        return login.get();
    }
    public void setLogin(String login) {
        this.login.set(login);
    }

//**********************************pass GETters and SETters 
    public StringProperty getPassProperty() {
        return pass;
    }
    public String getPass(){
        return pass.get();
    }
    public void setPass(String pass) {
        this.pass.set(pass);   }
    
    
    
    
}
