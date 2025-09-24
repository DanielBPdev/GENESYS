package com.asopagos.novedades.fovis.convertidores;
/**
 * 
 */

import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.novedades.fovis.clients.ConsultarInhabilidadSubsidioFovisPorDatosPersona;
import com.asopagos.novedades.fovis.clients.CrearActualizarInhabilidadSubsidioFovis;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos del Integrante Hogar
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ConformacionNuevoHogarNovedadFovis implements NovedadFovisCore {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadFovisDTO solicitudNovedadDTO) {

        /* se obtienen los datos de la novedad Regular. */
        DatosFOVISNovedadRegularDTO datosNovedadRegular = solicitudNovedadDTO.getDatosNovedadRegularFovisDTO();

        ConsultarInhabilidadSubsidioFovisPorDatosPersona consultarDatosPersona;
        if (ClasificacionEnum.JEFE_HOGAR.equals(solicitudNovedadDTO.getClasificacion())) {
            consultarDatosPersona = new ConsultarInhabilidadSubsidioFovisPorDatosPersona(datosNovedadRegular.getNumeroIdJefeHogar(),
                    datosNovedadRegular.getTipoIdJefeHogar());
        }
        else {
            consultarDatosPersona = new ConsultarInhabilidadSubsidioFovisPorDatosPersona(datosNovedadRegular.getNumeroIdIntegrante(),
                    datosNovedadRegular.getTipoIdIntegrante());
        }
        consultarDatosPersona.execute();
        InhabilidadSubsidioFovisModeloDTO inhabilidadDTO = consultarDatosPersona.getResult();

        if (inhabilidadDTO != null) {
            inhabilidadDTO.setInhabilitadoParaSubsidio(Boolean.FALSE);
        }
        else {
            inhabilidadDTO = new InhabilidadSubsidioFovisModeloDTO();
        }
        CrearActualizarInhabilidadSubsidioFovis actualizarMarcaInhabilidad = new CrearActualizarInhabilidadSubsidioFovis(inhabilidadDTO);
        return actualizarMarcaInhabilidad;
    }

}
