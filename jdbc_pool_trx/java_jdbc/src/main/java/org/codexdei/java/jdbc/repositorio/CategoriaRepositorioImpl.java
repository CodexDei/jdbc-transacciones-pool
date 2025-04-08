package org.codexdei.java.jdbc.repositorio;

import org.codexdei.java.jdbc.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl implements Repositorio<Categoria> {

    private Connection conn;

    public CategoriaRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl() {

    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {

        List<Categoria> categoriaList = new ArrayList<>();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")
            ){

            while (rs.next()){
                categoriaList.add(crearCategoria(rs));
            }
        }
        return categoriaList;
    }

    @Override
    public Categoria buscarId(Long id) throws SQLException {

        if (id == null){

            return null;
        }

        String sql = "SELECT * FROM categorias as c WHERE c.id=?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1,id);

            try(ResultSet rs = stmt.executeQuery()){

                    return  rs.next() ? crearCategoria(rs) : null;
            }
        }

    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {

        String sql = null;

        if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0){

            sql = "UPDATE categorias SET nombre=? WHERE idcategorias=?";

        }else {
            sql = "INSERT INTO categorias (nombre_categoria) VALUES (?)";
        }

        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, categoria.getNombreCategoria());

            //Para UPDATE, actualizar el nombre de alguna categoria
            if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0){

                stmt.setLong(2,categoria.getIdCategoria());
            }

            stmt.executeUpdate();

            //INSERT
            if (categoria.getIdCategoria() == null){

                try(ResultSet rs = stmt.getGeneratedKeys()){

                    if (rs.next()){
                        categoria.setIdCategoria(rs.getLong(1));
                    }
                }
            }
        }

        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {

        String sql = "DELETE FROM categorias WHERE idcategorias=?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1,id);
            stmt.executeUpdate()
            ;
        }
    }

    private static Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategorias"));
        c.setNombreCategoria(rs.getString("nombre_categoria"));
        return c;
    }
}
