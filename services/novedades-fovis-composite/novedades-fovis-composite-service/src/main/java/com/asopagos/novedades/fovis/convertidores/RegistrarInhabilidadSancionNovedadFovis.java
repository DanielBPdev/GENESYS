package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.fovis.composite.clients.EjecutarRegistroInhabilidad;
import com.asopagos.novedades.fovis.composite.dto.InhabilidadSubsidioFovisInDTO;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar la inhabilidad de la persona procesada
 * @author Edward Castaño<ecastano@heinsohn.com.co>
 *
 */
public class RegistrarInhabilidadSancionNovedadFovis implements NovedadFovisCore {


    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {

        // se obtienen los datos de la novedad Regular.
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        // Se obtienen los datos de la postulacion asociada a la novedad
        SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = solicitudNovedadDTO.getDatosPostulacion();

        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        // Objeto para el registro de inhabilidad
        InhabilidadSubsidioFovisInDTO inDTO = new InhabilidadSubsidioFovisInDTO();
        //Al registrar la inhabilidad para un hogar, se hará extensiva esta condición 
        // a cada uno de las personas que conforman el hogar, sin embargo no se cambiará el 
        // estado del hogar ni de las personas. Para efectuar estos cambios de estado, 
        // se debe registrar la novedad respectiva (Rechazo, Restitución, etc.)
        // Lista de integrantes del hogar
        if (datosNovedadRegular.getInhabilitadoSubsidioVivienda() == null || !datosNovedadRegular.getInhabilitadoSubsidioVivienda()) {
            EjecutarRegistroInhabilidad service = new EjecutarRegistroInhabilidad(inDTO);
            return service;
        }
        
        if (TipoTransaccionEnum.REGISTRAR_INHABILIDAD_SUBSIDIO_HOGAR.equals(novedad)) {
            // Se asocia la información de la postulación
            inDTO.setIdPostulacion(solicitudPostulacionFOVISDTO.getIdPostulacion());
            // Se asocia la información del jefe para el procesamiento
            inDTO.setTipoIdJefeHogar(datosNovedadRegular.getTipoIdJefeHogar());
            inDTO.setNumeroIdJefeHogar(datosNovedadRegular.getNumeroIdJefeHogar());
        } else if(ClasificacionEnum.JEFE_HOGAR.equals(solicitudNovedadDTO.getClasificacion())){
            // Se asocia la información del jefe
            inDTO.setTipoIdJefeHogar(datosNovedadRegular.getTipoIdJefeHogar());
            inDTO.setNumeroIdJefeHogar(datosNovedadRegular.getNumeroIdJefeHogar());
        }
        else {
            // Se asocia la información de la postulación
            inDTO.setIdPostulacion(solicitudPostulacionFOVISDTO.getIdPostulacion());
            // Se consulta el integrante del hogar
            inDTO.setTipoIdIntegrante(datosNovedadRegular.getTipoIdIntegrante());
            inDTO.setNumeroIdIntegrante(datosNovedadRegular.getNumeroIdIntegrante());
            // Se asocia la información del jefe para el procesamiento
            inDTO.setTipoIdJefeHogar(datosNovedadRegular.getTipoIdJefeHogar());
            inDTO.setNumeroIdJefeHogar(datosNovedadRegular.getNumeroIdJefeHogar());
        }

        EjecutarRegistroInhabilidad service = new EjecutarRegistroInhabilidad(inDTO);
        return service;
    }

}
