/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.utils.Utils;
import java.io.File;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author iasancheza
 */
public class Pdp extends Page {
    public static final String PROPERTIES_FILE = "pdp.properties";
    
    public static final String SELECTOR_CANTIDAD = "selector_cantidad";
    public static final String BTN_ADD_CART = "btn_add_cart";
    public static final String CLOSE_POPUP_COMPRAR = "close_popup_comprar";
    public static final String IMAGEN_SKU = "imagen_sku";
    public static final String TITLE_SKU = "title_sku";
    public static final String PRECIO_ESPECIAL = "precio_especial";
    public static final String PRECIO_PROMOCION = "precio-promocion";
    public static final String URL = "url";
    public static final String BTN_COMPRA_SIN_REGISTRO = "btn_compra_sin_registro";
    public static final String DECIMALES = "decimales";
    public static final String PROMOS_LIVERPOOL = "promos_liverpool";
    public static final String PROMOS_BANCARIAS = "promos_bancarias";
    public static final String ATRIBUTOS_ARTICULO = "atributos_articulo";
    
    public Pdp(WebDriver driver) {
        super(driver);
        this.properties = Utils.loadProperties(new File(PROPERTIES_FILE).getAbsolutePath());
    }
    
    public boolean inputCantidad(String cantidad){
        WebElement element;
        if ((element = Find.element(driver,properties.getProperty(SELECTOR_CANTIDAD))) == null){
            return false;
        }
        element.clear();
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(cantidad);
        return true;
    }
    
    // Agregar el sku a la bolsa
    public boolean addToCart(){
        WebElement element;
        if((element = Find.element(driver,properties.getProperty(BTN_ADD_CART))) != null){
            element.click();
        }
        return true;
    }
    
    
    
}
