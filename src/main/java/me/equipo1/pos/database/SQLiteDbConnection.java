package me.equipo1.pos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDbConnection implements DbConnection {

    private Logger logger;
    private String connectionURI;

    public SQLiteDbConnection(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void connect(Map<String, Object> connectionParameters) throws SQLException {
        String uri = (String) connectionParameters.getOrDefault("uri", "jdbc:sqlite:");

        if (uri.equals("jdbc:sqlite:")) {
            uri += connectionParameters.getOrDefault("file-dir", ":memory:");
            logger.log(Level.WARNING, "No fue especificado un parametro de directorio para la base de datos, se utilizara base de datos en memoria!");
        }

        connectionURI = uri;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLiteStatements.CREATE_ITEMS_TABLE);
            statement.executeUpdate(SQLiteStatements.CREATE_INV_TABLE);

            logger.log(Level.INFO, "Generadas tablas de la base de datos.");
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(connectionURI);

        return connection;
    }
}
