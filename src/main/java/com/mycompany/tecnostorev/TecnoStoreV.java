/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tecnostorev;

import com.mycompany.persistencia.ConexionData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TecnoStoreV {

    public static void main(String[] args) {

        try (Scanner teclado = new Scanner(System.in)) {

            System.out.print("Usuario de MySQL: ");
            String usuario = teclado.nextLine();

            System.out.print("Contraseña de MySQL: ");
            String clave = teclado.nextLine();

            String consulta = "SELECT DATABASE()";

            try (
                Connection conexion = ConexionData.conectar(usuario, clave);
                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery(consulta)
            ) {

                if (resultado.next()) {
                    System.out.println("Conexión exitosa");
                    System.out.println(
                        "Base de datos: " + resultado.getString(1)
                    );
                }

            } catch (SQLException error) {
                System.out.println(
                    "No se pudo conectar: " + error.getMessage()
                );
            }
        }
    }
}
