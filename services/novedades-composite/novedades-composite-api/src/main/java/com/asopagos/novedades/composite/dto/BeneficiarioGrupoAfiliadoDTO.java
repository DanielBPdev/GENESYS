package com.asopagos.novedades.composite.dto;

import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;

/**
 * DTO que contiene los datos de desafiliación.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class BeneficiarioGrupoAfiliadoDTO {
    
	 /**
     * Variable beneficiario
     */
    private BeneficiarioModeloDTO beneficiario;
    
    /**
     * Variable grupo familiar
     */
    private GrupoFamiliarModeloDTO grupoFamiliar;
    
    /**
     * Variable afiliado
     */
    private AfiliadoModeloDTO afiliado;
    
    /**
     * Informacion solicitud novedad
     */
    private SolicitudNovedadDTO solicitudNovedadDTO;

	/**
	 * Método que retorna el valor de beneficiario.
	 * @return valor de beneficiario.
	 */
	public BeneficiarioModeloDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * Método encargado de modificar el valor de beneficiario.
	 * @param valor para modificar beneficiario.
	 */
	public void setBeneficiario(BeneficiarioModeloDTO beneficiario) {
		this.beneficiario = beneficiario;
	}

	/**
	 * Método que retorna el valor de grupoFamiliar.
	 * @return valor de grupoFamiliar.
	 */
	public GrupoFamiliarModeloDTO getGrupoFamiliar() {
		return grupoFamiliar;
	}

	/**
	 * Método encargado de modificar el valor de grupoFamiliar.
	 * @param valor para modificar grupoFamiliar.
	 */
	public void setGrupoFamiliar(GrupoFamiliarModeloDTO grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	/**
	 * Método que retorna el valor de afiliado.
	 * @return valor de afiliado.
	 */
	public AfiliadoModeloDTO getAfiliado() {
		return afiliado;
	}

	/**
	 * Método encargado de modificar el valor de afiliado.
	 * @param valor para modificar afiliado.
	 */
	public void setAfiliado(AfiliadoModeloDTO afiliado) {
		this.afiliado = afiliado;
	}

    /**
     * @return the solicitudNovedadDTO
     */
    public SolicitudNovedadDTO getSolicitudNovedadDTO() {
        return solicitudNovedadDTO;
    }

    /**
     * @param solicitudNovedadDTO the solicitudNovedadDTO to set
     */
    public void setSolicitudNovedadDTO(SolicitudNovedadDTO solicitudNovedadDTO) {
        this.solicitudNovedadDTO = solicitudNovedadDTO;
    }
	        
}
