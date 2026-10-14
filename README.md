# TecnoStore 📲

Sistema de consola desarrollado en Java para gestionar celulares,
clientes, ventas e inventario. Los datos se guardan en MySQL mediante JDBC.

## Requisitos

- JDK 17.
- Apache NetBeans IDE 18.
- MySQL Server 8.0.16 o superior.
- Conexión a Internet para descargar las dependencias de Maven.

## Estructura

- modelo: clases Celular, Cliente, Venta, ItemVenta y enum Gama.
- persistencia: ConexionData y clases DAO para acceder a MySQL.
- servicio: reglas de negocio y coordinación de operaciones.
- patron: EstrategiaImpuesto e Iva19, implementación de Strategy.
- utilidades: ReporteUtils y ArchivoUtils.
- tecnostorev: clase principal TecnoStoreV y menús de consola.

## Capturas 📸
<img width="1774" height="494" alt="Captura desde 2026-10-14 12-25-03" src="https://github.com/user-attachments/assets/e4c8f98e-3827-4f14-a916-56de3fda0f9a" />
<img width="1727" height="927" alt="image" src="https://github.com/user-attachments/assets/fdb2578b-54f4-4ced-ab53-50fdd603a02c" />



## Preparar la base de datos

Desde una terminal ubicada en la carpeta del proyecto:

```bash
mysql -u TU_USUARIO -p < tecnostore_db.sql
```

Reemplazar TU_USUARIO por un usuario de MySQL con permisos para
crear la base de datos y las tablas.

La conexión está configurada en ConexionData.java:

```text
jdbc:mysql://localhost:3306/tecnostore_db
```

El programa solicita el usuario y la contraseña al iniciar.
Las credenciales no deben escribirse en el código ni subirse a GitHub.

Para el funcionamiento de la aplicación, el usuario necesita permisos
de consulta, inserción, actualización y eliminación en tecnostore_db.

## Ejecutar en NetBeans

1. Iniciar MySQL.
2. Abrir NetBeans y seleccionar File > Open Project.
3. Elegir la carpeta que contiene pom.xml.
4. Ejecutar Build para descargar dependencias y compilar.
5. Abrir TecnoStoreV.java y seleccionar Run File.
6. Introducir el usuario y la contraseña de MySQL.
7. Escribir el número de una opción y presionar Enter.

La contraseña introducida con Scanner es visible en la consola.
No incluirla en las capturas.

## Menú principal

1. Registrar celular.
2. Listar celulares.
3. Actualizar celular.
4. Eliminar celular.
5. Gestionar clientes.
6. Registrar venta.
7. Ver reportes y generar archivo.
0. Salir.

## Ejemplo de venta

Para un celular con precio de 100000.00 y una cantidad de 2:

- Subtotal: 200000.00
- IVA del 19 %: 38000.00
- Total: 238000.00

Al confirmar la venta, el inventario disminuye en 2 unidades.
Si falla una operación durante el registro, se revierten los cambios
de la transacción.

## Reporte de ventas

La opción 7 muestra los reportes y genera reporte_ventas.txt
en el directorio de ejecución. La consola indica su ruta completa.

Cada ejecución de esta opción reemplaza el archivo con los datos actuales.
El archivo incluye los reportes y el resumen de todas las ventas.

## AUTOR 👑

Vanessa Diaz 
