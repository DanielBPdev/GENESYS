package com.asopagos.novedades.fovis.convertidores;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ActualizarEstadoHogar;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para generar la novedad regular Renuncia a subsidio asignado
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class RenunciarSubsidioAsignadoNovedadFovis implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {
        String path = "RenunciarSubsidioAsignadoNovedadFovis.transformarServicio(SolicitudNovedadFovisDTO): ServiceClient";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        // se obtienen los datos de la novedad Regular
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        EstadoHogarEnum estadoHogarNuevo = null;
        if (TipoTransaccionEnum.RENUNCIO_SUBISIDIO_ASIGNADO.equals(novedad)) {
            estadoHogarNuevo = EstadoHogarEnum.RENUNCIO_A_SUBSIDIO_ASIGNADO;
        }

        //Se instancia el cliente del servicio que procesa la novedad
        ActualizarEstadoHogar service = new ActualizarEstadoHogar(solicitudNovedadDTO.getIdPostulacion(), estadoHogarNuevo);
        return service;
    }

}
