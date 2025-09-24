/**
 * 
 */
package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO encargada de transportar los datos ingresados al consultarRecaudo.
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra Zuluaga </a>
 */
public class ConsultarRecaudoDTO implements Serializable{
    private static final long serialVersionUID = -5434784188712360380L;

    /**
	 * Tipo de solicitante.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	
	/**
	 * Tipo de identificación del solicitante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Número de identificación del solicitante.
	 */
	private String numeroIdentificacion;
	
	/**
	 * Método de recaudo.
	 */
	private ModalidadRecaudoAporteEnum metodoRecaudo;
	
	/**
	 * Período del recaudo.
	 */
	private Long periodoRecaudo;
	
	/**
	 * Número de planilla.
	 */
	private String numeroPlanilla;
	
	/**
     * Código identificador de la persona. 
     */
    private Long idPersona;
    
    /**
     * Código identificador de la empresa. 
     */
    private Long idEmpresa;
    
    /**
     * Fecha inicio para la consulta de la vista 360 para persona aportes.
     */
    private Long fechaInicio;
    
    /**
     * Fecha fin para la consulta de la vista 360 para persona aportes
     */
    private Long fechaFin;
    
    /**
     * Nombre completo ó razon social para vista 360 de aportes.
     */
    private String nombreCompleto;
    
    /**
     * Período del recaudo para vista 360 de aportes.
     */
    private Long periodoFin;
    
    /**
     * Id en tabla del registro general (staging).
     */
    private Long idRegistroGeneral;
    
    /**
     * Listado de ids de aporte general recibidos de consulta de vista 360
     * */
    private List<Long> listaIdsAporteGeneral;
    
    /**
     * Lista de Método de recaudo para vista 360 de aportes empleador.
     */
    private List<ModalidadRecaudoAporteEnum> listMetodoRecaudo; 
    
    /**
     * Código identificador de llave primaria llamada No. de operación de
     * recaudo general
     */
    private Long idAporteGeneral;
    
    /** Mapa que referencia las empresas tramitadoras de aporte (pagador por terceros) */
    private Map<Long, EmpresaModeloDTO> empresasTramitadoras;
    
	/**
	 * Método que retorna el valor de tipoSolicitante.
	 * @return valor de tipoSolicitante.
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitante.
	 * @param valor para modificar tipoSolicitante.
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
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
	 * Método que retorna el valor de metodoRecaudo.
	 * @return valor de metodoRecaudo.
	 */
	public ModalidadRecaudoAporteEnum getMetodoRecaudo() {
		return metodoRecaudo;
	}

	/**
	 * Método encargado de modificar el valor de metodoRecaudo.
	 * @param valor para modificar metodoRecaudo.
	 */
	public void setMetodoRecaudo(ModalidadRecaudoAporteEnum metodoRecaudo) {
		this.metodoRecaudo = metodoRecaudo;
	}

	/**
	 * Método que retorna el valor de periodoRecaudo.
	 * @return valor de periodoRecaudo.
	 */
	public Long getPeriodoRecaudo() {
		return periodoRecaudo;
	}

	/**
	 * Método encargado de modificar el valor de periodoRecaudo.
	 * @param valor para modificar periodoRecaudo.
	 */
	public void setPeriodoRecaudo(Long periodoRecaudo) {
		this.periodoRecaudo = periodoRecaudo;
	}

	/**
	 * Método que retorna el valor de numeroPlanilla.
	 * @return valor de numeroPlanilla.
	 */
	public String getNumeroPlanilla() {
		return numeroPlanilla;
	}

	/**
	 * Método encargado de modificar el valor de numeroPlanilla.
	 * @param valor para modificar numeroPlanilla.
	 */
	public void setNumeroPlanilla(String numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
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
     * Método que retorna el valor de idEmpresa.
     * @return valor de idEmpresa.
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Método encargado de modificar el valor de idEmpresa.
     * @param valor para modificar idEmpresa.
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor para modificar fechaFin.
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Método que retorna el valor de nombreCompleto.
     * @return valor de nombreCompleto.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Método encargado de modificar el valor de nombreCompleto.
     * @param valor para modificar nombreCompleto.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Método que retorna el valor de periodoFin.
     * @return valor de periodoFin.
     */
    public Long getPeriodoFin() {
        return periodoFin;
    }

    /**
     * Método encargado de modificar el valor de periodoFin.
     * @param valor para modificar periodoFin.
     */
    public void setPeriodoFin(Long periodoFin) {
        this.periodoFin = periodoFin;
    }

    /**
     * Método que retorna el valor de listMetodoRecaudo.
     * @return valor de listMetodoRecaudo.
     */
    public List<ModalidadRecaudoAporteEnum> getListMetodoRecaudo() {
        return listMetodoRecaudo;
    }

    /**
     * Método encargado de modificar el valor de listMetodoRecaudo.
     * @param valor para modificar listMetodoRecaudo.
     */
    public void setListMetodoRecaudo(List<ModalidadRecaudoAporteEnum> listMetodoRecaudo) {
        this.listMetodoRecaudo = listMetodoRecaudo;
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
     * @return the idAporteGeneral
     */
    public Long getIdAporteGeneral() {
        return idAporteGeneral;
    }

    /**
     * @param idAporteGeneral the idAporteGeneral to set
     */
    public void setIdAporteGeneral(Long idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
    }

    /**
     * @return the listaIdsAporteGeneral
     */
    public List<Long> getListaIdsAporteGeneral() {
        return listaIdsAporteGeneral;
    }

    /**
     * @param listaIdsAporteGeneral the listaIdsAporteGeneral to set
     */
    public void setListaIdsAporteGeneral(List<Long> listaIdsAporteGeneral) {
        this.listaIdsAporteGeneral = listaIdsAporteGeneral;
    }

    /**
     * @return the empresasTramitadoras
     */
    public Map<Long, EmpresaModeloDTO> getEmpresasTramitadoras() {
        return empresasTramitadoras;
    }

    /**
     * @param empresasTramitadoras the empresasTramitadoras to set
     */
    public void setEmpresasTramitadoras(Map<Long, EmpresaModeloDTO> empresasTramitadoras) {
        this.empresasTramitadoras = empresasTramitadoras;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ConsultarRecaudoDTO{" +
                "tipoSolicitante=" + tipoSolicitante +
                ", tipoIdentificacion=" + tipoIdentificacion +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", metodoRecaudo=" + metodoRecaudo +
                ", periodoRecaudo=" + periodoRecaudo +
                ", numeroPlanilla='" + numeroPlanilla + '\'' +
                ", idPersona=" + idPersona +
                ", idEmpresa=" + idEmpresa +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", periodoFin=" + periodoFin +
                ", idRegistroGeneral=" + idRegistroGeneral +
                ", listaIdsAporteGeneral=" + listaIdsAporteGeneral +
                ", listMetodoRecaudo=" + listMetodoRecaudo +
                ", idAporteGeneral=" + idAporteGeneral +
                ", empresasTramitadoras=" + empresasTramitadoras +
                '}';
    }
}
