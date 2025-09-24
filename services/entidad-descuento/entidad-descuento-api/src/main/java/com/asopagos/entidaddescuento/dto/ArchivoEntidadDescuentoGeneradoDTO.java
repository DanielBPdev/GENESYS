package com.asopagos.entidaddescuento.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.entidades.subsidiomonetario.liquidacion.SubsidioMonetarioValorPignorado;

/**
 * <b>Descripcion:</b> Clase que se encarga de definir los atributos utilizados en la generación de archivos de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU 432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoEntidadDescuentoGeneradoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación del trabajador
     */
    private String tipoIdentificacionTrabajador;

    /**
     * Número de identificación del trabajador
     */
    private String numeroIdentificacionTrabajador;

    /**
     * Nombre del trabajador
     */
    private String nombreTrabajador;

    /**
     * Tipo de identificación del administrador
     */
    private String tipoIdentificacionAdministrador;

    /**
     * Número de identificación del administrador
     */
    private String numeroIdentificacionAdministrador;

    /**
     * Código del grupo familiar asociado al beneficiario
     */
    private String codigoGrupoFamiliar;

    /**
     * Monto a descontar sobre el subsidio asignado
     */
    private BigDecimal valorPignorar;

    /**
     * Monto descontado sobre el subsidio asignado
     */
    private BigDecimal valorPignorado;
    
    /**
     * Nuevo saldo por descontar.
     */
    private BigDecimal nuevoSaldoDescontar;
    
    /**
     * Código de Referencia
     */
    private String codigoReferencia;
    
    /**
     * Tipo de medio de pago del admon subsidio
     */
    private String tipoMedioDePago;
    
    /**
     * Identificador asociado al descuento
     */
    private String idDescuento; 

    /**
     * Método constructor
     * @param sm
     *        registro para la generación del archivo
     */
    public ArchivoEntidadDescuentoGeneradoDTO(SubsidioMonetarioValorPignorado sm) {
        this.setTipoIdentificacionTrabajador(sm.getTipoIdentificacionTrabajador().getValorEnPILA());
        this.setNumeroIdentificacionTrabajador(sm.getNumeroIdentificacionTrabajador());
        this.setNombreTrabajador(sm.getNombreTrabajador());
        this.tipoIdentificacionAdministrador = sm.getTipoIdentificacionAdministrador()!= null ? sm.getTipoIdentificacionAdministrador().getValorEnPILA() : null;
        this.numeroIdentificacionAdministrador = sm.getNumeroIdentificacionAdministrador() != null ? sm.getNumeroIdentificacionAdministrador() : "";
        this.codigoGrupoFamiliar = sm.getCodigoGrupoFamiliar().toString();
        this.valorPignorar = sm.getValorPignorar();
        if (sm.getValorPignorado() == null) {
            this.valorPignorado = BigDecimal.valueOf(0);
        }
        else {
            this.valorPignorado = sm.getValorPignorado();
        }
        this.nuevoSaldoDescontar = valorPignorar.subtract(valorPignorado);
        this.codigoReferencia = sm.getCodigoReferencia();
        this.tipoMedioDePago = sm.getMedioDePago() != null ? sm.getMedioDePago().name() : "";
        this.idDescuento = sm.getIdSubsidioMonetarioValorPignorado().toString();
    }

    /**
     * @return the tipoIdentificacionAdministrador
     */
    public String getTipoIdentificacionAdministrador() {
        return tipoIdentificacionAdministrador;
    }

    /**
     * @param tipoIdentificacionAdministrador
     *        the tipoIdentificacionAdministrador to set
     */
    public void setTipoIdentificacionAdministrador(String tipoIdentificacionAdministrador) {
        this.tipoIdentificacionAdministrador = tipoIdentificacionAdministrador;
    }

    /**
     * @return the numeroIdentificacionAdministrador
     */
    public String getNumeroIdentificacionAdministrador() {
        return numeroIdentificacionAdministrador;
    }

    /**
     * @param numeroIdentificacionAdministrador
     *        the numeroIdentificacionAdministrador to set
     */
    public void setNumeroIdentificacionAdministrador(String numeroIdentificacionAdministrador) {
        this.numeroIdentificacionAdministrador = numeroIdentificacionAdministrador;
    }

    /**
     * @return the codigoGrupoFamiliar
     */
    public String getCodigoGrupoFamiliar() {
        return codigoGrupoFamiliar;
    }

    /**
     * @param codigoGrupoFamiliar
     *        the codigoGrupoFamiliar to set
     */
    public void setCodigoGrupoFamiliar(String codigoGrupoFamiliar) {
        this.codigoGrupoFamiliar = codigoGrupoFamiliar;
    }

    /**
     * @return the valorPignorar
     */
    public BigDecimal getValorPignorar() {
        return valorPignorar;
    }

    /**
     * @param valorPignorar
     *        the valorPignorar to set
     */
    public void setValorPignorar(BigDecimal valorPignorar) {
        this.valorPignorar = valorPignorar;
    }

    /**
     * @return the valorPignorado
     */
    public BigDecimal getValorPignorado() {
        return valorPignorado;
    }

    /**
     * @param valorPignorado
     *        the valorPignorado to set
     */
    public void setValorPignorado(BigDecimal valorPignorado) {
        this.valorPignorado = valorPignorado;
    }

    /**
     * @return the tipoIdentificacionTrabajador
     */
    public String getTipoIdentificacionTrabajador() {
        return tipoIdentificacionTrabajador;
    }

    /**
     * @param tipoIdentificacionTrabajador
     *        the tipoIdentificacionTrabajador to set
     */
    public void setTipoIdentificacionTrabajador(String tipoIdentificacionTrabajador) {
        this.tipoIdentificacionTrabajador = tipoIdentificacionTrabajador;
    }

    /**
     * @return the numeroIdentificacionTrabajador
     */
    public String getNumeroIdentificacionTrabajador() {
        return numeroIdentificacionTrabajador;
    }

    /**
     * @param numeroIdentificacionTrabajador
     *        the numeroIdentificacionTrabajador to set
     */
    public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
        this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
    }

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @param nombreTrabajador
     *        the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    /**
	 * @return the tipoMedioDePago
	 */
	public String getTipoMedioDePago() {
		return tipoMedioDePago;
	}

	/**
	 * @param tipoMedioDePago the tipoMedioDePago to set
	 */
	public void setTipoMedioDePago(String tipoMedioDePago) {
		this.tipoMedioDePago = tipoMedioDePago;
	}

	/**
	 * @return the idDescuento
	 */
	public String getIdDescuento() {
		return idDescuento;
	}

	/**
	 * @param idDescuento the idDescuento to set
	 */
	public void setIdDescuento(String idDescuento) {
		this.idDescuento = idDescuento;
	}

	/**
	 * @return the nuevoSaldoDescontar
	 */
	public BigDecimal getNuevoSaldoDescontar() {
		return nuevoSaldoDescontar;
	}

	/**
	 * @param nuevoSaldoDescontar the nuevoSaldoDescontar to set
	 */
	public void setNuevoSaldoDescontar(BigDecimal nuevoSaldoDescontar) {
		this.nuevoSaldoDescontar = nuevoSaldoDescontar;
	}

	/**
	 * @return the codigoReferencia
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia the codigoReferencia to set
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public ArchivoEntidadDescuentoGeneradoDTO() {
		// TODO Auto-generated constructor stub
	}
}
