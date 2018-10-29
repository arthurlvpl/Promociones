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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author iasancheza
 */
public class Header extends Page {
    public static final String PROPERTIES_FILE = "header.properties";    
    public static final String HEADER = "header";
    public static final String LOGO = "logo";
    public static final String BUSCADOR = "buscador";
    public static final String BTN_BUSCAR = "btn_buscar";
    public static final String A_MIS_PEDIDOS = "link_mis_pedidos";
    public static final String BTN_LOGIN = "btn_login";
    public static final String A_BAG = "a_bag";
    public static final String CART_COUNT = "cart_count";
    public static final String MENU = "menu";
    public static final String A_DEPTOS = "a_deptos";
    public static final String A_MESA_DE_REGALOS = "a_mesa_de_regalos";
    public static final String A_CREDITO = "a_credito";
    public static final String A_SEGUROS = "a_seguros";
    public static final String A_SORTEOS = "a_sorteos";
    public static final String A_AYUDA = "a_ayuda";
    public static final String A_TIENDAS = "a_tiendas";
    
    public Header(WebDriver driver, Archivo folder){
        super(driver, folder);
        
        // cargar el properties
        this.properties = Utils.loadProperties(new File(folder, PROPERTIES_FILE).getAbsolutePath());
    }
    
    public WebElement buscador(){
        return Find.element(driver, properties.getProperty(BUSCADOR));
    }
    
    public WebElement btnLogin(){
        return Find.element(driver, properties.getProperty(BTN_LOGIN));
    }
    
    
}
