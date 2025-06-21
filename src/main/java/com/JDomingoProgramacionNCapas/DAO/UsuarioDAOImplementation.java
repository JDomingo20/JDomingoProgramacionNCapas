package com.JDomingoProgramacionNCapas.DAO;

import com.JDomingoProgramacionNCapas.ML.Colonia;
import com.JDomingoProgramacionNCapas.ML.Direccion;
import com.JDomingoProgramacionNCapas.ML.Estado;
import com.JDomingoProgramacionNCapas.ML.Municipio;
import com.JDomingoProgramacionNCapas.ML.Pais;
import com.JDomingoProgramacionNCapas.ML.Result;
import com.JDomingoProgramacionNCapas.ML.Rol;
import com.JDomingoProgramacionNCapas.ML.Usuario;
import com.JDomingoProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired //Este es para inyectar dependencias (Field, Constructor, Setter)
    private JdbcTemplate jdbcTemplate; //Conexion con JDBC

    @Autowired
    private EntityManager entityManager; //Conexion con JPA

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{ CALL UsuarioGetAll(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {

                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {
                            int idUsuario = resultSet.getInt("IdUsuario");
                            if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                                //Se agregará solo la direccion
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                            } else {
                                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                                usuarioDireccion.Usuario = new Usuario();
                                usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                                usuarioDireccion.Usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                                usuarioDireccion.Usuario.setCorreoElectronico(resultSet.getString("CorreoElectronico"));
                                usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                                usuarioDireccion.Usuario.Rol = new Rol();
                                usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                                usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                                usuarioDireccion.Direcciones = new ArrayList<>();
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                usuarioDireccion.Direcciones.add(direccion);
                                result.objects.add(usuarioDireccion);
                            }
                        }
                        result.correct = true;
                        return 1;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            //Procedimiento almacenado

            jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.Usuario.getNombre());
                callableStatement.setString(2, usuarioDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoMaterno());
                callableStatement.setString(4, usuarioDireccion.Usuario.getNumeroTelefonico());
                callableStatement.setString(5, usuarioDireccion.Usuario.getCorreoElectronico());
                callableStatement.setDate(6, new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setInt(7, usuarioDireccion.Usuario.Rol.getIdRol());
                callableStatement.setString(8, usuarioDireccion.Usuario.getUserName());
                callableStatement.setString(9, usuarioDireccion.Usuario.getPassword());
                callableStatement.setString(10, usuarioDireccion.Usuario.getSexo());
                callableStatement.setString(11, usuarioDireccion.Usuario.getCURP());
                callableStatement.setString(12, usuarioDireccion.Usuario.getCelular());
                callableStatement.setString(13, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(14, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setString(15, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setInt(16, usuarioDireccion.Direccion.Colonia.getIdColonia());
                int rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected > 0 ? true : false;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DireccionesByIdUsuario(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DireccionesByIdUsuario (?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);
                callableStatement.execute();

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);

                if (resultSetUsuario.next()) {
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setUserName(resultSetUsuario.getString("Username"));
                    usuarioDireccion.Usuario.setCorreoElectronico(resultSetUsuario.getString("CorreoElectronico"));
                }
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);

                usuarioDireccion.Direcciones = new ArrayList();

                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("NombreCalle"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));

                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.object = usuarioDireccion;
                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                    usuarioDireccion.Usuario.setCorreoElectronico(resultSet.getString("CorreoElectronico"));
                    usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                    usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                    usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                    usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                    usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));

                    result.object = usuarioDireccion;
                }

                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuarioUpdate(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioUpdate (?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getNumeroTelefonico());
                callableStatement.setString(6, usuario.getCorreoElectronico());
                callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setInt(8, usuario.Rol.getIdRol());
                callableStatement.setString(9, usuario.getUserName());
                callableStatement.setString(10, usuario.getPassword());
                callableStatement.setString(11, usuario.getSexo());
                callableStatement.setString(12, usuario.getCURP());
                callableStatement.setString(13, usuario.getCelular());

                int rowsAffected = callableStatement.executeUpdate();
                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Error en la actualización";
                }

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DireccionUpdate(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DireccionUpdate(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(2, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(4, usuarioDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, usuarioDireccion.Usuario.getIdUsuario());

                callableStatement.executeUpdate();

                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAllDinamicoNombre(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioGetAllApellidosRol(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        //Se agregará solo la direccion
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {
                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.Usuario = new Usuario();
                        usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                        usuarioDireccion.Usuario.setNumeroTelefonico(resultSet.getString("NumeroTelefonico"));
                        usuarioDireccion.Usuario.setCorreoElectronico(resultSet.getString("CorreoElectronico"));
                        usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.Usuario.Rol = new Rol();
                        usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }
                }
                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAllJPA() {
        //Está en lenguaje JPQL
        Result result = new Result();

        try {
            TypedQuery<com.JDomingoProgramacionNCapas.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario", com.JDomingoProgramacionNCapas.JPA.Usuario.class);
            List<com.JDomingoProgramacionNCapas.JPA.Usuario> usuarios = queryUsuarios.getResultList();

            result.objects = new ArrayList<>();
            for (com.JDomingoProgramacionNCapas.JPA.Usuario usuario : usuarios) {

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.Usuario.setNombre(usuario.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.Usuario.setNumeroTelefonico(usuario.getNumeroTelefonico());
                usuarioDireccion.Usuario.setCorreoElectronico(usuario.getCorreoElectronico());
                usuarioDireccion.Usuario.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioDireccion.Usuario.setUserName(usuario.getUserName());
                usuarioDireccion.Usuario.setPassword(usuario.getPassword());
                usuarioDireccion.Usuario.setSexo(usuario.getSexo());
                usuarioDireccion.Usuario.setCURP(usuario.getCURP());
                usuarioDireccion.Usuario.setCelular(usuario.getCelular());
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(usuario.Rol.getIdRol());
                usuarioDireccion.Usuario.Rol.setNombre(usuario.Rol.getNombre());
                TypedQuery<com.JDomingoProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idUsuario", com.JDomingoProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("idUsuario", usuario.getIdUsuario());

                List<com.JDomingoProgramacionNCapas.JPA.Direccion> DireccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = new ArrayList<>();
                for (com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA : DireccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                    direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                    direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                    direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());
                    usuarioDireccion.Direcciones.add(direccion);
                }
                result.objects.add(usuarioDireccion);

            }
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Transactional
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            com.JDomingoProgramacionNCapas.JPA.Usuario usuarioJPA = new com.JDomingoProgramacionNCapas.JPA.Usuario();
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setNumeroTelefonico(usuarioDireccion.Usuario.getNumeroTelefonico());
            usuarioJPA.setCorreoElectronico(usuarioDireccion.Usuario.getCorreoElectronico());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setCURP(usuarioDireccion.Usuario.getCURP());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.Rol = new com.JDomingoProgramacionNCapas.JPA.Rol();
            usuarioJPA.Rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            entityManager.persist(usuarioJPA);

            //Aqui insertaremos la direccion
            com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA = new com.JDomingoProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.Colonia = new com.JDomingoProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Usuario = usuarioJPA;
            entityManager.persist(direccionJPA);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result UsuarioUpdateJPA(Usuario usuario) {
        Result result = new Result();
        com.JDomingoProgramacionNCapas.JPA.Usuario usuarioJPA = new com.JDomingoProgramacionNCapas.JPA.Usuario();
        usuarioJPA = entityManager.find(com.JDomingoProgramacionNCapas.JPA.Usuario.class, usuario.getIdUsuario());

        usuarioJPA.setNombre(usuario.getNombre());
        usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
        usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
        usuarioJPA.setNumeroTelefonico(usuario.getNumeroTelefonico());
        usuarioJPA.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioJPA.setUserName(usuario.getUserName());
        usuarioJPA.setPassword(usuario.getSexo());
        usuarioJPA.setCURP(usuario.getCURP());
        usuarioJPA.setCelular(usuario.getCelular());
        usuarioJPA.Rol = new com.JDomingoProgramacionNCapas.JPA.Rol();
        usuarioJPA.Rol.setIdRol(usuario.Rol.getIdRol());
        entityManager.merge(usuarioJPA);
        try {
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result DeleteJPA(int idUsuario) {
        Result result = new Result();
        try {
            com.JDomingoProgramacionNCapas.JPA.Usuario usuarioJPA= entityManager.find(com.JDomingoProgramacionNCapas.JPA.Usuario.class, idUsuario);

            if (usuarioJPA != null) {
                // Buscar todas las direcciones relacionadas
                Query query = entityManager.createQuery("SELECT d FROM Direccion d WHERE d.Usuario.idUsuario = :idUsuario");
                query.setParameter("idUsuario", idUsuario);
                List<Direccion> direcciones = query.getResultList();

                for (Direccion d : direcciones) {
                    entityManager.remove(d);
                }

                entityManager.remove(usuarioJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result DireccionUpdateJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA = new com.JDomingoProgramacionNCapas.JPA.Direccion();
            direccionJPA.setIdDireccion(usuarioDireccion.Direccion.getIdDireccion());
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());

            direccionJPA.Colonia = new com.JDomingoProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
//
            direccionJPA.Usuario = new com.JDomingoProgramacionNCapas.JPA.Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());

            //Vaciar ML a JPA
            entityManager.merge(direccionJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();

        try {
            com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA = new com.JDomingoProgramacionNCapas.JPA.Direccion();
            direccionJPA = entityManager.find(com.JDomingoProgramacionNCapas.JPA.Direccion.class, IdDireccion);
            entityManager.remove(direccionJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result DireccionAddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA = new com.JDomingoProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());

            direccionJPA.Colonia = new com.JDomingoProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());

            direccionJPA.Usuario = new com.JDomingoProgramacionNCapas.JPA.Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
            entityManager.persist(direccionJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuaDirByIdJPA(int IdUsuario) {

        Result result = new Result();
 
        try {
            TypedQuery<com.JDomingoProgramacionNCapas.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario WHERE IdUsuario = :idusuario", com.JDomingoProgramacionNCapas.JPA.Usuario.class);
            List<com.JDomingoProgramacionNCapas.JPA.Usuario> usuarios = queryUsuarios.getResultList();
            result.objects = new ArrayList<>();
            
            for (com.JDomingoProgramacionNCapas.JPA.Usuario usuario : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.Usuario.setUserName(usuario.getUserName());
                usuarioDireccion.Usuario.setNombre(usuario.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.Usuario.setCorreoElectronico(usuario.getCorreoElectronico());
                usuarioDireccion.Usuario.setSexo(usuario.getSexo());
                usuarioDireccion.Usuario.setNumeroTelefonico(usuario.getNumeroTelefonico());
                usuarioDireccion.Usuario.setCelular(usuario.getCelular());
                usuarioDireccion.Usuario.setCURP(usuario.getCURP());
                usuarioDireccion.Usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.Usuario.setPassword(usuario.getPassword());
                usuarioDireccion.Usuario.setFechaNacimiento(usuario.getFechaNacimiento());
 
                TypedQuery<com.JDomingoProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.JDomingoProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());
                List<com.JDomingoProgramacionNCapas.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = new ArrayList();

                for (com.JDomingoProgramacionNCapas.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                    direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                    usuarioDireccion.Direcciones.add(direccion);

                }
                result.objects.add(usuarioDireccion);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
