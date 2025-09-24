package com.asopagos.usuarios.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.entidades.seguridad.ReferenciaToken;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.usuarios.service.IReferenciaTokenPersistenceServices;

@Stateless
public class ReferenciaTokenPersistenceServices implements IReferenciaTokenPersistenceServices {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void persistir(ReferenciaToken referenciaToken) {
		ReferenciaToken r = buscarReferenciaToken(referenciaToken.getTipoIdentificacion(),
				referenciaToken.getNumeroIdentificacion(), referenciaToken.getDigitoVerificacion());
		if (r == null) {
			entityManager.persist(referenciaToken);
		}

	}

	@Override
	public ReferenciaToken actualizarReferenciaToken(ReferenciaToken referenciaToken) {

		if (referenciaToken.getIdReferenciaToken() != null) {
			entityManager.merge(referenciaToken);
			return referenciaToken;
		}
		return null;
	}

	@Override
	public void borrarReferenciaToken(Long id) {
		ReferenciaToken r = buscarReferenciaToken(id);
		if (r != null) {
			entityManager.remove(r);
			;
		}
	}

	@Override
	public ReferenciaToken buscarReferenciaToken(Long id) {
		ReferenciaToken r=null;
		try {
			r=entityManager.find(ReferenciaToken.class, id);
			return r;
		} catch (NoResultException e) {			
		}
		return null;
	}

	@Override
	public ReferenciaToken buscarReferenciaToken(String sessionId) {
		try {
			return (ReferenciaToken) entityManager.createNamedQuery("ReferenciaToken.consultarPorSessionId")
                    .setParameter("sessionId", sessionId).getSingleResult();
		} catch (NoResultException e) {
            return null;
		}
	}

	@Override
	public ReferenciaToken buscarReferenciaToken(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion) {
		if(tipoIdentificacion!=null&&(numeroIdentificacion!=null&&!numeroIdentificacion.equals(""))&&digitoVerificacion==null){
			Query query=entityManager.createNamedQuery("ReferenciaToken.consultarPersona").setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion);
			try {
				ReferenciaToken r=(ReferenciaToken)query.getSingleResult();
				return r;
			} catch (NoResultException e) {
				return null;
			}
			
		}else if(tipoIdentificacion!=null&&(numeroIdentificacion!=null&&!numeroIdentificacion.equals(""))&&digitoVerificacion!=null){
			Query query=entityManager.createNamedQuery("ReferenciaToken.consultarPersonaDV").setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("digitoVerificacion", digitoVerificacion);
			try {
				ReferenciaToken r=(ReferenciaToken)query.getSingleResult();
				return r;
			} catch (NoResultException e) {
				return null;
			}
		}
		return null;

	}

}
