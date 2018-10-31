/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.utils;

import com.liverpool.modelo.Promocion;
import com.liverpool.negocio.MyAcount;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author iasancheza
 */
public class Excel {
    private MyAcount db;
    
    public Excel(){
        db = new MyAcount("org.mariadb.jdbc.Driver", 
                "jdbc:mariadb://db.host:db.port/db.name", 
                "172.22.136.185", 
                "3306", 
                "myacount", 
                "pepe1", 
                "1qazxsw2");
    }
    
    private boolean isAHeader(XSSFRow row){
        boolean header = false;
        
        for(int i=row.getFirstCellNum(); i<=row.getLastCellNum(); i++){
            XSSFCell cell = row.getCell(i);
            
            if(i > 10){
                break;
            }
            
            if(cell.getCellType() != CellType.STRING.getCode()){
                header = false;
                break;
            }
            
            String value = cell.getStringCellValue().trim();
            switch(i){
                case 0: 
                    header = value.equals("Resultado Prod P0");
                    break;
                case 1: 
                    header = value.equals("Resultado Prod P3");
                    break;
                case 2: 
                    header = value.equals("Resultado Staging");
                    break;
                case 3: 
                    header = value.equals("Comentarios");
                    break;
                case 4:
                    header = value.equals("Promotion ID");
                    break;
                case 5:
                    header = value.equals("Sección");
                    break;
                case 6: 
                    header = value.equals("Nombre sección");
                    break;
                case 7: 
                    header = value.equals("Paticipa / No participa");
                    break;
                case 8:
                    header = value.equals("Tipo de excepción");
                    break;
                case 9:
                    header = value.equals("Clave");
                    break;
                case 10:
                    header = value.equals("Descripción");
            }
        }
        return header;
    }
    
    private ArrayList rowToArray(XSSFRow row){
        ArrayList array = new ArrayList();
        
        for(int i=row.getFirstCellNum(); i <= row.getLastCellNum(); i++){
            
            XSSFCell cell = row.getCell(i);
            
            if(cell == null){
                array.add("");
                continue;
            }
            
            array.add(getStringValue(cell));
            
        }
        return array;
    }
    
    public String getStringValue(XSSFCell cell){
        String value = "";
        
        if(cell == null){
            return value;
        }
        
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
        }
        
        return value;
    }
    
    public void writeExcel(ArrayList<ArrayList> data){
        System.out.println("Inicia escritura de archivo de salida");
        XSSFWorkbook wb = new XSSFWorkbook();
        
        XSSFSheet sheet = wb.createSheet();
        
        int rowCount = -1;
        for(ArrayList array : data){
            XSSFRow row = sheet.createRow(++rowCount);
            
            int cellCount = -1;
            for(Object obj : array){
                XSSFCell cell = row.createCell(++cellCount);
                cell.setCellValue((String)obj);
            }
        }
        
        try { // Escribir el archivo de salida
            FileOutputStream out = new FileOutputStream("promociones.xlsx"); // para escribir el archivo de salida
            wb.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private boolean isValidExcepcion(String tipoExcepcion){
        boolean valido = true;
        
        switch(tipoExcepcion){
            case "Grupo de Artículos":
            case "Proveedor":
            case "Submarca":
            case "SKU":
                valido = true;
                break;
            default:
                valido = false;
        }
        
        return valido;
    }
    
    public String getSku(String tipoExcepcion, String seccion, String clave, String descripcion){
        String query = "";
        
        ArrayList<String> datos = new ArrayList<String>(){{
            add(seccion);
        }};
        
        switch(tipoExcepcion){
            case "Submarca":
                query = "Select * from skus where seccion = ? and marca = ?";
                datos.add(descripcion);
                break;
            case "Grupo de Artículos":
                query = "Select * from skus where seccion = ? and grupo_articulo = ?";
                datos.add(clave);
                break;
        }
        
        return db.getSku(query, datos).getNumSku();
    }
    
    private String getPromociones(XSSFRow row, int init){
        StringBuilder promociones = new StringBuilder();
        // Concatenar con |
        XSSFCell cell = null;
        while((cell = row.getCell(++init)) != null){
            String value = getStringValue(cell);
            promociones.append(value).append("|");
        }
        promociones.deleteCharAt(promociones.length()-1);
        return promociones.toString();
    }
    
    private void procesaRow(XSSFRow row){
        
        if(row == null){
            return;
        }

        // Validar si el row es un header
        if(isAHeader(row)){
            return;
        }

        // Primero recuperar las promociones
        // Despues recuperar el sku correspondiente

        // A partir de la celda 11 en adelante comienza el detalle de las promociones
        int contador = -1;

        String rProdP0 = getStringValue(row.getCell(++contador));
        String rProdP3 = getStringValue(row.getCell(++contador));
        String rStaging = getStringValue(row.getCell(++contador));
        String comments = getStringValue(row.getCell(++contador));
        String promotionId = getStringValue(row.getCell(++contador));
        String seccion = getStringValue(row.getCell(++contador));
        String nombreSeccion = getStringValue(row.getCell(++contador));
        String participa = getStringValue(row.getCell(++contador));
        String tipoExcepcion = getStringValue(row.getCell(++contador));
        String clave = getStringValue(row.getCell(++contador));
        String descripcion = getStringValue(row.getCell(++contador)); // Descripcion de la promocion 
        String sku = getStringValue(row.getCell(++contador));

        // Validar que el tipo de excepcion sea valido
        if(!isValidExcepcion(tipoExcepcion)){
            return;
        }
        
        ArrayList<Promocion> promociones = new ArrayList<>();
        String[] promos = getPromociones(row, contador).split("\\|O\\|");
        for(String promo : promos){
            promociones.add(new Promocion(promo.split("\\|Y\\|")));
        }

        // Ya tenemos las promociones, hay que buscarlas en staging
        sku=sku.equals("")? getSku(tipoExcepcion, seccion, clave, descripcion) : sku;
        
        System.out.println("abrir staging: " + sku);
        
        
    }
    
    public void procesaExcel(File f){
        System.out.println("Inicia procesamiento del archivo: " + f.getAbsolutePath());
        
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            
            XSSFSheet sheet = wb.getSheetAt(0);
            
            for(Row row : sheet){
                procesaRow((XSSFRow) row);
            }
            
            System.out.println("Se termina de leer el archivo: " + f.getAbsolutePath());
        } catch(IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if(fis != null){
                    fis.close();
                }
            } catch (IOException ex){
                System.out.println(ex.toString());
            }
        }
    }

}
