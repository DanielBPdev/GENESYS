package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;

/**
 * <b>Descripcion:</b> DTO que respresenta los datos del afiliado para el cual se consulta el
 * estado de sus servicios <br/>
 * <b>Módulo:</b> Asopagos - HU-211-394 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class DatosAfiliadoServiciosDTO implements Serializable {
    private static final long serialVersionUID = 4870099135387567955L;
    
    /** Día de vencimiento de aportes */
    private Short diaVencimiento;
    
    /** Nombre del aportante */
    private String nombreAportante;
    
    /** ID de registro en tabla Persona */
    private Long idPersona;
    
    /** Estado del aporte más reciente al período de cálculo */
    private EstadoRegistroAportesArchivoEnum estadoAporte;
    
    /** ID de registro detallado de aporte en staging de PILA */
    private Long idRegistroGeneral;
    
    /** Tipo de afiliación consultada */
    private TipoAfiliadoEnum tipoAfiliado;
    
    /** Estado de afiliación del aportante respecto al tipo de afiliación */
    private EstadoAfiliadoEnum estadoAfiliacion;
    
    /** Oportunidad de pago de aportes de acuerdo a formulario de afiliación */
    private PeriodoPagoPlanillaEnum oportunidadPago;
    
    /** fecha día hábil de vencimiento de aportes */
    private Date fechaVencimiento;
    
    /** fecha de afiliacion de aportenate */
    private Date fechaAfiliacion;
    
    /** fecha día hábil del siguiente vencimiento de aportes */
    private Date siguienteFechaVencimiento;
    
    /**
     * Constructor por defecto
     * */
    public DatosAfiliadoServiciosDTO(){
        super();
    }

    /**
     * Constructor con campos
     * @param diaVencimiento
     * @param nombreAportante
     * @param idPersona
     */
    public DatosAfiliadoServiciosDTO(Object[] datos) {
        super();

        if(datos[0] != null && datos[0] instanceof Number) {
            this.diaVencimiento = ((Number) datos[0]).shortValue();
        }
        this.nombreAportante = (String) datos[1];
        if(datos[2] != null && datos[2] instanceof Number) {
            this.idPersona = ((Number) datos[2]).longValue();
        }
        
        this.oportunidadPago = datos[3] != null ? PeriodoPagoPlanillaEnum.valueOf(datos[3].toString()) : null;

        if(datos.length > 4)
        	fechaVencimiento = (Date)  datos[4];
        
        if(datos.length > 5)
        	fechaAfiliacion = (Date)  datos[5];
    
    }

    /**
     * Constructor con campos
     * @param diaVencimiento
     * @param nombreAportante
     * @param idPersona
     */
	public DatosAfiliadoServiciosDTO(Short diaVencimiento, String nombreAportante, Long idPersona,
			String oportunidadPago, Date fechaVencimiento, Date siguienteFechaVencimiento, Date fechaAfiliacion) {
        super();
        
        this.diaVencimiento = diaVencimiento;
        this.nombreAportante = nombreAportante;
        this.idPersona = idPersona;
        this.oportunidadPago = oportunidadPago != null ? PeriodoPagoPlanillaEnum.valueOf(oportunidadPago) : null;
        this.fechaAfiliacion = fechaAfiliacion;
        this.fechaVencimiento = fechaVencimiento;
        this.siguienteFechaVencimiento = siguienteFechaVencimiento;
    }

    /**
     * @return the diaVencimiento
     */
    public Short getDiaVencimiento() {
        return diaVencimiento;
    }

    /**
     * @param diaVencimiento the diaVencimiento to set
     */
    public void setDiaVencimiento(Short diaVencimiento) {
        this.diaVencimiento = diaVencimiento;
    }

    /**
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * @param nombreAportante the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the estadoAporte
     */
    public EstadoRegistroAportesArchivoEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * @param estadoAporte the estadoAporte to set
     */
    public void setEstadoAporte(EstadoRegistroAportesArchivoEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the oportunidadPago
     */
    public PeriodoPagoPlanillaEnum getOportunidadPago() {
        return oportunidadPago;
    }

    /**
     * @param oportunidadPago the oportunidadPago to set
     */
    public void setOportunidadPago(PeriodoPagoPlanillaEnum oportunidadPago) {
        this.oportunidadPago = oportunidadPago;
    }

	/**
	 * @return the fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @return the fechaAfiliacion
	 */
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * @param fechaAfiliacion the fechaAfiliacion to set
	 */
	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * @return the siguienteFechaVencimiento
	 */
	public Date getSiguienteFechaVencimiento() {
		return siguienteFechaVencimiento;
	}

	/**
	 * @param siguienteFechaVencimiento the siguienteFechaVencimiento to set
	 */
	public void setSiguienteFechaVencimiento(Date siguienteFechaVencimiento) {
		this.siguienteFechaVencimiento = siguienteFechaVencimiento;
	}
}
