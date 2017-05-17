/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.collections.ObservableList;

/**
 * Interface para UsuarioDAOsql
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface IUsuarioDAO {

    public void agregarUsuario(Usuario usuario);

    public void modificarUsuario(Usuario usuario);

    public void eliminarUsuario(int idUsuario);

    public String obtenerTipoUsuario(int id);

    public boolean buscaUsuario(String usuario);

    public ObservableList<Usuario> obtenerUsuarios();
    
   public String descripcionTipobyID(int id);
}
