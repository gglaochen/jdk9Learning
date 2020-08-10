package Str2MathFunction;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author ChenHanLin 2020/8/10
 */
public class ScriptEngineTests {
    public static void main(String[] args) throws Exception {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
        String str = "((2+3)*5)^(1/2)";
        System.out.println(se.eval(str));
    }
}
