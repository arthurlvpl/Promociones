package com.liverpool.utils;
//import static Controlador.Browser.driver;
//import alertas.AlertError1;
//import Vista.Ventana;
//import static com.mycompany.promosions.Excel.fila;
//import static com.mycompany.promosions.InicializarDriver.driver;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LecturaJSON {
    /**
     * @function main
     * @description Método principal del programa, lee el archivo de
     *              configuración del enlace-mysql-vfp
     */
     private String Nombre,Apellido_P,Correo,Lada,Teléfono,Tarjeta,NumeroTarj,cvv,
             cp,calle,NumExt,Interna,mes,año;
    public void main(String ruta) throws ParseException
    {        
        /**
         * Variables que se utilizaran para la obtención de datos del archivo
         * Json las cuales llevaran el nombre del texto obtener de el archivo
         */
       
        /**
         * Se crear una intancia de la clase JSONParser que es parte de la 
         * libreria json-simple-1.1.1.jar
         */
        JSONParser jspar = new JSONParser();
        /**
         * Se crear una intancia de la clase ConectaSQL que es parte del paquete 
         * configuraciones
         */
      //  ConectaSQL SQL = new ConectaSQL();
       
        //
        try
        {
            // obj se utilizara para guardar los datos contenidos en el archivo
            Object obj = jspar.parse(new FileReader(ruta));
            // Se intancia obj en un objeto de json
            JSONObject jsonObject = (JSONObject) obj;
            /**
             * Se asigna el valor a las variables de los datos que se desean
             * obtener del archivo
             */
            Nombre = (String) jsonObject.get("Nombre");
            Apellido_P = (String) jsonObject.get("Apellido_P");
            Correo = (String) jsonObject.get("Correo");
            Lada = (String) jsonObject.get("Lada");
            Teléfono = (String) jsonObject.get("Teléfono");
            Interna = (String) jsonObject.get("Interna");
            Tarjeta = (String) jsonObject.get("Tarjeta");
            NumeroTarj = (String) jsonObject.get("NumeroTarj");
            cvv = (String) jsonObject.get("cvv");
            mes = (String) jsonObject.get("mes");
            año =(String) jsonObject.get("año");
            cp = (String) jsonObject.get("cp");
            calle = (String) jsonObject.get("calle");
            NumExt = (String) jsonObject.get("NumExt");
            // Se le pasan los datos optenidos del archivo a la clase ConectaSQL
          
            //SQL.conexion( host , database , user , password );
            
        }
        
        catch (IOException e)
        {
            System.out.println("Adios");                        
        }// fin del catch
    }// Fin del método main

    public String getNombre() {
        return Nombre;
    }

    public String getApellido_P() {
        return Apellido_P;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getLada() {
        return Lada;
    }

    public String getTeléfono() {
        return Teléfono;
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public String getNumeroTarj() {
        return NumeroTarj;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCp() {
        return cp;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumExt() {
        return NumExt;
    }

    public String getInterna() {
        return Interna;
    }

    public String getMes() {
        return mes;
    }

    public String getAño() {
        return año;
    }
    
}// Fin de la clase LecturaJSON
