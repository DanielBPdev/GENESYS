package com.asopagos.listaschequeo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.DocumentoRequisitoDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.core.GrupoRequisito;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.entidades.transversal.core.RequisitoCajaClasificacion;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.constants.NamedQueriesConstants;
import com.asopagos.listaschequeo.dto.EtiquetaSolicitudesDTO;
import com.asopagos.listaschequeo.service.ListasChequeoService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rutine.listaschequeorutines.guardarlistachequeo.GuardarListaChequeoRutine;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de listas de chequeo para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class ListasChequeoBusiness implements ListasChequeoService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "listaschequeo_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(ListasChequeoBusiness.class);

	/**
	 * @see com.asopagos.listaschequeo.ejb.ListasChequeoBusiness#crearListaChequeo(com.asopagos.dto.ListaChequeoDTO)
	 */
	@Override
	public List<Long> guardarListaChequeo(ListaChequeoDTO listaChequeo) {
	    GuardarListaChequeoRutine g = new GuardarListaChequeoRutine();
		for (ItemChequeoDTO itemChequeoDTO : listaChequeo.getListaChequeo()) {
			logger.info("itemChequeoDTO guardarListaChequeo " +itemChequeoDTO.toString());
			logger.info("itemChequeoDTO guardarListaChequeo " +itemChequeoDTO.getIdentificadorDocumento());
		}
        return g.guardarListaChequeo(listaChequeo, entityManager);
	}

	/**
	 * @see com.asopagos.listaschequeo.ejb.ListasChequeoBusiness#consultarListaChequeo(java.lang.Long,
	 *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String)
	 */
	@Override
	public List<ListaChequeoDTO> consultarListaChequeo(Long idSolicitud, ClasificacionEnum clasificacion,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<String> grupos) {

		List<ListaChequeoDTO> listasChequeoDTO = new ArrayList();
		if (tipoIdentificacion != null && numeroIdentificacion != null) {
			
			Integer idCajaCompensacion = Integer.valueOf((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID));
			
			List<ItemChequeoDTO> itemsChequeoDTO = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_POR_CLASIFICACION)
					.setParameter("idSolicitud", idSolicitud).setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("clasificacion", clasificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("idCaja", idCajaCompensacion).getResultList();

			if (grupos != null && !grupos.isEmpty()) {
				List<Long> listaIdRCC = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_PERSONA_PREVIO)
						.setParameter("idSolicitud", idSolicitud).setParameter("tipoIdentificacion", tipoIdentificacion)
						.setParameter("listaGrupos", grupos).setParameter("numeroIdentificacion", numeroIdentificacion)
						.getResultList();

				for (ItemChequeoDTO itemChequeoDTO : itemsChequeoDTO) {
					if (itemChequeoDTO.getIdRequisitoCajaClasificacion() != null && listaIdRCC != null
							&& !listaIdRCC.isEmpty()) {
						if (!listaIdRCC.contains(itemChequeoDTO.getIdRequisitoCajaClasificacion())) {
							itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
						}
					} else {
						itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
					}
				}
			} else {
				for (ItemChequeoDTO itemChequeoDTO : itemsChequeoDTO) {
					itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
				}
			}

			ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();
			listaChequeoDTO.setIdSolicitudGlobal(idSolicitud);
			listaChequeoDTO.setNumeroIdentificacion(numeroIdentificacion);
			listaChequeoDTO.setTipoIdentificacion(tipoIdentificacion);
			listaChequeoDTO.setListaChequeo(itemsChequeoDTO);
			listasChequeoDTO.add(listaChequeoDTO);

		} else {
			List<Object[]> itemsChequeoRaw = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD)
					.setParameter("idSolicitud", idSolicitud).getResultList();

			List<Long> listaIdRCC = new ArrayList<Long>();
			if (grupos != null && !grupos.isEmpty()) {
				listaIdRCC = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_POR_SOLICITUD_PREVIO)
						.setParameter("idSolicitud", idSolicitud).setParameter("listaGrupos", grupos).getResultList();
			}

			if (!itemsChequeoRaw.isEmpty()) {
				Long key;
				Map<Long, ListaChequeoDTO> listas = new HashMap();
				Long idRCC;
				for (Object[] itemRaw : itemsChequeoRaw) {
					ItemChequeo itemChequeo = (ItemChequeo) itemRaw[0];
					key = itemChequeo.getPersona().getIdPersona();
					if (!listas.containsKey(key)) {
						ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();
						listaChequeoDTO.setIdSolicitudGlobal(idSolicitud);
						listaChequeoDTO.setNumeroIdentificacion(numeroIdentificacion);
						listaChequeoDTO.setTipoIdentificacion(tipoIdentificacion);
						listaChequeoDTO.setListaChequeo(new ArrayList());
						listas.put(key, listaChequeoDTO);
						listasChequeoDTO.add(listaChequeoDTO);
					}

					ItemChequeoDTO itemChequeoDTO = new ItemChequeoDTO();
					itemChequeoDTO.setComentarios(itemChequeo.getComentarios());
					itemChequeoDTO.setComentariosBack(itemChequeo.getComentariosBack());
					itemChequeoDTO.setCumpleRequisito(itemChequeo.getCumpleRequisito());
					itemChequeoDTO.setCumpleRequisitoBack(itemChequeo.getCumpleRequisitoBack());
					itemChequeoDTO.setEstadoRequisito(itemChequeo.getEstadoRequisito());
					itemChequeoDTO.setFormatoEntregaDocumento(itemChequeo.getFormatoEntregaDocumento());
					itemChequeoDTO.setIdRequisito(itemChequeo.getRequisito().getIdRequisito());
					itemChequeoDTO.setIdentificadorDocumento(itemChequeo.getIdentificadorDocumento());
					// itemChequeoDTO.setIdentificadorDocumentoPrevio(itemChequeo.getIdentificadorDocumentoPrevio());
					itemChequeoDTO.setNombreRequisito(itemChequeo.getRequisito().getDescripcion());
					itemChequeoDTO.setVersionDocumento(itemChequeo.getVersionDocumento());

					RequisitoCajaClasificacion requisitoCajaClasificacion = (RequisitoCajaClasificacion) itemRaw[1];
					if (requisitoCajaClasificacion != null && listaIdRCC != null && !listaIdRCC.isEmpty()) {
						idRCC = requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja();
						if (!listaIdRCC.contains(idRCC)) {
							itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
						}
					} else {
						itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
					}

					List<GrupoRequisito> grupoRequisitos = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPO_USUARIO, GrupoRequisito.class)
							.setParameter("idRequisitoCajaClasificacion",
									requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja())
							.getResultList();

					if (requisitoCajaClasificacion != null) {
						itemChequeoDTO.setTextoAyuda(requisitoCajaClasificacion.getTextoAyuda());
						itemChequeoDTO.setTipoRequisito(requisitoCajaClasificacion.getTipoRequisito());
						itemChequeoDTO.setIdRequisitoCajaClasificacion(
								requisitoCajaClasificacion.getIdRequisitoTipoSolicitanteCaja());
						if (grupoRequisitos != null && !grupoRequisitos.isEmpty()) {
							itemChequeoDTO.setGrupoRequisitos(grupoRequisitos);
						}
					}
					listas.get(key).getListaChequeo().add(itemChequeoDTO);
				}
			}
		}
		return listasChequeoDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.listaschequeo.service.ListasChequeoService#
	 * consultarListaChequeoPorClasificacion(java.lang.Long,
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String, com.asopagos.enumeraciones.core.ClasificacionEnum)
	 */
	@Override
	public ListaChequeoDTO consultarListaChequeoPorClasificacion(Long idSolicitud,
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, ClasificacionEnum clasificacion) {

		ListaChequeoDTO listaChequeoDTO = new ListaChequeoDTO();
		if (tipoIdentificacion != null && numeroIdentificacion != null) {

			List<ItemChequeoDTO> itemsChequeoDTO = entityManager 
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_CHEQUEO_POR_CLASIFICACION)
					.setParameter("idSolicitud", idSolicitud).setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("clasificacion", clasificacion).getResultList();

			for (ItemChequeoDTO itemChequeoDTO : itemsChequeoDTO) {
				itemChequeoDTO.setIdentificadorDocumentoPrevio(null);
			}

			listaChequeoDTO.setIdSolicitudGlobal(idSolicitud);
			listaChequeoDTO.setNumeroIdentificacion(numeroIdentificacion);
			listaChequeoDTO.setTipoIdentificacion(tipoIdentificacion);
			listaChequeoDTO.setListaChequeo(itemsChequeoDTO);

		}
		return listaChequeoDTO;
	}

	@Override
	public List<EtiquetaSolicitudesDTO> consultaItemsChequeo(String numeroRadicacion) {
		try {
			List<EtiquetaSolicitudesDTO> resultado = new ArrayList<EtiquetaSolicitudesDTO>();

			resultado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ITEMS_CHEQUEO_SOLICITUD)
					.setParameter("numeroRadicacion", numeroRadicacion).getResultList();
			
			return resultado;
		} catch (Exception e) {
			logger.error("Ocurrió un error en el servicio consultarAportantesCaja");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

	}

    /**
     * (non-Javadoc)
     * @see com.asopagos.listaschequeo.service.ListasChequeoService#consultarDocumentosRequisitoPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DocumentoRequisitoDTO> consultarDocumentosRequisitoPersona(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.debug("Inicia consultarDocumentosRequisitoPersona(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DOCUMENTO_REQUISITO_PERSONA, DocumentoRequisitoDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
    }

}
