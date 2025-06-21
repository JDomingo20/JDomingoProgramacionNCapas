package com.JDomingoProgramacionNCapas;

import com.JDomingoProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.JDomingoProgramacionNCapas.ML.Colonia;
import com.JDomingoProgramacionNCapas.ML.Direccion;
import com.JDomingoProgramacionNCapas.ML.Result;
import com.JDomingoProgramacionNCapas.ML.Rol;
import com.JDomingoProgramacionNCapas.ML.Usuario;
import com.JDomingoProgramacionNCapas.ML.UsuarioDireccion;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JUnitTest {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Test
    public void GetAllJUnit() {
        Result result = new Result();
        result = usuarioDAOImplementation.GetAllJPA();

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNotNull(result.objects, "El result.objects viene null");
        Assertions.assertNull(result.ex, "Hay una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un msj de error");
        Assertions.assertTrue(result.correct, "El result.correct es false");

    }

    @Test
    public void AddUsuarioDireccionJUnit() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setNombre("Jesustest");
        usuarioDireccion.Usuario.setApellidoPaterno("Domingo");
        usuarioDireccion.Usuario.setApellidoMaterno("Peralta");
        usuarioDireccion.Usuario.setNumeroTelefonico("5525896401");
        usuarioDireccion.Usuario.setCorreoElectronico("jdomingo121@outlook.com");
        usuarioDireccion.Usuario.setFechaNacimiento(new Date(20122002));
        usuarioDireccion.Usuario.Rol = new Rol();
        usuarioDireccion.Usuario.Rol.setIdRol(2);
        usuarioDireccion.Usuario.setUserName("jdomingotest");
        usuarioDireccion.Usuario.setPassword("123");
        usuarioDireccion.Usuario.setSexo("M");
        usuarioDireccion.Usuario.setCelular("5569874201");
        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle1");
        usuarioDireccion.Direccion.setNumeroExterior("1");
        usuarioDireccion.Direccion.setNumeroInterior("1");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(1);

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");
    }
    

    @Test
    public void UsuarioUpdate() {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(192);
        usuario.setNombre("JesustestEdit2");
        usuario.setApellidoPaterno("Domingo");
        usuario.setApellidoMaterno("Peralta");
        usuario.setNumeroTelefonico("5525896401");
        usuario.setCorreoElectronico("jdomingo121@outlook.com");
        usuario.setFechaNacimiento(new Date(20122002));
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(2);
        usuario.setUserName("jdomingotest");
        usuario.setPassword("123");
        usuario.setSexo("M");
        usuario.setCelular("5569874201");

        Result result = usuarioDAOImplementation.UsuarioUpdateJPA(usuario);

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");

    }

    @Test
    public void DireccionUpdate() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle2");
        usuarioDireccion.Direccion.setNumeroExterior("2");
        usuarioDireccion.Direccion.setNumeroInterior("2");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(2);
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(192);
        Result result = usuarioDAOImplementation.DireccionUpdateJPA(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");
    }
    
    @Test
    public void DireccionDelete(){
        
    }
}
