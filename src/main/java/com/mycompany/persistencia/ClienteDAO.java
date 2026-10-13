/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import com.mycompany.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Connection conexion;

    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void registrar(Cliente cliente) throws SQLException {

        String sql = """
            INSERT INTO clientes
            (nombre, identificacion, correo, telefono)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getIdentificacion());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.setString(4, cliente.getTelefono());

            sentencia.executeUpdate();
        }
    }

    public List<Cliente> listar() throws SQLException {

        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id";

        try (
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Cliente cliente = new Cliente(
                    resultado.getInt("id"),
                    resultado.getString("nombre"),
                    resultado.getString("identificacion"),
                    resultado.getString("correo"),
                    resultado.getString("telefono")
                );

                clientes.add(cliente);
            }
        }

        return clientes;
    }
}
