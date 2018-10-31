/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.modelo;

import com.liverpool.automatizacion.modelo.Promo;
import com.liverpool.utils.RE;
import java.util.ArrayList;

/**
 *
 * @author iasancheza
 */
public class PromoSap {
    public static final String EFECTIVO = "EFECTIVO";
    public static final String CUALQUIER_FORMA_PAGO = "Cualquier Forma de Pago";
    public static final String DILISA = "DILISA";
    public static final String EXTERNAS = "Externas";
    
    private String formaPago;
    private String msi;
    private String valoresMsi;
    private Beneficio beneficio1;
    private Beneficio beneficio2;

    public PromoSap() {
        this(CUALQUIER_FORMA_PAGO, "", "");
    }

    public PromoSap(String formaPago, String msi, String valoresMsi) {
        this.formaPago = formaPago;
        this.msi = msi;
        this.valoresMsi = valoresMsi;
        this.beneficio1 = new Beneficio(Beneficio.DESCUENTO);
        this.beneficio2 = new Beneficio(Beneficio.DESCUENTO_ADICIONAL);
    }
    
    public PromoSap(String[] promocion){
        this();
        switch(promocion.length){
            case 3: // Tiene forma de pago y dos beneficios
                this.beneficio2 = new Beneficio(promocion[2].split("\\|"));
            case 2: // Tiene forma de pago y un beneficio
                this.beneficio1 = new Beneficio(promocion[1].split("\\|"));
            case 1: // Solo tiene la forma de pago 
                // Crear la forma de pago y los dos beneficios
                PromoSap pTemp = new PromoSap(promocion[0]);
                this.formaPago = pTemp.formaPago;
                this.msi = pTemp.msi;
                this.valoresMsi = pTemp.valoresMsi;
        }
    }
    
    public PromoSap(String promocion){
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
        return str.equals(CUALQUIER_FORMA_PAGO) || str.equals(DILISA) || str.equals(EXTERNAS);
    }
    
    // Param 1: lista de todas las promociones del sku, recuperadas en SAP
    // Param 2: Filtro para recuperar solo las promos del Param 1 que coincidan con la forma de pago especificada
    public static ArrayList<PromoSap> getPromos(ArrayList<PromoSap> promos, String formaPago){
        ArrayList<PromoSap> promociones = new ArrayList<>();
        
        
        
        return promociones;
    }
    
    public ArrayList<Promo> getPromos(){
        ArrayList<Promo> promos = new ArrayList<>();
        
        // Validar si el getPonderado es 0
        String ponderado = this.getPonderado();
        
        // Validar si tiene meses sin interes
        if(valoresMsi.equals("No Aplica")){
            
        }
        
        
        
        // BANCO:
        // SAP                     - VALIDACION
        // DILISA                  - DILISA
        // EXTERNAS                - EXTERNAS PARTICIPANTES
        // Cualquier Forma de Pago - DILISA, EXTERNAS PARTICIPANTES, PAGO EFECTIVO
        
        final String banco = this.formaPago;
        switch(banco){
            case DILISA:
                promos = new ArrayList<Promo>(){{new Promo();}};
                promos.get(0).setBanco(Promo.DILISA);
                break;
            case EXTERNAS:
                promos = new ArrayList<Promo>(){{new Promo();}};
                promos.get(0).setBanco(Promo.EXTERNAS);
                break;
            case CUALQUIER_FORMA_PAGO:
                promos = new ArrayList<Promo>(){{new Promo();new Promo();new Promo();}};
                for(int i=0; i < promos.size(); i++){
                    switch(i){
                        case 0:
                            promos.get(i).setBanco(Promo.DILISA);
                            break;
                        case 1: 
                            promos.get(i).setBanco(Promo.EXTERNAS);
                            break;
                        case 2: 
                            promos.get(i).setBanco(Promo.EFECTIVO);
                    }
                }
        }
        
        
        
        return promos;
    }
    
    // En staging mantiene los decimales
    // en atg lo redondea hacia arriba
    public String getPonderado(){
        // No importa si uno o ambos descuentos son cero
        // El resultado siempre es el correcto
        int desc1 = Integer.parseInt(beneficio1.getValorBeneficio());
        int desc2 = Integer.parseInt(beneficio2.getValorBeneficio());
        float result = (1-(1-desc1/100)*(1-desc2/100))*100;
        return RE.valorBeneficio(String.valueOf(result));
    }
    
    public String printPonderado(){
        return getPonderado() + "%";
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
