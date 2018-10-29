/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.negocio;

import com.liverpool.modelo.Sku;
import com.liverpool.utils.DB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author iasancheza
 */
public class MyAcount extends DB {
    
    public MyAcount(String clase, String url, String host, String port, String name, String usr, String psw) {
        super(clase, url, host, port, name, usr, psw);
    }

    public MyAcount(Properties p) {
        super(p);
    }
    
    public boolean execute(String query, ArrayList<String> data){
        boolean success = false;
        try {
            success = this.executeUpdate(query,data);
        } catch (SQLException ex) {
            System.out.println("Ocurrio una excepcion al ejecutar el sig. query: ");
            System.out.println(query);
            System.out.println(ex.toString());
            success = false;
        }
        return success;
    }
    
    public ArrayList<Sku> getSkus(String query, ArrayList<String> data){
        ArrayList<Sku> skus = new ArrayList<>();
        ArrayList<ArrayList<Object>> result = this.getListBy(query,data);
        if(result.isEmpty()){
            return new ArrayList<>();
        }
        for(ArrayList<Object> datos : result){
            skus.add(new Sku(datos));
        }
        return skus;
    }
    
    public Sku getSku(String query, ArrayList<String> data){
        Sku sku = new Sku();
        ArrayList<Object> result;
        try {
            result = this.getFirst(query, data);
            if(result.isEmpty()){
                return sku;
            }
            sku = new Sku(result);
        } catch(SQLException ex){
            System.out.println("Ocurrio una excepcion al ejecutar el sig. query: ");
            System.out.println(query);
            System.out.println(ex.toString());
            sku = new Sku();
        }
        return sku;
    }
    
    private ArrayList<ArrayList<Object>> getListBy(String query, ArrayList<String> datos){
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        try {
            result = this.getList(query,datos);
        } catch (SQLException ex) {
            System.out.println("Ocurrio una excepcion al ejecutar el sig. query: ");
            System.out.println(query);
            System.out.println(ex.toString());
            result = new ArrayList<>();
        }
        return result;
    }
    
    
}
