
package com.JDomingoProgramacionNCapas.DAO;

import com.JDomingoProgramacionNCapas.ML.Result;
import com.JDomingoProgramacionNCapas.ML.Usuario;
import com.JDomingoProgramacionNCapas.ML.UsuarioDireccion;


public interface IUsuarioDAO {
    Result GetAll();
    Result Add(UsuarioDireccion usuarioDireccion);
    Result DireccionesByIdUsuario(int IdUsuario);
    Result GetById(int IdUsuario);
    Result UsuarioUpdate (Usuario usuario);
    Result DireccionUpdate(UsuarioDireccion usuarioDireccion);
    Result GetAllDinamicoNombre(Usuario usuario);
    Result GetAllJPA();
    Result AddJPA(UsuarioDireccion usuarioDireccion);
    Result UsuarioUpdateJPA(Usuario usuario);
    Result DeleteJPA(int IdUsuario);
    Result DireccionUpdateJPA(UsuarioDireccion usuarioDireccion);
    Result DireccionDeleteJPA(int IdDireccion);
    Result DireccionAddJPA(UsuarioDireccion usuarioDireccion);
    Result UsuaDirByIdJPA(int IdUsuario);
}
