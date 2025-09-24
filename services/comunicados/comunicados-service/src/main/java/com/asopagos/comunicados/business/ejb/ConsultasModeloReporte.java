package com.asopagos.comunicados.business.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloReporte implements IConsultasModeloReporte, Serializable{

    /**
     * Serial Version ID 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloReporte.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "certificados_PU")
    private EntityManager entityManagerReporte;
    
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte#estuvoAfiliadoEnCaja(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public boolean estuvoAfiliadoEnCaja(Long idPersona, Boolean empleador) {
		String firmaMetodo = "haTenidoAfiliacion(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		boolean haTenidoAfiliacion = false;
		try {
			List<Object[]> afiliaciones = null;
			// consultar historico empleador o persona (procedimiento)
			afiliaciones = (List<Object[]>) entityManagerReporte
					.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_EMPLEADOR)
					.setParameter("idPersona", idPersona.toString()).setParameter("tipoAportante", empleador ? "EMPLEADOR" : "")
					.getResultList();

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			for (Object[] objects : afiliaciones) {
				if (objects[0].equals("ACTIVO") || objects[0].equals("NO_FORMALIZADO_RETIRADO_CON_APORTES") || objects[0].equals("INACTIVO")) {
					haTenidoAfiliacion = true;
					break;
				}
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			return false;
		}
		return haTenidoAfiliacion;
	}

//    /**
//     * (non-Javadoc)
//     * @see com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte#aporteRecibidoVigente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Boolean, java.lang.Short)
//     */
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public boolean aporteRecibidoVigente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
//            Boolean empleador, Short anio){
//        boolean estadoVigente=false;
//        List<Object[]> certificados;
//        String query;
//        if(empleador){
//            query=NamedQueriesConstants.CONSULTA_CERTIFICADO_APORTANTE_EMPLEADOR;
//        }else{
//            query=NamedQueriesConstants.CONSULTA_CERTIFICADO_APORTANTE_PERSONA;
//        }
//        certificados = entityManagerReporte
//                .createNamedQuery(query)
//                .setParameter("numeroIdentificacion", numeroIdentificacion)
//                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
//                .setParameter("anio", anio).getResultList();
//        for (Object[] objects : certificados) {
//            if (objects[0].equals("VIGENTE")) {
//                estadoVigente = true;
//                break;
//            }
//        }
//        return estadoVigente;
//    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte#validarEstadoDependiente(java.lang.String, java.lang.Long, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> validarEstadoAfiliado(String idPersona,Long idEmpleador, String estadoActualAfiliado, TipoAfiliadoEnum tipoAfiliado){
        try{
            logger.info("entró a validar el estado de afiliado");
            logger.info("el tipo de afiliado es: "+tipoAfiliado.name());
            if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)){
                return (List<Object[]>) entityManagerReporte.createNamedQuery(NamedQueriesConstants.VALIDAR_ESTADO_AFILIADO_DEPENDIENTE)
                        .setParameter("idPersona", idPersona)
                        .setParameter("idEmpleador", idEmpleador)
                        .setParameter("estadoActualEnCore", estadoActualAfiliado)
                        .getResultList();   
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)){
                return (List<Object[]>) entityManagerReporte.createNamedQuery(NamedQueriesConstants.VALIDAR_ESTADO_AFILIADO_INDEPENDIENTE)
                        .setParameter("idPersona", idPersona)
                        .setParameter("estadoActualEnCore", estadoActualAfiliado)
                        .getResultList();
            } else {
                return entityManagerReporte.createNamedQuery(NamedQueriesConstants.VALIDAR_ESTADO_AFILIADO_PENSIONADO)
                        .setParameter("idPersona", idPersona)
                        .setParameter("estadoActualEnCore", estadoActualAfiliado)
                        .getResultList();
            }
        }catch (Exception e) {
            logger.debug("Finaliza ejecutarBloqueStaging");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte#consultarHistoricoAfiliaciones(com.asopagos.enumeraciones.personas.TipoAfiliadoEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> consultarHistoricoAfiliaciones(TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdAfiliado,
            String numeroIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        try { 
            return (List<Object[]>) entityManagerReporte
                .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTA_HISTORICO_AFILIACIONES_PERSONA)
                .setParameter("tipoIdentificacion", tipoIdAfiliado.name()).setParameter("numeroIdentificacion", numeroIdAfiliado)
                .setParameter("tipoAfiliado", tipoAfiliado.name())
                .setParameter("tipoIdentificacionEmpleador", tipoIdEmpleador != null ? tipoIdEmpleador.name() : "")
                .setParameter("numeroIdentificacionEmpleador", numeroIdEmpleador).getResultList();
        }catch (Exception e) {
            logger.debug("Finaliza ejecutarBloqueStaging");
            logger.error("Hubo un error en la consulta:", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerHistoricoAfiliacionesEmpleador(Long idPersonaEmpleador) {
        List<Object[]> respuestaSP = (List<Object[]>)entityManagerReporte.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFILIACION_EMPLEADOR)
        .setParameter("idPersona", idPersonaEmpleador.toString())
        .getResultList();
        
        return respuestaSP;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerHistoricoEstadosEmpleador(Long idPersona, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, TipoSolicitanteMovimientoAporteEnum tipoAportante) {

        List<Object[]> respuestaSP = (List<Object[]>)entityManagerReporte.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_EMPLEADOR)
                .setParameter("idPersona", idPersona.toString())
                .setParameter("tipoAportante", tipoAportante.name())
                .getResultList();
        
        return respuestaSP;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarHistoricoEstadosEmpleador(List<Object[]> estado) {
        entityManagerReporte.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_HISTORICO_ESTADO_EMPLEADOR)
        .setParameter("persona", estado.get(0)[0].toString())
        .setParameter("estadoAfiliacion", estado.get(0)[1] !=null ? estado.get(0)[1].toString(): null)
        .setParameter("motivoDesafiliacion", estado.get(0)[2] != null ? estado.get(0)[2].toString(): null)
        .setParameter("numeroTrabajadores", estado.get(0)[3] != null ? estado.get(0)[3].toString(): null)
        .executeUpdate();
        
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> obtenerEstadoActualEmpleador(Long idPersonaEmpleador) {
        List<Object[]> salida = (List<Object[]>) entityManagerReporte.createNamedQuery(NamedQueriesConstants.OBTENER_ESTADO_ACTUAL_EMPLEADOR_REPORTES)
                .setParameter("idPersonaEmpleador",idPersonaEmpleador.toString())
                .getResultList();
        return salida;
    }
}
