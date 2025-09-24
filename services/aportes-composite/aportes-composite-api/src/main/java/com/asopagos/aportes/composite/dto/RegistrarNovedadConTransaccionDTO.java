package com.asopagos.aportes.composite.dto;

import java.util.List;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import java.util.List;

public class RegistrarNovedadConTransaccionDTO {
	private NovedadPilaDTO novedadPilaDTO;
	private PersonaModeloDTO personaCotizante;


    public NovedadPilaDTO getNovedadPilaDTO() {
        return this.novedadPilaDTO;
    }

    public void setNovedadPilaDTO(NovedadPilaDTO novedadPilaDTO) {
        this.novedadPilaDTO = novedadPilaDTO;
    }

    public PersonaModeloDTO getPersonaCotizante() {
        return this.personaCotizante;
    }

    public void setPersonaCotizante(PersonaModeloDTO personaCotizante) {
        this.personaCotizante = personaCotizante;
    }

}