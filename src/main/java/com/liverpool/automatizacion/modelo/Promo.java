/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author iasancheza
 */
public class Promo {
    public static final String DILISA = "DILISA";
    public static final String EXTERNAS = "EXTERNAS PARTICIPANTES";
    public static final String EFECTIVO = "PAGO EFECTIVO";
    
    private String banco;
    private String descripcion;
    private String descuento; // Se recupera con fines informativos
    private String plan; // Se recupera con fines informativos

    public Promo() {
        this("", "", "", "");
    }

    public Promo(String banco, String descripcion, String descuento, String plan) {
        this.banco = banco;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.plan = plan;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
    
}
