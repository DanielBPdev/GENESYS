package com.asopagos.lion.config;

import java.util.Date;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import co.com.heinsohn.lion.common.exception.PropertiesConfigurationException;
import co.com.heinsohn.lion.common.factories.LionConfigurationFactory;

public class LionConfigurationAsopagosImpl extends LionConfigurationFactory {

    /**
     * Contiene la información de los archivos de propiedades que contiene la
     * configuración de los componentes de LionFramework.
     */
    private static Configuration configuration;

    /**
     * Contiene la información de los archivos de propiedades que contiene la
     * configuración de los componentes de LionFramework.
     */
    private static ConfigurationFactory configFactory;

    /**
     * Fecha que indica la última recarga de propiedades
     */
    private static Date lastLoadProperties;

    /**
     * Referencia al logger
     */
    //	private final ILogger logger = LogManager.getLogger(EmpleadoresBusiness.class);

    @Override
    /**
     * Método encargado de obtener el archivo de de configuración de tipo xml
     * que contiene las rutas de los archivo de propiedades que a su vez
     * contiene todas las llaves o propiedades para la configuración y manejo de
     * los componentes de Lion Framework.
     * 
     * @return Contiene la información de todos los archivos de configuración o
     *         de propiedades.
     */
    public Configuration getConfiguration() {
        //		logger.debug("Inicio-getConfiguration");

        if (configuration != null) {
            return configuration;
        }
        if (configFactory == null) {
            configFactory = new ConfigurationFactory("config.xml");
        }
        //		logger.debug("Fin-getConfiguration");
        try {
            configuration = configFactory.getConfiguration();
            lastLoadProperties = new Date();
            return configuration;
        } catch (ConfigurationException e) {
            throw new PropertiesConfigurationException(" Error leyendo la configiración del manejo de propiedades,"
                    + " verifique que si archivo config.xml se encuentre en la ruta que se indica en el parámetro VM de nombre Dlion.configFile,"
                    + " y que este bien configurado," + "  Error: " + e.getMessage(), e);
        }
    }
}
