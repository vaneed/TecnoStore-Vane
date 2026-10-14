/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import com.mycompany.modelo.ItemVenta;
import com.mycompany.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Cliente;
import com.mycompany.modelo.Gama;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VentaDAO {

    private final Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardar(Venta venta) throws SQLException {

        if (venta.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                "La venta debe tener al menos un celular."
            );
        }

        if (!conexion.getAutoCommit()) {
            throw new SQLException(
                "Ya hay una transacción activa en esta conexión."
            );
        }

        conexion.setAutoCommit(false);

        try {
            descontarStock(venta);

            int idVenta = guardarEncabezado(venta);
            guardarDetalles(venta, idVenta);

            conexion.commit();
            venta.setId(idVenta);

        } catch (SQLException | RuntimeException error) {

            try {
                conexion.rollback();
            } catch (SQLException errorReversion) {
                error.addSuppressed(errorReversion);
            }

            throw error;

        } finally {
            conexion.setAutoCommit(true);
        }
    }

    private void descontarStock(Venta venta) throws SQLException {

        String sql = """
            UPDATE celulares
            SET stock = stock - ?
            WHERE id = ? AND stock >= ? AND precio = ?
            """;

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            for (ItemVenta item : venta.getItems()) {

                sentencia.setInt(1, item.getCantidad());
                sentencia.setInt(2, item.getCelular().getId());
                sentencia.setInt(3, item.getCantidad());
                sentencia.setBigDecimal(4, item.getPrecioUnitario());

                int filas = sentencia.executeUpdate();

                if (filas != 1) {
                    throw new SQLException(
                        "No se pudo vender "
                        + item.getCelular().getModelo()
                        + ": cambió el precio, falta stock "
                        + "o el celular ya no existe. "
                        + "Vuelve a iniciar la venta."
                    );
                }
            }
        }
    }

    private int guardarEncabezado(Venta venta) throws SQLException {

        String sql = """
            INSERT INTO ventas (id_cliente, fecha, total)
            VALUES (?, ?, ?)
            """;

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setInt(1, venta.getCliente().getId());
            sentencia.setTimestamp(
                2, Timestamp.valueOf(venta.getFecha())
            );
            sentencia.setBigDecimal(3, venta.calcularTotal());

            sentencia.executeUpdate();

            try (ResultSet resultado = sentencia.getGeneratedKeys()) {

                if (resultado.next()) {
                    return resultado.getInt(1);
                }

                throw new SQLException(
                    "No se pudo obtener el ID de la venta."
                );
            }
        }
    }

    private void guardarDetalles(Venta venta, int idVenta)
            throws SQLException {

        String sql = """
            INSERT INTO detalle_ventas
            (id_venta, id_celular, cantidad, subtotal)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            for (ItemVenta item : venta.getItems()) {

                sentencia.setInt(1, idVenta);
                sentencia.setInt(2, item.getCelular().getId());
                sentencia.setInt(3, item.getCantidad());
                sentencia.setBigDecimal(4, item.calcularSubtotal());

                sentencia.executeUpdate();
            }
        }
    }
    
    public List<Venta> listar() throws SQLException {

    Map<Integer, Venta> ventas = new LinkedHashMap<>();

    String sql = """
        SELECT
            v.id AS venta_id, v.fecha, v.total,
            c.id AS cliente_id, c.nombre, c.identificacion,
            c.correo, c.telefono,
            d.id AS detalle_id, d.cantidad, d.subtotal,
            ce.id AS celular_id, ce.marca, ce.modelo,
            ce.sistema_operativo, ce.gama, ce.precio, ce.stock
        FROM ventas v
        INNER JOIN clientes c ON v.id_cliente = c.id
        LEFT JOIN detalle_ventas d ON d.id_venta = v.id
        LEFT JOIN celulares ce ON d.id_celular = ce.id
        ORDER BY v.fecha, v.id, d.id
        """;

    try (
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery()
    ) {

        while (resultado.next()) {

            int idVenta = resultado.getInt("venta_id");
            Venta venta = ventas.get(idVenta);

            if (venta == null) {

                Cliente cliente = new Cliente(
                    resultado.getInt("cliente_id"),
                    resultado.getString("nombre"),
                    resultado.getString("identificacion"),
                    resultado.getString("correo"),
                    resultado.getString("telefono")
                );

                venta = new Venta(
                    idVenta,
                    cliente,
                    resultado.getTimestamp("fecha").toLocalDateTime(),
                    resultado.getBigDecimal("total")
                );

                ventas.put(idVenta, venta);
            }

            if (resultado.getObject("detalle_id") != null) {

                Celular celular = new Celular(
                    resultado.getInt("celular_id"),
                    resultado.getString("marca"),
                    resultado.getString("modelo"),
                    resultado.getString("sistema_operativo"),
                    Gama.valueOf(
                        resultado.getString("gama")
                            .toUpperCase(Locale.ROOT)
                    ),
                    resultado.getBigDecimal("precio"),
                    resultado.getInt("stock")
                );

                ItemVenta item = new ItemVenta(
                    celular,
                    resultado.getInt("cantidad"),
                    resultado.getBigDecimal("subtotal")
                );

                venta.agregarItem(item);
            }
        }
    }

    return new ArrayList<>(ventas.values());
    }
}