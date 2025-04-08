package org.codexdei.java.jdbc.repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {

    //Se coloca para que permita la coneccion en la clase Catalogo de servicio
    void setConn(Connection conn);

    List<T> listar() throws SQLException;
    T buscarId(Long id) throws SQLException;
    T guardar (T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
