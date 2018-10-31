/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.test;

import com.liverpool.automatizacion.modelo.Promo;
import com.liverpool.promociones.Monitor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author IASANCHEZA
 */
public class Test {
    
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        // Seleccionar el radio button de Staging
        monitor.selectProd();
        // Ingresar el numero de sku en la caja de texto
        monitor.insertSku("1059543781");
        // Dar click en el boton buscar
        monitor.buscar();
        // Seleccionar uno de los resultados obtenidos
        monitor.selectFirstResult();
        // Obtener las promociones del sku
        HashMap<String,ArrayList<Promo>> promos = monitor.getPromociones();
        System.out.println(promos.size());
    }
}
