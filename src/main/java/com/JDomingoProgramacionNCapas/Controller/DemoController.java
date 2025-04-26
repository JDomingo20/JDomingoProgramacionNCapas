package com.JDomingoProgramacionNCapas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("saludo")
    public String Holamundo() {
        return "HolaMundo";
    }

    @GetMapping("saludoPath/{nombre}")
    public String SaludoPath(@PathVariable String nombre) {
        return "HolaMundo";
    }

    @GetMapping("saludoRequest")
    public String SaludoRequest(@RequestParam String nombre) {
        return "HolaMundo";
    }

    @GetMapping("operaciones/{op}/{numeroUno}/{numeroDos}")
    public String Operaciones(@PathVariable String op, @PathVariable int numeroUno, @PathVariable int numeroDos) {
        int resultado;
        if (op.equals("suma")) {
            resultado = numeroUno + numeroDos;
            System.out.println("El resultado de la suma es: " + resultado);
        } else if (op.equals("resta")) {
            resultado = numeroUno - numeroDos;
            System.out.println("El resultado de la resta es: " + resultado);
        } else if(op.equals("multiplicacion")){
            resultado = numeroUno * numeroDos;
            System.out.println("El resultado de la multiplicacion es: "+resultado);
        } else if(op.equals("division")){
            if(numeroDos>0){
                resultado = numeroUno/numeroDos;
                System.out.println("El resultado de la division es: "+resultado);
            }else
                System.out.println("No se puede dividir entre 0");
        }
        return "HolaMundo";

    }
}
