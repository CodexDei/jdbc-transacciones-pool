package org.codexdei.java.jdbc.repositorio;

import org.codexdei.java.jdbc.modelo.Categoria;
import org.codexdei.java.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto>{

    //Implementamos una clase privada para la conexion a la base de datos
    private Connection conn;

    public ProductoRepositorioImpl(Connection conn){

        this.conn = conn;
    }

    public ProductoRepositorioImpl() {

    }


    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
            "SELECT p.*, c.nombre_categoria as categoria FROM productos as p " +
                "INNER JOIN categorias as c ON (p.categoria_id = c.idcategorias)")){

            while (rs.next()){

                Producto p = crearProducto(rs);

                productos.add(p);
            }

        }

        return productos;
    }


    @Override
    public Producto buscarId(Long id) throws SQLException {

        Producto producto = null;

        try(PreparedStatement psts = this.conn.prepareStatement(
            "SELECT p.*, c.nombre_categoria as categoria FROM productos as p " +
                "INNER JOIN categorias as c ON (p.categoria_id = c.idcategorias) WHERE idproductos = ?")
            ){

            psts.setLong(1,id);
            ResultSet rst = psts.executeQuery();

            if(rst.next()){

                producto = crearProducto(rst);

            }
            rst.close();
        }

        return producto;
    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {

        String sql;
        if (producto.getId() != null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=?, sku=? WHERE idproductos=?";
        } else {
            sql = "INSERT INTO productos(nombre, precio, categoria_id, sku, fecha_registro) VALUES(?,?,?,?,?)";
        }
       try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3,producto.getCategoria().getIdCategoria());
            stmt.setString(4,producto.getSku());

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(5, producto.getId());
            } else {
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }

            stmt.executeUpdate();

            if (producto.getId() == null){

                try(ResultSet rs = stmt.getGeneratedKeys()){

                    if (rs.next()){
                        producto.setId(rs.getLong(1));
                    }
                }
            }
        }
       return producto;

    }

    @Override
    public void eliminar(Long id) throws SQLException {

       try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE idproductos=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("idproductos"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        p.setSku(rs.getString("sku"));
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("categoria_id"));
        c.setNombreCategoria(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
