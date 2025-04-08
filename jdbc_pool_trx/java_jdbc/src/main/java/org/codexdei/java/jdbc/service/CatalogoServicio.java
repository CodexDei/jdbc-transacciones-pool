package org.codexdei.java.jdbc.service;

import org.codexdei.java.jdbc.modelo.Categoria;
import org.codexdei.java.jdbc.modelo.Producto;
import org.codexdei.java.jdbc.repositorio.CategoriaRepositorioImpl;
import org.codexdei.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.codexdei.java.jdbc.repositorio.Repositorio;
import org.codexdei.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServicio implements Service{

    private Repositorio<Producto> productoRepositorio;
    private Repositorio<Categoria> categoriaRepositorio;

    public CatalogoServicio(){

        productoRepositorio = new ProductoRepositorioImpl();
        categoriaRepositorio = new CategoriaRepositorioImpl();
    }

    @Override
    public List<Producto> listarProducto() throws SQLException {

        try(Connection conn = ConexionBaseDatos.getConnection()){
            productoRepositorio.setConn(conn);

            return  productoRepositorio.listar();
        }
    }

    @Override
    public Producto buscarIdProducto(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            productoRepositorio.setConn(conn);

            return productoRepositorio.buscarId(id);

        }
    }

    @Override
    public Producto guardarProducto(Producto producto) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            productoRepositorio.setConn(conn);

            if (conn.getAutoCommit()){

                conn.setAutoCommit(false);
            }
            Producto nuevoProducto = null;

            try{

                nuevoProducto = productoRepositorio.guardar(producto);
                conn.commit();

            }catch (SQLException e){

                conn.rollback();
                e.printStackTrace();
            }
            return nuevoProducto;
        }
    }

    @Override
    public void eliminarProducto(Long id) throws SQLException {

        try(Connection conn = ConexionBaseDatos.getConnection()){
            productoRepositorio.setConn(conn);

            if (conn.getAutoCommit()){

                conn.setAutoCommit(false);
            }
            try{
                productoRepositorio.eliminar(id);
                conn.commit();

            }catch (SQLException e){

                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Categoria> listarCategoria() throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConn(conn);
            return categoriaRepositorio.listar();
        }
    }

    @Override
    public Categoria buscarIdCategoria(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConn(conn);
            return categoriaRepositorio.buscarId(id);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()){

                conn.setAutoCommit(false);
            }
            Categoria nuevaCategoria = null;

            try{
                nuevaCategoria = categoriaRepositorio.guardar(categoria);
                conn.commit();

            }catch (SQLException e){

                conn.rollback();
                e.printStackTrace();
            }
            return nuevaCategoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()){

                conn.setAutoCommit(false);
            }
            try{
                categoriaRepositorio.eliminar(id);
                conn.commit();

            }catch (SQLException e){

                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()){
            productoRepositorio.setConn(conn);
            categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()){

                conn.setAutoCommit(false);
            }
            try{
                Categoria nuevaCategoria = categoriaRepositorio.guardar(categoria);
                producto.setCategoria(nuevaCategoria);
                productoRepositorio.guardar(producto);
                conn.commit();

            }catch (SQLException e){

                conn.rollback();
                e.printStackTrace();
            }
        }

    }
}
