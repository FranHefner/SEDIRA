/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.vistas;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sedira.DatosValidacionesCalculo;
import sedira.DatosValidacionesCalculoBasico;
import sedira.IDatosValidaciones;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.invoker;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JLabel;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import sedira.FuncionesGenerales;
import sedira.MathJS;
import sedira.model.ValorDescripcion;
import sedira.model.VariableCalculo;



/**
 * FXML Controller class
 *
 * @author Hefner Francisco, Quelin Pablo
 */
public class PestañaCalculoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea txtEntrada;
    @FXML
    private Button btnCalcular;
   
    @FXML
    private TextField txtResult;

    private IDatosValidaciones dValidaciones;
    @FXML
    private TableView <ValorDescripcion> griValorDescripcionPhantom;  
    @FXML
    private TableView <ValorDescripcion> griValorDescripcionRadionuclido;  
    @FXML
    private  TableView <VariableCalculo> griVariables;
    
    @FXML    
    private Pane pnFuncion;
      @FXML
    private TableColumn <ValorDescripcion, Double> clVdValorPhantom;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcionPhantom;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidadPhantom;
    
     @FXML
    private TableColumn <ValorDescripcion, Double> clVdValorRadionuclido;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdDescripcionRadionuclido;
    @FXML
    private TableColumn <ValorDescripcion, String> clVdUnidadRadionuclido;
    
     @FXML
    private TableColumn <VariableCalculo, String> clDescripcionVariable;
      @FXML
    private TableColumn <VariableCalculo, Double> clValorVariable;
      @FXML
    private TableColumn <VariableCalculo, String> clLetraVariable;
      
    
   
            
            
    private String formulEnTex;
    
    private ValorDescripcion variableSeleccionada;
    private char letra = 'A';
    private ObservableList<VariableCalculo> listaVariables = FXCollections.observableArrayList();
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        
        /* Se inicializa la interface para que se adapte al tipo de cálculo actual */
        if (MenuPrincipalController.TipoUsuario == "Cientifico") {
            dValidaciones = new DatosValidacionesCalculo();
        }
        if (MenuPrincipalController.TipoUsuario == "Medico") {
            dValidaciones = new DatosValidacionesCalculoBasico();
        }
        /**
         * *************************************************************************
         */
          
         //Inicializo la tabla de Propiedad Valor, correspondiente a los Phantoms. 
        clVdValorPhantom.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcionPhantom.setCellValueFactory(
                cellData->cellData.getValue().descripcionProperty());
        clVdUnidadPhantom.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
        // Limpieza de los detalles de Phantoms. 
       // FuncionesGenerales.mostrarDetalleTablaValorDescripcion(null,griValorDescripcionPhantom);
        
        // Muestro las propiedades del phantom selecionado
        FuncionesGenerales.mostrarDetalleTablaValorDescripcion( dValidaciones.getPhantomActual().getPropiedades(), griValorDescripcionPhantom);
         
        
        griValorDescripcionPhantom.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionPropiedadPhantom(newValue));
        
        // Muestro las propiedades del radionuclido seleccionado
        
        //Inicializo la tabla de Propiedad Valor, correspondiente a la informacion de los radioNuclidos . 
        clVdValorRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().valorProperty().asObject());
        clVdDescripcionRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        clVdUnidadRadionuclido.setCellValueFactory(
                cellData -> cellData.getValue().unidadProperty());
         //Completo tabla de Info Radionuclido
          FuncionesGenerales.mostrarDetalleTablaValorDescripcion(dValidaciones.getRadionuClidoActual().getPropiedades(), griValorDescripcionRadionuclido);
    
         griValorDescripcionRadionuclido.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionPropiedadRadionuclido(newValue));
                
         //Inicializo la tabla de Variables, correspondiente a la informacion de los radioNuclidos . 
       clDescripcionVariable.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
                  
       
        clValorVariable.setCellValueFactory(
               cellData -> cellData.getValue().valorProperty().asObject());
        
         clLetraVariable.setCellValueFactory(
                cellData -> cellData.getValue().variableProperty());
         
        
        
                
         
    }

    
    public void seleccionPropiedadPhantom(ValorDescripcion datoSeleccionado) {
       
        
       
         griValorDescripcionRadionuclido.getSelectionModel().clearSelection();
       variableSeleccionada = datoSeleccionado;
         
        
        
       
    }
    public void seleccionPropiedadRadionuclido(ValorDescripcion datoSeleccionado) {
       
       
            griValorDescripcionPhantom.getSelectionModel().clearSelection();
      
       
         
        variableSeleccionada = datoSeleccionado;
        
       
    }
    
    
    @FXML
    public void agregarVariables() {
        
      
        
       if (letra == '[')
       {
             Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Límite Variables");
            alert.setHeaderText("Atención!");
            alert.setContentText("No es posible agregar más variables al cálculo");
            alert.show();
       }else
       {  
           VariableCalculo nuevaVariable = new VariableCalculo(variableSeleccionada.getId(),variableSeleccionada.getDescripcion(),variableSeleccionada.getValor(), String.valueOf(letra++));
       
         listaVariables.add(nuevaVariable);

         //griVariables.refresh();
      
         griVariables.setItems(listaVariables);
       }
               
     
                 
     
         
    }
    
    @FXML
    public void GuardarResultado() {
        dValidaciones.guardarCalculo();

    }

    @FXML
    public void RealizarCalculo() throws ScriptException, NoSuchMethodException, IOException {

        
      
       
        
         MathJS math = new MathJS();
         
      
          
     /*  int IndexVariable =  listaVariables.indexOf('A');
       
         System.out.println(listaVariables.get(IndexVariable).getvariable()+"= "+listaVariables.get(IndexVariable).getValor());
          
        if (IndexVariable != -1)
        {
           String Asingacion=  listaVariables.get(IndexVariable).getvariable()+"= "+listaVariables.get(IndexVariable).getValor();
           math.Ejecutar( Asingacion );
        }
        */
          
          math.Ejecutar("value = '"+txtEntrada.getText() +"';");
                  
             math.Ejecutar(" math.parse(value).toTex() " );
             
   formulEnTex =  math.Ejecutar(" math.parse(value).toTex() " );
  
      
         
   
       System.out.println(formulEnTex);
        txtResult.setText( math.eval( txtEntrada.getText()) );
        
    
           pnFuncion.getChildren().clear();
            MyCanvas canvas = new MyCanvas();
    
        canvas.widthProperty().bind( pnFuncion.widthProperty()); 
        canvas.heightProperty().bind( pnFuncion.heightProperty());  
       
     //   spnFuncion.getChildren().add(canvas);  
      //  spnFuncion.autosize();
    
      /* Calculo viejo con motor de JS basico */
     pnFuncion.getChildren().add(canvas);  
       
     /*  double resultado = eval(txtEntrada.getText());
        txtResult.setText( String.valueOf(resultado));
        ScriptEngineManager mgr = new ScriptEngineManager();
         ScriptEngine engine = mgr.getEngineByName("JavaScript");
             String foo = txtEntrada.getText();
        try {
            System.out.println(engine.eval(foo));
        } catch (ScriptException ex) {
            Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    */
        /****************************************/
        /* Nuevo MEOTOD !! */ 
        
        
        /* Se convierte el RESULTADO A BLOB */
        try {

            float resultado_temp_float = 489238;

            int bits = Float.floatToIntBits(resultado_temp_float);
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (bits & 0xff);
            bytes[1] = (byte) ((bits >> 8) & 0xff);
            bytes[2] = (byte) ((bits >> 16) & 0xff);
            bytes[3] = (byte) ((bits >> 24) & 0xff);

            Blob resultado_temp_blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            dValidaciones.finalizarCalculo(resultado_temp_blob);

            /* DE UN FLOAT COMO RESULTADO DEL CALCULO PASA A UN BLOB Y DESPUES A UN FLOAT */
            float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();

            String ResultString = String.valueOf(f);
            //txtResult.setText(ResultString);

        } catch (SQLException ex) {
            Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
        }
               
  
    }
   @FXML
    public void seleccionarPhantom(){
        
        
    }
    
    
      class MyCanvas extends Canvas { 

        private FXGraphics2D g2;

        private TeXIcon icon;

        public MyCanvas() {
            this.g2 = new FXGraphics2D(getGraphicsContext2D());

            // create a formula
            
            
                    
                  
            String FormulaString =   formulEnTex;
           //  String FormulaString = "\\frac12\\begin{array}{cccc}5&7&7&7\\\\4&4&4&4\\\\4&4&4&4\\end{array}";
           
           
           // TeXFormula formula = new TeXFormula("x=\\frac{-b \\pm \\sqrt {b^2-7ac}}{2a}");
       //    TeXFormula Result = new TeXFormula("\\pgfmathparse{sin(60)}\\pgfmathresult");

         //   txtResult.setText(Result.toString());
          // txtEntrada.setText(FormulaString);
            TeXFormula formula = new TeXFormula(FormulaString);
            
            // render the formla to an icon of the same size as the formula.
            this.icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

            // Redraw canvas when size changes. 
            widthProperty().addListener(evt -> draw()); 
            heightProperty().addListener(evt -> draw()); 
        }  

        private void draw() { 
            double width = getWidth(); 
            double height = getHeight();
            getGraphicsContext2D().clearRect(0, 0, width, height);

            // ideally it should be possible to draw directly to the FXGraphics2D
            // instance without creating an image first...but this does not generate
            // good output
            //this.icon.paintIcon(new JLabel(), g2, 50, 50);

            // now create an actual image of the rendered equation
            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D gg = image.createGraphics();
            gg.setColor(Color.WHITE);
            gg.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel jl = new JLabel();
            jl.setForeground(new Color(0, 0, 0));
            icon.paintIcon(jl, gg, 0, 0);
            // at this point the image is created, you could also save it with ImageIO

            this.g2.drawImage(image, 0, 0, null);
        } 

        @Override 
        public boolean isResizable() { 
            return true;
        }  

        @Override 
        public double prefWidth(double height) { return getWidth(); }  

        @Override 
        public double prefHeight(double width) { return getHeight(); } 
    }
      
      
      /***************** Funcion de evaluacion 1 *///////////
      
       public static String valuacionString (String Expresion){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
       
        try {
            //System.out.println(engine.eval(foo));
            return engine.eval(Expresion).toString();
        } catch (ScriptException ex) {
            Logger.getLogger(PestañaCalculoController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        } 
    
    
      /**************** Funcion De evaluacion  2 *************/
      public static double eval(final String str) {
    return new Object() {
        int pos = -1, ch;

        void nextChar() {
            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
        }

        boolean eat(int charToEat) {
            while (ch == ' ') nextChar();
            if (ch == charToEat) {
                nextChar();
                return true;
            }
            return false;
        }

        double parse() {
            nextChar();
            double x = parseExpression();
            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
            return x;
        }

        // Grammar:
        // expression = term | expression `+` term | expression `-` term
        // term = factor | term `*` factor | term `/` factor
        // factor = `+` factor | `-` factor | `(` expression `)`
        //        | number | functionName factor | factor `^` factor

        double parseExpression() {
            double x = parseTerm();
            for (;;) {
                if      (eat('+')) x += parseTerm(); // addition
                else if (eat('-')) x -= parseTerm(); // subtraction
                else return x;
            }
        }

        double parseTerm() {
            double x = parseFactor();
            for (;;) {
                if      (eat('*')) x *= parseFactor(); // multiplication
                else if (eat('/')) x /= parseFactor(); // division
                else return x;
            }
        }

        double parseFactor() {
            if (eat('+')) return parseFactor(); // unary plus
            if (eat('-')) return -parseFactor(); // unary minus

            double x;
            int startPos = this.pos;
            if (eat('(')) { // parentheses
                x = parseExpression();
                eat(')');
            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                x = Double.parseDouble(str.substring(startPos, this.pos));
            } else if (ch >= 'a' && ch <= 'z') { // functions
                while (ch >= 'a' && ch <= 'z') nextChar();
                String func = str.substring(startPos, this.pos);
                x = parseFactor();
                if (func.equals("sqrt")) x = Math.sqrt(x);
                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                else throw new RuntimeException("Unknown function: " + func);
            } else {
                throw new RuntimeException("Unexpected: " + (char)ch);
            }

            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

            return x;
        }
    }.parse();
}
     
   
}
