package com.asopagos.subsidiomonetario.load.source;

import java.util.Map;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;

/**
 * <b>Descripcion:</b> Clase que se encarga de definir los atributos utilizados como filtros en la generación del archivo de liquidación
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-442, HU 317-266<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoLiquidacionFilterDTO extends QueryFilterInDTO {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo que representa el número de radicación asociado a la solicitud de liquidación de subsidio monetario
     */
    private String numeroRadicacion;

    /**
     * Descuentos agrupados por los beneficiarios
     */
    private Map<String, String> descuentosBeneficiarios;
    
    /**
     * indica el tipo de identificación del empleador en caso de la vista 360
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * indica el número de identificación del empleador en caso de la vista 360
     */
    private String numeroIdentificacion;

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the descuentosBeneficiarios
     */
    public Map<String, String> getDescuentosBeneficiarios() {
        return descuentosBeneficiarios;
    }

    /**
     * @param descuentosBeneficiarios
     *        the descuentosBeneficiarios to set
     */
    public void setDescuentosBeneficiarios(Map<String, String> descuentosBeneficiarios) {
        this.descuentosBeneficiarios = descuentosBeneficiarios;
    }

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

}
