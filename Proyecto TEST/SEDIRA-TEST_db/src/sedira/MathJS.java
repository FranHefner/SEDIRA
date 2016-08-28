/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Fran
 */
public class MathJS {
    
     protected static String MATHJS_FILE = "tools/math.js";

    protected ScriptEngine engine;

    public MathJS () 
            throws FileNotFoundException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager ();
        engine = manager.getEngineByName ("js");

        engine.eval(new FileReader(MATHJS_FILE));
    //    engine.eval("var math = global.mathjs();");
        engine.eval("var parser = math.parser();");
       engine.eval("var precision = 14;");
    }

    public String eval (String expr) 
            throws ScriptException {
        String script = "math.format(parser.eval('" + expr + "'), precision);";
        return (String) engine.eval(script);
    }
    public String toTex (String node) 
            throws ScriptException {
     //  String script = "(parser.eval('" + expr + "').toTex();";
     String script = node+".toTex()";
          
     return (String) engine.eval(script);
    }
    
    public String Ejecutar (String codigo) 
            throws ScriptException {   
     String script = codigo;
      return (String) engine.eval(script);
    }
     
  /*  var node = math.parse('sqrt(2/3)');
node.toTex(); // returns '\sqrt{\frac{2}{3}}'*/

     
}