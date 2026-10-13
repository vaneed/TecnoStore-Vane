/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Gama;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CelularDAO {
    private final Connection conexion;

    public CelularDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void registrar(Celular celular) throws SQLException {

        String sql = """
            INSERT INTO celulares
            (marca, modelo, sistema_operativo, gama, precio, stock)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement sentencia =
                conexion.prepareStatement(sql)) {

            sentencia.setString(1, celular.getMarca());
            sentencia.setString(2, celular.getModelo());
            sentencia.setString(3, celular.getSistemaOperativo());
            sentencia.setString(4, convertirGama(celular.getGama()));
            sentencia.setBigDecimal(5, celular.getPrecio());
            sentencia.setInt(6, celular.getStock());

            sentencia.executeUpdate();
        }
    }

    public List<Celular> listar() throws SQLException {

        List<Celular> celulares = new ArrayList<>();

        String sql = "SELECT * FROM celulares ORDER BY id";

        try (
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Gama gama = Gama.valueOf(
                    resultado.getString("gama").toUpperCase(Locale.ROOT)
                );

                Celular celular = new Celular(
                    resultado.getInt("id"),
                    resultado.getString("marca"),
                    resultado.getString("modelo"),
                    resultado.getString("sistema_operativo"),
                    gama,
                    resultado.getBigDecimal("precio"),
                    resultado.getInt("stock")
                );

                celulares.add(celular);
            }
        }

        return celulares;
    }

    private String convertirGama(Gama gama) {
        switch (gama) {
            case ALTA:
                return "Alta";
            case MEDIA:
                return "Media";
            case BAJA:
                return "Baja";
            default:
                throw new IllegalArgumentException("Gama no válida.");
        }
    }
}
