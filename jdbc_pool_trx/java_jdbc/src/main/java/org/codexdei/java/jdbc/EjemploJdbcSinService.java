package org.codexdei.java.jdbc;

import org.codexdei.java.jdbc.modelo.Categoria;
import org.codexdei.java.jdbc.modelo.Producto;
import org.codexdei.java.jdbc.repositorio.CategoriaRepositorioImpl;
import org.codexdei.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.codexdei.java.jdbc.repositorio.Repositorio;
import org.codexdei.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Date;

public class EjemploJdbcSinService {

    public static void main(String[] args) throws SQLException {

            try(Connection conn = ConexionBaseDatos.getConnection()){

             if (conn.getAutoCommit()){

                     conn.setAutoCommit(false);
             }

            try{

                    Repositorio<Categoria> categoriaRepositorio = new CategoriaRepositorioImpl(conn);
                    System.out.println("================ INSERTAR CATEGORIA =============");
                    Categoria categoria = new Categoria();
                    categoria.setNombreCategoria("Electrohogar");
                    Categoria nuevaCategoria = categoriaRepositorio.guardar(categoria);
                    System.out.println("Categoria guardada con éxito:" + nuevaCategoria.getIdCategoria());


                    Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                    System.out.println("============= listar =============");
                    repositorio.listar().forEach(System.out::println);

                    System.out.println("============= obtener por id =============");
                    System.out.println(repositorio.buscarId(1L));

                    System.out.println("============= insertar nuevo producto =============");
                    Producto producto = new Producto();
                    producto.setNombre("Refrigerador Samsung");
                    producto.setPrecio(9900);
                    producto.setFechaRegistro(new Date());
                    producto.setSku("abc1234567");

                    producto.setCategoria(nuevaCategoria);
                    repositorio.guardar(producto);
                    System.out.println("Producto guardado con éxito:" + producto.getId());
                    repositorio.listar().forEach(System.out::println);

                    conn.commit();

            }catch (SQLException e){

                    conn.rollback();
                    e.printStackTrace();
            }

            }

    }
}
