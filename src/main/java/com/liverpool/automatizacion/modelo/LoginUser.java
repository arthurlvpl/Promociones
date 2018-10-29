/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import javax.json.JsonObject;

/**
 *
 * @author IASANCHEZA
 */
public class LoginUser {
    public static final String JSON_KEY_USUARIO = "usuario";
    public static final String JSON_KEY_PASSWORD = "password";
    
    private String user;
    private String password;

    public LoginUser() {
        this("", "");
    }    

    public LoginUser(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public LoginUser(JsonObject login){
        this.user = login.containsKey(JSON_KEY_USUARIO)? login.getString(JSON_KEY_USUARIO) : "";
        this.password = login.containsKey(JSON_KEY_PASSWORD)? login.getString(JSON_KEY_PASSWORD) : "";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
