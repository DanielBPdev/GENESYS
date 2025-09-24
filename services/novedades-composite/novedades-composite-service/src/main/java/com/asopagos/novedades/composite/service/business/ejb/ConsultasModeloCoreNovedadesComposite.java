package com.asopagos.novedades.composite.service.business.ejb;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


import com.asopagos.novedades.composite.service.business.interfaces.IConsultasModeloCoreNovedadesComposite;

import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.asopagos.rutine.afiliacionesrutines.radicarlistasolicitudes.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.database.NumeroRadicadoUtil;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
//
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloCoreNovedadesComposite implements IConsultasModeloCoreNovedadesComposite, Serializable {

	/**
	 * Constantes para nombramiento de parámetros de consulta
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "novedades_PU")
	private EntityManager entityManagerCore;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearSolicitudAporte(com.asopagos.dto.modelo.SolicitudAporteModeloDTO)
	 */

	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, String> radicarListaSolicitudesNovedades(List<Long> listSolicitudes, String sccID, UserDTO userDTO) {
		  String firma = "rutina radicarListaSolicitudes(List<Long>, String, UserDTO):Map<Long, String>";
          System.out.println(" inicia rutina ");
        int cont=1;
                for (Long idsolicitudfor : listSolicitudes) {
                      System.out.println(" idsolicitudfornovedadesinterssface "+cont +" : "+idsolicitudfor);
                      cont ++;
                }
            System.out.println(ConstantesComunes.INICIO_LOGGER + firma);
            Map<String, String> mapResult = new HashMap<>();
            // Se consulta la informacion de las solicitudes enviadas
           // List<Solicitud> listaSolicitud = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_IDS, Solicitud.class)
                 System.out.println("**__** cambio createQuery en radicarListaSolicitudesNovedades");
               List<Solicitud> listaSolicitud = entityManagerCore.createQuery("SELECT sol FROM Solicitud sol WHERE sol.idSolicitud IN (:idsSolicitud)", Solicitud.class)
                    .setParameter("idsSolicitud", listSolicitudes).getResultList();
            if (listaSolicitud == null || listaSolicitud.isEmpty()) {
                System.out.println("- No existe solicitud");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            }
            for (Solicitud solicitud : listaSolicitud) {
				if(solicitud != null ){
					try{
					// Se verifica que la solicitud no se haya radicado previamente
					if (solicitud.getNumeroRadicacion() != null) {
						System.out.println( "- La solicitud ya fué radicada previamente");
						throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
					}
					System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina  radicarListaSolicitudes");
					// Se calcula el numero de radicado
					NumeroRadicadoCorrespondenciaDTO numeroRadicadoDTO = obtenerNumeroRadicadoCorrespondencia(
							TipoEtiquetaEnum.NUMERO_RADICADO, 1, userDTO, entityManagerCore);
					Calendar fecha = Calendar.getInstance();
					System.out.println(" Fin NumeroRadicadoCorrespondenciaDTO en rutina  radicarListaSolicitudes: "+numeroRadicadoDTO.nextValue());
					String numeroRadicado = numeroRadicadoDTO.nextValue();
					
					// Se actualiza la informacion de la solicitud
					System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina entityManagerCore.merge(solicitud)");
					entityManagerCore.merge(solicitud);
					solicitud.setNumeroRadicacion(numeroRadicado);
					solicitud.setFechaRadicacion(fecha.getTime());
					System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina entityManagerCore.merge(solicitud)");
					// Se marca el codigo como asignado
					//asignarCodigoPreImpreso(numeroRadicado);
					// Se agrega a la respuesta del servicio
					System.out.println("**__**idsolicitu RadicarListaSolicitudesRutine " +solicitud.getIdSolicitud().toString()+ 
					" solicitud.getNumeroRadicacion(): "+solicitud.getNumeroRadicacion());
					mapResult.put(solicitud.getIdSolicitud().toString(), solicitud.getNumeroRadicacion());
					
				}catch(Exception e){
 				System.out.println(" Problema con la solicitud "+solicitud);
				}
				}
		    }
            System.out.println(firma);
            return mapResult;
       
    }
	
	  private NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum tipoEtiqueta, Integer cantidad, UserDTO userDTO, 
	          EntityManager entityManager) {
	        String firmaServicio = "consultarRegistroListaEspecialRevision(TipoEtiquetaEnum, Integer, UserDTO)";
	        System.out.println("Inicia "+ firmaServicio);
	        
	        NumeroRadicadoUtil util = new NumeroRadicadoUtil();
            System.out.println(" obtenerNumeroRadicadoCorrespondencia: tipoEtiqueta: "+tipoEtiqueta+" cantidad: "+cantidad+" userDTO: "+userDTO);
	        NumeroRadicadoCorrespondenciaDTO num = util.obtenerNumeroRadicadoCorrespondencia(entityManager, tipoEtiqueta, cantidad, userDTO);
	        System.out.println("Finaliza "+ firmaServicio);
	        return num;
	    }
	
}
