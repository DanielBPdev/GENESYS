package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información de detalle para los beneficiarios de acuerdo con el administrador de subsidio<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ItemBeneficiarioPorAdministradorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de indetificación del beneficiario */
    private TipoIdentificacionEnum tipoIdentificacion;

    /** Número de identificación del beneficiario */
    private String numeroIdentificacion;

    /** Nombre del beneficiario */
    private String nombre;

    /** Tipo de cumplimiento del beneficiario */
    private TipoCumplimientoEnum cumple;

    /** Total de derecho para el beneficario */
    private BigDecimal totalDerecho;

    /** Total descuentos por entidad sobre el beneficio asignado */
    private BigDecimal totalDescuentosPorEntidad;

    /** Total descuentos sobre el beneficio asignado */
    private BigDecimal totalDescuentos;

    /** Total a pagar al beneficiario */
    private BigDecimal totalPago;
    
    /** tipo de beneficiario (vista 360 parentesco) */
    private TipoBeneficiarioEnum tipoBeneficiario;
    
    /** tipo de estado del derecho (vista 360 estado del derecho) */
    private EstadoDerechoSubsidioEnum estadoDerechoSubsidio;
    
    /** Indica si recibe por el segundo afiliado principal (vista 360 estado del derecho) */
    private Boolean recibeXSegundoAfiliado;
    
    /** Nombre del segudno afiliado ppl */
    private String nombreXSegundoAfiliado;
    
    /** tipo de cuota del beneficiario (vista 360 estado del derecho) */
    private TipoCuotaSubsidioEnum tipoCuotaSubsidio;
    
    /** Causal no asiganción subsidio (vista 360 estado del derecho) */
    private String causal;
    
    /** Estado respecto al afiliado ppl (vista 360 estado del derecho) */
    private EstadoAfiliadoEnum estadoRespectoAfiliado;

    /** Id del resultado  de validacion de liquidación */
    private Long idResultadoValidacionLiquidacion;
    
    /** Total dispersado */
    private BigDecimal totalDispersado;
    
    /** Total pendiente por dispersar (programado) */
    private BigDecimal pendienteProgramado;
    
    /** Total pagado a la fecha */
    private BigDecimal pagado;

    /** Total pendiente por cobrar  */
    private BigDecimal saldo;
    
    /** identificación del beneficiario */
    private Long idCondicionBeneficiario;
    
    /** identifica que haya sido dispersado */
    private Boolean dispersado;

    /**
     * Estado de subsidio dispersado asignado 
     */
    private EstadoSubsidioAsignadoEnum estadoSubsidio;

	private Boolean conyugeCuidador;


    public BigDecimal getTotalDescuentosPorEntidad() {
        return totalDescuentosPorEntidad;
    }

    public void setTotalDescuentosPorEntidad(BigDecimal totalDescuentosPorEntidad) {
        this.totalDescuentosPorEntidad = totalDescuentosPorEntidad;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
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
     * @return the cumple
     */
    public TipoCumplimientoEnum getCumple() {
        return cumple;
    }

    /**
     * @param cumple
     *        the cumple to set
     */
    public void setCumple(TipoCumplimientoEnum cumple) {
        this.cumple = cumple;
    }

    /**
     * @return the totalDerecho
     */
    public BigDecimal getTotalDerecho() {
        return totalDerecho;
    }

    /**
     * @param totalDerecho
     *        the totalDerecho to set
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
     * @param totalDescuentos
     *        the totalDescuentos to set
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
     * @param totalPago
     *        the totalPago to set
     */
    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

	/**
	 * @return the tipoBeneficiario
	 */
	public TipoBeneficiarioEnum getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * @param tipoBeneficiario the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(TipoBeneficiarioEnum tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * @return the estadoDerechoSubsidio
	 */
	public EstadoDerechoSubsidioEnum getEstadoDerechoSubsidio() {
		return estadoDerechoSubsidio;
	}

	/**
	 * @param estadoDerechoSubsidio the estadoDerechoSubsidio to set
	 */
	public void setEstadoDerechoSubsidio(EstadoDerechoSubsidioEnum estadoDerechoSubsidio) {
		this.estadoDerechoSubsidio = estadoDerechoSubsidio;
	}

	/**
	 * @return the recibeXSegundoAfiliado
	 */
	public Boolean getRecibeXSegundoAfiliado() {
		return recibeXSegundoAfiliado;
	}

	/**
	 * @param recibeXSegundoAfiliado the recibeXSegundoAfiliado to set
	 */
	public void setRecibeXSegundoAfiliado(Boolean recibeXSegundoAfiliado) {
		this.recibeXSegundoAfiliado = recibeXSegundoAfiliado;
	}

	/**
	 * @return the nombreXSegundoAfiliado
	 */
	public String getNombreXSegundoAfiliado() {
		return nombreXSegundoAfiliado;
	}

	/**
	 * @param nombreXSegundoAfiliado the nombreXSegundoAfiliado to set
	 */
	public void setNombreXSegundoAfiliado(String nombreXSegundoAfiliado) {
		this.nombreXSegundoAfiliado = nombreXSegundoAfiliado;
	}

	/**
	 * @return the tipoCuotaSubsidio
	 */
	public TipoCuotaSubsidioEnum getTipoCuotaSubsidio() {
		return tipoCuotaSubsidio;
	}

	/**
	 * @param tipoCuotaSubsidio the tipoCuotaSubsidio to set
	 */
	public void setTipoCuotaSubsidio(TipoCuotaSubsidioEnum tipoCuotaSubsidio) {
		this.tipoCuotaSubsidio = tipoCuotaSubsidio;
	}

	/**
	 * @return the causal
	 */
	public String getCausal() {
		return causal;
	}

	/**
	 * @param causal the causal to set
	 */
	public void setCausal(String causal) {
		this.causal = causal;
	}

	/**
	 * @return the estadoRespectoAfiliado
	 */
	public EstadoAfiliadoEnum getEstadoRespectoAfiliado() {
		return estadoRespectoAfiliado;
	}

	/**
	 * @param estadoRespectoAfiliado the estadoRespectoAfiliado to set
	 */
	public void setEstadoRespectoAfiliado(EstadoAfiliadoEnum estadoRespectoAfiliado) {
		this.estadoRespectoAfiliado = estadoRespectoAfiliado;
	}

	/**
	 * @return the idResultadoValidacionLiquidacion
	 */
	public Long getIdResultadoValidacionLiquidacion() {
		return idResultadoValidacionLiquidacion;
	}

	/**
	 * @param idResultadoValidacionLiquidacion the idResultadoValidacionLiquidacion to set
	 */
	public void setIdResultadoValidacionLiquidacion(Long idResultadoValidacionLiquidacion) {
		this.idResultadoValidacionLiquidacion = idResultadoValidacionLiquidacion;
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

	/**
	 * @return the pendienteProgramado
	 */
	public BigDecimal getPendienteProgramado() {
		return pendienteProgramado;
	}

	/**
	 * @param pendienteProgramado the pendienteProgramado to set
	 */
	public void setPendienteProgramado(BigDecimal pendienteProgramado) {
		this.pendienteProgramado = pendienteProgramado;
	}

	/**
	 * @return the pagado
	 */
	public BigDecimal getPagado() {
		return pagado;
	}

	/**
	 * @param pagado the pagado to set
	 */
	public void setPagado(BigDecimal pagado) {
		this.pagado = pagado;
	}

	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the idCondicionBeneficiario
	 */
	public Long getIdCondicionBeneficiario() {
		return idCondicionBeneficiario;
	}

	/**
	 * @param idCondicionBeneficiario the idCondicionBeneficiario to set
	 */
	public void setIdCondicionBeneficiario(Long idCondicionBeneficiario) {
		this.idCondicionBeneficiario = idCondicionBeneficiario;
	}

	/**
	 * @return the dispersado
	 */
	public Boolean getDispersado() {
		return dispersado;
	}

	/**
	 * @param dispersado the dispersado to set
	 */
	public void setDispersado(Boolean dispersado) {
		this.dispersado = dispersado;
	}

    /**
     * @return the estadoSubsidio
     */
    public EstadoSubsidioAsignadoEnum getEstadoSubsidio() {
        return estadoSubsidio;
    }

    /**
     * @param estadoSubsidio
     *        the estadoSubsidio to set
     */
    public void setEstadoSubsidio(EstadoSubsidioAsignadoEnum estadoSubsidio) {
        this.estadoSubsidio = estadoSubsidio;
    }

	public Boolean getConyugeCuidador() {
		return this.conyugeCuidador;
	}

	public void setConyugeCuidador(Boolean conyugeCuidador) {
		this.conyugeCuidador = conyugeCuidador;
	}

}
