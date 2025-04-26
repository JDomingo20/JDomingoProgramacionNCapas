
package com.JDomingoProgramacionNCapas.ML;

import jakarta.validation.constraints.NotNull;

public class Direccion {
    private int IdDireccion;
    @NotNull(message="No se permiten nulos")
    private String Calle;
    @NotNull(message="No se permiten nulos")
    private String NumeroExterior;
    @NotNull(message="No se permiten nulos")
    private String NumeroInterior;
    
    public Colonia Colonia;

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }
}
