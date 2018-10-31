/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.test;

import com.liverpool.utils.RE;

/**
 *
 * @author iasancheza
 */
public class Promos {
    
    public static void main(String[] args) {
        //System.out.println(RE.valoresMSI("9"));
        //System.out.println(RE.valoresMSI("No Aplica"));
        //System.out.println(RE.tipoBeneficio("Descuento %"));
        System.out.println("Resultado: " + RE.valorBeneficio("75879832% De Descuento"));
        //final String promoSap = "Cualquier Forma de Pago|No|No Aplica|Y|Descuento %|15.00%|0.00|Y|Descuento Adic %|0.00%|0.00";
        //final String promoSap = "Cualquier Forma de Pago|";
        //RE.getPromos(promoSap);
    }
    
}
