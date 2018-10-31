/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author iasancheza
 */
public class Utils {

    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Log.write(ex.toString());
        }
    }
    
    public static Properties loadProperties(String nameFl){
        Properties p = new Properties();
        boolean load = false;
        InputStream input = null;
        Reader reader = null;
        try{
            input = new FileInputStream(new File(nameFl));
            reader = new InputStreamReader(input, "UTF-8");
            p.load(reader);
            p.stringPropertyNames().forEach((k) -> {
                p.put(k, p.getProperty(k).trim());
            });
            load = true;
        } catch (IOException ex) {
            Log.write("Ocurrio una excepcion al cargar el archivo de configuracion: " + nameFl);
            Log.write(ex.toString());
            load = false;
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
                
                if (input != null) {
                    input.close();
                }
            } catch (IOException e){
                Log.write(e.toString());
                load = false;
            }
        }
        
        if(!load){
            Log.write("Ocurrio una excepcion al leer el archivo: " + nameFl);
            //JOptionPane.showMessageDialog(null,"Falta el archivo de configuración: " + nameFl,"", JOptionPane.ERROR_MESSAGE);
        }
        return p;
    }
    
    
    
    
    
    
    
}
