/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isancheza
 */
public class Log {
                                         // PrefijoDDMMAA                         
    private static final String re = "^([\\w]*)([0-3][\\d][0-1][\\d][\\d]{2})$";
    public static String nameFile = "log_"+Fecha.date();
    
    public static void write(String msg){
        File fl = null;
        FileWriter fw = null;
        
        try {
            fl = new File(nameFile);
            fw = new FileWriter(fl, true);
            fw.write(Fecha.dateTimeFile() + " - " + msg + "\r\n");
            fw.flush();
        } catch (Exception e) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            if(fw != null){
                try{
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    // Obtener el prefijo del nombre del log
    public static String getPrefijo(String nameLog){
        Pattern pat = Pattern.compile(re);
        Matcher mat = pat.matcher(nameLog);
        if(mat.matches()){
            return mat.group(1);
        }
        return null;
    }
    
    // Obtener el prefijo del nombre del log
    public static String getDate(String nameLog){
        Pattern pat = Pattern.compile(re);
        Matcher mat = pat.matcher(nameLog);
        if(mat.matches()){
            return mat.group(2);
        }
        return null;
    }
    
    public static boolean isAValidFile(File f, String prefijo){
        Pattern pat = Pattern.compile("^" + prefijo + "[0-3][\\d][0-1][\\d][\\d]{2}$");
        Matcher mat = pat.matcher(f.getName());
        return mat.matches();
    }
    
}
