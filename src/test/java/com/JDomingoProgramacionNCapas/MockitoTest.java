package com.JDomingoProgramacionNCapas;

import com.JDomingoProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.JDomingoProgramacionNCapas.ML.Colonia;
import com.JDomingoProgramacionNCapas.ML.Direccion;
import com.JDomingoProgramacionNCapas.ML.Result;
import com.JDomingoProgramacionNCapas.ML.Rol;
import com.JDomingoProgramacionNCapas.ML.Usuario;
import com.JDomingoProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MockitoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Test
    public void GetAllMockito() {
        com.JDomingoProgramacionNCapas.JPA.Usuario usuario = new com.JDomingoProgramacionNCapas.JPA.Usuario();
        usuario.setNombre("Jesus");
        com.JDomingoProgramacionNCapas.JPA.Rol rol = new com.JDomingoProgramacionNCapas.JPA.Rol();
        rol.setIdRol(1);
        com.JDomingoProgramacionNCapas.JPA.Direccion direccion = new com.JDomingoProgramacionNCapas.JPA.Direccion();
        direccion.setCalle("Calle1");
        direccion.setNumeroExterior("1");
        direccion.setNumeroInterior("2");
        com.JDomingoProgramacionNCapas.JPA.Colonia colonia = new com.JDomingoProgramacionNCapas.JPA.Colonia();
        colonia.setIdColonia(1);

        TypedQuery<com.JDomingoProgramacionNCapas.JPA.Usuario> queryUsuarios = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("FROM Usuario", com.JDomingoProgramacionNCapas.JPA.Usuario.class)).thenReturn(queryUsuarios);

        TypedQuery<com.JDomingoProgramacionNCapas.JPA.Direccion> queryDirecciones = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idUsuario", com.JDomingoProgramacionNCapas.JPA.Direccion.class)).thenReturn(queryDirecciones);

        List<com.JDomingoProgramacionNCapas.JPA.Usuario> usuarios = new ArrayList();
        Mockito.when(queryUsuarios.getResultList()).thenReturn(usuarios);

        List<com.JDomingoProgramacionNCapas.JPA.Direccion> direcciones = new ArrayList<>();

        Mockito.when(queryDirecciones.getResultList()).thenReturn(direcciones);

        Result result = usuarioDAOImplementation.GetAllJPA();

        Mockito.verify(entityManager, Mockito.times(1)).createQuery("FROM Usuario", com.JDomingoProgramacionNCapas.JPA.Usuario.class);
        Mockito.verify(entityManager, Mockito.times(1)).createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idUsuario", com.JDomingoProgramacionNCapas.JPA.Direccion.class);
    }

    @Test
    public void AddUsuarioDireccionMockito() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();

        usuarioDireccion.Usuario.setNombre("Jesus");
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

        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.JDomingoProgramacionNCapas.JPA.Usuario.class));
        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.JDomingoProgramacionNCapas.JPA.Direccion.class));

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");

    }

    @Test
    public void UpdateUsuarioMockito() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(192);
        usuario.setNombre("JesustestEdit");
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

        com.JDomingoProgramacionNCapas.JPA.Usuario usuarioJPA = new com.JDomingoProgramacionNCapas.JPA.Usuario();
        Mockito.when(entityManager.find(
                Mockito.eq(com.JDomingoProgramacionNCapas.JPA.Usuario.class),
                Mockito.eq(usuario.getIdUsuario())
        )).thenReturn(usuarioJPA);

        Result result = usuarioDAOImplementation.UsuarioUpdateJPA(usuario);
        Mockito.verify(entityManager, Mockito.times(1)).merge(Mockito.any(com.JDomingoProgramacionNCapas.JPA.Usuario.class));

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");
    }

    @Test
    public void DireccionAddMockito() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(192);
        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle1");
        usuarioDireccion.Direccion.setNumeroExterior("1");
        usuarioDireccion.Direccion.setNumeroInterior("1");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(1);

        Result result = usuarioDAOImplementation.DireccionAddJPA(usuarioDireccion);

        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.JDomingoProgramacionNCapas.JPA.Direccion.class));

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");
    }

    @Test
    public void DireccionUpdateMockito() {

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle2");
        usuarioDireccion.Direccion.setNumeroExterior("2");
        usuarioDireccion.Direccion.setNumeroInterior("2");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(2);
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(192);

        com.JDomingoProgramacionNCapas.JPA.Direccion dirJPA = new com.JDomingoProgramacionNCapas.JPA.Direccion();
        Mockito.when(entityManager.find(
                Mockito.eq(com.JDomingoProgramacionNCapas.JPA.Direccion.class),
                Mockito.eq(usuarioDireccion.Direccion.getIdDireccion())
        )).thenReturn(dirJPA);

        Result result = usuarioDAOImplementation.DireccionUpdateJPA(usuarioDireccion);

        Mockito.verify(entityManager, Mockito.times(1)).merge(Mockito.any(com.JDomingoProgramacionNCapas.JPA.Direccion.class));
        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");

    }

    @Test
    public void DireccionDeleteMockito() {
        Result result = new Result();

        com.JDomingoProgramacionNCapas.JPA.Direccion direccion = new com.JDomingoProgramacionNCapas.JPA.Direccion();
        direccion.setIdDireccion(1);

        Mockito.when(entityManager.find(com.JDomingoProgramacionNCapas.JPA.Direccion.class, 1)).thenReturn(direccion);

        result = usuarioDAOImplementation.DireccionDeleteJPA(1);

        Mockito.verify(entityManager, Mockito.atLeast(1)).remove(direccion);
        Mockito.verify(entityManager, Mockito.atLeast(1)).find(com.JDomingoProgramacionNCapas.JPA.Direccion.class, 1);
        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");

    }

}
