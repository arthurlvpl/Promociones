/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.controlador.Ambiente;
import com.liverpool.utils.Utils;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author IASANCHEZA
 */
public class Buscador extends Page {
    public static final String PROPERTIES_FILE = "buscador.properties";
    public static final String SKU_SEARCH = "sku_search";
    public static final String SEARCH_FAIL = "search_fail";
    public static final String LBL_RESULT = "lbl_result";
    public static final String SKUS_SEARCH_LIST = "skus_search_list";
    
    private Header header;
    private String ambienteUrl;
     public Buscador(){
         super();
     }
    public Buscador(WebDriver driver, Archivo folder, String ambienteUrl) {
        super(driver);
        this.properties = Utils.loadProperties(new File(folder, PROPERTIES_FILE).getAbsolutePath());
        this.header = new Header(driver);
        this.ambienteUrl = ambienteUrl;
    }
    public  Buscador(WebDriver driver, String ambienteUrl){
       super(driver);
        this.properties = Utils.loadProperties(new File(PROPERTIES_FILE).getAbsolutePath());
        this.header = new Header(driver);
        this.ambienteUrl = ambienteUrl;
    }
    public boolean buscarSku(String sku){
        WebElement element;
        
        // Ingresar el sku en la barra de busqueda
        if((element = header.buscador()) == null){
            return false;
        }
        
        element.sendKeys(sku, Keys.ENTER);
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Validar si entro al PDP del sku
        String urlSearch = properties.getProperty(SKU_SEARCH).replace(Ambiente.URL, ambienteUrl)+sku;
         Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        if (driver.getCurrentUrl().equals(urlSearch)) {
            // Validar si no se encontro el producto
            if ((element = Find.element(driver, properties.getProperty(LBL_RESULT))) != null) {
                String leyenda = element.getText();
                String leyendaFail = properties.getProperty(SEARCH_FAIL).replace("?", sku);
                if (leyenda.equals(leyendaFail)) {
                    return false;
                }
            } else {
                // Validar si el sku pertenece a mas de un articulo
                String articulo = properties.getProperty(SKUS_SEARCH_LIST).replace("?", sku);
                if ((element = Find.element(driver, articulo)) != null) {
                    element.click();
                }
            }
        }
        return true;
    }
    public void Buscar(String sku){
        driver.findElement(By.id("")).sendKeys(sku);
    }
    
    
}
