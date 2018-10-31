/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.modelo;

import com.liverpool.utils.RE;

/**
 *
 * @author iasancheza
 */
public class Beneficio {
    public static final String DESCUENTO = "Descuento %";
    public static final String DESCUENTO_ADICIONAL = "Descuento Adic %";
    public static final String MONEDERO = "Monedero %";
    
    private String tipoBeneficio;
    private String valorBeneficio;
    private String montoMinimo;

    public Beneficio() {
        this(DESCUENTO_ADICIONAL, "0", "0.00");
    }
    
    public Beneficio(String tipoBeneficio){
        this(tipoBeneficio, "0", "0.00");
    }
    
    public Beneficio(String[] beneficio){
        this();
        for(int i=0; i < beneficio.length; i++){
            String dato = beneficio[i];
            
            switch(i){
                case 0: // Tipo de Beneficio
                    this.tipoBeneficio = dato;
                    break;
                case 1: // Valor de Beneficio
                    this.valorBeneficio = RE.valorBeneficio(dato);
                    break;
                case 2: // Monto minimo
                    this.montoMinimo = dato;
                    break;
            }
        }
    }

    public Beneficio(String tipoBeneficio, String valorBeneficio, String montoMinimo) {
        this.tipoBeneficio = tipoBeneficio;
        this.valorBeneficio = RE.valorBeneficio(valorBeneficio);
        this.montoMinimo = montoMinimo;
    }

    public String getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(String tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public String getValorBeneficio() {
        return valorBeneficio;
    }

    public void setValorBeneficio(String valorBeneficio) {
        this.valorBeneficio = RE.valorBeneficio(valorBeneficio);
    }

    public String getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(String montoMinimo) {
        this.montoMinimo = montoMinimo;
    }
}
