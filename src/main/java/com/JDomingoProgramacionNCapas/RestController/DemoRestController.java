package com.JDomingoProgramacionNCapas.RestController;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    @GetMapping("/saludo/{nombre}")
    public String Saludo(@PathVariable String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("/sumar/{nro1}/{nro2}")
    public ResponseEntity<String> sumar(@PathVariable int nro1, @PathVariable int nro2) {
        int resultado = nro1 + nro2;
        return ResponseEntity.ok("Resultado: " + resultado);
    }

    @GetMapping("/restar/{nro1}/{nro2}")
    public ResponseEntity<String> restar(@PathVariable int nro1, @PathVariable int nro2) {
        int resultado = nro1 - nro2;
        return ResponseEntity.ok("Resultado: " + resultado);
    }

    @GetMapping("/multiplicar/{nro1}/{nro2}")
    public ResponseEntity<String> multiplicar(@PathVariable int nro1, @PathVariable int nro2) {
        int resultado = nro1 * nro2;
        return ResponseEntity.ok("Resultado: " + resultado);
    }

    @GetMapping("/dividir/{nro1}/{nro2}")
    public ResponseEntity<String> dividir(@PathVariable int nro1, @PathVariable int nro2) {
        if (nro2 == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: No se puede dividir por cero");
        }
        double resultado = (double) nro1 / nro2;
        return ResponseEntity.ok("Resultado: " + resultado);
    }

    @PostMapping("/sumarjson")
    public ResponseEntity<String> sumarjson(@RequestBody Map<String, Integer> params) {
        int nro1 = params.get("nro1");
        int nro2 = params.get("nro2");
        int resultado = nro1 + nro2;
        return ResponseEntity.ok("Resultado: " + resultado);

    }
    @PostMapping("/restarjson")
    public ResponseEntity<String> restarjson(@RequestBody Map<String, Integer>params){
        int nro1 = params.get("nro1");
        int nro2 = params.get("nro2");
        int resultado = nro1 - nro2;
        return ResponseEntity.ok("Resultado: "+resultado);
}
}
