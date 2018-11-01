/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.promociones;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.controlador.Ambiente;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Pasos;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.modelo.Validacion;
import com.liverpool.automatizacion.paginas.Buscador;
import com.liverpool.automatizacion.paginas.Header;
import com.liverpool.automatizacion.paginas.LoginForm;
import com.liverpool.automatizacion.paginas.Pdp;
import com.liverpool.automatizacion.properties.Shipping;
import com.liverpool.utils.RE;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author iasancheza
 */
public class Checkout extends Matriz {
    private Properties cart; // propiedades de la pagina cart.jsp
    private Properties shipping; // propiedades de la pagina shipping.jsp
    private ArrayList<Sku> skus; // Obtener estos skus para la prueba, ya sea de BD o de archivo
    
    public Checkout(String navegador, boolean tlog){
        this.navegador = navegador;
        this.validaciones = new ArrayList<>();
        this.tlog = tlog;
    }
    
    public ArrayList<Validacion> validateHeader(String paso){
        ArrayList<Validacion> resultado = new ArrayList<>();
        return resultado;
    }
    
    public ArrayList<Validacion> validateFooter(String paso){
        ArrayList<Validacion> resultado = new ArrayList<>();
        return resultado;
    }
    
    public void execute(){
        WebElement element;
        
        pasoCero();
        
        ArrayList<Tienda> tiendas = getClickAndCollect();
        for(Tienda t : tiendas){
            System.out.println(t);
        }
        
        // Insertar las tiendas en la base
        for(Tienda t : tiendas){
            //db.insertTienda(shipping.getProperty(Shipping.QUERY_INSERT_TIENDAS), t);
        }
        
        // INICIA PASO 1. ENTREGA
        
        // Caso 1.1: El header debe estar visible
        if((element = Find.byId(driver, shipping.getProperty(Shipping.HEADER))) != null){
            if(element.isDisplayed()){
                validaciones.add(new Validacion("1.1", true, ""));
            } else {
                validaciones.add(new Validacion("1.1", false, "No se muestra el header"));
            }
        } else {
            validaciones.add(new Validacion("1.1", false, "No se encontro el header de la pagina"));
        }
        
        // Caso 1.2: El logo de la tienda se muestra en la esquina superior izquierda   
        if((element = Find.element(driver, shipping.getProperty(Shipping.LOGO))) != null){
            if(element.isDisplayed()){
                // Validar que el logo este en la posicion indicada
                
                validaciones.add(new Validacion(true));
                element.click();
//                if(driver.getCurrentUrl().equals(cart.getProperty(Cart.URL))){
//                    validaciones.add(new Validacion(true));
//                    driver.navigate().back();
//                } else {
//                    validaciones.add(new Validacion(false));
//                }
            } else {
                validaciones.add(new Validacion("1.2", false, "No se muestra el logo de la tienda"));
            }
        } else {
            validaciones.add(new Validacion("1.2", false, "No se encuentra el logo de la tienda"));
        }
        
        Pasos pasos = new Pasos(driver.findElements(By.className(shipping.getProperty(Shipping.PASOS))));
        if(pasos.getNumPasos() != Integer.parseInt(shipping.getProperty(Shipping.NUM_PASOS))){
            validaciones.add(new Validacion("",Validacion.FAIL, "El numero de pasos mostrado es diferente al esperado."));
        }
        
        // Validacion de los pasos del checkout
        validaciones.add(enabledPasos(pasos));
        
        // Validacion del color rosa sobre el paso 1
        validaciones.add(new Validacion(isPasoSelected(pasos, 1)));
        
        // Validar los datos del formulario de Destinatario
        String[] datosDestinatario = shipping.getProperty(Shipping.DATOS_FORM_DESTINATARIO).split("\\|");        
        List<WebElement> formDestinatario = driver.findElements(By.className(shipping.getProperty(Shipping.FORM_DESTINATARIO)));
        System.out.println("Tamanio original: " + formDestinatario.size());
        // Remover de la lista los WebElement que tengan texto vacio
        List<WebElement> removes = new ArrayList<>();
        int i=0;
        for(WebElement e : formDestinatario){
            String text = e.getText();
            System.out.println("Element: " + i + ", Contenido: " + text + ", Displayed: " + e.isDisplayed() + ", Enabled: " + e.isEnabled());
            if((RE.isEmpty(text)) || (!e.isDisplayed())){
                removes.add(e);
                //formDestinatario.remove(e);
                System.out.println("Se removio el elemento: " + i);
            }
            i++;
        }
        
        if(formDestinatario.removeAll(removes)){
            System.out.println("Se eliminaron todos los elementos");
            System.out.println("Tamanio final: " + formDestinatario.size());
        }
        
        // Comparar que los tamanios sean iguales
        if(datosDestinatario.length != formDestinatario.size()){
            validaciones.add(new Validacion("", false, "El numero de elementos mostados en la pagina es diferente al esperado"));
        }
        
        // Validar uno a uno los elementos del formulario de destinatario
        for(i=0; i < datosDestinatario.length; i++){
            String text = datosDestinatario[i];
            String textForm = formDestinatario.get(i).getText();
            
            if(!text.equals(textForm)){
                validaciones.add(new Validacion("", false, "El texto mostrado es diferente del esperado"));
            }
        }
    }
    
    private ArrayList<Tienda> getClickAndCollect(){
        ArrayList<Tienda> tiendas = new ArrayList<>();
        WebElement element;
        
        // Seleccionar envio a Tienda
        if((element = Find.element(driver, shipping.getProperty(Shipping.RBUTTON_CLICK_COLLECT))) == null){
            return tiendas;
        }
        element.click(); // Hacer click en el radio button de envio a tienda
        
        // Recuperar el dropdown de Selecciona Estado
        if((element = Find.element(driver, shipping.getProperty(Shipping.DROPDOWN_SELECT_ESTADO))) == null){
            return tiendas;
        }
        
        Select select = new Select(element);
        List<WebElement> estados = select.getOptions();
        for(WebElement estado : estados){
            String edo = estado.getText().trim();
            //System.out.println("-  " + estado.getText().trim());
            select.selectByVisibleText(estado.getText());
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            
            List<WebElement> direcciones = Find.elements(element, shipping.getProperty(Shipping.DIRECCIONES));
            for(WebElement direccion : direcciones){
                Tienda t = null;
                // Validar si la tienda tiene mas de un click and collect
                List<WebElement> typeCollects = Find.elements(direccion, shipping.getProperty(Shipping.TYPE_COLLECTS));
                for(WebElement collect : typeCollects){
                    //System.out.println(collect.getText());
                    t = RE.getTienda(collect.getText());
                    if(t!=null){
                        t.setEstado(edo);
                        tiendas.add(t);
                    }
                }
                //System.out.println(direccion.findElement(By.className(shipping.getProperty(Shipping.INFO_STORE))).getText());
                t = RE.getTienda(direccion.findElement(By.className(shipping.getProperty(Shipping.INFO_STORE))).getText());
                if(t!=null){
                    t.setEstado(edo);
                    tiendas.add(t);
                }
            }
            //System.out.println("\n");
        }
        return tiendas;
    }
    
    private void dataSku(Sku sku){
        WebElement element;
        
        // Imagen del sku
        if((element = Find.element(driver, ambiente.getProperty(Pdp.IMAGEN_SKU))) != null){
            sku.setUrlImg(element.getAttribute("src"));
        }
            
        // titulo (nombre) del sku en el pdp
        if((element = Find.element(driver, ambiente.getProperty(Pdp.TITLE_SKU))) != null){
            sku.setTitulo(element.getText());
        }
            
        sku.setPrecioBase(getPrecio(ambiente.getProperty(Pdp.PRECIO_ESPECIAL)));
        sku.setPrecioVenta(getPrecio(ambiente.getProperty(Pdp.PRECIO_PROMOCION)));
            
        sku.setPromosLiverpool(getPromociones(ambiente.getProperty(Pdp.PROMOS_LIVERPOOL)));
        sku.setPromosBancarias(getPromociones(ambiente.getProperty(Pdp.PROMOS_BANCARIAS)));            
            
        // Obtener las propiedades del sku
        // devuelve un json con las caracteristicas del producto
        if((element = Find.element(driver, ambiente.getProperty(Pdp.ATRIBUTOS_ARTICULO))) != null){
            String json = element.getAttribute("value").trim();
            sku.setjCaracteristicas(json);
        }
    }
    
    private void pasoCeroCheck(){
        // Iniciar busqueda de articulos por SKU
        WebElement element;
        for(Sku sku : skus){
            if((element = Find.element(driver, ambiente.getProperty(Header.BUSCADOR))) == null){
                return;
            }
            
            element.sendKeys(sku.getId(), Keys.ENTER); // Buscar el sku
            
            // Validar el PDP del sku
            String url = driver.getCurrentUrl();
            //sku.setUrl(url);
            String urlFail = ambiente.getProperty(Buscador.SKU_SEARCH).replace(Ambiente.URL, ambiente.getProperty(Ambiente.URL))+sku.getId();
            if(url.equals(urlFail)){
                continue;
            }
            
            //dataSku(sku);  // Guardar la informacion del sku            
            
            // Agregar el sku a la bolsa
            if((element = Find.element(driver, ambiente.getProperty(Pdp.BTN_ADD_CART))) != null){
                element.click();
            }
            
            // Esperar a que se abra al popup de compra
            // MODIFICAR DESPUES
            while (driver.findElements(By.xpath(ambiente.getProperty(Pdp.CLOSE_POPUP_COMPRAR))).size() < 1) {
                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
            }
            
            // Cerrar el popup de compra
            if((element = Find.element(driver, ambiente.getProperty(Pdp.CLOSE_POPUP_COMPRAR))) != null){
                element.click();
            }
        }
        
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        
        // Ir al home del sitio
        if((element = Find.element(driver, ambiente.getProperty(Header.LOGO))) != null){
            element.click();
        }
        
        // Ir a la bolsa
        if((element = Find.element(driver, ambiente.getProperty(Header.A_BAG))) != null){
            element.click();
        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
        
        // Validar el boton superior
//        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_SUP))) != null){
//            element.click();
//        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
        
        // Cambiar el foco al popup de login        
        validaciones.add(new Validacion(validarBtnPagar()));
        
        // Cambiar el foco al contenido de la bolsa
        driver.switchTo().defaultContent();
//        if((element = Find.element(driver, cart.getProperty(Cart.CLOSE_POPUP_LOGIN))) != null){
//            element.click();
//        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
        
        // Validacion del boton inferior
//        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_INF))) != null){
//            element.click();
//        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
        
        // Cambiar el foco al popup de login
        validaciones.add(new Validacion(validarBtnPagar()));
        
        // Realizar compra sin registro
        if((element = Find.element(driver, ambiente.getProperty(LoginForm.BTN_COMPRA_SIN_REGISTRO))) != null){
            element.click();
        }
    }
    
    private void pasoCero(){
        // Iniciar busqueda de articulos por SKU
        WebElement element;
        for(Sku sku : skus){
            
            Buscador b = new Buscador(driver, folder, ambiente.getProperty(Ambiente.URL));
            if(!b.buscarSku(sku.getId())){
                continue;
            }
            
            Pdp pdp = new Pdp(driver);
            if(!pdp.inputCantidad(sku.getCantidad())){
                continue;
            }
            
            if(!pdp.addToCart()){
                continue;
            }
            
            // Cerrar el popup de compra
            while((element = Find.element(driver, ambiente.getProperty(Pdp.CLOSE_POPUP_COMPRAR))) == null){
                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);     
            }
            element.click();
        }
        
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        
        // Ir a la bolsa
        if((element = Find.element(driver, ambiente.getProperty(Header.A_BAG))) != null){
            element.click();
        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        
//        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_SUP))) != null){
//            element.click();
//        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        
        // Cambiar el foco al popup de login        
        validaciones.add(new Validacion(validarBtnPagar()));
               
        // Realizar compra sin registro
        if((element = Find.element(driver, ambiente.getProperty(LoginForm.BTN_COMPRA_SIN_REGISTRO))) != null){
            element.click();
        }
        
        // Aqui llegamos a paso 1
    }
    
    private ArrayList<String> getPromociones(String tipoPromo){
        ArrayList<String> promos = new ArrayList<>();
        WebElement element;
        
        // Ya encontramos el ul
        if((element = Find.element(driver, tipoPromo)) != null){
            // Hay que obtener los elementos de la lista
            List<WebElement> list = element.findElements(By.tagName("li"));
            for(WebElement e : list){
                promos.add(e.getText());
            }
        }
        return promos;
    }
    
    private String getPrecio(String tipoPrecio){
        WebElement element;
        StringBuilder precioBase =  new StringBuilder();
        if((element = Find.element(driver, tipoPrecio)) != null){
            precioBase.append(element.getText().trim());
                
//            if((element = Find.element(driver, ambiente.getProperty(Ambiente.DECIMALES))) != null){
//                precioBase.append(element.getText().trim());
//            }
            return precioBase.toString();
        }
        return "";
    }
    
    public Validacion enabledPasos(Pasos pasos){
        Validacion validacion = new Validacion(true);
        
        if(!pasos.getPaso1().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 1 no esta disponible");
        } else if(!pasos.getPaso2().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 2 no esta disponible");
        } else if(!pasos.getPaso3().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 3 no esta disponible");
        }
        
        return validacion;
    }
    
    public boolean isPasoSelected(Pasos pasos, int numPaso){
        boolean selected = false;
        switch(numPaso){
            case 1: // Paso 1
                selected =  pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
            case 2: // Paso 2
                selected =  pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
            case 3: // Paso 3
                selected =  pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
        }
        return selected;
    }
    
    public boolean validarBtnPagar(){
        WebElement element;
        if((element = Find.element(driver, ambiente.getProperty(LoginForm.POPUP_LOGIN))) != null){
            if((Find.frame(driver, element)) != null){
                if((element = Find.element(driver, ambiente.getProperty(LoginForm.DIV_LOGIN))) != null){
                    return element.isEnabled();
                }
            }
        }
        return false;
    }

    public ArrayList<Validacion> getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ArrayList<Validacion> validaciones) {
        this.validaciones = validaciones;
    }
}
