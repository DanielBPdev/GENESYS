package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.novedades.fovis.composite.clients.ActualizarHogarYMovilizacionAhorros;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos del Integrante Hogar
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarHogarMovilizacionAhorrosNovedadFovis implements NovedadFovisCore {

    private List<TipoTransaccionEnum> listaNovedades;

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
        if (!listaNovedades.contains(novedad)) {
            return null;
        }
        switch (novedad) {
            case DESISTIMIENTO_POSTULACION:
                postulacionFOVISModeloDTO.setMotivoDesistimientoPostulacion(datosNovedadRegular.getMotivoDesistimientoPostulacion());
                postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.HOGAR_DESISTIO_POSTULACION);
                break;
            case MOVILIZACION_AHORRO_PREVIO_PAGO_OFERENTE:
                // TODO estado hogar
                // Sei se envio requerida la movilización de ahorro se movilizan los ahorros
                if (datosNovedadRegular.getRequiereMovilizacionAhorroPagoOferente() != null
                        && datosNovedadRegular.getRequiereMovilizacionAhorroPagoOferente()) {
                    datosNovedadRegular.setAhorroEvaluacionCrediticiaMovilizado(Boolean.TRUE);
                    datosNovedadRegular.setAhorroProgramadoMovilizado(Boolean.TRUE);
                    datosNovedadRegular.setCesantiasMovilizado(Boolean.TRUE);
                }
                break;
            default:
                break;
        }
        postulacionFOVISModeloDTO.setAhorroEvaluacionCrediticiaMobilizado(datosNovedadRegular.getAhorroEvaluacionCrediticiaMovilizado());
        postulacionFOVISModeloDTO.setAhorroProgramadoMobilizado(datosNovedadRegular.getAhorroProgramadoMovilizado());
        postulacionFOVISModeloDTO.setCesantiasMovilizado(datosNovedadRegular.getCesantiasMovilizado());

        ActualizarHogarYMovilizacionAhorros actualizarHogarYMovilizacionAhorros = new ActualizarHogarYMovilizacionAhorros(
                postulacionFOVISModeloDTO);

        return actualizarHogarYMovilizacionAhorros;
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void llenarListaNovedades() {
        listaNovedades = new ArrayList<>();
        listaNovedades.add(TipoTransaccionEnum.DESISTIMIENTO_POSTULACION);
        listaNovedades.add(TipoTransaccionEnum.MOVILIZACION_AHORRO_PREVIO_HOGAR_NO_ASIGNADO);
        listaNovedades.add(TipoTransaccionEnum.MOVILIZACION_AHORRO_PREVIO_PAGO_OFERENTE);
    }

}
