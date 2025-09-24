package com.asopagos.archivos.almacenamiento;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.persistence.EntityManager;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Factory para la implementación de almacenamiento de archivos
 * 
 * @author sbrinez
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
public class AlmacenamientoArchivosFactory {
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(AlmacenamientoArchivosFactory.class);
    
    /**
     * Referencia al Context
     */
    private static InitialContext context;
    
    /**
     * Crea la instancioa de IAlmacenamientoArchivos de acuerdo a la parametrización establecida
     * @param entityManager
     * @return 
     */
    public static IAlmacenamientoArchivos obtenerAlmacenamientoArchivos(EntityManager entityManager) {
        String almacenamientoArchivosEnum = (String) CacheManager.getParametro(ParametrosSistemaConstants.SISTEMA_ALMACENAMIENTO_ARCHIVOS);
        AlmacenamientoArchivosEnum almacenamientoEnum = AlmacenamientoArchivosEnum.valueOf(almacenamientoArchivosEnum);
        IAlmacenamientoArchivos almacenamientoArchivos = null;
        InitialContext jndiContext = null;
        try {
        	context = new InitialContext();
        	almacenamientoArchivos = (IAlmacenamientoArchivos) context.lookup(almacenamientoEnum.getImplClass());
        	return almacenamientoArchivos;
            /*return (IAlmacenamientoArchivos) Class.forName(almacenamientoEnum.getImplClass())
                    .getConstructor(EntityManager.class).newInstance(entityManager);*/
        } catch (Exception ex) {
            String errorMsg = "No se pudo construir la implementación de almacenamiento de archivos parametrizada";
            logger.error(errorMsg, ex);
            throw new TechnicalException(errorMsg, ex);
        }
    }
    
}
