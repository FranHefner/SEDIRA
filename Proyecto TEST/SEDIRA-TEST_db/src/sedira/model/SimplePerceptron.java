/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

/**
 * Clase que describe un Perceptrón simple.
 *
 * @author Quelin Pablo, Hefner Francisco
 */
public class SimplePerceptron {

    private double[] pesos;
    private double[] objetivos;
    private double[][] entradas;
    private int numeroEntradas;
    private static final double TASA_APRENDIZAJE = 0.5d;
    private String textoResultado ="";

    public String getTextoResultado() {
        return textoResultado;
    }

    public void setTextoResultado(String textoResultado) {
        this.textoResultado = textoResultado;
    }

    public double[] getPesos() {
        return pesos;
    }

    public void setPesos(double[] pesos) {
        this.pesos = pesos;
    }

    public double[] getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(double[] objetivos) {
        this.objetivos = objetivos;
    }

    public double[][] getEntradas() {
        return entradas;
    }

    public void setEntradas(double[][] entradas) {
        this.entradas = entradas;
        this.numeroEntradas = entradas[0].length;
    }

    /**
     * Método que inicializa los pesos sinápticos con números aleeatorios del
     * intervalo [-1, 1]
     */
    public void inicializarPesos() {
        pesos = new double[numeroEntradas];
        for (int i = 0; i < numeroEntradas; i++) {
            pesos[i] = Math.random();
        }
    }

    /**
     * Método para imprimir los pesos del perceptron.
     */
    public void imprimirPesos() {
        
        for (int i = 0; i < numeroEntradas; i++) {
            textoResultado += "W[" + i + "] = " + pesos[i];
            textoResultado += "\n";
        }
    }

    /**
     * wj(k+1)=wj(k)+&#951;[z(k)&#8722;y(k)]xj(k), j =1,2,...,n+1
     *
     * @param posicionEntrada
     */
    public void recalcularPesos(int posicionEntrada, double y) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + TASA_APRENDIZAJE * (objetivos[posicionEntrada] - y) * entradas[posicionEntrada][i];
        }
    }

    public void entrenar() {
        int indice = 0;
        double yi = 0;
        while (indice < entradas.length) {
            double suma = 0;
            for (int i = 0; i < numeroEntradas; i++) {
                suma += (pesos[i] * entradas[indice][i]);//&#8721; x[i] * W[i] 
            }
            /**
             * if (x>y) mayor = x; else mayor = y;
             * 
             * mayor=(x>y)?x:y;
             */
            yi = suma >= 0 ? 1 : -1;
           
            if (yi == objetivos[indice]) {
                //Correcto
                for (int i = 0; i < numeroEntradas; i++) {
                    textoResultado +=(entradas[indice][i]+"\t");
                }
                textoResultado +=(" => Esperada = " + objetivos[indice] + ", Calculada = " + yi + "\n");
            } else {
                //Incorrecto
                for (int i = 0; i < numeroEntradas; i++) {
                    textoResultado +=(entradas[indice][i] + "\t");
                }
                textoResultado +=(" => Esperada = " + objetivos[indice] + ", Calculada = " + yi + " [Error]");
                textoResultado +=("\n\nCorrección de pesos\n");
                recalcularPesos(indice, yi);
                imprimirPesos();
                textoResultado +=("\n--\n");
                indice = -1;
            }
            indice++;
        }
        textoResultado += "\nPESOS FINALES\n";
        imprimirPesos();
    }

}
