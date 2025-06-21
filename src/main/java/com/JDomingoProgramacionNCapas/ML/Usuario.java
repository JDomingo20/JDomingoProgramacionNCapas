package com.JDomingoProgramacionNCapas.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotNull
    @NotEmpty(message = "Ingresa información")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    private String Nombre;

    @NotNull(message = "No se permiten nulos")
    private String ApellidoPaterno;
    @NotNull(message = "No se permiten nulos")
    private String ApellidoMaterno;
    @NotNull(message = "No se permiten nulos")
    private String NumeroTelefonico;
    @NotNull(message = "No se permiten nulos")
    private String CorreoElectronico;
    @NotNull(message = "No se permiten nulos")
    private String UserName;
    @NotNull(message = "No se permiten nulos")
    private String Password;
    @NotNull(message = "No se permiten nulos")
    private String Sexo;
    @NotNull(message = "No se permiten nulos")
    private String CURP;

    private String Celular;

    @NotNull(message = "No se permiten nulos")
    public Rol Rol;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getNumeroTelefonico() {
        return NumeroTelefonico;
    }

    public void setNumeroTelefonico(String NumeroTelefonico) {
        this.NumeroTelefonico = NumeroTelefonico;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String CorreoElectronico) {
        this.CorreoElectronico = CorreoElectronico;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

}
