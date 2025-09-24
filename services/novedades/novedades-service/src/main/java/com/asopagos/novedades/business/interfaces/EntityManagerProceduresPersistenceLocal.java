package com.asopagos.novedades.business.interfaces;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * <b>Descripcion:</b> Interface para el EJB encargado de suministrar una instancia de EntityManagger
 * para cargue de <br/>
 *
 * @author <a href="mailto:juan.quintero@asopagos.com"> Alfonso Baquero E.</a>
 */
@Local
public interface EntityManagerProceduresPersistenceLocal {

    /**
     * Método encargado de retornar el EntityManager para el modelo de datos PILA
     * @return <b>EntityManager</b>
     *         Instancia de EntityManager para el modelo de datos PILA
     */
    public EntityManager getEntityManager();
}
