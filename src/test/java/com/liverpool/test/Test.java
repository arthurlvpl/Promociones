/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.test;

import com.liverpool.promociones.Monitor;

/**
 *
 * @author IASANCHEZA
 */
public class Test {
    
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        // Seleccionar el radio button de Staging
        monitor.selectStaging();
        // Ingresar el numero de sku en la caja de texto
        monitor.insertSku("1028042724");
        // Dar click en el boton buscar
        monitor.buscar();
        // Seleccionar uno de los resultados obtenidos
        monitor.selectFirstResult();
    }
}
