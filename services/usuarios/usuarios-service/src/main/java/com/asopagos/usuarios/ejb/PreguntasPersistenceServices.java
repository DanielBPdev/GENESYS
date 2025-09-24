package com.asopagos.usuarios.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.entidades.seguridad.Pregunta;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.usuarios.constants.NamedQueriesConstants;
import com.asopagos.usuarios.service.IPreguntasPersistenceServices;

@Stateless
public class PreguntasPersistenceServices implements IPreguntasPersistenceServices {

    @PersistenceContext(unitName = "usuarios_PU")
    private EntityManager entityManager;

    @Override
    public Long persistirPregunta(String pregunta) {

        String preg = buscarPregunta(pregunta);
        if (preg == null) {
            Pregunta p = new Pregunta();
            p.setPregunta(pregunta);
            p.setEstado(EstadoActivoInactivoEnum.ACTIVO);
            entityManager.persist(pregunta);
            return p.getIdPregunta();
        }
        return null;
    }

    @Override
    public void actualizarPregunta(Pregunta pregunta) {
        entityManager.merge(pregunta);
    }

    @Override
    public void borrarPregunta(Long id) {
        Pregunta p = entityManager.find(Pregunta.class, id);
        if (p != null) {
            entityManager.remove(p);
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String buscarPregunta(Long id) {
        Pregunta p = entityManager.find(Pregunta.class, id);
        if (p != null) {
            return p.getPregunta();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.IPreguntasPersistenceServices#buscarPregunta(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String buscarPregunta(String pregunta) {
        try {
            Pregunta p = entityManager.createNamedQuery(NamedQueriesConstants.PREGUNTA_CONSULTAR_PREGUNTA, Pregunta.class)
                    .setParameter("pregunta", pregunta).getSingleResult();
            return p.getPregunta();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Pregunta> buscarPreguntasPorEstado(EstadoActivoInactivoEnum estado) {
        try {
            List<Pregunta> preguntas = entityManager
                    .createNamedQuery(NamedQueriesConstants.PREGUNTA_CONSULTAR_PREGUNTAS_ESTADO, Pregunta.class)
                    .setParameter("estado", estado).getResultList();
            return preguntas;
        } catch (NoResultException nre) {
            return null;
        }
    }

}
