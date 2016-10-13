/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import javafx.collections.ObservableList;

/**
 * Interface para CalculoDAO
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public interface ICalculoDAO {

    void getCalculoPaciente(int idPaciente);   
    
    ObservableList<CalculoMuestra>  getCalculos();

    void setCalculo(Calculo calculo);

    void setCalculoBasico(Calculo calculo);

}
