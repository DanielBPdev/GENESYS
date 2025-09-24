package com.asopagos.dto;

import javax.validation.constraints.NotNull;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

public class ActualizarExclusionSumatoriaSalarioDTO {

    private DatosPersonaNovedadDTO datosPersonaNovedadDTO;

    private TipoTransaccionEnum novedad;

    public ActualizarExclusionSumatoriaSalarioDTO() {
    }

    public ActualizarExclusionSumatoriaSalarioDTO(DatosPersonaNovedadDTO datosPersonaNovedadDTO, TipoTransaccionEnum novedad) {
        this.datosPersonaNovedadDTO = datosPersonaNovedadDTO;
        this.novedad = novedad;
    }

    public DatosPersonaNovedadDTO getDatosPersonaNovedadDTO() {
        return datosPersonaNovedadDTO;
    }

    public void setDatosPersonaNovedadDTO(DatosPersonaNovedadDTO datosPersonaNovedadDTO) {
        this.datosPersonaNovedadDTO = datosPersonaNovedadDTO;
    }

    public TipoTransaccionEnum getNovedad() {
        return novedad;
    }

    public void setNovedad(TipoTransaccionEnum novedad) {
        this.novedad = novedad;
    }
}
