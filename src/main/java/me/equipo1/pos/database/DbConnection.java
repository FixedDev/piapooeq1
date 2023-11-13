package me.equipo1.pos.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface DbConnection {

    void connect(Map<String, Object> connectionParameters) throws SQLException;

    Connection getConnection() throws SQLException;
}
