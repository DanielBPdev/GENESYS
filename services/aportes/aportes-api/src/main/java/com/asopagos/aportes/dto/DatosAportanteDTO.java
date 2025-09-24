package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class DatosAportanteDTO implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -815322392120740972L;
    /**
     * Descripción del tipo de identificación de la persona aportante
     */
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    /**
     * Número de identificación de la persona aportante
     */
    private String numeroIdentificacionAportante;
    
    /**
     * Sucursal a la que pertenece el aporte
     */
    private String codigoSucursal;
    
    /**
     * Descripción de la razón social
     */
    private String razonSocial;
    /**
     * Lista con los detalles de los aportes
     */
    private List<DetalleDatosAportanteDTO> datosAportes;
    /**
     * Código identificador de llave primaria llamada No. de operación de
     * recaudo general
     */
    private Long idAporteGeneral;
    /**
     * Fecha de procesamiento del aporte (Sistema al momento de relacionar o
     * registrar)
     */
    private String fechaProcesamiento;
    /**
     * Período de pago
     */
    private String periodoAporte;
    
    /**
     * Cantidad de días pagados en el aporte
     */
    private Short diasCotizados;
    /**
     * Descripción del estado del aporte a nivel de Aportante
     */
    private EstadoAporteEnum estadoAporteAportante;
    
    /**
     * Código identificador de la persona aportante
     */
    private Long idPersonaAportante;
    
    
    
    /**
     * @param tipoIdentificacionAportante
     * @param numeroIdentificacionAportante
     * @param codigoSucursal
     * @param razonSocial
     * @param idAporteGeneral
     * @param fechaProcesamiento
     * @param periodoAporte
     * @param estadoAporteAportante
     * @param idPersonaAportante
     */
    public DatosAportanteDTO(String tipoIdentificacionAportante, String numeroIdentificacionAportante,
			String codigoSucursal, String razonSocial, String idAporteGeneral, String fechaProcesamiento,
			String periodoAporte, String estadoAporteAportante,
			String idPersonaAportante, String diasCotizados) {
		this.tipoIdentificacionAportante = tipoIdentificacionAportante != null ? TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante) : null;
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
		this.codigoSucursal = codigoSucursal;
		this.razonSocial = razonSocial;
		this.idAporteGeneral = idAporteGeneral != null ? Long.parseLong(idAporteGeneral) : null;
		this.fechaProcesamiento = fechaProcesamiento;
		this.periodoAporte = periodoAporte;
		this.estadoAporteAportante = estadoAporteAportante != null ? EstadoAporteEnum.valueOf(estadoAporteAportante) : null;
		this.idPersonaAportante = idPersonaAportante != null ? Long.parseLong(idPersonaAportante) : null;
		this.diasCotizados = diasCotizados != null ? Short.parseShort(diasCotizados) : null;
	}

	/**
     * Método constructor
     * 
     * @param aporteGeneral Registro del recaudo manual 
     * @param persona Persona relacionada al aporte general
     */
    public DatosAportanteDTO(AporteGeneral aporteGeneral, Persona persona) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.setTipoIdentificacionAportante(persona.getTipoIdentificacion());
        this.setNumeroIdentificacionAportante(persona.getNumeroIdentificacion());
        if (persona.getRazonSocial() != null) {
            this.setRazonSocial(persona.getRazonSocial());
        }
        else {
            StringBuilder nombre = new StringBuilder();
            nombre.append(persona.getPrimerNombre() != null ? persona.getPrimerNombre() + " " : "");
            nombre.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
            nombre.append(persona.getPrimerApellido() != null ? persona.getPrimerApellido() + " " : "");
            nombre.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
            this.setRazonSocial(nombre.toString());
        }
        this.setEstadoAporteAportante(aporteGeneral.getEstadoAporteAportante());
        this.setIdAporteGeneral(aporteGeneral.getId());
        this.setFechaProcesamiento(aporteGeneral.getFechaProcesamiento()!=null? format.format(aporteGeneral.getFechaProcesamiento()):null);
        this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
        this.setIdPersonaAportante(persona.getIdPersona());
    }

    /**
     * Método constructor
     * 
     * @param aporteGeneral Registro del recaudo manual 
     * @param persona Persona relacionada al aporte general
     * @param aporteDetallado Registro detallado del recaudo
     */
    public DatosAportanteDTO(AporteGeneral aporteGeneral, Persona persona, AporteDetallado aporteDetallado) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.setTipoIdentificacionAportante(persona.getTipoIdentificacion());
        this.setNumeroIdentificacionAportante(persona.getNumeroIdentificacion());
        if (persona.getRazonSocial() != null) {
            this.setRazonSocial(persona.getRazonSocial());
        }
        else {
            StringBuilder nombre = new StringBuilder();
            nombre.append(persona.getPrimerNombre() != null ? persona.getPrimerNombre() + " " : "");
            nombre.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
            nombre.append(persona.getPrimerApellido() != null ? persona.getPrimerApellido() + " " : "");
            nombre.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
            this.setRazonSocial(nombre.toString());
        }
        this.setEstadoAporteAportante(aporteGeneral.getEstadoAporteAportante());
        this.setIdAporteGeneral(aporteGeneral.getId());
        this.setFechaProcesamiento(aporteGeneral.getFechaProcesamiento()!=null? format.format(aporteGeneral.getFechaProcesamiento()):null);
        this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
        this.setIdPersonaAportante(persona.getIdPersona());
        
        this.setCodigoSucursal(aporteDetallado.getCodSucursal());
        this.setDiasCotizados(aporteDetallado.getDiasCotizados());
    }
    
    /**
     * Método constructor
     */
    public DatosAportanteDTO() {
    }

    /**
     * @return the tipoIdentificacionAportante
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * @param tipoIdentificacionAportante
     *        the tipoIdentificacionAportante to set
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * @return the numeroIdentificacionAportante
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
    }

    /**
     * @param numeroIdentificacionAportante
     *        the numeroIdentificacionAportante to set
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *        the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the datosAportes
     */
    public List<DetalleDatosAportanteDTO> getDatosAportes() {
        return datosAportes;
    }

    /**
     * @param datosAportes
     *        the datosAportes to set
     */
    public void setDatosAportes(List<DetalleDatosAportanteDTO> datosAportes) {
        this.datosAportes = datosAportes;
    }

    /**
     * @return the idAporteGeneral
     */
    public Long getIdAporteGeneral() {
        return idAporteGeneral;
    }

    /**
     * @param idAporteGeneral
     *        the idAporteGeneral to set
     */
    public void setIdAporteGeneral(Long idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
    }

    /**
     * @return the fechaProcesamiento
     */
    public String getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(String fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the estadoAporteAportante
     */
    public EstadoAporteEnum getEstadoAporteAportante() {
        return estadoAporteAportante;
    }

    /**
     * @param estadoAporteAportante the estadoAporteAportante to set
     */
    public void setEstadoAporteAportante(EstadoAporteEnum estadoAporteAportante) {
        this.estadoAporteAportante = estadoAporteAportante;
    }

    /**
     * @return the idPersonaAportante
     */
    public Long getIdPersonaAportante() {
        return idPersonaAportante;
    }

    /**
     * @param idPersonaAportante the idPersonaAportante to set
     */
    public void setIdPersonaAportante(Long idPersonaAportante) {
        this.idPersonaAportante = idPersonaAportante;
    }

	/**
	 * @return the codigoSucursal
	 */
	public String getCodigoSucursal() {
		return codigoSucursal;
	}

	/**
	 * @param codigoSucursal the codigoSucursal to set
	 */
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}

	/**
	 * @return the diasCotizados
	 */
	public Short getDiasCotizados() {
		return diasCotizados;
	}

	/**
	 * @param diasCotizados the diasCotizados to set
	 */
	public void setDiasCotizados(Short diasCotizados) {
		this.diasCotizados = diasCotizados;
	}
}
