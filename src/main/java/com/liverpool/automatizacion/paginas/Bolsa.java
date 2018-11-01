
package com.liverpool.automatizacion.paginas;

import com.google.common.util.concurrent.Uninterruptibles;
import static com.liverpool.automatizacion.paginas.Buscador.PROPERTIES_FILE;
import com.liverpool.promociones.Login;
import com.liverpool.utils.LecturaJSON;
import com.liverpool.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author JCGASCAG
 */
public class Bolsa {
   
    


    
    
    
    public void bolsita_guest(WebDriver driver) {
        LecturaJSON LJ = new LecturaJSON();
        //LJ.main(URLS.getJson());
        
        driver.findElement(By.id("pdpselectedQuantity")).clear();
        //driver.findElement(By.id("pdpselectedQuantity")).sendKeys("1");
        driver.findElement(By.id("addButton")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Click en el botón pagar
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Cerra el pop-pup emergente
        driver.findElement(By.cssSelector("a.fancybox-item.fancybox-close.icon-liv-close")).click();
        //driver.findElement(By.xpath("//a[@href='javascript:;']")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Click en la bolsita
        driver.findElement(By.id("cart-count")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Click en el botn pagar
        driver.findElement(By.className("btn-primary")).click();
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@class='fancybox-iframe'])[1]")));
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        // click en el boton comprar sin registro
        driver.findElement(By.xpath("//*[@id='comprar']/a/p/button")).click();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        // Rellena el campo de nombre
        driver.findElement(By.name("nombre")).sendKeys(LJ.getNombre()); //
        driver.findElement(By.name("a_paterno")).sendKeys(LJ.getApellido_P());
        driver.findElement(By.name("email")).sendKeys(LJ.getCorreo());
        driver.findElement(By.name("lada")).sendKeys(LJ.getLada());
        driver.findElement(By.name("phoneNumber")).sendKeys(LJ.getTeléfono());
        // click en el radio button de envio a tienda
        driver.findElement(By.id("pick-up-storin")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // click en el btn siguiente paso
        driver.findElement(By.name("/atg/commerce/order/purchase/ShippingGroupFormHandler.createAndMoveToBilling")).click();
        // esperamos 2 segundos a que la pagina nos redireccione a la seccion de escoger el ipo de tarjeta
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Genera un número aleatorio para seleccionar alguna de los estados de las tiendas
        int numero = (int) (Math.random() * 32) + 1;
        driver.findElement(By.xpath("//*[@id='select-a-store']/option["+numero+"]")).click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
driver.findElement(By.name("/atg/commerce/order/purchase/ShippingGroupFormHandler.createAndMoveToBilling")).click();
// COLOCACIÓN DEL NÚMERO DEL LA TARJETA
Select Tarjeta = new Select (driver.findElement(By.name("/atg/commerce/order/purchase/PaymentGroupFormHandler.creditCardValues.creditCardType")));
Tarjeta.selectByVisibleText(LJ.getTarjeta());
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
// SE COLOC EL NÚMERO DE LA TARHETA
driver.findElement(By.name("cardnum")).sendKeys(LJ.getNumeroTarj());
// SE COLOCA EL CVV DE LA TARJETA
driver.findElement(By.id("bilcodseg")).sendKeys(LJ.getCvv());
// mes y año de expircion
boolean mes,año;
mes = driver.findElement(By.className("month")).isDisplayed();
año = driver.findElement(By.className("year")).isDisplayed();
if(mes == true && año == true){
    Select Mes = new Select (driver.findElement(By.className("month")));
    Select Año = new Select (driver.findElement(By.className("year")));
    Mes.selectByVisibleText(LJ.getMes());
    Año.selectByVisibleText(LJ.getAño());
}
// Datos del tarjeta Habiente
driver.findElement(By.name("nombre")).sendKeys(LJ.getNombre());
driver.findElement(By.name("apaterno")).sendKeys(LJ.getApellido_P());
driver.findElement(By.name("cp")).sendKeys(LJ.getCp());
Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
driver.findElement(By.name("ciudad")).sendKeys("México");
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
driver.findElement(By.name("calle")).sendKeys(LJ.getCalle());
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
driver.findElement(By.name("numexterior")).sendKeys(LJ.getNumExt());
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
driver.findElement(By.name("lada")).sendKeys(LJ.getLada());
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
driver.findElement(By.name("telparticular")).sendKeys(LJ.getTeléfono());
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
// CLICK EN EL BOTON SIGUIENTE PASO
driver.findElement(By.xpath("//*[@id='pay-options']/div[2]/div[3]/span[2]/a/span")).click();
Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
// comparar promociones antes de dar click en el boton eliminar
// botn eliminar
driver.findElement(By.className("button-class")).click();

 //       System.out.println("");


//     System.out.println("promosiones pagina \n "+driver.findElement(By.cssSelector(".step3 select.promo_selector.active-promo")).getText());
    }
    
    public void bolsita_login(WebDriver driver){
        
        boolean btn;
        // verifica si el boton agrgar a mi bolsa existe 
        try {
            driver.findElement(By.id("addButton")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // Cerra el pop-pup emergente
            driver.findElement(By.cssSelector("a.fancybox-item.fancybox-close.icon-liv-close")).click();
           Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // Click en la bolsita
            driver.findElement(By.id("cart-count")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // Click en el botn pagar
            driver.findElement(By.className("btn-primary")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // click en el button radio de enviar a tienda
            driver.findElement(By.xpath("//*[@id='storepickup']/div/div/label")).click();
            // selecciona aleatoriamente un estado de la lista
            int numero = (int) (Math.random() * 32) + 1;
            driver.findElement(By.xpath("//*[@id='select-a-store']/option["+numero+"]")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // selecciona una tienda
            driver.findElement(By.xpath("//*[@id='showAddress']/span[1]")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // click en el botón de siguiente paso
            driver.findElement(By.xpath("//*[@id='form_checkout_ship_add']/span[2]")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // seleccionamos la tarjeta
            driver.findElement(By.xpath("//*[@id='billingFormIdlp']/div/label")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // colocar nip de la tarjeta
            driver.findElement(By.id("nip_express")).sendKeys("123");
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // click en el boton de siguiente paso
            driver.findElement(By.xpath("//*[@id='pay-options']/div[3]/span[2]/a/span")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // tomar los valores de la promocion
            String promoTarjeta = driver.findElement(By.id("list_products")).getText();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            System.out.println("promoTarjeta\n"+promoTarjeta);
            // click en el boton de regresar
            driver.findElement(By.xpath("//*[@id='main-left']/div/a/div")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // click en el boton paypal
            driver.findElement(By.xpath("//*[@id='payments']/li[2]/img")).click();
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            // click en el botón de siguiente paso
            driver.findElement(By.xpath("//*[@id='pay-options']/div[3]/span[2]/a/span")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // tomar la promociones 
            String paypal;
            paypal = driver.findElement(By.id("list_products")).getText();
            System.out.println("paypal "+paypal);
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // clieck en el boton regresar
            driver.findElement(By.xpath("//*[@id='main-left']/div/a/div")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // click en el boton pago en efectivo
            driver.findElement(By.xpath("//*[@id='payments']/li[3]")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            // click en el boton de siguiente paso
            driver.findElement(By.xpath("//*[@id='pay-options']/div[3]/span[2]/a/span")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            String efectivo;
            efectivo = driver.findElement(By.id("list_products")).getText();
            
            System.out.println("efectivo "+efectivo);
            // click en el boton de regresar 
            driver.findElement(By.xpath("//*[@id='main-left']/div/a/div")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
             // click en el boton de regresar 
            driver.findElement(By.xpath("//*[@id='pay-options']/div[3]/span[1]/a/span")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
             // click en el boton de regresar 
            driver.findElement(By.xpath("//*[@id='form_checkout_ship_add']/span[1]/a")).click();
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
           // click en el boon quitar de mi lista
           driver.findElement(By.xpath("//*[@id='remove_1']/a")).click();
           Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
           // click en el boton seguir comprando
           driver.findElement(By.className("btn-main")).click();
//           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
    }
    
}
