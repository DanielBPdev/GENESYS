package com.asopagos.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.asopagos.locator.ResourceLocator;

/**
 * Clase encargada de la gestion de los recursos de base de datos de la aplicacion
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class DatabaseUtil {

    /**
     * @return
     * @throws Exception 
     */
    public static Connection getCoreConnection() throws Exception {
        return ResourceLocator.getCoreDataSource().getConnection();
    }

    /**
     * @param conn
     * @throws SQLException
     */
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
    public static PreparedStatement getStatement(Connection conn, String statement) throws SQLException {
        if (statement != null && !statement.isEmpty()) {
            return conn.prepareStatement(statement);
        }
        return null;
    }
    
    public static void closeStatement(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }
}
