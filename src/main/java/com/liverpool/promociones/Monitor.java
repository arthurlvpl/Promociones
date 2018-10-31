/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.promociones;

import com.liverpool.automatizacion.modelo.Promo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



/**
 *
 * @author iasancheza
 */
public class Monitor {
    public static final String STAGING_PAGE = "http://172.17.207.38:9084/PromotionsMonitor2/index.jsp#/init";
    public static final String CHROME_DRIVER = "webdriver.chrome.driver";
    public static final String CHROME_PATH = "C:\\chromedriver.exe";
    
    private WebDriver driver;
    
    public Monitor(){
        System.setProperty(CHROME_DRIVER,CHROME_PATH);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(STAGING_PAGE);
    }
    
    public void close(){
        driver.close();
    }
    
    public boolean selectProd(){
        final String rbProd = "/html/body/ng-view/form/div/div/div[1]/div[3]/input[3]";
        WebElement element = driver.findElement(By.xpath(rbProd));
        if(element == null){
            return false;
        }
        if(!element.isSelected()){
            element.click();
        }
        return element.isSelected();
    }
    
    public boolean selectStaging(){
        final String rbStaging = "/html/body/ng-view/form/div/div/div[1]/div[3]/input[4]";
        WebElement element = driver.findElement(By.xpath(rbStaging));
        if(element == null){
            return false;
        }
        if(!element.isSelected()){
            element.click();
        }
        return element.isSelected();
    }
    
    public boolean insertSku(String sku){
        final String txtSku = "/html/body/ng-view/form/div/div/div[1]/div[1]/div[1]/input";
        WebElement element = driver.findElement(By.xpath(txtSku));
        if(element == null){
            return false;
        }
        element.sendKeys(sku);
        return true;
    }
    
    public boolean buscar(){
        final String btnBuscar = "/html/body/ng-view/form/div/div/div[3]/div[1]/button";
        WebElement element = driver.findElement(By.xpath(btnBuscar));
        if(element == null){
            return false;
        }
        element.click();
        return true;
    }
    
    public WebElement getTableResult(){
        final String tableSkus = "/html/body/ng-view/div[3]/table";
        return driver.findElement(By.xpath(tableSkus));
    }
    
    public boolean selectFirstResult(){
        final String sku = "/html/body/ng-view/div[3]/table/tbody/tr/td[2]/a";
        WebElement element = driver.findElement(By.xpath(sku));
        if(element == null){
            return false;
        }
        element.click();
        return true;
    }
    
    public HashMap<String,ArrayList<Promo>> getPromociones(){
        HashMap<String,ArrayList<Promo>> promociones = new HashMap<>();
        
        final String promos = "/html/body/div[1]/div/div/div[2]/div[2]/table/tbody/tr";
        ArrayList<WebElement> rows = (ArrayList<WebElement>) driver.findElements(By.xpath(promos));
        for(WebElement r : rows){
            ArrayList<WebElement> columns = (ArrayList<WebElement>) r.findElements(By.tagName("td"));
            System.out.println(columns.size());
            Promo p = new Promo();
            
            for(int i=0; i < columns.size(); i++){
                String dato = columns.get(i).getText();
                switch(i){
                    case 0: // Banco
                        p.setBanco(dato);
                        break;
                    case 1: // Descripcion
                        p.setDescripcion(dato);
                        break;
                    case 2: // Descuento
                        p.setDescuento(dato);
                        break;
                    case 3: // Plan
                        p.setPlan(dato);
                        break;
                }
            }
            String banco = p.getBanco();
            ArrayList<Promo> array = promociones.get(banco);
            if(array == null){
                array = new ArrayList<>();
                promociones.put(banco, array);
            }
            array.add(p);
        }
        return promociones;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
