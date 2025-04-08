package org.codexdei.java.jdbc.service;

import org.codexdei.java.jdbc.modelo.Categoria;
import org.codexdei.java.jdbc.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

public interface Service{

    List<Producto> listarProducto() throws SQLException;
    Producto buscarIdProducto(Long id) throws SQLException;
    Producto guardarProducto(Producto producto) throws  SQLException;
    void eliminarProducto(Long id) throws SQLException;

    List<Categoria> listarCategoria() throws SQLException;
    Categoria buscarIdCategoria(Long id) throws SQLException;
    Categoria guardarCategoria(Categoria categoria) throws  SQLException;
    void eliminarCategoria(Long id) throws SQLException;

    void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException;

}
