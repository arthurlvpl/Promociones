/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.modelo;

import java.util.ArrayList;

/**
 *
 * @author iasancheza
 */
public class Sku {
    private String numSku;
    private String descripcion;
    private String seccion;
    private String grupoArticulo;
    private String marca;
    
    public Sku(){
        this("", "", "", "", "");
    }

    public Sku(String numSku, String descripcion, String seccion, String grupoArticulo, String marca) {
        this.numSku = numSku;
        this.descripcion = descripcion;
        this.seccion = seccion;
        this.grupoArticulo = grupoArticulo;
        this.marca = marca;
    }
    
    public Sku(ArrayList<Object> datos){
        this((String)datos.get(0), 
             (String)datos.get(1),
             (String)datos.get(2),
             (String)datos.get(3),
             (String)datos.get(4));
    }

    public String getNumSku() {
        return numSku;
    }

    public void setNumSku(String numSku) {
        this.numSku = numSku;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getGrupoArticulo() {
        return grupoArticulo;
    }

    public void setGrupoArticulo(String grupoArticulo) {
        this.grupoArticulo = grupoArticulo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
