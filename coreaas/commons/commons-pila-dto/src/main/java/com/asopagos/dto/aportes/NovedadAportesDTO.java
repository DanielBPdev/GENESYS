/**
 * 
 */
package com.asopagos.dto.aportes;

import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con los datos para radicar una solicitud de novedades de aporte.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class NovedadAportesDTO {
	
	/**
	 * Tipo de novedad.
	 */
	private TipoTransaccionEnum tipoNovedad;
	/**
	 * Fecha de inicio de la novedad.
	 */
	private Long fechaInicio;
	/**
	 * Fecha fin de la novedad
	 */
	private Long fechaFin;
	/**
	 * Atributo que indica si se debería enviar a aprobar la novedad.
	 */
	private MarcaNovedadEnum aplicar;
	
	/**
	 * Comentarios o glosa para novedad.
	 */
	private String comentarios;
	/**
	 * Tipo de identificación del cotizante. 
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Número de identificación del cotizante.
	 */
	private String numeroIdentificacion;
	
	/**
	 * Canal de recepcion.
	 */
	private CanalRecepcionEnum canalRecepcion;
	
	/**
	 * Identificador del Registro Detallado.
	 */
	private Long idRegistroDetallado;

	/**
	 * clasificación de alfiliado.
	 */
	 private ClasificacionEnum clasificacionAfiliado;
	 
	 /**
	  * Tipo identificación del aportante.
	  */
	 private TipoIdentificacionEnum tipoIdentificacionAportante;
	 
	 /**
	  * Número identficación del aportante.
	  */
	 private String numeroIdentificacionAportante;
	
	 /**
	  * identificador para la sucursal de empresa (caso novedad cambio de sucursal)
	  */
	 private SucursalEmpresa sucursal;
	 
	 /**
	  * Beneficiario asociado a una novedad de cascada (Producto de novedad PILA)
	  */
	 private BeneficiarioDTO beneficiario;
	 
	 /**
      * id tabla TemNovedad
      */
     private Long tenNovedadId;
     
     private Long idRegistroDetalladoNovedad;

     private Boolean isIngresoRetiro;
    private String beneficiariosCadena;

	  
	 
	 
    /**
     * Método que retorna el valor de tipoNovedad.
     * @return valor de tipoNovedad.
     */
    public TipoTransaccionEnum getTipoNovedad() {
        return tipoNovedad;
    }

    /**
     * Método encargado de modificar el valor de tipoNovedad.
     * @param valor para modificar tipoNovedad.
     */
    public void setTipoNovedad(TipoTransaccionEnum tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor para modificar fechaFin.
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Método que retorna el valor de aplicar.
     * @return valor de aplicar.
     */
    public MarcaNovedadEnum getAplicar() {
        return aplicar;
    }

    /**
     * Método encargado de modificar el valor de aplicar.
     * @param valor para modificar aplicar.
     */
    public void setAplicar(MarcaNovedadEnum aplicar) {
        this.aplicar = aplicar;
    }

    /**
     * Método que retorna el valor de comentarios.
     * @return valor de comentarios.
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Método encargado de modificar el valor de comentarios.
     * @param valor para modificar comentarios.
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
	
    /**
     * Método que retorna el valor de canalRecepcion.
     * @return valor de canalRecepcion.
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * Método encargado de modificar el valor de canalRecepcion.
     * @param valor para modificar canalRecepcion.
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

	/**
	 * @return the idRegistroDetallado
	 */
	public Long getIdRegistroDetallado() {
		return idRegistroDetallado;
	}

	/**
	 * @param idRegistroDetallado the idRegistroDetallado to set
	 */
	public void setIdRegistroDetallado(Long idRegistroDetallado) {
		this.idRegistroDetallado = idRegistroDetallado;
	}

	/**
	 * Método que retorna el valor de clasificacionAfiliado.
	 * @return valor de clasificacionAfiliado.
	 */ 
	public ClasificacionEnum getClasificacionAfiliado() {
		return clasificacionAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de clasificacionAfiliado.
	 * @param valor para modificar clasificacionAfiliado.
	 */ 
	public void setClasificacionAfiliado(ClasificacionEnum clasificacionAfiliado) {
		this.clasificacionAfiliado = clasificacionAfiliado;
	}

    /**
     * Método que retorna el valor de tipoIdentificacionAportante.
     * @return valor de tipoIdentificacionAportante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionAportante.
     * @param valor para modificar tipoIdentificacionAportante.
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionAportante.
     * @return valor de numeroIdentificacionAportante.
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionAportante.
     * @param valor para modificar numeroIdentificacionAportante.
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

	/**
	 * Método que retorna el valor de sucursal.
	 * @return valor de sucursal.
	 */ 
	public SucursalEmpresa getSucursal() {
		return sucursal;
	}

	/**
	 * Método encargado de modificar el valor de sucursal.
	 * @param valor para modificar sucursal.
	 */ 
	public void setSucursal(SucursalEmpresa sucursal) {
		this.sucursal = sucursal;
	}

    /**
     * @return the beneficiario
     */
    public BeneficiarioDTO getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario the beneficiario to set
     */
    public void setBeneficiario(BeneficiarioDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * @return s
     */
    public Long getTenNovedadId() {
        return tenNovedadId;
    }

    /**
     * @param tenNovedadId
     */
    public void setTenNovedadId(Long tenNovedadId) {
        this.tenNovedadId = tenNovedadId;
    }

        /**
     * @return the idRegistroDetalladoNovedad
     */
    public Long getIdRegistroDetalladoNovedad() {
        return idRegistroDetalladoNovedad;
    }

    /**
     * @param idRegistroDetalladoNovedad the idRegistroDetalladoNovedad to set
     */
    public void setIdRegistroDetalladoNovedad(Long idRegistroDetalladoNovedad) {
        this.idRegistroDetalladoNovedad = idRegistroDetalladoNovedad;
    }
	public Boolean getIsIngresoRetiro() {
		return this.isIngresoRetiro;
	}

	public void setIsIngresoRetiro(Boolean isIngresoRetiro) {
		this.isIngresoRetiro = isIngresoRetiro;
	}

    public String getBeneficiariosCadena() {
        return beneficiariosCadena;
    }

    public void setBeneficiariosCadena(String beneficiariosCadena) {
        this.beneficiariosCadena = beneficiariosCadena;
    }
}
