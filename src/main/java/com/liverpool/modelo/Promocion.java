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
public class Promocion {
    public static final String EFECTIVO = "EFECTIVO";
    public static final String CUALQUIER_FORMA_PAGO = "Cualquier Forma de Pago";
    public static final String DILISA = "DILISA";
    public static final String EXTERNAS = "Externas";
    
    private String formaPago;
    private String msi;
    private String valoresMsi;
    private Beneficio beneficio1;
    private Beneficio beneficio2;

    public Promocion() {
        this(CUALQUIER_FORMA_PAGO, "", "");
    }

    public Promocion(String formaPago, String msi, String valoresMsi) {
        this.formaPago = formaPago;
        this.msi = msi;
        this.valoresMsi = valoresMsi;
        this.beneficio1 = new Beneficio(Beneficio.DESCUENTO);
        this.beneficio2 = new Beneficio(Beneficio.DESCUENTO_ADICIONAL);
    }
    
    public Promocion(String[] promocion){
        this();
        switch(promocion.length){
            case 3: // Tiene forma de pago y dos beneficios
                this.beneficio2 = new Beneficio(promocion[2].split("\\|"));
            case 2: // Tiene forma de pago y un beneficio
                this.beneficio1 = new Beneficio(promocion[1].split("\\|"));
            case 1: // Solo tiene la forma de pago 
                // Crear la forma de pago y los dos beneficios
                Promocion pTemp = new Promocion(promocion[0]);
                this.formaPago = pTemp.formaPago;
                this.msi = pTemp.msi;
                this.valoresMsi = pTemp.valoresMsi;
        }
    }
    
    public Promocion(String promocion){
        this();
        // Hacerle split con \\|
        String[] data = promocion.split("\\|");
        for(int i=0; i < data.length; i++){
            String dato = data[i];
            switch(i){
                case 0: // Forma de Pago
                    this.formaPago = dato;
                    break;
                case 1: // MSI
                    this.msi = dato;
                    break;
                case 2: // Valores MSI
                    this.valoresMsi = dato;
                    break;
            }
        }
    }
    
    public static boolean isFormaDePago(String str){
        return str.equals(CUALQUIER_FORMA_PAGO) || str.equals(DILISA) || 
                str.equals(EFECTIVO) || str.equals(EXTERNAS);
    }
    
    public static ArrayList<Promocion> getPromos(ArrayList<Promocion> promos, String formaPago){
        ArrayList<Promocion> promociones = new ArrayList<>();
        
        
        
        return promociones;
    }
    
    public String ponderado(){
        return "";
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getMsi() {
        return msi;
    }

    public void setMsi(String msi) {
        this.msi = msi;
    }

    public String getValoresMsi() {
        return valoresMsi;
    }

    public void setValoresMsi(String valoresMsi) {
        this.valoresMsi = valoresMsi;
    }

    public Beneficio getBeneficio1() {
        return beneficio1;
    }

    public void setBeneficio1(Beneficio beneficio1) {
        this.beneficio1 = beneficio1;
    }

    public Beneficio getBeneficio2() {
        return beneficio2;
    }

    public void setBeneficio2(Beneficio beneficio2) {
        this.beneficio2 = beneficio2;
    }
}
