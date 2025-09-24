package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información detallada para un administrador asociado a un proceso de liqudiación especifica<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleResultadoPorAdministradorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de identificación de administrador de subsidio */
    private TipoIdentificacionEnum tipoIdentificacion;

    /** Número de identificación del administrador de subsidio */
    private String numeroIdentificacion;

    /** Nombre del administrador */
    private String nombre;

    /** Medio de pago asociado a la cuenta del administrador de subsidio */
    private TipoMedioDePagoEnum medioPago;

    /** Lista de beneficiarios relacionados con el adminstrador de subsidio */
    private List<ItemBeneficiarioPorAdministradorDTO> beneficiariosPorAdministrador;
    
    /** Sitio de pago (vista 360) */
    private String sitioPago;
    
    /** Total de derecho para el beneficario (vista 360) */
    private BigDecimal totalDerecho;

	/** Total descuentos por entidad sobre el beneficio asignado  (Vista 360) */
    private BigDecimal totalDescuentosPorEntidad;

    /** Total descuentos sobre el beneficio asignado (vista 360) */
    private BigDecimal totalDescuentos;

    /** Total a pagar al beneficiario (vista 360) */
    private BigDecimal totalPago;
    
    /** Total a pagar al beneficiario (vista 360) */
    private BigDecimal totalRetirado;
    
    /** fecha de la liquidación (vista 360) */
    private Date fechaLiquidacion;
    
    /** periodo (vista 360) */
    private Date periodo;
    
    private BigDecimal totalDispersado;

    public BigDecimal getTotalDescuentosPorEntidad() {
        return totalDescuentosPorEntidad;
    }

    public void setTotalDescuentosPorEntidad(BigDecimal totalDescuentosPorEntidad) {
        this.totalDescuentosPorEntidad = totalDescuentosPorEntidad;
    }
	
    /**
     * @return the tipoIdentificacionEnum
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacionEnum
     *        the tipoIdentificacionEnum to set
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
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the medioPago
     */
    public TipoMedioDePagoEnum getMedioPago() {
        return medioPago;
    }

    /**
     * @param medioPago
     *        the medioPago to set
     */
    public void setMedioPago(TipoMedioDePagoEnum medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * @return the beneficiariosPorAdministrador
     */
    public List<ItemBeneficiarioPorAdministradorDTO> getBeneficiariosPorAdministrador() {
        return beneficiariosPorAdministrador;
    }

    /**
     * @param beneficiariosPorAdministrador
     *        the beneficiariosPorAdministrador to set
     */
    public void setBeneficiariosPorAdministrador(List<ItemBeneficiarioPorAdministradorDTO> beneficiariosPorAdministrador) {
        this.beneficiariosPorAdministrador = beneficiariosPorAdministrador;
    }

	/**
	 * @return the sitioPago
	 */
	public String getSitioPago() {
		return sitioPago;
	}

	/**
	 * @param sitioPago the sitioPago to set
	 */
	public void setSitioPago(String sitioPago) {
		this.sitioPago = sitioPago;
	}

	/**
	 * @return the totalDerecho
	 */
	public BigDecimal getTotalDerecho() {
		return totalDerecho;
	}

	/**
	 * @param totalDerecho the totalDerecho to set
	 */
	public void setTotalDerecho(BigDecimal totalDerecho) {
		this.totalDerecho = totalDerecho;
	}

	/**
	 * @return the totalDescuentos
	 */
	public BigDecimal getTotalDescuentos() {
		return totalDescuentos;
	}

	/**
	 * @param totalDescuentos the totalDescuentos to set
	 */
	public void setTotalDescuentos(BigDecimal totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	/**
	 * @return the totalPago
	 */
	public BigDecimal getTotalPago() {
		return totalPago;
	}

	/**
	 * @param totalPago the totalPago to set
	 */
	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}

	/**
	 * @return the totalRetirado
	 */
	public BigDecimal getTotalRetirado() {
		return totalRetirado;
	}

	/**
	 * @param totalRetirado the totalRetirado to set
	 */
	public void setTotalRetirado(BigDecimal totalRetirado) {
		this.totalRetirado = totalRetirado;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the periodo
	 */
	public Date getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the totalDispersado
	 */
	public BigDecimal getTotalDispersado() {
		return totalDispersado;
	}

	/**
	 * @param totalDispersado the totalDispersado to set
	 */
	public void setTotalDispersado(BigDecimal totalDispersado) {
		this.totalDispersado = totalDispersado;
	}
	
	

}
