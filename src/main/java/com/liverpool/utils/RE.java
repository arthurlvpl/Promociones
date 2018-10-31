/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.utils;

import com.liverpool.automatizacion.modelo.Promo;
import com.liverpool.automatizacion.modelo.Tienda;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isancheza
 */
public class RE {
    
    public static String valorBeneficio(String value){
        final String str = "([\\d]+)[\\.]*[\\d]*[%]*[\\s\\w]*";
        Pattern pat = Pattern.compile(str);
        Matcher mat = pat.matcher(value);
        if(!mat.matches()){
            return "";
        }
        return mat.group(1);
    }
    
    public static String tipoBeneficio(String value){
        final String str = "^([\\w\\s]+%)$";
        Pattern pat = Pattern.compile(str);
        Matcher mat = pat.matcher(value);
        return mat.group();
    }
    
    public static String valoresMSI(String value){
        final String str = "^(No Aplica|[\\d]+[\\s,]*[\\s]*)+$";
        Pattern pat = Pattern.compile(str);
        Matcher mat = pat.matcher(value);
        return mat.group();
    }
    
    public static ArrayList<Promo> getPromos(String promoSap){
        ArrayList<Promo> promos = new ArrayList<>();
        
        // Crear una expresion regular que evalue la promo recuperada de SAP
        // FormaDePago|MSI|VALORES MSI|Y|Tipo de Beneficio|Valor de Beneficio|Monto minimo|Y|Tipo Beneficio|Valor de Beneficio|Monto Minimo
        final String re = "^([\\w]+)\\|([\\w]+)\\|(No Aplica|[\\d]+[\\s,]*[\\s]*)+\\|Y\\|([\\w\\s]+%)\\|([\\d\\.]+00%)\\|0.00[\\|Y]*[\\|]*([\\w\\s]+%)*[\\|]*([\\d\\.]+00%)*[\\|]*(0.00)*$";
        //final String re = "^([\\w\\s]+)\\|$";
        Pattern pat = Pattern.compile(re);
        Matcher mat = pat.matcher(promoSap);
        
        return promos; 
    }
    
    public static Tienda getTienda(String address){
        Tienda t = null;
        // Nota: No sirve usar [.]+
        Pattern pat = Pattern.compile("^([\\d]+)(.+),(.+,){5}C\\.P\\.([\\d]+).*$");
        Matcher mat = pat.matcher(address);
        if(!mat.matches()){
            return t;
        }
        t = new Tienda();
        t.setNumTienda(mat.group(1));
        t.setDescripcion(mat.group(2));
        t.setCp(mat.group(4));
        
        return t;
    }
    
    public static boolean isADate(String birthday){
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(birthday);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static boolean isAMail(String mail){
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(mail);
        return mat.matches();
    }
    
    public static boolean isAPhoneNumber(String num, int min, int max) {
        Pattern pat = Pattern.compile("^[\\d]{"+min+","+max+"}");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
    
    public static boolean isAnAmount(String monto){
        Pattern pat = Pattern.compile("^[\\d]+(\\.[\\d]{2})*");
        Matcher mat = pat.matcher(monto);
        return mat.matches();
    }
    
    public static boolean isAName(String name){
        Pattern pat = Pattern.compile("^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]{2,}");
        Matcher mat = pat.matcher(name);
        return mat.matches();
    }
    
    public static boolean isANum(String num){
        Pattern pat = Pattern.compile("^[\\d]+");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
    
    // Validar cadena vacio o cadena de espacios
    public static boolean isEmpty(String text){
        Pattern pat = Pattern.compile("^$|[\\s]+");
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

    public static boolean isAPsw(String psw) {
        Matcher pass = Pattern.compile("^[\\w]{8}").matcher(psw);
        Matcher mins = Pattern.compile("^([A-Z0-9]*[a-z]+[A-Z0-9]*)*$").matcher(psw);
        Matcher mays = Pattern.compile("^([a-z0-9]*[A-Z]+[a-z0-9]*)*$").matcher(psw);
        Matcher nums = Pattern.compile("^([a-zA-Z]*[0-9]+[a-zA-Z]*)*$").matcher(psw);
        return pass.matches() && mins.matches() && mays.matches() && nums.matches();
    }
    
    public static boolean validNum(String num, int min, int max){
        Pattern pat = Pattern.compile("^[\\d]{"+min+","+max+"}");
        Matcher mat = pat.matcher(num);
        return mat.matches();
    }
}
