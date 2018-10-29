/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.promociones;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Validacion;
import com.liverpool.negocio.MyAcount;
import com.liverpool.controlador.Ambiente;
import com.liverpool.controlador.Const;
import com.liverpool.utils.Log;
import com.liverpool.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

/**
 *
 * @author IASANCHEZA
 */
public class Matriz {
    protected String navegador;
    protected WebDriver driver;
    protected ArrayList<Validacion> validaciones;
    protected Properties ambiente;
    protected boolean tlog;
    protected ArrayList<JsonObject> casos;
    protected Archivo folder;
    protected MyAcount db;

    public Matriz() {
        this("", true, new Archivo(""));
    }

    public Matriz(String navegador, boolean tlog, Archivo folder) {
        this.navegador = navegador;
        this.tlog = tlog;
        this.folder = folder;
        this.validaciones = new ArrayList<>();
        this.casos = readCasos();
        loadAmbiente();
    }
    
    protected void loadAmbiente() {
        // Cargar el ambiente
        String ambienteFl = System.getProperty(Const.APP_ENTORNO).replace("?", folder.getName());
        ambienteFl = new File(folder, ambienteFl).getAbsolutePath();
        this.ambiente = Utils.loadProperties(new File(ambienteFl).getAbsolutePath());
    }
    
    public void execute(){
    
    }
    
    public boolean waitForElement(String nameElement, long seconds){
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofSeconds(seconds))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(Find.element(driver, nameElement))) 
                != null;
    }
    
    protected void initDriver() {
        switch (navegador) {
            case "Chrome":
                System.setProperty(System.getProperty(Const.CHROME_DRIVER), System.getProperty(Const.CHROME_PATH));
                driver = new ChromeDriver(); // Abre el navegador
                break;
            case "Mozilla Firefox":
                System.setProperty(System.getProperty(Const.FIREFOX_DRIVER), System.getProperty(Const.FIREFOX_PATH));
                driver = new FirefoxDriver();
                break;
            case "Internet Eplorer":

                break;
            case "No Aplica":

                break;
        }

        // Configurar timeouts
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(System.getProperty(Const.DRIVER_PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(Long.parseLong(System.getProperty(Const.DRIVER_SET_SCRIPT_TIMEOUT)), TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Long.parseLong(System.getProperty(Const.DRIVER_IMPLICITLY_WAIT)), TimeUnit.SECONDS);

        // Determinar ambiente de ejecucion
        Cookie cookie = new Cookie(System.getProperty(Const.APP_COOKIE), System.getProperty(Const.APP_SITIO));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        try {
            driver.manage().addCookie(cookie);
        } catch (Exception ex) {
            Log.write("Ocurrio una excepcion al agregar la sig cookie:");
            Log.write("Cookie = {" + cookie.getName() + "," + cookie.getValue() + "}");
            Log.write(ex.toString());
        }

        driver.get(ambiente.getProperty(Ambiente.URL)); // Abre la pagina en la pestaña actual
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        driver.navigate().refresh(); // refresca la pagina

    }
    
    private ArrayList<JsonObject> readCasos(){
        ArrayList<JsonObject> casos = new ArrayList<>();
        
        File folder =  new File(System.getProperty(Const.APP_TESTING));
        
        if(!folder.exists()){
            return casos;
        }
        
        // Listar y filtrar todos los archivos de la carpeta de testing
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(System.getProperty(Const.APP_TEST_FILE_EXTENSION));
            }
        });
        
        // Leer los archivos cargados
        for(File f: files){
            JsonObject json = readJson(f);
            if(json != null){
                casos.add(json);
            }
        }
        return casos;
    }
    
    private JsonObject readJson(File f){
        JsonObject json = null;
        InputStream is = null;
        JsonReader reader = null;
        try {
            is = new FileInputStream(f);
            reader = (JsonReader) Json.createReader(is);
            json = reader.readObject();
            reader.close();
        } catch (FileNotFoundException ex) {
            Log.write("Ocurrio una excepcion al leer el archivo json: " + f.getAbsolutePath());
            Log.write(ex.toString());
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
                
                if(is != null){
                    is.close();
                }
            } catch(IOException e){
                System.out.println(e.toString());
            }
        }
        return json;
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

    public Properties getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Properties ambiente) {
        this.ambiente = ambiente;
    }

    public boolean isTlog() {
        return tlog;
    }

    public void setTlog(boolean tlog) {
        this.tlog = tlog;
    }

    public ArrayList<JsonObject> getCasos() {
        return casos;
    }

    public void setCasos(ArrayList<JsonObject> casos) {
        this.casos = casos;
    }

    public Archivo getFolder() {
        return folder;
    }

    public void setFolder(Archivo folder) {
        this.folder = folder;
    }

    public MyAcount getDb() {
        return db;
    }

    public void setDb(MyAcount db) {
        this.db = db;
    }
}
