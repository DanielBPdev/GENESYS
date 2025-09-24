package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.HistoricoDTO;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoGestionAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase DTO encarcagada de representar un análisis de devolución de aportes.
 * 
 * @author <a href="criparra@heinsohn.com.co"> Cristian David Parra
 *         Zuluaga. </a>
 * @author <a href="fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalisisDevolucionDTO implements Serializable {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6196954293573519L;

	/**
	 * Id del aporte seleccionado.
	 */
	private Long idAporte;

	/**
	 * Identificador único de la operación de recuador de aporte.
	 */
	private String numOperacion;

	/**
	 * Fecha y hora del registro del recaudo de aporte (apgFechaProcesamiento).
	 */
	private Long fecha;

	/**
     * Fecha de pago del recaudo de aporte. (apgFechaRecaudo)
     */
    private Long fechaPago;
	
	/**
	 * Método de recaudo del aporte a devolver.
	 */
	private ModalidadRecaudoAporteEnum metodo;

	/**
	 * Campo que indica si hay o no detalle para mostrar.
	 */
	private Boolean conDetalle;

	/**
	 * Número de planilla PILA, en caso de que el recaudo se haya hecho por
	 * PILA. Si no, muestra N/A.
	 */
	private String numPlanilla;

	/**
	 * Estado del archivo si se hizo por PILA.
	 */
	private EstadoProcesoArchivoEnum estadoArchivo;

	/**
	 * Tipo de archivo PILA, si el recaudo se hizo por dicho medio.
	 */
	private TipoArchivoPilaEnum tipoArchivo;

	/**
	 * Tipo de planilla.
	 */
	private TipoPlanillaEnum tipoPlanilla;

	/**
	 * Período de la planilla por la que se está reclamando la devolución de
	 * aportes.
	 */
	private Long periodo;

	/**
	 * Monto del aporte solicitado.
	 */
	private BigDecimal monto;

	/**
	 * Intereses del monto.
	 */
	private BigDecimal interes;

	/**
	 * Identificador del archivo en el ECM.
	 */
	private String idEcmArchivo;

	/**
	 * Valor que indica si ha sido o no gestionado.
	 */
	private Boolean gestionado;

	/**
	 * Resultado de proceso, puede tomar los valores APROBADA, RECHAZADA,
	 * PENDIENTE
	 */
	private EstadoGestionAporteEnum resultado;

	/**
	 * Indica si tiene o no modificaciones el archivo que reportó el recaudo.
	 */
	private Boolean tieneModificaciones;

	/**
	 * Lista con los cotizantes involucrados en la gestión de devolución de
	 * aportes.
	 */
	private List<CotizanteDTO> cotizanteDTO;

	/**
	 * Comentarios acerca del resultado.
	 */
	private String comentariosResultado;

	/**
	 * Historico del analisis (caso sin detalle)
	 */
	private HistoricoDTO historico;

	/**
	 * Total del pago de aportes
	 */
	private BigDecimal total;

	/**
	 * Id del registro general almacenado en la primera simulación
	 */
	private Long idRegistroGeneral;

	/**
	 * Id del registro general por el cual se buscará si ya hubo una simulación
	 * previa
	 */
	private Long idRegistroGeneralNuevo;

	/**
	 * Id de la persona relacionada al aporte general
	 */
	private Long idPersona;
	
	/**
     * Id de la persona relacionada al aporte detallado
     */
    private Long idPersonaCotizante;
	
	/**
	 * Id de la empresa relacionada al aporte general
	 */
	private Long idEmpresa;
	
	/**
	 * Monto del aporte registro.
	 */
	private BigDecimal montoRegistro;

	/**
	 * Intereses del monto de registro.
	 */
	private BigDecimal interesRegistro;
	
    /**
     * Estado del aporte
     */
    private EstadoAporteEnum estadoAporte;
    
    /**
     * Tipo de solicitante que se adiciono para la vista 360 persona de aporte.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Tipo de identificación del solicitante que se adiciono para la vista 360 persona de aporte.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación del solicitante que se adiciono para la vista 360 persona de aporte.
     */
    private String numeroIdentificacion;
    
    /**
     * Marca de Referencia que indica que el aportante es "pagador por terceros"
     * para el aporte asociado para la vista 360 persona de aporte.
     */
    private Boolean pagadorPorTerceros;
    
    /**
     * Lista con los identificadores de aporte detallado que se adiciono para la vista 360 persona de aporte.
     */
    private List<Long> listIdAporteDetallados = new ArrayList<>();
    
    /**
     * Nombre completo ó razon social para vista 360 de aportes.
     */
    private String nombreCompleto;
    
    /**
     * Codigo de la entidad financiera recaudadora o receptora para vista 360 de aportes
     */
    private Short codigoEntidadFinanciera;
	
    /**
     * Identificador de la solicitud de correccion para cuando se consulta el recaudo desde las vistas 360 
     * pestaña correcciones
     */
    private Long idSolicitudCorreccion;
    

    /**
     * Identificador de la solicitud global para cuando se consulta el recaudo desde las vistas 360 
     * pestaña correcciones
     */
    private Long idSolicitudGlobal;
    
    /**
     * Monto de los aportes solicitados para corrección
     */
    private BigDecimal montoCorreccion;

    /**
     * Monto de intereses solicitados para corrección
     */
    private BigDecimal interesesCorreccion;
    
    /**
     * Monto de intereses solicitados para corrección
     */
    private BigDecimal totalCorreccion;
    
    /**
     * Tipo de identificación del tramitador que se adiciono para la vista 360 persona de aporte.
     */
    private TipoIdentificacionEnum tipoIdentificacionTramitador;
    
    /**
     * Número de identificación del tramitador que se adiciono para la vista 360 persona de aporte.
     */
    private String numeroIdentificacionTramitador;
    
    /**
     * Nombre completo ó razon social para vista 360 de aportes (tramitador).
     */
    private String nombreCompletoTramitador;
    
    
    /** Indica si la planilla aún no ha terminado su procesamiento  */
    private Boolean enProcesamiento;
    
    /** Indica si la planilla tuvo un fallo en su procesamiento y se debe ir a la bandeja transitoria */
    private Boolean enBandejaTransitoria;
    
    /**
     * cuentaBancariaRecaudo GLPI 57808
     */
    private Integer cuentaBancariaRecaudo;

    /**
     * cuentaBancariaRecaudo Texto GLPI 57808
     */
    private String cuentaBancariaRecaudoTexto;    
    
	/**
	 * Obtiene el valor de idAporte
	 * 
	 * @return El valor de idAporte
	 */
	public Long getIdAporte() {
		return idAporte;
	}

	/**
	 * Establece el valor de idAporte
	 * 
	 * @param idAporte
	 *            El valor de idAporte por asignar
	 */
	public void setIdAporte(Long idAporte) {
		this.idAporte = idAporte;
	}

	/**
	 * Obtiene el valor de numOperacion
	 * 
	 * @return El valor de numOperacion
	 */
	public String getNumOperacion() {
		return numOperacion;
	}

	/**
	 * Establece el valor de numOperacion
	 * 
	 * @param numOperacion
	 *            El valor de numOperacion por asignar
	 */
	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}

	/**
	 * Obtiene el valor de fecha
	 * 
	 * @return El valor de fecha
	 */
	public Long getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor de fecha
	 * 
	 * @param fecha
	 *            El valor de fecha por asignar
	 */
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor de metodo
	 * 
	 * @return El valor de metodo
	 */
	public ModalidadRecaudoAporteEnum getMetodo() {
		return metodo;
	}

	/**
	 * Establece el valor de metodo
	 * 
	 * @param metodo
	 *            El valor de metodo por asignar
	 */
	public void setMetodo(ModalidadRecaudoAporteEnum metodo) {
		this.metodo = metodo;
	}

	/**
	 * Obtiene el valor de conDetalle
	 * 
	 * @return El valor de conDetalle
	 */
	public Boolean getConDetalle() {
		return conDetalle;
	}

	/**
	 * Establece el valor de conDetalle
	 * 
	 * @param conDetalle
	 *            El valor de conDetalle por asignar
	 */
	public void setConDetalle(Boolean conDetalle) {
		this.conDetalle = conDetalle;
	}

	/**
	 * Obtiene el valor de numPlanilla
	 * 
	 * @return El valor de numPlanilla
	 */
	public String getNumPlanilla() {
		return numPlanilla;
	}

	/**
	 * Establece el valor de numPlanilla
	 * 
	 * @param numPlanilla
	 *            El valor de numPlanilla por asignar
	 */
	public void setNumPlanilla(String numPlanilla) {
		this.numPlanilla = numPlanilla;
	}

	/**
	 * Obtiene el valor de estadoArchivo
	 * 
	 * @return El valor de estadoArchivo
	 */
	public EstadoProcesoArchivoEnum getEstadoArchivo() {
		return estadoArchivo;
	}

	/**
	 * Establece el valor de estadoArchivo
	 * 
	 * @param estadoArchivo
	 *            El valor de estadoArchivo por asignar
	 */
	public void setEstadoArchivo(EstadoProcesoArchivoEnum estadoArchivo) {
		this.estadoArchivo = estadoArchivo;
	}

	/**
	 * Obtiene el valor de tipoArchivo
	 * 
	 * @return El valor de tipoArchivo
	 */
	public TipoArchivoPilaEnum getTipoArchivo() {
		return tipoArchivo;
	}

	/**
	 * Establece el valor de tipoArchivo
	 * 
	 * @param tipoArchivo
	 *            El valor de tipoArchivo por asignar
	 */
	public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	/**
	 * Obtiene el valor de tipoPlanilla
	 * 
	 * @return El valor de tipoPlanilla
	 */
	public TipoPlanillaEnum getTipoPlanilla() {
		return tipoPlanilla;
	}

	/**
	 * Establece el valor de tipoPlanilla
	 * 
	 * @param tipoPlanilla
	 *            El valor de tipoPlanilla por asignar
	 */
	public void setTipoPlanilla(TipoPlanillaEnum tipoPlanilla) {
		this.tipoPlanilla = tipoPlanilla;
	}

	/**
	 * Obtiene el valor de periodo
	 * 
	 * @return El valor de periodo
	 */
	public Long getPeriodo() {
		return periodo;
	}

	/**
	 * Establece el valor de periodo
	 * 
	 * @param periodo
	 *            El valor de periodo por asignar
	 */
	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	/**
	 * Obtiene el valor de monto
	 * 
	 * @return El valor de monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Establece el valor de monto
	 * 
	 * @param monto
	 *            El valor de monto por asignar
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * Obtiene el valor de interes
	 * 
	 * @return El valor de interes
	 */
	public BigDecimal getInteres() {
		return interes;
	}

	/**
	 * Establece el valor de interes
	 * 
	 * @param interes
	 *            El valor de interes por asignar
	 */
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	/**
	 * Obtiene el valor de idEcmArchivo
	 * 
	 * @return El valor de idEcmArchivo
	 */
	public String getIdEcmArchivo() {
		return idEcmArchivo;
	}

	/**
	 * Establece el valor de idEcmArchivo
	 * 
	 * @param idEcmArchivo
	 *            El valor de idEcmArchivo por asignar
	 */
	public void setIdEcmArchivo(String idEcmArchivo) {
		this.idEcmArchivo = idEcmArchivo;
	}

	/**
	 * Obtiene el valor de gestionado
	 * 
	 * @return El valor de gestionado
	 */
	public Boolean getGestionado() {
		return gestionado;
	}

	/**
	 * Establece el valor de gestionado
	 * 
	 * @param gestionado
	 *            El valor de gestionado por asignar
	 */
	public void setGestionado(Boolean gestionado) {
		this.gestionado = gestionado;
	}

	/**
	 * Obtiene el valor de resultado
	 * 
	 * @return El valor de resultado
	 */
	public EstadoGestionAporteEnum getResultado() {
		return resultado;
	}

	/**
	 * Establece el valor de resultado
	 * 
	 * @param resultado
	 *            El valor de resultado por asignar
	 */
	public void setResultado(EstadoGestionAporteEnum resultado) {
		this.resultado = resultado;
	}

	/**
	 * Obtiene el valor de tieneModificaciones
	 * 
	 * @return El valor de tieneModificaciones
	 */
	public Boolean getTieneModificaciones() {
		return tieneModificaciones;
	}

	/**
	 * Establece el valor de tieneModificaciones
	 * 
	 * @param tieneModificaciones
	 *            El valor de tieneModificaciones por asignar
	 */
	public void setTieneModificaciones(Boolean tieneModificaciones) {
		this.tieneModificaciones = tieneModificaciones;
	}

	/**
	 * Obtiene el valor de cotizanteDTO
	 * 
	 * @return El valor de cotizanteDTO
	 */
	public List<CotizanteDTO> getCotizanteDTO() {
		return cotizanteDTO;
	}

	/**
	 * Establece el valor de cotizanteDTO
	 * 
	 * @param cotizanteDTO
	 *            El valor de cotizanteDTO por asignar
	 */
	public void setCotizanteDTO(List<CotizanteDTO> cotizanteDTO) {
		this.cotizanteDTO = cotizanteDTO;
	}

	/**
	 * Obtiene el valor de comentariosResultado
	 * 
	 * @return El valor de comentariosResultado
	 */
	public String getComentariosResultado() {
		return comentariosResultado;
	}

	/**
	 * Establece el valor de comentariosResultado
	 * 
	 * @param comentariosResultado
	 *            El valor de comentariosResultado por asignar
	 */
	public void setComentariosResultado(String comentariosResultado) {
		this.comentariosResultado = comentariosResultado;
	}

	/**
	 * Obtiene el valor de historico
	 * 
	 * @return El valor de historico
	 */
	public HistoricoDTO getHistorico() {
		return historico;
	}

	/**
	 * Establece el valor de historico
	 * 
	 * @param historico
	 *            El valor de historico por asignar
	 */
	public void setHistorico(HistoricoDTO historico) {
		this.historico = historico;
	}

	/**
	 * Obtiene el valor de total
	 * 
	 * @return El valor de total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Establece el valor de total
	 * 
	 * @param total
	 *            El valor de total por asignar
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Obtiene el valor de idRegistroGeneral
	 * 
	 * @return El valor de idRegistroGeneral
	 */
	public Long getIdRegistroGeneral() {
		return idRegistroGeneral;
	}

	/**
	 * Establece el valor de idRegistroGeneral
	 * 
	 * @param idRegistroGeneral
	 *            El valor de idRegistroGeneral por asignar
	 */
	public void setIdRegistroGeneral(Long idRegistroGeneral) {
		this.idRegistroGeneral = idRegistroGeneral;
	}

	/**
	 * Obtiene el valor de idRegistroGeneralNuevo
	 * 
	 * @return El valor de idRegistroGeneralNuevo
	 */
	public Long getIdRegistroGeneralNuevo() {
		return idRegistroGeneralNuevo;
	}

	/**
	 * Establece el valor de idRegistroGeneralNuevo
	 * 
	 * @param idRegistroGeneralNuevo
	 *            El valor de idRegistroGeneralNuevo por asignar
	 */
	public void setIdRegistroGeneralNuevo(Long idRegistroGeneralNuevo) {
		this.idRegistroGeneralNuevo = idRegistroGeneralNuevo;
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
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
	 * Método que retorna el valor de montoRegistro.
	 * @return valor de montoRegistro.
	 */
	public BigDecimal getMontoRegistro() {
		return montoRegistro;
	}

	/**
	 * Método que retorna el valor de interesRegistro.
	 * @return valor de interesRegistro.
	 */
	public BigDecimal getInteresRegistro() {
		return interesRegistro;
	}

	/**
	 * Método encargado de modificar el valor de montoRegistro.
	 * @param valor para modificar montoRegistro.
	 */
	public void setMontoRegistro(BigDecimal montoRegistro) {
		this.montoRegistro = montoRegistro;
	}

	/**
	 * Método encargado de modificar el valor de interesRegistro.
	 * @param valor para modificar interesRegistro.
	 */
	public void setInteresRegistro(BigDecimal interesRegistro) {
		this.interesRegistro = interesRegistro;
	}

	/**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the idPersonaCotizante
     */
    public Long getIdPersonaCotizante() {
        return idPersonaCotizante;
    }

    /**
     * @param idPersonaCotizante the idPersonaCotizante to set
     */
    public void setIdPersonaCotizante(Long idPersonaCotizante) {
        this.idPersonaCotizante = idPersonaCotizante;
    }

    /**
     * @return the estadoAporte
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * @param estadoAporte the estadoAporte to set
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de idAporteDetallado.
     * @return valor de idAporteDetallado.
     */
    public List<Long> getListIdAporteDetallados() {
        return listIdAporteDetallados;
    }

    /**
     * Método encargado de modificar el valor de listIdAporteDetallados.
     * @param valor para modificar listIdAporteDetallados.
     */
    public void setListIdAporteDetallados(List<Long> listIdAporteDetallados) {
        this.listIdAporteDetallados = listIdAporteDetallados;
    }

    /**
     * Método que retorna el valor de pagadorPorTerceros.
     * @return valor de pagadorPorTerceros.
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
    }

    /**
     * Método encargado de modificar el valor de pagadorPorTerceros.
     * @param valor para modificar pagadorPorTerceros.
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
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
     * Método que retorna el valor de codigoEntidadFinanciera.
     * @return valor de codigoEntidadFinanciera.
     */
    public Short getCodigoEntidadFinanciera() {
        return codigoEntidadFinanciera;
    }

    /**
     * Método encargado de modificar el valor de codigoEntidadFinanciera.
     * @param valor para modificar codigoEntidadFinanciera.
     */
    public void setCodigoEntidadFinanciera(Short codigoEntidadFinanciera) {
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
    }

    /**
     * @return the idSolicitudCorreccion
     */
    public Long getIdSolicitudCorreccion() {
        return idSolicitudCorreccion;
    }

    /**
     * @param idSolicitudCorreccion the idSolicitudCorreccion to set
     */
    public void setIdSolicitudCorreccion(Long idSolicitudCorreccion) {
        this.idSolicitudCorreccion = idSolicitudCorreccion;
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
     * @return the montoCorreccion
     */
    public BigDecimal getMontoCorreccion() {
        return montoCorreccion;
    }

    /**
     * @param montoCorreccion the montoCorreccion to set
     */
    public void setMontoCorreccion(BigDecimal montoCorreccion) {
        this.montoCorreccion = montoCorreccion;
    }

    /**
     * @return the interesesCorreccion
     */
    public BigDecimal getInteresesCorreccion() {
        return interesesCorreccion;
    }

    /**
     * @param interesesCorreccion the interesesCorreccion to set
     */
    public void setInteresesCorreccion(BigDecimal interesesCorreccion) {
        this.interesesCorreccion = interesesCorreccion;
    }

    /**
     * @return the totalCorreccion
     */
    public BigDecimal getTotalCorreccion() {
        return totalCorreccion;
    }

    /**
     * @param totalCorreccion the totalCorreccion to set
     */
    public void setTotalCorreccion(BigDecimal totalCorreccion) {
        this.totalCorreccion = totalCorreccion;
    }

    /**
     * @return the tipoIdentificacionTramitador
     */
    public TipoIdentificacionEnum getTipoIdentificacionTramitador() {
        return tipoIdentificacionTramitador;
    }

    /**
     * @param tipoIdentificacionTramitador the tipoIdentificacionTramitador to set
     */
    public void setTipoIdentificacionTramitador(TipoIdentificacionEnum tipoIdentificacionTramitador) {
        this.tipoIdentificacionTramitador = tipoIdentificacionTramitador;
    }

    /**
     * @return the numeroIdentificacionTramitador
     */
    public String getNumeroIdentificacionTramitador() {
        return numeroIdentificacionTramitador;
    }

    /**
     * @param numeroIdentificacionTramitador the numeroIdentificacionTramitador to set
     */
    public void setNumeroIdentificacionTramitador(String numeroIdentificacionTramitador) {
        this.numeroIdentificacionTramitador = numeroIdentificacionTramitador;
    }

    /**
     * @return the nombreCompletoTramitador
     */
    public String getNombreCompletoTramitador() {
        return nombreCompletoTramitador;
    }

    /**
     * @param nombreCompletoTramitador the nombreCompletoTramitador to set
     */
    public void setNombreCompletoTramitador(String nombreCompletoTramitador) {
        this.nombreCompletoTramitador = nombreCompletoTramitador;
    }

    /**
     * @return the fechaPago
     */
    public Long getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Long fechaPago) {
        this.fechaPago = fechaPago;
    }

	public Boolean getEnProcesamiento() {
		return enProcesamiento;
	}

	public void setEnProcesamiento(Boolean enProcesamiento) {
		this.enProcesamiento = enProcesamiento;
	}

	/**
	 * @return the enBandejaTransitoria
	 */
	public Boolean getEnBandejaTransitoria() {
		return enBandejaTransitoria;
	}

	/**
	 * @param enBandejaTransitoria the enBandejaTransitoria to set
	 */
	public void setEnBandejaTransitoria(Boolean enBandejaTransitoria) {
		this.enBandejaTransitoria = enBandejaTransitoria;
	}

    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }

    public String getCuentaBancariaRecaudoTexto() {
        return cuentaBancariaRecaudoTexto;
    }

    public void setCuentaBancariaRecaudoTexto(String cuentaBancariaRecaudoTexto) {
        this.cuentaBancariaRecaudoTexto = cuentaBancariaRecaudoTexto;
    }
        

	@Override
	public String toString() {
		return "{" +
			" idAporte='" + getIdAporte() + "'" +
			", numOperacion='" + getNumOperacion() + "'" +
			", fecha='" + getFecha() + "'" +
			", fechaPago='" + getFechaPago() + "'" +
			", metodo='" + getMetodo() + "'" +
			", conDetalle='" + getConDetalle() + "'" +
			", numPlanilla='" + getNumPlanilla() + "'" +
			", estadoArchivo='" + getEstadoArchivo() + "'" +
			", tipoArchivo='" + getTipoArchivo() + "'" +
			", tipoPlanilla='" + getTipoPlanilla() + "'" +
			", periodo='" + getPeriodo() + "'" +
			", monto='" + getMonto() + "'" +
			", interes='" + getInteres() + "'" +
			", idEcmArchivo='" + getIdEcmArchivo() + "'" +
			", gestionado='" + getGestionado() + "'" +
			", resultado='" + getResultado() + "'" +
			", tieneModificaciones='" + getTieneModificaciones() + "'" +
			", cotizanteDTO='" + getCotizanteDTO() + "'" +
			", comentariosResultado='" + getComentariosResultado() + "'" +
			", historico='" + getHistorico() + "'" +
			", total='" + getTotal() + "'" +
			", idRegistroGeneral='" + getIdRegistroGeneral() + "'" +
			", idRegistroGeneralNuevo='" + getIdRegistroGeneralNuevo() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", idPersonaCotizante='" + getIdPersonaCotizante() + "'" +
			", idEmpresa='" + getIdEmpresa() + "'" +
			", montoRegistro='" + getMontoRegistro() + "'" +
			", interesRegistro='" + getInteresRegistro() + "'" +
			", estadoAporte='" + getEstadoAporte() + "'" +
			", tipoSolicitante='" + getTipoSolicitante() + "'" +
			", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
			", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
			", pagadorPorTerceros='" + getPagadorPorTerceros() + "'" +
			", listIdAporteDetallados='" + getListIdAporteDetallados() + "'" +
			", nombreCompleto='" + getNombreCompleto() + "'" +
			", codigoEntidadFinanciera='" + getCodigoEntidadFinanciera() + "'" +
			", idSolicitudCorreccion='" + getIdSolicitudCorreccion() + "'" +
			", idSolicitudGlobal='" + getIdSolicitudGlobal() + "'" +
			", montoCorreccion='" + getMontoCorreccion() + "'" +
			", interesesCorreccion='" + getInteresesCorreccion() + "'" +
			", totalCorreccion='" + getTotalCorreccion() + "'" +
			", tipoIdentificacionTramitador='" + getTipoIdentificacionTramitador() + "'" +
			", numeroIdentificacionTramitador='" + getNumeroIdentificacionTramitador() + "'" +
			", nombreCompletoTramitador='" + getNombreCompletoTramitador() + "'" +
			", enProcesamiento='" + getEnProcesamiento() + "'" +
			", enBandejaTransitoria='" + getEnBandejaTransitoria() + "'" +
			", cuentaBancariaRecaudo='" + getCuentaBancariaRecaudo() + "'" +
			", cuentaBancariaRecaudoTexto='" + getCuentaBancariaRecaudoTexto() + "'" +
			"}";
	}
    
}
