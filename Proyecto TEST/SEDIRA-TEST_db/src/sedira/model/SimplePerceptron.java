/*
 * 
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
    private static final double TASA_APRENDIZAJE = 0.9d;
    private String textoResultado = "";
    private int pasos = 0;
    private double pesoInicial[];

    public double[] getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(double[] pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public int getNumeroEntradas() {
        return numeroEntradas;
    }

    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }

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

    public int getPasos() {
        return pasos;
    }

    public void setPasos() {
        this.pasos = pasos + 1;
    }

    /**
     * Método que inicializa los pesos sinápticos con números aleeatorios del
     * intervalo [-1, 1].
     */
    public void inicializarPesos() {
        pesos = new double[numeroEntradas];
        pesoInicial = new double[numeroEntradas];
        for (int i = 0; i < numeroEntradas; i++) {
            pesos[i] = Math.random();
        }
       
        /* pesos[0]=2.1964972347264435;
         pesos[1]=1.8275669069278204;
         pesos[2]=-1.737038378012491;*/
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
     * Método llamado en el momento de aprendizaje.
     *
     * @param posicionEntrada
     * @param y
     */
    public void recalcularPesos(int posicionEntrada, double y) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + TASA_APRENDIZAJE * (objetivos[posicionEntrada] - y) * entradas[posicionEntrada][i];
        }

    }

    /**
     * Método que se encarga de entrenar el perceptrón.
     */
    public void entrenar() {
        int indice = 0;
        double yi = 0;
        //Variable para contar los loops. 
        pasos = 0;
        
        textoResultado += "\nPESOS Iniciales\n";
        imprimirPesos();
        textoResultado += "\n";
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
                    textoResultado += (entradas[indice][i] + "\t");
                }
                textoResultado += (" => Esperada = " + objetivos[indice] + ", Calculada = " + yi + "\n");
            } else {
                //Incorrecto
                for (int i = 0; i < numeroEntradas; i++) {
                    textoResultado += (entradas[indice][i] + "\t");
                }
                textoResultado += (" => Esperada = " + objetivos[indice] + ", Calculada = " + yi + " [Error]");
                textoResultado += ("\n\nCorrección de pesos\n");
                setPasos();
                recalcularPesos(indice, yi);

                imprimirPesos();
                textoResultado += ("\n--\n");
                indice = -1;
            }
            indice++;
        }
        textoResultado += "\nPESOS FINALES\n";
        imprimirPesos();
                
    }
    
    

}
