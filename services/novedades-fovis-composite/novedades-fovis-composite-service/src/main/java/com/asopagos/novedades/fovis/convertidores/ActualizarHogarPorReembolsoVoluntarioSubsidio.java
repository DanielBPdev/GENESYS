package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.PostulacionFovisDevDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ActualizarEstadoHogar;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFovisDev;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos del Hogar cuando hay reembolso del subsidio
 * @author Alexander Quintero <alquintero@heinsohn.com.co>
 *
 */
public class ActualizarHogarPorReembolsoVoluntarioSubsidio implements NovedadFovisCore {

    private List<TipoTransaccionEnum> reembolsoSubsidio;

    private final ILogger logger = LogManager.getLogger(ActualizarHogarPorReembolsoVoluntarioSubsidio.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {

        /* se obtienen los datos de la novedad Regular. */
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        /* Se consulta la postulación asociada a la novedad */
        ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(solicitudNovedadDTO.getIdPostulacion());
        consultarPostulacionFOVIS.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();

        /* Asocia los tipos de Novedad de Persona. */
        this.llenarListaNovedades();

        if (TipoTransaccionEnum.REEMBOLSO_VOLUNTARIO_SUBSIDIO.equals(novedad)) {
            postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.SUBSIDIO_REEMBOLSADO);
            PostulacionFovisDevDTO postulacionFovisDev = new PostulacionFovisDevDTO();
            postulacionFovisDev.setFechaRegistro(new Date());
            postulacionFovisDev.setMedioPago(datosNovedadRegular.getMedioPago().name());
            postulacionFovisDev.setPostulacionfovis(postulacionFOVISModeloDTO.getIdPostulacion());
            postulacionFovisDev.setNombreCompleto(datosNovedadRegular.getNombreCompleto());
            postulacionFovisDev.setNumeroIdQuienHaceDevolucion(datosNovedadRegular.getNumeroIdQuienHaceDevolucion());
            postulacionFovisDev.setRendimientofinanciero(datosNovedadRegular.getRendimientoFinanciero().intValue());
            postulacionFovisDev.setSolicitudglobal(solicitudNovedadDTO.getIdSolicitud());
            postulacionFovisDev.setTipoIdQuienHaceDevolucion(datosNovedadRegular.getTipoIdQuienHaceDevolucion().name());
            postulacionFovisDev.setValorRestituir(datosNovedadRegular.getValorRestituir().intValue());
            logger.info("postulacionFovisDev " + postulacionFovisDev.toString());
            CrearActualizarPostulacionFovisDev crearActualizarPostulacionFovisDev = new CrearActualizarPostulacionFovisDev(postulacionFovisDev);
            crearActualizarPostulacionFovisDev.execute();
            logger.info("crearActualizarPostulacionFovisDev " + crearActualizarPostulacionFovisDev);


        }

        ActualizarEstadoHogar actualizarEstadoHogar = new ActualizarEstadoHogar(postulacionFOVISModeloDTO.getIdPostulacion(), postulacionFOVISModeloDTO.getEstadoHogar());
        return actualizarEstadoHogar;
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void llenarListaNovedades() {

        reembolsoSubsidio = new ArrayList<>();
        reembolsoSubsidio.add(TipoTransaccionEnum.REEMBOLSO_VOLUNTARIO_SUBSIDIO);
    }

}
