package com.asopagos.novedades.fovis.convertidores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.PostulacionFovisDevDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFovisDev;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.clients.EjecutarRegistroInhabilidad;
import com.asopagos.novedades.fovis.composite.dto.InhabilidadSubsidioFovisInDTO;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.ejb.NovedadesConvertidorFovisCompositeBusiness;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para generar la novedad regular de Restitución de susbsidio por incumplimiento
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 *
 */
public class RestitucionSubsidioIncumplimientoNovedadFovis implements NovedadFovisCore {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    /**
     * Lista del tipo de transaccion a procesar
     */
    private List<TipoTransaccionEnum> restitucionPostulacion;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {
        String path = "RestitucionSubsidioIncumplimientoNovedadFovis.transformarServicio(SolicitudNovedadFovisDTO): ServiceClient";
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

            if (TipoTransaccionEnum.RESTITUCION_SUBSIDIO_INCUMPLIMIENTO.equals(novedad)) {
                //Cambia el estado del hogar, puede tomar los siguientes valores:
                //Restituido sin sanción
                //Restituido con sanción (si el campo "Hogar sancionado" tiene el valor si y el campo "tiempo de sanción" es mayor que cero)
                if (datosNovedadRegular.getRestituidoConSancion() != null && datosNovedadRegular.getRestituidoConSancion()
                        && datosNovedadRegular.getTiempoSancion() != null) {
                    postulacionFOVISModeloDTO.setRestituidoConSancion(datosNovedadRegular.getRestituidoConSancion());
                    postulacionFOVISModeloDTO.setTiempoSancion(datosNovedadRegular.getTiempoSancion());
                    postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.RESTITUIDO_CON_SANCION);
                }
                else {
                    postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.RESTITUIDO_SIN_SANCION);
                }
                //Se asocia el motivo de restitución
                postulacionFOVISModeloDTO.setMotivoRestitucion(datosNovedadRegular.getMotivoRestitucion());
                // Se registra la inhabilidad al hogar
                InhabilidadSubsidioFovisInDTO inDTO = new InhabilidadSubsidioFovisInDTO();
                inDTO.setIdPostulacion(postulacionFOVISModeloDTO.getIdPostulacion());
                inDTO.setNumeroIdJefeHogar(datosNovedadRegular.getNumeroIdJefeHogar());
                inDTO.setTipoIdJefeHogar(datosNovedadRegular.getTipoIdJefeHogar());
                EjecutarRegistroInhabilidad ejecutarRegistroInhabilidad = new EjecutarRegistroInhabilidad(inDTO);
                ejecutarRegistroInhabilidad.execute();

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
        restitucionPostulacion = new ArrayList<TipoTransaccionEnum>();
        restitucionPostulacion.add(TipoTransaccionEnum.RESTITUCION_SUBSIDIO_INCUMPLIMIENTO);

    }

}
