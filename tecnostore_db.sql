CREATE DATABASE IF NOT EXISTS tecnostore_db
CHARACTER SET utf8mb4;

USE tecnostore_db;

CREATE TABLE IF NOT EXISTS celulares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    sistema_operativo VARCHAR(50) NOT NULL,
    gama ENUM('Alta', 'Media', 'Baja') NOT NULL,
    precio DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL,
    CHECK (precio > 0),
    CHECK (stock >= 0)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(14, 2) NOT NULL,
    CHECK (total >= 0),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(14, 2) NOT NULL,
    CHECK (cantidad > 0),
    CHECK (subtotal > 0),
    FOREIGN KEY (id_venta) REFERENCES ventas(id),
    FOREIGN KEY (id_celular) REFERENCES celulares(id)
) ENGINE = InnoDB;
