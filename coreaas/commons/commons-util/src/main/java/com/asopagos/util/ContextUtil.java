package com.asopagos.util;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Clase encargada de la gestion del contexto de la aplicacion
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ContextUtil {

    /**
     * Agrega el valor al contexto
     * @param clase
     *        Clase
     * @param objeto
     *        Instancia
     */
    public static <T> void addValueContext(Class<T> clase, T objeto) {
        ResteasyProviderFactory.pushContext(clase, objeto);
    }
}
