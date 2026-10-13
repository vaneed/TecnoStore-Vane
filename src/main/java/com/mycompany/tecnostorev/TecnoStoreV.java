/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tecnostorev;

import com.mycompany.persistencia.ConexionData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class TecnoStoreV {

    public static void main(String[] args) {

        try (Scanner teclado = new Scanner(System.in)) {

            System.out.print("Usuario de MySQL: ");
            String usuario = teclado.nextLine();

            System.out.print("Contraseña de MySQL: ");
            String clave = teclado.nextLine();


            try (Connection conexion = ConexionData.conectar(usuario, clave)) {

                System.out.println("Conexión exitosa");
                MenuCelulares.ejecutar(conexion, teclado);

            } catch (SQLException error) {

                System.out.println(
                    "No se pudo completar la operación: " + error.getMessage()
                );
            }
        }
    }
}
