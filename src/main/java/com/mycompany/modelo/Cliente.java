/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;


public class Cliente {
    private final int id;
    private final String nombre;
    private final String identificacion;
    private final String correo;
    private final String telefono;

    public Cliente(int id, String nombre, String identificacion,
                   String correo, String telefono) {

        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return id + " | " + nombre
                + " | Identificación: " + identificacion
                + " | Correo: " + correo
                + " | Teléfono: " + telefono;
    }
}
