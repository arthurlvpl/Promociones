
package com.liverpool.promociones;

import com.google.common.util.concurrent.Uninterruptibles;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author JCGASCAG
 */
public class Login {
   private WebDriver driver;
   private static String explorador = "C:\\chromedriver.exe";
   
    public Login() {
        System.setProperty("webdriver.chrome.driver",explorador);
        this.driver = new ChromeDriver();
        this.driver.get("https://www.liverpool.com.mx/tienda/home.jsp");
        this.driver.manage().window().maximize();
    }
   
   
    public void iniciar_sesion(String correo, String password){
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // click en el botón de iniciar sesion
        driver.findElement(By.id("login_btn")).click();
        // cambio de frame para obtener acceso a la ventana de iniciar sesion
        // de la página web  
        driver.switchTo().frame(
        driver.findElement(By.xpath("(//iframe[@class='fancybox-iframe'])[1]")));
        // realizamos una espera de 1 segundo a que la pagina se estabilize
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        // ingresa el correo en la ventana de inicio de sesion
        driver.findElement(By.id("login")).sendKeys(correo);
        // ingresa la contraseña en la ventana de inicio de sesion
        driver.findElement(By.id("pass")).sendKeys(password);
        // realizamos una espera de 1 segundo a que la pagina se estabilize
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        // click en el botón de iniciar sesion del fancy
        driver.findElement(By.id("lp_loginFormBtn")).click();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
