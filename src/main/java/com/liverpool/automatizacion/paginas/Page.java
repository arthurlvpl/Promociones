/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Validacion;
import java.util.ArrayList;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author iasancheza
 */
public class Page {
    protected WebDriver driver;
    protected ArrayList<Validacion> validaciones;
    protected Properties properties;
    
    public Page(){
        
    }

    public Page(WebDriver driver){
        this.driver = driver;
        this.validaciones = new ArrayList<>();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public ArrayList<Validacion> getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ArrayList<Validacion> validaciones) {
        this.validaciones = validaciones;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
