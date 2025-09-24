package com.asopagos.novedades.fovis.convertidores;
/**
 *
 */

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarCicloAsignacion;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Clase que contiene la lógica para actualizar los datos del Integrante Hogar
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class HabilitarPostulacionSuspendidaXCambioAnio implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO)  {
        try {
            /* se obtienen los datos de la novedad Regular. */
            DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
            TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

            logger.info("ingresa a la novedad HABILITACION_POSTULACION_SUSPENDIDA_CAMBIO_ANIO");
            SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = solicitudNovedadDTO.getDatosPostulacion();
            PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = solicitudNovedadDTO.getDatosPostulacion().getPostulacion();
            postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.HABIL_SEGUNDO_ANIO);

            ConsultarCicloAsignacion cicloAsignacion = new ConsultarCicloAsignacion(datosNovedadRegular.getCicloAsignacionDestino());
            cicloAsignacion.execute();
            CicloAsignacionModeloDTO cicloAsignacionModeloDTO = cicloAsignacion.getResult();
            postulacionFOVISModeloDTO.setIdCicloAsignacion(datosNovedadRegular.getCicloAsignacionDestino());
            postulacionFOVISModeloDTO.setCicloAsignacion(cicloAsignacionModeloDTO);
            solicitudPostulacionFOVISDTO.setPostulacion(postulacionFOVISModeloDTO);
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(solicitudPostulacionFOVISDTO);
            postulacionFOVISModeloDTO.setInformacionPostulacion(jsonPayload);
            CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
            service.execute();
            postulacionFOVISModeloDTO = service.getResult();
            return service;
        }catch(Exception e){
            logger.info("Genero error " + e);
        }
        return null;
    }
}
