package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.personas.clients.ActualizarDatosPersona;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos del Integrante Hogar
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarIntegranteHogarNovedadFovis implements NovedadFovisCore {

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {

        /* se obtienen los datos de la novedad Regular. */
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        /* Se consulta la Persona Integrante Hogar. */
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(datosNovedadRegular.getNumeroIdIntegrante(),
                datosNovedadRegular.getTipoIdIntegrante());
        consultarDatosPersona.execute();
        PersonaModeloDTO personaModeloDTO = consultarDatosPersona.getResult();

        switch (novedad) {
            case CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD:
                personaModeloDTO.setTipoIdentificacion(datosNovedadRegular.getTipoIdIntegranteNuevo());
                personaModeloDTO.setNumeroIdentificacion(datosNovedadRegular.getNumeroIdIntegranteNuevo());
                break;
            case CAMBIO_NOMBRES_APELLIDOS:
                personaModeloDTO.setPrimerApellido(datosNovedadRegular.getPrimerApellidoIntegrante());
                personaModeloDTO.setPrimerNombre(datosNovedadRegular.getPrimerNombreIntegrante());
                personaModeloDTO.setSegundoApellido(datosNovedadRegular.getSegundoApellidoIntegrante());
                personaModeloDTO.setSegundoNombre(datosNovedadRegular.getSegundoNombreIntegrante());
                break;
            default:
                break;
        }
        ActualizarDatosPersona actualizarDatosPersona = new ActualizarDatosPersona(personaModeloDTO);
        return actualizarDatosPersona;
    }
}
