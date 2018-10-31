/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.controlador;

import com.liverpool.automatizacion.paginas.Buscador;
import com.liverpool.automatizacion.paginas.Page;
import com.liverpool.promociones.Login;
import com.liverpool.utils.Excel;
import com.liverpool.utils.Utils;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.By;

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

//        if(args.length < 1){
//            System.out.println("Favor de ingresar el nombre del archivo con extension");
//            return;
//        }

        System.out.println("Hola Mundo");
//        new Promociones(args[0]);
        Login login = new Login();
        Properties p = Utils.loadProperties("Promocion.properties");
        login.iniciar_sesion(p.getProperty("usuario"),p.getProperty("password"));
        Buscador b = new Buscador(login.getDriver(),"https://www.liverpool.com.mx");
        
        b.buscarSku("1022237779");
       

        System.out.println("ya acabe");
      

    }
    
}
