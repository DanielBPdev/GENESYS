package com.asopagos.novedades.fovis.convertidores;
/**
 *
 */

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.empresas.clients.CrearActualizarOferente;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarProyectoSolucionVivienda;
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
public class CambioDatosBasicosSolucionVivienda implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {
        try {
            /* se obtienen los datos de la novedad Regular. */
            DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
            TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();
            SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = solicitudNovedadDTO.getDatosPostulacion();
            ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaModeloDTO = null;

            logger.info("ingresa a la novedad CAMBIO_DATOS_BASICOS_SOLUCION_VIVIENDA");
            logger.info("datosNovedadRegular  " + datosNovedadRegular);
            logger.info("novedad  " + novedad);

            ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(solicitudNovedadDTO.getIdPostulacion());
            consultarPostulacionFOVIS.execute();
            PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();
            logger.info("solicitudNovedadDTO.getDatosPostulacion().getOferente().getOferente()" +solicitudNovedadDTO.getDatosPostulacion().getOferente());
            if(solicitudNovedadDTO.getDatosPostulacion().getPostulacion().getProyectoSolucionVivienda() != null && solicitudNovedadDTO.getDatosPostulacion().getOferente() != null){
                CrearActualizarProyectoSolucionVivienda crearActualizarProyectoSolucionVivienda = new CrearActualizarProyectoSolucionVivienda(solicitudNovedadDTO.getDatosPostulacion().getPostulacion().getProyectoSolucionVivienda());
                crearActualizarProyectoSolucionVivienda.execute();
                proyectoSolucionViviendaModeloDTO = crearActualizarProyectoSolucionVivienda.getResult();
            }

            logger.info("proyectoSolucionViviendaModeloDTO " + proyectoSolucionViviendaModeloDTO);

            postulacionFOVISModeloDTO.setProyectoSolucionVivienda(proyectoSolucionViviendaModeloDTO);
            postulacionFOVISModeloDTO.setOferente(solicitudNovedadDTO.getDatosPostulacion().getOferente() != null ? solicitudNovedadDTO.getDatosPostulacion().getOferente().getOferente() : null);
            postulacionFOVISModeloDTO.setUbicacionVivienda(proyectoSolucionViviendaModeloDTO != null ? solicitudNovedadDTO.getDatosPostulacion().getPostulacion().getUbicacionVivienda() : null);
            postulacionFOVISModeloDTO.setUbicacionViviendaMismaProyecto(solicitudNovedadDTO.getDatosPostulacion().getPostulacion().getUbicacionViviendaMismaProyecto());
            solicitudPostulacionFOVISDTO.setPostulacion(postulacionFOVISModeloDTO);
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(solicitudPostulacionFOVISDTO);
            postulacionFOVISModeloDTO.setInformacionPostulacion(jsonPayload);
            CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
            service.execute();
            postulacionFOVISModeloDTO = service.getResult();
            return service;
        }catch (Exception e){
            logger.info("Genero error " + e);
        }
        return null;
    }
}
