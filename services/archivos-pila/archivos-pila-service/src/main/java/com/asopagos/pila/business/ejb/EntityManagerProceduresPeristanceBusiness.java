package com.asopagos.pila.business.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.pila.business.interfaces.EntityManagerProceduresPeristanceLocal;
/**
 * <b>Descripcion:</b> Clase que implementa la interfaz para el EJB encargado de suministrar una 
 * instancia de EntityManagger para el modelo de datos de PILA a las clases de persistencia del 
 * contenido de archivo PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 y HU-211-407 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

@Stateless
public class EntityManagerProceduresPeristanceBusiness implements EntityManagerProceduresPeristanceLocal
{

    @PersistenceContext(unitName = "archivosPila_PU")
    private EntityManager entityManager;

    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.EntityManagerProceduresPeristanceLocal#getEntityManager()
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
