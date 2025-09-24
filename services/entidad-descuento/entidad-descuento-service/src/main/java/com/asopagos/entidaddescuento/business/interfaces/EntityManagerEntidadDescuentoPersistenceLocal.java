package com.asopagos.entidaddescuento.business.interfaces;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * <b>Descripcion:</b> Interface para el EJB encargado de suministrar una instancia de EntityManagger
 * para el modelo de datos de Subsidio a las clases de persistencia del contenido de archivo de Entidades de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU-311-432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Local
public interface EntityManagerEntidadDescuentoPersistenceLocal {

    /**
     * Método encargado de retornar el EntityManager para el modelo de datos Subsidio
     * @return <b>EntityManager</b>
     *         Instancia de EntityManager para el modelo de datos Subsidio
     */
    public EntityManager getEntityManager();
}
