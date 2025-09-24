package com.asopagos.subsidiomonetario.business.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.subsidiomonetario.business.interfaces.EntityManagerSubsidioPersistenceLocal;

/**
 * <b>Descripcion:</b> Clase que implementa la interfaz para el EJB encargado de suministrar una 
 * instancia de EntityManagger para el modelo de datos de Subsidio a las clases de persistencia del 
 * contenido de archivo de Entidades de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU-311-432 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Stateless
public class EntityManagerSubsidioPersistenceBusiness implements EntityManagerSubsidioPersistenceLocal {
    
    /**
     * Referencia a la unidad de persistenica de Subsidio
     */
    @PersistenceContext(unitName = "subsidio_PU")
    private EntityManager entityManager;
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.EntityManagerSubsidioPersistenceLocal#getEntityManager()
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
