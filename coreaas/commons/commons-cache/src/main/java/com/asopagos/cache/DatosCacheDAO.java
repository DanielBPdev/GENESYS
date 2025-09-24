package com.asopagos.cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;  
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
   
/**
 * @author sbrinez
 */
public class DatosCacheDAO {
    
     
    private static final ILogger log = LogManager.getLogger(DatosCacheDAO.class);
    
    private static DataSource datasource;
    
    private static final String QUERY_PARAMETROS = "select prmNombre, prmValor from Parametro";
    
    private static final String QUERY_CONSTANTES = "select cnsNombre, cnsValor from Constante";
    
    private static final String QUERY_PARAMETRO = "select prmValor from Parametro where prmNombre = ?";
    
    private static final String QUERY_CONSTANTE = "select cnsValor from Constante where cnsNombre = ? ";

    private static final String QUERY_PARAMETRO_GAP = "select prgEstado from ParametrizacionGaps where prgNombre = ? ";

    
    static {
        try {
            datasource = ResourceLocator.getCoreDataSource();
        } catch (Exception mre) {
            log.error("No se puede obtener una referencia al datasource");
            throw new RuntimeException(mre);
        }
    }    

    /**
     * Consulta el listado de parametros devolviendo un mapa con los datos 
     * obtenidos.
     *
     * @return Map<String, String> con los datos obtenidos.
     */
    public static Map<String, String> obtenerDatosParametros() {
        return obtenerDatosCache(QUERY_PARAMETROS);
    }  

    /**
     * Consulta el listado de parametros, devolviendo un mapa con los datos 
     * obtenidos.
     *
     * @return Map<String, String> con los datos obtenidos.
     */
    public static Map<String, String> obtenerDatosConstantes() {
        return obtenerDatosCache(QUERY_CONSTANTES);
    }  

    /**
     * Consulta el listado de parametros devolviendo un mapa con los datos 
     * obtenidos.
     * 
     * @param parametro
     * @return Map<String, String> con los datos obtenidos.
     */
    public static String obtenerParametro(String parametro) {
        return obtenerDatoCache(QUERY_PARAMETRO, parametro);
    }  

    /**
     * Consulta el listado de parametros, devolviendo un mapa con los datos 
     * obtenidos.
     *
     * @return Map<String, String> con los datos obtenidos.
     */
    public static Map<String, String> obtenerDatosParametrosGap() {
        return obtenerDatosCache(QUERY_PARAMETRO_GAP);
    }

    /**
     * Consulta el listado de parametro Gap devolviendo un mapa con los datos 
     * obtenidos.
     *
     * @param parametro
     * @return Map<String, String> con los datos obtenidos.
     */
    public static String obtenerParametroGap(String parametro) {
        return obtenerDatoCache(QUERY_PARAMETRO_GAP, parametro);
    } 

    /**
     * Consulta el listado de parametros, devolviendo un mapa con los datos 
     * obtenidos.
     *
     * @param constante
     * @return Map<String, String> con los datos obtenidos.
     */
    public static String obtenerConstante(String constante) {
        return obtenerDatoCache(QUERY_CONSTANTE, constante);
    }  
    
    /**
     * Ejecuta el query especificado para Constantes y Parametros
     * obtenidos.
     *
     * @param query
     * @return Map<String, String> con los datos obtenidos.
     */
    private static Map<String, String> obtenerDatosCache(String query) {
        
        Map<String, String> datos;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //Se obtiene una conexión a la base de datos
            conn = getDatabaseCoreConnection();

            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int i;
            datos = new HashMap();
            while (rs.next()) {
                i = 0;
                datos.put(rs.getString(++i), rs.getString(++i));
            }
            return datos;
        } catch (Exception e) {
            log.error(e);
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                log.error("No se pudo cerrar la conexión a la base de datos");
                throw new RuntimeException(ex);
            }
        }
    } 
    
    /**
     * Ejecuta el query especificado para Constantes y Parametros
     * obtenidos.
     *
     * @param query
     * @return Map<String, String> con los datos obtenidos.
     */
    private static String obtenerDatoCache(String query, String valorFiltro) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //Se obtiene una conexión a la base de datos
            conn = getDatabaseCoreConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, valorFiltro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                log.error("No se pudo cerrar la conexión a la base de datos");
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
    
    static Connection getDatabaseCoreConnection() {
        try {
            return datasource.getConnection();
        } catch (SQLException ex) {
            log.error("No se puede obtener una conexión a la BBDD Core");
            throw new RuntimeException(ex);
        }
    }    
     
}
