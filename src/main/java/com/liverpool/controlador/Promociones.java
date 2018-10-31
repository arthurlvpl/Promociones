/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.controlador;

import com.liverpool.utils.Excel;
import java.io.File;

/**
 *
 * @author iasancheza
 */
public class Promociones {
    
    public Promociones(String nameFile){
        // Leer y escribir el excel al mismo tiempo
        File f = new File(nameFile);
        Excel excel = new Excel();
        excel.procesaExcel(f);
        // Escribir el archivo
        //excel.writeExcel(array);
    }
    
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Favor de ingresar el nombre del archivo con extension");
            return;
        }
        new Promociones(args[0]);
    }
    
}
