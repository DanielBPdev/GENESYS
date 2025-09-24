package com.asopagos.novedades.business.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.novedades.business.interfaces.EntityManagerProceduresPersistenceLocal;

@Stateless
public class EntityManagerProceduresPersistenceBusiness implements EntityManagerProceduresPersistenceLocal
{

    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;

    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.EntityManagerProceduresPersistenceLocal#getEntityManager()
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
