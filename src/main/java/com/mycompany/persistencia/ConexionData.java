/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionData {

    public static Connection conectar(String usuario, String clave)
            throws SQLException {

        String url = "jdbc:mysql://localhost:3306/tecnostore_db";

        return DriverManager.getConnection(url, usuario, clave);
    }
}
