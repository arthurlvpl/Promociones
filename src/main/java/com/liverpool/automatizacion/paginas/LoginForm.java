/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.LoginUser;
import com.liverpool.utils.Utils;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author iasancheza
 */
public class LoginForm extends Page {
    public static final String PROPERTIES_FILE = "login_form.properties";
    public static final String POPUP_LOGIN = "popup_login";
    public static final String DIV_LOGIN = "div_login";
    public static final String BTN_COMPRA_SIN_REGISTRO = "btn_compra_sin_registro";
    public static final String TXT_LOGIN = "txt_login";
    public static final String TXT_PASSWORD = "txt_password";
    public static final String BTN_FRM_LOGIN = "btn_frm_login";
    
    private Header header;
    
    public LoginForm(WebDriver driver, Archivo folder){
        super(driver, folder);
        
        // cargar el properties
        this.properties = Utils.loadProperties(new File(folder, PROPERTIES_FILE).getAbsolutePath());
    
        header = new Header(driver, folder);
    }
    
    public boolean login(LoginUser lu){
        String oldWindow = driver.getWindowHandle();
        WebElement element;
        
        // Validar si se encuentra el boton de login
        if((element = header.btnLogin()) == null){
            return false; // No hay boton de click, no es posible iniciar sesion
        }
        element.click();
        
        // Cambiar el foco al popup de login
        if((element = Find.element(driver, properties.getProperty(POPUP_LOGIN))) == null){
            return false; // No se puede acceder al popup de login
        }
            
        // Cambiar al popup de login
        if((Find.frame(driver, element)) == null){
            return false; // No se pudo cambiar al frame del popup
        }
            
        // Validar el div del login
        if(Find.element(driver, properties.getProperty(DIV_LOGIN)) == null){
            return false; // No hay div de login
        }
            
        // Iniciar sesion
        if((element = Find.element(driver, properties.getProperty(TXT_LOGIN))) != null){
            element.sendKeys(lu.getUser());
        }
               
        if((element = Find.element(driver, properties.getProperty(TXT_PASSWORD))) != null){
            element.sendKeys(lu.getPassword());
        }
                
        // Hacer click en el boton inicia sesion
        if((element = Find.element(driver, properties.getProperty(BTN_FRM_LOGIN))) != null){
            element.click();
        }
        
        // Cambiar al frame original
        return driver.switchTo().window(oldWindow) != null;
    }
    
}
