package com.asopagos.novedades.fovis.convertidores;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.CrearActualizarDetalleNovedad;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para generar la novedad regular Autorización de enajenación de vivienda subsidiada
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class AutorizacionEnajenacionViviendaSubsidiadaNovedadFovis implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    /**
     * Lista del tipo de transaccion a procesar
     */
    private List<TipoTransaccionEnum> autorizacionEnajenacion;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {
        String path = "AutorizacionEnajenacionViviendaSubsidiadaNovedadFovis.transformarServicio(SolicitudNovedadFovisDTO): ServiceClient";
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

            if (TipoTransaccionEnum.AUTORIZACION_ENAJENACION_VIVIENDA_SUBSIDIADA.equals(novedad)) {
                postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.SUBSIDIO_PENDIENTE_REEMBOLSAR);
                postulacionFOVISModeloDTO.setMotivoEnajenacion(datosNovedadRegular.getMotivoEnajenacion());
                // Se realiza el registro de dellate de novedad para asociar el documento soporte de la novedad 
                DetalleNovedadFovisModeloDTO detalleNovedad = new DetalleNovedadFovisModeloDTO();
                detalleNovedad.setIdentificadorDocumentoSoporte(datosNovedadRegular.getDocumentoSoporte());
                detalleNovedad.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedadFovis());
                CrearActualizarDetalleNovedad crearDetalleNovedadService =  new CrearActualizarDetalleNovedad(detalleNovedad);
                crearDetalleNovedadService.execute();
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
        autorizacionEnajenacion = new ArrayList<TipoTransaccionEnum>();
        autorizacionEnajenacion.add(TipoTransaccionEnum.AUTORIZACION_ENAJENACION_VIVIENDA_SUBSIDIADA);

    }

}
