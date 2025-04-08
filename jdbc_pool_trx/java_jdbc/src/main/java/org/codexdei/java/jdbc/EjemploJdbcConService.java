package org.codexdei.java.jdbc;

import org.codexdei.java.jdbc.modelo.Categoria;
import org.codexdei.java.jdbc.modelo.Producto;
import org.codexdei.java.jdbc.service.CatalogoServicio;
import org.codexdei.java.jdbc.service.Service;

import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcConService {

    public static void main(String[] args) throws SQLException {

        Service service = new CatalogoServicio();

        System.out.println("============= Listar =============");
        service.listarProducto().forEach(System.out::println);

        System.out.println("================ INSERTAR NUEVA CATEGORIA =============");
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Iluminacion");
        System.out.println("categoria. = " + categoria.getNombreCategoria());

        System.out.println("============= obtener por id =============");
        System.out.println(service.buscarIdProducto(1L));

        System.out.println("============= Insertar Nuevo Producto =============");
        Producto producto = new Producto();
        producto.setNombre("Lampara Led Escritorio");
        producto.setPrecio(870);
        producto.setFechaRegistro(new Date());
        producto.setSku("abcdef1234");
        service.guardarProductoConCategoria(producto,categoria);

        System.out.println("Producto guardado con éxito:" + producto.getId());
        service.listarProducto().forEach(System.out::println);

    }
}
