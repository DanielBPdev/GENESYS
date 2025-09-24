package com.asopagos.novedades.fovis.convertidores;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.empleadores.clients.GuardarDatosTemporalesEmpleador;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.fovis.clients.ActualizarIngresosMiembrosHogar;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.CrearActualizarDetalleNovedad;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * Clase que contiene la lógica para generar la novedad regular Ajuste y actualización valor SFV
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class AjusteActualizacionValorSFVNovedadFovis implements NovedadFovisCore {

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
        String path = "AjusteActualizacionValorSFVNovedadFovis.transformarServicio(SolicitudNovedadFovisDTO): ServiceClient";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        // se obtienen los datos de la novedad Regular
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        /* Se consulta la postulación asociada a la novedad */
        ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(solicitudNovedadDTO.getIdPostulacion());
        consultarPostulacionFOVIS.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();

        if (TipoTransaccionEnum.AJUSTE_ACTUALIZACION_VALOR_SFV.equals(novedad)) {
            postulacionFOVISModeloDTO.setValorAjusteIPCSFV(datosNovedadRegular.getValorAjusteIPCSFV());
        } else if (TipoTransaccionEnum.AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018.equals(novedad)) {
            DetalleNovedadFovisModeloDTO detalleNovedad = datosNovedadRegular.getDetalleNovedad();
            // Se realiza el registro de información detalle de novedad
            detalleNovedad.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedadFovis());
            CrearActualizarDetalleNovedad crearDetalleNovedadService = new CrearActualizarDetalleNovedad(detalleNovedad);
            crearDetalleNovedadService.execute();
            // Se realiza la actualización de los ingresos de los miembros
            ActualizarIngresosMiembrosHogar actualizarIngresosMiembrosHogar = new ActualizarIngresosMiembrosHogar(datosNovedadRegular.getListaMiembros());
            actualizarIngresosMiembrosHogar.execute();
            // Se actualiza el valor SFV Ajustado
            postulacionFOVISModeloDTO.setValorSFVAjustado(detalleNovedad.getValorSFVAprobado());
            solicitudNovedadDTO.getDatosPostulacion().getPostulacion().setValorSFVAjustado(detalleNovedad.getValorSFVAprobado());
            Date fecha = new Date();
            solicitudNovedadDTO.setFechaRadicacion(fecha.getTime());

        }
        //Se instancia el cliente del servicio que procesa la novedad
        CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
        logger.info("solicitudNovedadDTO " + solicitudNovedadDTO.getDatosPostulacion().getPostulacion().toString());
        GuardarDatosTemporalesEmpleador datosTemporales = new GuardarDatosTemporalesEmpleador(solicitudNovedadDTO.getIdSolicitud(), null, null, solicitudNovedadDTO.toString());
        datosTemporales.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return service;
    }

}
