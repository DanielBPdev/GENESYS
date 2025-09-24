package com.asopagos.correspondencia.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.correspondencia.constants.NamedQueriesConstants;
import com.asopagos.correspondencia.service.CorrespondenciaService;
import com.asopagos.database.NumeroRadicadoUtil;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.afiliaciones.DestinatarioCajaCorrespondenciaDTO;
import com.asopagos.entidades.ccf.core.CajaCorrespondencia;
import com.asopagos.entidades.ccf.core.EtiquetaCorrespondenciaRadicado;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.EstadoCajaCorrespondenciaEnum;
import com.asopagos.enumeraciones.core.ProcedenciaEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * implemanta los servicios relacionados con la gestion de afiliaciones
 * <b>Módulo:</b> Asopagos - HU <br/>
 * m 66-92-93-70
 *
 * @author Josué Nicolás Pinzón Villamil
 *         <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon@heinsohn.com.co
 *         </a>
 */
@Stateless
public class CorrespondenciaBusiness implements CorrespondenciaService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "correspondencia_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(CorrespondenciaBusiness.class);

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.CorrespondenciaService.service.AfiliacionesService#obtenerCajaCorrespondenciaAbierta(java.lang.String, java.lang.String) 
	 */    
	@Override
	public CajaCorrespondencia obtenerCajaCorrespondenciaAbierta(String codigoSedeCajaCompensacion, UserDTO userDTO) {

		CajaCorrespondencia cajaCorrespondencia;
		try {
			cajaCorrespondencia = (CajaCorrespondencia) entityManager.createNamedQuery(
                    NamedQueriesConstants.BUSCAR_CAJA_ABIERTA_POR_SEDE)
					.setParameter("sedeRemitente", userDTO.getSedeCajaCompensacion())
					.setParameter("estado", EstadoCajaCorrespondenciaEnum.ABIERTA).getSingleResult();

		} catch (NoResultException e) {
			NumeroRadicadoCorrespondenciaDTO numeroCorrespondenciaDTO = 
					obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum.CORRESPONDENCIA_FISICA, 1, userDTO);
                    //obtenerStickerCorrespondenciaFisica(userDTO.getSedeCajaCompensacion(), userDTO);
			cajaCorrespondencia = new CajaCorrespondencia();
			cajaCorrespondencia.setCodigoEtiqueta(numeroCorrespondenciaDTO.nextValue());
			cajaCorrespondencia.setFechaInicio(new Date());
			cajaCorrespondencia.setEstado(EstadoCajaCorrespondenciaEnum.ABIERTA);
			cajaCorrespondencia.setRemitente(userDTO.getNombreUsuario());
			cajaCorrespondencia.setSedeRemitente(userDTO.getSedeCajaCompensacion());
			entityManager.persist(cajaCorrespondencia);
		}
		return cajaCorrespondencia;
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.CorrespondenciaService.service.AfiliacionesService#obtnenerListaSolicitudesCajaCorrespondecia(java.lang.String) 
	 */    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> obtnenerListaSolicitudesCajaCorrespondecia(String codigoEtiquetaCajaCorrespondencia) {

		return entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_CAJA)
				.setParameter("codigoEtiqueta", codigoEtiquetaCajaCorrespondencia).getResultList();
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.CorrespondenciaService.service.AfiliacionesService#buscarCajasCorrespondencia(java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.core.EstadoCajaCorrespondenciaEnum) 
	 */        
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CajaCorrespondencia> buscarCajasCorrespondencia(String codigoEtiqueta, String codigoSede, 
			Long fechaInicio, Long fechaFin, EstadoCajaCorrespondenciaEnum estado) {

		//se crea la instancia del criteriaBuilder
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CajaCorrespondencia> criteriaQuery = builder.createQuery(CajaCorrespondencia.class);
		Root<CajaCorrespondencia> root = criteriaQuery.from(CajaCorrespondencia.class);
		criteriaQuery.select(root);
		
		//se crea una lista que contendrá las condiciones para la consulta
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		//se verifica cada parámetro del método. si no viene null se agrega a la consulta como 
		//condición de busqueda
		if (codigoEtiqueta != null) {
			predicates.add(builder.equal(root.get("codigoEtiqueta"), codigoEtiqueta));
		}
		if (codigoSede != null) {
			predicates.add(builder.equal(root.get("sedeRemitente"), codigoSede));
		}
		if (fechaInicio != null) {	
			Date fechaInicial = CalendarUtils.truncarHora(new Date(fechaInicio));
			predicates.add(builder.greaterThanOrEqualTo(root.get("fechaInicio"), fechaInicial));
		}
		if (fechaFin != null) {
		    Date fechaFinal = CalendarUtils.truncarHora(new Date(fechaFin));
	        Calendar fechaFinalC = Calendar.getInstance();
	        fechaFinalC.setTime(fechaFinal);
	        fechaFinalC.add(Calendar.DAY_OF_YEAR, 1);
	        fechaFinal = fechaFinalC.getTime();

			predicates.add(builder.lessThanOrEqualTo(root.get("fechaFin"), fechaFinal));
		}
		if (estado != null) {
			predicates.add(builder.equal(root.get("estado"), estado));
		}
		
		//se verifica si el arreglo de condiciones no está vacio. en este caso se agregan 
		//dichas condiciones a la consulta
		if(predicates.size() > 0){
			criteriaQuery.where(predicates.toArray(new Predicate[]{}));
		}
		
		//se agrega al log de ejecución la hora del sistema
		logger.debug(new Date().getTime());
		
		//se ejecuta la consulta y se guarda su set de resultados en un arreglo
		List<CajaCorrespondencia> cajasCorrespondencia = this.entityManager.createQuery(criteriaQuery).getResultList();

		return cajasCorrespondencia;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.asopagos.CorrespondenciaService.service.AfiliacionesService#cerrarCajaCorrespondencia(java.lang.String, com.asopagos.dto.afiliaciones.DestinatarioCajaCorrespondenciaDTO) 
	 */        
	@Override
	public void cerrarCajaCorrespondencia(String codigoEtiqueta, DestinatarioCajaCorrespondenciaDTO inDTO) {
		
		// Se valida que exista la caja para esa etiqueta
		List<CajaCorrespondencia> cajasCorrespondencia = buscarCajasCorrespondencia(
                codigoEtiqueta, null, null, null, null);
		if (cajasCorrespondencia.isEmpty()) {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
		CajaCorrespondencia cajaCorrespondencia = cajasCorrespondencia.get(0);
        cajaCorrespondencia.setEstado(EstadoCajaCorrespondenciaEnum.CERRADA);
        cajaCorrespondencia.setDestinatario(inDTO.getDestinantario());
        cajaCorrespondencia.setSedeDestinatario(inDTO.getSedeDestinatario());
        cajaCorrespondencia.setFechaFin(new Date());
        entityManager.merge(cajaCorrespondencia);
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.CorrespondenciaService.service.AfiliacionesService#actualizarEstadoEtiqueta(java.lang.String, com.asopagos.enumeraciones.core.EstadoCajaCorrespondenciaEnum) 
	 */        
	@Override
    public void registrarRecepcionCajaCorrespondencia(String codigoEtiqueta, UserDTO userDTO) {

        // Se valida que exista la caja para esa etiqueta
        List<CajaCorrespondencia> cajasCorrespondencia = buscarCajasCorrespondencia(codigoEtiqueta, null, null, null, null);
        if (!cajasCorrespondencia.isEmpty()) {
            CajaCorrespondencia cajaCorrespondencia = cajasCorrespondencia.get(0);
            cajaCorrespondencia.setEstado(EstadoCajaCorrespondenciaEnum.RECIBIDA);
            cajaCorrespondencia.setFechaRecepcion(new Date());
            cajaCorrespondencia.setUsuarioRecepcion(userDTO.getNombreUsuario());
            entityManager.merge(cajaCorrespondencia);
        }
        else {
            return;
        }
    }

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.service.AfiliacionesService#
	 * obtenerStickerCorrespondenciaFisica(java.lang.String)
	 */
	@Override
	@Deprecated
	public synchronized NumeroRadicadoCorrespondenciaDTO obtenerStickerCorrespondenciaFisica(String sccID, UserDTO userDTO) {
		logger.debug("Inicia obtenerStickerCorrespondenciaFisica(String)");
    	NumeroRadicadoCorrespondenciaDTO dto = obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum.CORRESPONDENCIA_FISICA, 1, userDTO);
		logger.debug("Finaliza obtenerStickerCorrespondenciaFisica(String)");
		return dto;
	}
	
	/**
	 * @param tipoEtiqueta
	 * @param cantidad
	 * @param userDTO
	 * @returncantidad
	 */
	private NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum tipoEtiqueta, Integer cantidad, UserDTO userDTO) {
		logger.debug("Inicia obtenerStickerCorrespondenciaFisica(TipoEtiquetaEnum, Integer, UserDTO)");
		
		if (userDTO.getSedeCajaCompensacion() == null) {
			logger.debug("Finaliza obtenerStickerCorrespondenciaFisica(String): Parametros inválidos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
		}
		
		NumeroRadicadoUtil util = new NumeroRadicadoUtil();
    	NumeroRadicadoCorrespondenciaDTO dto = util.obtenerNumeroRadicadoCorrespondencia(entityManager, tipoEtiqueta, cantidad, userDTO);
    	
		logger.debug("Finaliza obtenerStickerCorrespondenciaFisica(TipoEtiquetaEnum, Integer, UserDTO)");
		return dto;
	}
	

}
	
