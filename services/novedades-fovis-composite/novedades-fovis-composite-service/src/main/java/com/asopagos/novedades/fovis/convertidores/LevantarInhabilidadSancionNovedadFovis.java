package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.novedades.fovis.clients.ConsultarInhabilidadSubsidioFovisPorDatosPersona;
import com.asopagos.novedades.fovis.clients.ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona;
import com.asopagos.novedades.fovis.clients.CrearActualizarListaInhabilidadSubsidioFovis;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar la inhabilidad de la persona procesada
 * @author Edward Castaño<ecastano@heinsohn.com.co>
 *
 */
public class LevantarInhabilidadSancionNovedadFovis implements NovedadFovisCore {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {

        // se obtienen los datos de la novedad Regular. 
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();
        
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();
        ClasificacionEnum clasificacion = solicitudNovedadDTO.getClasificacion();

        /* Se consulta la postulación asociada a la novedad */
        ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(solicitudNovedadDTO.getIdPostulacion());
        consultarPostulacionFOVIS.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();
        
        if (datosNovedadRegular.getRestituidoConSancion() != null && !datosNovedadRegular.getRestituidoConSancion() && postulacionFOVISModeloDTO.getRestituidoConSancion() != null) {
            postulacionFOVISModeloDTO.setRestituidoConSancion(Boolean.FALSE);
            // Actualizar la postulación
            CrearActualizarPostulacionFOVIS crearActualizarPostulacionFOVIS = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
            crearActualizarPostulacionFOVIS.execute();
        }
        
        List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades = new ArrayList<InhabilidadSubsidioFovisModeloDTO>();
        if (datosNovedadRegular.getInhabilitadoSubsidioVivienda() == null || datosNovedadRegular.getInhabilitadoSubsidioVivienda()) {
            CrearActualizarListaInhabilidadSubsidioFovis service = new CrearActualizarListaInhabilidadSubsidioFovis(listaInhabilidades);
            return service;
        }
        // Se obtiene el numero de identificacion 
        String numeroIdentificacion = null;
        TipoIdentificacionEnum tipoIdentificacion = null;
        
        if (TipoTransaccionEnum.LEVANTAR_INHABILIDAD_SANCION_HOGAR.equals(novedad)) {
            ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona inhabilidadesHogarService = new ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona(
                    datosNovedadRegular.getNumeroIdJefeHogar(), datosNovedadRegular.getTipoIdJefeHogar());
            inhabilidadesHogarService.execute();
            listaInhabilidades = inhabilidadesHogarService.getResult();
            if (listaInhabilidades != null && !listaInhabilidades.isEmpty()) {
                for (InhabilidadSubsidioFovisModeloDTO inhabilidadIntegrante : listaInhabilidades) {
                    inhabilidadIntegrante.setInhabilitadoParaSubsidio(Boolean.FALSE);
                    inhabilidadIntegrante.setFechaFinInhabilidad((new Date()).getTime());
                }
            }
        } else if (ClasificacionEnum.JEFE_HOGAR.equals(clasificacion)) {
            numeroIdentificacion = datosNovedadRegular.getNumeroIdJefeHogar();
            tipoIdentificacion = datosNovedadRegular.getTipoIdJefeHogar();
        } else {
            numeroIdentificacion = datosNovedadRegular.getNumeroIdIntegrante();
            tipoIdentificacion = datosNovedadRegular.getTipoIdIntegrante();
        }
        
        if (numeroIdentificacion != null && tipoIdentificacion != null) {
            // Se consulta el registro de inhabilidad y se actualiza para levantar la inhabilidad
            ConsultarInhabilidadSubsidioFovisPorDatosPersona consultarInhabilidadSubsidioFovisPorDatosPersona = new ConsultarInhabilidadSubsidioFovisPorDatosPersona(
                    numeroIdentificacion, tipoIdentificacion);
            consultarInhabilidadSubsidioFovisPorDatosPersona.execute();
            InhabilidadSubsidioFovisModeloDTO inhabilidadDTO = consultarInhabilidadSubsidioFovisPorDatosPersona.getResult();
            if (inhabilidadDTO != null) {
                inhabilidadDTO.setInhabilitadoParaSubsidio(Boolean.FALSE);
                inhabilidadDTO.setFechaFinInhabilidad((new Date()).getTime());
                listaInhabilidades.add(inhabilidadDTO);
            }
        }
        CrearActualizarListaInhabilidadSubsidioFovis service = new CrearActualizarListaInhabilidadSubsidioFovis(listaInhabilidades);
        return service;
    }


}
