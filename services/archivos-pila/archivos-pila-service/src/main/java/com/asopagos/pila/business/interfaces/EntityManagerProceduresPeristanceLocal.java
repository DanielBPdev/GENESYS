package com.asopagos.pila.business.interfaces;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * <b>Descripcion:</b> Interface para el EJB encargado de suministrar una instancia de EntityManagger
 * para el modelo de datos de PILA a las clases de persistencia del contenido de archivo PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 y HU-211-407 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface EntityManagerProceduresPeristanceLocal {

    /**
     * Método encargado de retornar el EntityManager para el modelo de datos PILA
     * @return <b>EntityManager</b>
     *         Instancia de EntityManager para el modelo de datos PILA
     */
    public EntityManager getEntityManager();
}
