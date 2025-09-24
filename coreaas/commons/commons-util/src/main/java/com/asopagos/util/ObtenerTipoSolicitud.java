package com.asopagos.util;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.TipoEspecificoSolicitudDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudCierreRecaudoEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudDesafiliacionEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;


/**
 * @author jzambrano
 *
 */
public class ObtenerTipoSolicitud {

    /** Referencia al logger */
    private static ILogger logger = LogManager.getLogger(ObtenerTipoSolicitud.class);

    /**
     * Método que determina por medio de la transacción que tipo de solicitud
     * pertenece
     * 
     * @param tipoTx
     * @return
     */
    public static TipoSolicitudEnum obtenerTipoSolicitudPorTipoTransaccion(TipoTransaccionEnum tipoTx) {
        logger.debug("Inicia obtenerTipoSolicitudPorTipoTransaccion(TipoTransaccionEnum)");
        TipoSolicitudEnum tipoSolicitud = null;
        if (tipoTx != null) {
            if (TipoTransaccionEnum.APORTES_MANUALES.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.APORTES;
            }
            else if (TipoTransaccionEnum.DEVOLUCION_APORTES.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.APORTES_DEVOLUCION;
            }
			else if (TipoTransaccionEnum.APORTES_MANUALES_MASIVA.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.APORTES_MANUALES_MASIVA;
            }
			else if (TipoTransaccionEnum.DEVOLUCION_APORTES_MASIVA.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.DEVOLUCION_APORTES_MASIVA;
            }
            else if (TipoTransaccionEnum.CORRECCION_APORTES.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.APORTES_CORRECCION;
            }
            else if (TipoTransaccionEnum.GESTION_PREVENTIVA_CARTERA.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.GESTION_PREVENTIVA;
            }
            else if (ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL.equals(tipoTx.getProceso())
                    || ProcesoEnum.POSTULACION_FOVIS_WEB.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.POSTULACION_FOVIS;
            }
            else if (ProcesoEnum.FISCALIZACION_CARTERA.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.FISCALIZACION;
            }
            else if (ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL.equals(tipoTx.getProceso())
                    || ProcesoEnum.GESTION_COBRO_2E.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.GESTION_COBRO_FISICO;
            }
            else if (ProcesoEnum.GESTION_COBRO_ELECTRONICO.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.GESTION_COBRO_ELECTRONICO;
            }
            else if (ProcesoEnum.GESTION_COBRO_MANUAL.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.GESTION_COBRO_MANUAL;
            }
            else if (ProcesoEnum.DESAFILIACION_APORTANTES.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.DESAFILIACION_APORTANTES;
            }
            else if (ProcesoEnum.NOVEDADES_FOVIS_REGULAR.equals(tipoTx.getProceso())
                    || ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.NOVEDAD_FOVIS;
            }
            else if (TipoTransaccionEnum.CONVENIO_PAGO.equals(tipoTx)) {
                tipoSolicitud = TipoSolicitudEnum.CARTERA;
            }
            else if (ProcesoEnum.LEGALIZACION_DESEMBOLSO_FOVIS.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.LEGALIZACION_FOVIS;
            }
            else if (ProcesoEnum.VERIFICACION_POSTULACION_FOVIS.equals(tipoTx.getProceso())){
                tipoSolicitud = TipoSolicitudEnum.POSTULACION_FOVIS_VERIFICACION;
            }
            else if (ProcesoEnum.CRUCES_POSTULACION_FOVIS.equals(tipoTx.getProceso())) {
                tipoSolicitud = TipoSolicitudEnum.POSTULACION_FOVIS_GESTION_CRUCE;
            }
            else if (ProcesoEnum.ANALISIS_NOVEDADES_PERSONAS_FOVIS.equals(tipoTx.getProceso())){
                tipoSolicitud = TipoSolicitudEnum.NOVEDAD_FOVIS_ANALISIS;
            }
            else if (ProcesoEnum.ASIGNACION_FOVIS.equals(tipoTx.getProceso())){
                tipoSolicitud = TipoSolicitudEnum.ASIGNACION_FOVIS;
            }
            else if (ProcesoEnum.CIERRE_RECAUDO.equals(tipoTx.getProceso())){
                tipoSolicitud = TipoSolicitudEnum.CIERRE_RECAUDO;
            }
            else if (TipoTransaccionEnum.RECAUDO_APORTES_PILA.equals(tipoTx)){
                tipoSolicitud = TipoSolicitudEnum.PILA;
            }
            else {
                if (TipoTipoSolicitanteEnum.EMPLEADOR.equals(tipoTx.getProceso().getTipoTipoSolicitante())) {
                    if (tipoTx.getEsNovedad()) {
                        tipoSolicitud = TipoSolicitudEnum.NOVEDAD_EMPLEADOR;
                    }
                    else {
                        tipoSolicitud = TipoSolicitudEnum.EMPLEADOR;
                    }
                }
                else if (TipoTipoSolicitanteEnum.PERSONA.equals(tipoTx.getProceso().getTipoTipoSolicitante())) {
                    if (tipoTx.getEsNovedad()) {
                        tipoSolicitud = TipoSolicitudEnum.NOVEDAD_PERSONA;
                    }
                    else {
                        tipoSolicitud = TipoSolicitudEnum.PERSONA;
                    }
                }

            }

        }
        if (tipoSolicitud != null) {
            return tipoSolicitud;
        }
        else {
            logger.error("Finaliza obtenerTipoSolicitudPorTipoTransaccion(TipoTransaccionEnum): "
                    + "no se encuentra una implementación para la fabrica solicitada");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
    }
    
    /**
	 * Metodo que obtiene informacion de la consulta nativa para un tipo de
	 * solicitud especifica, se utiliza para construir la consulta y busqueda de
	 * solicitudes
	 * 
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @return
	 */
	public static TipoEspecificoSolicitudDTO obtenerInformacionTipoSolicitudEspecifico(TipoSolicitudEnum tipoSolicitud,
			String estadoSolicitud) {
		logger.debug("Inicia obtenerInformacionTablaTipoSolicitud(TipoSolicitudEnum)");
		String estadoConsultaDevolucion ="";
		String estadoConsultaCorreccion ="";
		String estadoConsultaAportes ="";
		//	if(EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.CERRADA ||
		//	 EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) != EstadoSolicitudAporteEnum.DESISTIDA &&
		//	 EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) != EstadoSolicitudAporteEnum.CANCELADA){
		//		logger.info("**__** estadoSolicitud CERRADA");
		//		estadoConsultaAportes = " se.soaEstadoSolicitud = :estadoSolicitud ";
		//		estadoConsultaCorreccion = " se.scaEstadoSolicitud = :estadoSolicitud ";
		//		estadoConsultaDevolucion = " se.sdaEstadoSolicitud = :estadoSolicitud ";
		//		
		//	} else 
		switch (tipoSolicitud) {
		case SUBSIDIO_FALLECIMIENTO:
			try {
				return new TipoEspecificoSolicitudDTO("sol.solResultadoProceso",
						ResultadoProcesoEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"outer apply (select TOP(1) pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica WHERE pleSolicitudLiquidacionSubsidio = sls.slsId ORDER BY pleId DESC) as ple "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sol.solResultadoProceso = (CASE WHEN :estadoSolicitud = 'TODOS' THEN sol.solResultadoProceso ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion = '"+TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION.name()+"' ");
			} catch (Exception e) {
				return new TipoEspecificoSolicitudDTO("sls.slsEstadoLiquidacion",
						EstadoProcesoLiquidacionEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"outer apply (select TOP(1) pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica WHERE pleSolicitudLiquidacionSubsidio = sls.slsId ORDER BY pleId DESC) as ple "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sls.slsEstadoLiquidacion = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sls.slsEstadoLiquidacion ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion = '"+TipoProcesoLiquidacionEnum.SUBSUDIO_DE_DEFUNCION.name()+"' ");
			}
		case SUBSIDIO_ESPECIFICO:
			try {
				return new TipoEspecificoSolicitudDTO("sol.solResultadoProceso",
						ResultadoProcesoEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"outer apply (select TOP(1) pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica WHERE pleSolicitudLiquidacionSubsidio = sls.slsId ORDER BY pleId DESC) as ple "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sol.solResultadoProceso = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sol.solResultadoProceso ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion in ('"+TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA.name()+"', '"+TipoProcesoLiquidacionEnum.RECONOCIMIENTO_DE_SUBSIDIOS.name()+"') ");
			} catch (Exception e) {
				return new TipoEspecificoSolicitudDTO("sls.slsEstadoLiquidacion",
						EstadoProcesoLiquidacionEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"outer apply (select top(1) pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica WHERE pleSolicitudLiquidacionSubsidio = sls.slsId ORDER BY pleId DESC) as ple "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sls.slsEstadoLiquidacion = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sls.slsEstadoLiquidacion ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion in ('"+TipoProcesoLiquidacionEnum.AJUSTES_DE_CUOTA.name()+"', '"+TipoProcesoLiquidacionEnum.RECONOCIMIENTO_DE_SUBSIDIOS.name()+"') ");
			}
		case SUBSIDIO_MASIVO:
			try {
				return new TipoEspecificoSolicitudDTO("sol.solResultadoProceso",
						ResultadoProcesoEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"left join (select pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica) as ple on ple.idSolicitud = slsId "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sol.solResultadoProceso = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sol.solResultadoProceso ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion = '"+TipoProcesoLiquidacionEnum.MASIVA.name()+"' ");
			} catch (Exception e) {
				return new TipoEspecificoSolicitudDTO("sls.slsEstadoLiquidacion",
						EstadoProcesoLiquidacionEnum.valueOf(estadoSolicitud),
						"solicitudLiquidacionSubsidio sls INNER JOIN solicitud sol ON sol.solId = sls.slsSolicitudGlobal "
								+"left join (select pleSolicitudLiquidacionSubsidio as idSolicitud, "
								+"(CASE WHEN pleEmpleador is not null THEN (SELECT per.perId FROM Empleador empl join empresa emp on empl.empEmpresa = emp.empId "
								+"join persona per on emp.empPersona = per.perId where empl.empId = pleEmpleador) "
								+"WHEN pleAfiliadoPrincipal is not null THEN (SELECT per.perId FROM Afiliado afi join persona per on afi.afiPersona = per.perId "
								+"where afi.afiId = pleAfiliadoPrincipal) "
								+"WHEN pleBeneficiarioDetalle is not null THEN (select per.perId from beneficiarioDetalle bed "
								+"join personaDetalle ped on bed.bedPersonaDetalle = ped.pedId "
								+"join persona per on ped.pedPersona = per.perId "
								+"where bed.bedId = pleBeneficiarioDetalle) "
								+"END) AS numeroIdPersona from personaLiquidacionEspecifica) as ple on ple.idSolicitud = slsId "
								+"left join persona per on per.perId = ple.numeroIdPersona ",
						"AND sls.slsEstadoLiquidacion = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sls.slsEstadoLiquidacion ELSE :estadoSolicitud END) AND sls.slsTipoLiquidacion = '"+TipoProcesoLiquidacionEnum.MASIVA.name()+"' ");
			} 
		case APORTES:
			if(EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.DESISTIDA || 
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.CANCELADA || 
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.RECHAZADA ||
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.APROBADA ||
			estadoSolicitud.equals("TODOS")) {
				estadoConsultaAportes = " AND (COALESCE(sol.solResultadoProceso, '') = "+
				"CASE WHEN :estadoSolicitud = 'TODOS' THEN COALESCE(sol.solResultadoProceso, '') "+
				"ELSE :estadoSolicitud END)";
			}else{
				estadoConsultaAportes = "AND se.soaEstadoSolicitud = :estadoSolicitud ";
			}
			logger.info("**__** estadoSolicitud DIFERENTE CERRADA DESITIDA O CANCELADA"+estadoConsultaAportes);
			logger.info("**__** estadoSolicitu "+estadoSolicitud);
			return new TipoEspecificoSolicitudDTO("se.soaEstadoSolicitud",
				EstadoSolicitudAporteEnum.valueOf(estadoSolicitud),
				" Solicitud sol LEFT JOIN SolicitudAporte se ON sol.solId = se.soaSolicitudGlobal"
				+ " LEFT JOIN persona per ON per.perNumeroIdentificacion = se.soanumeroidentificacion"
				+ " AND (se.soaNumeroIdentificacion IS NULL OR per.perNumeroIdentificacion IS NULL " 
				+ " OR se.soaNumeroIdentificacion = per.perNumeroIdentificacion) ", estadoConsultaAportes );
		case APORTES_CORRECCION:
				if(EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.DESISTIDA || 
			 EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.CANCELADA ||
			  EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.RECHAZADA ||
			 EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.APROBADA ||
			 estadoSolicitud.equals("TODOS")) {
					logger.info("**__** estadoSolicitud DIFERENTE CERRADA DESITIDA O CANCELADA");
				estadoConsultaCorreccion = "  sol.solResultadoProceso = (CASE WHEN :estadoSolicitud = 'TODOS' THEN sol.solResultadoProceso ELSE :estadoSolicitud END) ";
			}else{
				estadoConsultaCorreccion = "se.scaEstadoSolicitud = :estadoSolicitud";
			}
			return new TipoEspecificoSolicitudDTO("se.scaEstadoSolicitud",
					EstadoSolicitudAporteEnum.valueOf(estadoSolicitud),
					" SolicitudCorreccionAporte se INNER JOIN Solicitud sol ON sol.solId = se.scaSolicitudGlobal , Persona per ",
					" AND se.scaPersona = per.perId AND  "+estadoConsultaCorreccion);
					//se.scaEstadoSolicitud = :estadoSolicitud
		case APORTES_DEVOLUCION:
			if(EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.DESISTIDA || 
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.CANCELADA ||
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.RECHAZADA ||
			EstadoSolicitudAporteEnum.valueOf(estadoSolicitud) == EstadoSolicitudAporteEnum.APROBADA ||
			estadoSolicitud.equals("TODOS")) {
				logger.info("**__** estadoSolicitud DIFERENTE CERRADA DESITIDA O CANCELADA");
				estadoConsultaDevolucion = " AND (COALESCE(sol.solResultadoProceso, '') = "+
				"CASE WHEN :estadoSolicitud = 'TODOS' THEN COALESCE(sol.solResultadoProceso, '') "+
				"ELSE :estadoSolicitud END)";			
			}else{
				estadoConsultaDevolucion = " AND se.sdaEstadoSolicitud = :estadoSolicitud ";
			}
			return new TipoEspecificoSolicitudDTO("se.sdaEstadoSolicitud",
				EstadoSolicitudAporteEnum.valueOf(estadoSolicitud),
				" Solicitud sol LEFT JOIN SolicitudDevolucionAporte se ON se.sdaSolicitudGlobal = sol.solId"
				+ " LEFT JOIN persona per ON per.perid = se.sdaPersona"
				+ " AND (se.sdaPersona IS NULL OR  per.perId IS NULL " 
				+ " OR se.sdaPersona = per.perId )", estadoConsultaDevolucion); 
		case ASIGNACION_FOVIS:
			return new TipoEspecificoSolicitudDTO("se.safEstadoSolicitudAsignacion",
					EstadoSolicitudAsignacionEnum.valueOf(estadoSolicitud),
					" SolicitudAsignacion se INNER JOIN Solicitud sol ON sol.solId = se.safSolicitudGlobal , PostulacionFOVIS pfov, JefeHogar jeh, Afiliado afi, Persona per ",
					" AND pfov.pofSolicitudAsignacion = se.safId AND pfov.pofJefeHogar = jeh.jehId AND jeh.jehAfiliado = afi.afiId AND afi.afiPersona = per.perId AND se.safEstadoSolicitudAsignacion = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.safEstadoSolicitudAsignacion ELSE :estadoSolicitud END)");
		case CIERRE_RECAUDO:
			if(estadoSolicitud.equals("APROBADO_REGISTROS_CONCILIADOS") || estadoSolicitud.equals("RECHAZADA")){
				String condicionEstadoSolicitud = " AND sol.solResultadoProceso = :estadoSolicitud ";
				if(estadoSolicitud.equals("RECHAZADA")){
					condicionEstadoSolicitud = " AND sol.solResultadoProceso IN ('RECHAZADA_ANALISTA', 'RECHAZADA_SUPERVISOR', 'RECHAZADA_CONTABILIDAD') AND :estadoSolicitud = :estadoSolicitud";
				}
				return new TipoEspecificoSolicitudDTO("sol.solResultadoProceso",
						ResultadoProcesoEnum.valueOf(estadoSolicitud),
						" SolicitudCierreRecaudo se INNER JOIN Solicitud sol ON sol.solId = se.sciSolicitudGlobal LEFT JOIN Persona per ON per.perId = NULL ",
						condicionEstadoSolicitud);
			}else{
				return new TipoEspecificoSolicitudDTO("se.sciEstadoSolicitud",
						EstadoSolicitudCierreRecaudoEnum.valueOf(estadoSolicitud),
						" SolicitudCierreRecaudo se INNER JOIN Solicitud sol ON sol.solId = se.sciSolicitudGlobal LEFT JOIN Persona per ON per.perId = NULL ",
						" AND se.sciEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sciEstadoSolicitud ELSE :estadoSolicitud END)");
			}
			
		case DESAFILIACION_APORTANTES:

			return new TipoEspecificoSolicitudDTO("se.sodEstadoSolicitud",
					EstadoSolicitudDesafiliacionEnum.valueOf(estadoSolicitud),
					" SolicitudDesafiliacion se INNER JOIN Solicitud sol ON sol.solId = se.sodSolicitudGlobal , DesafiliacionAportante desap , Persona per ",
					" AND  desap.deaSolicitudDesafiliacion = se.sodId AND desap.deaPersona = per.perId AND se.sodEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sodEstadoSolicitud ELSE :estadoSolicitud END) ");

		case EMPLEADOR:

			return new TipoEspecificoSolicitudDTO("se.saeEstadoSolicitud",
					EstadoSolicitudAfiliacionEmpleadorEnum.valueOf(estadoSolicitud),
					" SolicitudAfiliaciEmpleador se INNER JOIN Solicitud sol ON sol.solId = se.saeSolicitudGlobal , Empleador emple  INNER JOIN Empresa empres ON emple.empEmpresa = empres.empId INNER JOIN Persona per ON per.perId =  empres.empPersona",
					" AND se.saeEmpleador = emple.empId AND  se.saeEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.saeEstadoSolicitud ELSE :estadoSolicitud END) ");

		case ENTIDAD_PAGADORA:

			return new TipoEspecificoSolicitudDTO("se.soaEstado",
					EstadoSolicitudPersonaEntidadPagadoraEnum.valueOf(estadoSolicitud),
					" SolicitudAsociacionPersonaEntidadPagadora se INNER JOIN Solicitud sol ON sol.solId = se.soaSolicitudGlobal  INNER JOIN RolAfiliado rolaf ON rolaf.roaId = se.soaRolAfiliado INNER JOIN Afiliado afil ON  afil.afiId = rolaf.roaAfiliado INNER JOIN Persona per ON per.perId = afil.afiPersona ",
					" AND  se.soaEstado = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.soaEstado ELSE :estadoSolicitud END)");

		case FISCALIZACION:

			return new TipoEspecificoSolicitudDTO("se.sfiEstadoFiscalizacion",
					EstadoFiscalizacionEnum.valueOf(estadoSolicitud),
					" SolicitudFiscalizacion se INNER JOIN  Solicitud sol ON sol.solId = se.sfiSolicitudGlobal INNER JOIN CicloAportante ciclap ON ciclap.capId = se.sfiCicloAportante ,  Persona per ",
					" AND ciclap.capPersona = per.perId AND se.sfiEstadoFiscalizacion = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.sfiEstadoFiscalizacion ELSE :estadoSolicitud END)");

		case GESTION_COBRO_ELECTRONICO:

			return new TipoEspecificoSolicitudDTO("se.sgeEstado", EstadoSolicitudGestionCobroEnum.valueOf(estadoSolicitud),
					" SolicitudGestionCobroElectronico se INNER JOIN Solicitud sol ON sol.solId = se.sgeSolicitud , Cartera cart , Persona per ",
					" AND se.sgeCartera = cart.carId AND cart.carPersona = per.perId AND se.sgeEstado = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sgeEstado ELSE  :estadoSolicitud END) ");

		case GESTION_COBRO_FISICO:

			return new TipoEspecificoSolicitudDTO("se.sgfEstado", EstadoSolicitudGestionCobroEnum.valueOf(estadoSolicitud),
					" SolicitudGestionCobroFisico se INNER JOIN Solicitud sol ON sol.solId = se.sgfSolicitud  , DetalleSolicitudGestionCobro detsol , Cartera cart , Persona per ",
					" AND detsol.dsgSolicitudPrimeraRemision = se.sgfId AND cart.carId = detsol.dsgCartera AND cart.carPersona = per.perId AND se.sgfEstado = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sgfEstado ELSE :estadoSolicitud END) ");

		case GESTION_COBRO_MANUAL:

			return new TipoEspecificoSolicitudDTO("se.scmEstadoSolicitud",
					EstadoFiscalizacionEnum.valueOf(estadoSolicitud),
					" SolicitudGestionCobroManual se INNER JOIN Solicitud sol ON sol.solId = se.scmSolicitudGlobal , CicloAportante ciclapo , Persona per ",
					" AND ciclapo.capId = se.scmCicloAportante AND ciclapo.capPersona = per.perId AND  se.scmEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.scmEstadoSolicitud  ELSE :estadoSolicitud END) ");

		case GESTION_PREVENTIVA:

			return new TipoEspecificoSolicitudDTO("se.sprEstadoSolicitudPreventiva",
					EstadoSolicitudPreventivaEnum.valueOf(estadoSolicitud),
					" SolicitudPreventiva se INNER JOIN Solicitud sol ON sol.solId = se.sprSolicitudGlobal , Persona per ",
					" AND se.sprPersona = per.perId AND  se.sprEstadoSolicitudPreventiva = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sprEstadoSolicitudPreventiva ELSE :estadoSolicitud END) ");

		case LEGALIZACION_FOVIS:

			return new TipoEspecificoSolicitudDTO("se.sldEstadoSolicitud",
					EstadoSolicitudLegalizacionDesembolsoEnum.valueOf(estadoSolicitud),
					" SolicitudLegalizacionDesembolso se INNER JOIN Solicitud sol ON sol.solId = se.sldSolicitudGlobal , PostulacionFOVIS pofovis, JefeHogar jeh, Afiliado afi, Persona per ",
					" AND  se.sldPostulacionFOVIS = pofovis.pofId AND pofovis.pofJefeHogar = jeh.jehId AND jeh.jehAfiliado = afi.afiId AND afi.afiPersona = per.perId  AND se.sldEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sldEstadoSolicitud ELSE :estadoSolicitud END) ");

		case NOVEDAD_EMPLEADOR:

			return new TipoEspecificoSolicitudDTO("se.snoEstadoSolicitud",
					EstadoSolicitudNovedadEnum.valueOf(estadoSolicitud),
					" SolicitudNovedad se INNER JOIN Solicitud sol ON sol.solId = se.snoSolicitudGlobal, SolicitudNovedadEmpleador snem , Empleador empl INNER JOIN Empresa empr ON empr.empId = empl.empEmpresa INNER JOIN Persona per ON per.perId = empr.empPersona",
					" AND snem.sneIdSolicitudNovedad = se.snoId AND snem.sneIdEmpleador = empl.empId AND se.snoEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.snoEstadoSolicitud ELSE :estadoSolicitud END) ");

		case NOVEDAD_FOVIS:

			return new TipoEspecificoSolicitudDTO("se.snfEstadoSolicitud",
					EstadoSolicitudNovedadFovisEnum.valueOf(estadoSolicitud),
					" SolicitudNovedadFovis se INNER JOIN Solicitud sol ON sol.solId = se.snfSolicitudGlobal  , SolicitudNovedadPersonaFovis solnovperfov , Persona per ",
					" AND solnovperfov.spfSolicitudNovedadFovis = se.snfId AND solnovperfov.spfPersona = per.perId AND se.snfEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.snfEstadoSolicitud ELSE  :estadoSolicitud END) ");

		case NOVEDAD_FOVIS_ANALISIS:

			return new TipoEspecificoSolicitudDTO("se.sanEstadoSolicitud",
					EstadoSolicitudAnalisisNovedadFovisEnum.valueOf(estadoSolicitud),
					" SolicitudAnalisisNovedadFovis se INNER JOIN Solicitud sol ON sol.solId = se.sanSolicitudGlobal , Persona per ",
					" AND se.sanPersona = per.perId AND  se.sanEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sanEstadoSolicitud ELSE :estadoSolicitud END) ");

		case NOVEDAD_PERSONA:

			return new TipoEspecificoSolicitudDTO("se.snoEstadoSolicitud",
					EstadoSolicitudNovedadEnum.valueOf(estadoSolicitud),
					" SolicitudNovedad se INNER JOIN Solicitud sol ON sol.solId = se.snoSolicitudGlobal  , Persona per, SolicitudNovedadPersona snp ",
					" AND snp.snpSolicitudNovedad = se.snoId AND snp.snpPersona = per.perId AND se.snoEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.snoEstadoSolicitud ELSE  :estadoSolicitud END) ");

		case PERSONA:

			return new TipoEspecificoSolicitudDTO("se.sapEstadoSolicitud",
					EstadoSolicitudAfiliacionPersonaEnum.valueOf(estadoSolicitud),
					" SolicitudAfiliacionPersona se INNER JOIN Solicitud sol ON sol.solId = se.sapSolicitudGlobal INNER JOIN RolAfiliado rola ON rola.roaId = se.sapRolAfiliado  INNER JOIN Afiliado afil ON afil.afiId = rola.roaAfiliado INNER JOIN Persona per ON per.perId = afil.afiPersona ",
					" AND se.sapEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sapEstadoSolicitud ELSE :estadoSolicitud END) ");

		case POSTULACION_FOVIS:

			return new TipoEspecificoSolicitudDTO("se.spoEstadoSolicitud",
					EstadoSolicitudPostulacionEnum.valueOf(estadoSolicitud),
					" SolicitudPostulacion se INNER JOIN Solicitud sol ON sol.solId = se.spoSolicitudGlobal , PostulacionFOVIS posfov, JefeHogar jeh, Afiliado afi, Persona per ",
					" AND se.spoPostulacionFOVIS = posfov.pofId AND posfov.pofJefeHogar = jeh.jehId AND jeh.jehAfiliado = afi.afiId AND afi.afiPersona = per.perId AND se.spoEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.spoEstadoSolicitud ELSE :estadoSolicitud END)  ");

		case POSTULACION_FOVIS_GESTION_CRUCE:

			return new TipoEspecificoSolicitudDTO("se.sgcEstado", EstadoSolicitudGestionCruceEnum.valueOf(estadoSolicitud),
					" SolicitudGestionCruce se INNER JOIN  Solicitud sol ON sol.solId = se.sgcSolicitudGlobal , SolicitudPostulacion solpos , PostulacionFOVIS posfov, JefeHogar jeh, Afiliado afi, Persona per  ",
					" AND  se.sgcSolicitudPostulacion = solpos.spoId AND solpos.spoPostulacionFOVIS = posfov.pofId AND posfov.pofJefeHogar = jeh.jehId AND jeh.jehAfiliado = afi.afiId AND afi.afiPersona = per.perId AND  se.sgcEstado = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  se.sgcEstado ELSE  :estadoSolicitud END) ");

		case POSTULACION_FOVIS_VERIFICACION:

			return new TipoEspecificoSolicitudDTO("se.svfEstadoSolicitud",
					EstadoSolicitudVerificacionFovisEnum.valueOf(estadoSolicitud),
					" SolicitudVerificacionFovis se INNER JOIN  Solicitud sol ON sol.solId = se.svfSolicitudGlobal , PostulacionFOVIS posfov, JefeHogar jeh, Afiliado afi, Persona per  ",
					"  AND se.svfPostulacionFOVIS = posfov.pofId AND  posfov.pofJefeHogar = jeh.jehId AND jeh.jehAfiliado = afi.afiId AND afi.afiPersona = per.perId AND se.svfEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN se.svfEstadoSolicitud ELSE :estadoSolicitud END) ");
		case NOVEDAD_ARCHIVO:
			return new TipoEspecificoSolicitudDTO("sno.snoEstadoSolicitud", 
					EstadoSolicitudNovedadEnum.valueOf(estadoSolicitud), 
					" SolicitudNovedad sno INNER JOIN Solicitud sol ON (sno.snoSolicitudGlobal = sol.solId) JOIN (SELECT snp.snpSolicitudNovedad AS solicitudNovedadId, snp.snpPersona AS personaId "
						+ " FROM SolicitudNovedadPersona snp UNION SELECT sne.sneIdSolicitudNovedad, emp.empPersona FROM SolicitudNovedadEmpleador sne JOIN Empleador empl ON (sne.sneIdEmpleador = empl.empId) "
						+ " JOIN Empresa emp ON (empl.empEmpresa = emp.empId)) snp ON (sno.snoId = snp.solicitudNovedadId) JOIN Persona per ON (snp.personaId = per.perId) ", 
					" AND sol.solCanalRecepcion = 'ARCHIVO_ACTUALIZACION' AND sol.solInstanciaProceso IS NOT NULL AND sno.snoEstadoSolicitud = (CASE WHEN :estadoSolicitud = 'TODOS' THEN  sno.snoEstadoSolicitud ELSE :estadoSolicitud END)");
		default:
			logger.error("Finaliza obtenerInformacionTablaTipoSolicitud(TipoSolicitudEnum): "
					+ "No se obtiene información de una tabla para el Tipo de solicitud especificado");
			throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
		}
	}
}
