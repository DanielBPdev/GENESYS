package com.asopagos.novedades.fovis.convertidores;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para generar la novedad regular Habilitación de postulación rechazada
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class HabilitarPostulacionRechazadaNovedadFovis implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    /**
     * Lista del tipo de transaccion a procesar
     */
    private List<TipoTransaccionEnum> listaNovedadPostulacion;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {
        String path = "HabilitarPostulacionRechazadaNovedadFovis.transformarServicio(SolicitudNovedadFovisDTO): ServiceClient";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // se obtienen los datos de la novedad Regular
            DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
            TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

            /* Se consulta la postulación asociada a la novedad */
            ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(solicitudNovedadDTO.getIdPostulacion());
            consultarPostulacionFOVIS.execute();
            PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();

            /* Asocia los tipos de Novedad de FOVIS. */
            this.llenarListaNovedades();
            logger.info("Inicia "+datosNovedadRegular.getCicloAsignacionDestino());
            if (TipoTransaccionEnum.HABILITACION_POSTULACION_RECHAZADA.equals(novedad)) {
                postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.HABIL);
                //Se valida si se evnia motido de habilitacion
                if (datosNovedadRegular.getMotivoHabilitacion() != null) {
                    postulacionFOVISModeloDTO.setMotivoHabilitacion(datosNovedadRegular.getMotivoHabilitacion());
                }
                logger.info("Inicia 2"+datosNovedadRegular);
                postulacionFOVISModeloDTO.setIdCicloAsignacion(datosNovedadRegular.getCicloAsignacionDestino());
            }

            //Se instancia el cliente del servicio que procesa la novedad
            CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
            return service;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void llenarListaNovedades() {
        listaNovedadPostulacion = new ArrayList<TipoTransaccionEnum>();
        listaNovedadPostulacion.add(TipoTransaccionEnum.HABILITACION_POSTULACION_RECHAZADA);

    }

}
