package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGestionProductoNoConformeSubsanableEnum;

/**
 *
 * @author sbrinez
 * 
 * @modificada:
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class GestionarProductoNoConformeSubsanableDTO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** resultado de la gestión de producto no conforme */
	private ResultadoGestionProductoNoConformeSubsanableEnum resultadoGestion;

	/** identificación de la solicitud global */
	private Long idSolicitudGlobal;
	
	private List<ProductoNoConformeDTO> productosNoConformeDTO;
	
	private List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> beneficiariosDTO;
	
	private String observaciones;
	
	/**
	 * bandera para identificar si la correcion es del back o de otro usuario
	 */
	private Boolean corrigeBack;
	
	/**
	 * Identificador de la tarea
	 */
	private Long idTarea;
	
	/**
	 * @return the resultadoGestion
	 */
	public ResultadoGestionProductoNoConformeSubsanableEnum getResultadoGestion() {
		return resultadoGestion;
	}

	/**
	 * @param resultadoGestion the resultadoGestion to set
	 */
	public void setResultadoGestion(ResultadoGestionProductoNoConformeSubsanableEnum resultadoGestion) {
		this.resultadoGestion = resultadoGestion;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

    /**
     * @return the productosNoConformeDTO
     */
    public List<ProductoNoConformeDTO> getProductosNoConformeDTO() {
        return productosNoConformeDTO;
    }

    /**
     * @param productosNoConformeDTO the productosNoConformeDTO to set
     */
    public void setProductosNoConformeDTO(List<ProductoNoConformeDTO> productosNoConformeDTO) {
        this.productosNoConformeDTO = productosNoConformeDTO;
    }

    /**
     * @return the corrigeBack
     */
    public Boolean getCorrigeBack() {
        return corrigeBack;
    }

    /**
     * @param corrigeBack the corrigeBack to set
     */
    public void setCorrigeBack(Boolean corrigeBack) {
        this.corrigeBack = corrigeBack;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the beneficiariosDTO
     */
    public List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> getBeneficiariosDTO() {
        return beneficiariosDTO;
    }

    /**
     * @param beneficiariosDTO the beneficiariosDTO to set
     */
    public void setBeneficiariosDTO(List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> beneficiariosDTO) {
        this.beneficiariosDTO = beneficiariosDTO;
    }

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
    
}
