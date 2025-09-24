package com.asopagos.cache.impl.infinispan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ServicioCacheLoader implements CacheLoader<String, String> {
	
	private static final String DATABASE = "database";
    private static final String DB_URL = "db.url";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_USERNAME = "db.username";
    private static final ILogger log = LogManager.getLogger(ServicioCacheLoader.class);
	
	@Override
	public String load(String key) {
		String resultado = null;
		String query = "SELECT prmNombre, prmValor "
				+ "FROM Parametro where prmNombre=?";
		SQLServerDataSource dataSource = getSqlServerDataSource();
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(query)) {
			pStatement.setString(1, key);
			log.debug("Ejecutando query: "+ query);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				log.debug(
						resultSet.getString(1) + ", " + resultSet.getString(2));
				resultado = resultSet.getString(2);
				log.info("key:"+  key +" value:"+ resultado);
			}
		} catch (SQLException e) {
		    log.error("Error consultando parametro con el nombre:"+ key, e);
			throw new CacheLoaderException(e);
		} 
		return resultado;
	}

	@Override
	public Map<String, String> loadAll(Iterable<? extends String> keys) {
		Map<String, String> constantes = new HashMap<>();
		for (String key : keys) {
            constantes.put(key, load(key));
        }		
		return constantes;
	}
	
	private SQLServerDataSource getSqlServerDataSource() {
	    SQLServerDataSource dataSource = new SQLServerDataSource();
        try {
            ResourceBundle rb = ResourceBundle.getBundle(DATABASE);
            dataSource.setUser(rb.getString(DB_USERNAME));
            dataSource.setPassword(rb.getString(DB_PASSWORD));
            dataSource.setURL(rb.getString(DB_URL));
        } catch (MissingResourceException e) {
            dataSource.setUser(System.getProperty(DB_USERNAME, System.getenv(DB_USERNAME)));
            dataSource.setPassword(System.getProperty(DB_PASSWORD, System.getenv(DB_PASSWORD)));
            dataSource.setURL(System.getProperty(DB_URL, System.getenv(DB_URL)));
        }
		return dataSource;
	}
}
