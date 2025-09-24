package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class DetalleDatosAportanteDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 406167909387300012L;
    
    /**
     * Indica el tipo de solicitante que realizo el movimiento en aporte
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;
    
    /**
     * Período de pago
     */
    private String periodoAporte;

    /**
     * Aporte obligatorio del aporte
     */
    private BigDecimal valorAporte;

    /**
     * Cantidad de días pagados en el aporte
     */
    private Short diasCotizados;
    
    /**
     * Valor interés de mora para el aporte
     */
    private BigDecimal valorInteresMora;

    /**
     * Fecha de recaudo del aporte
     */
    private String fechaRecaudo;

    /**
     * Método de recaudo.
     */
    private ModalidadRecaudoAporteEnum metodoRecaudo;

    /**
     * Descripción del estado del aportante
     */
    private EstadoEmpleadorEnum estadoAportante;

    /**
     * Marca de Referencia que indica que el aportante es "pagador por terceros"
     * para el aporte asociado
     */
    private Boolean pagadorPorTerceros;

    /**
     * Indica la clase de aportante indicada en <code>ClaseAportanteEnum</code>
     */
    private ClaseAportanteEnum claseAportante;

    /**
     * Código identificador de llave primaria llamada No. de operación de recaudo general
     */
    private Long idRegistroGeneral;

    /**
     * Día hábil de vencimiento de aportes 
     */
    private Short diaHabilVencimientoAporte;

    /**
     * Código de identificación de la sucursal asociada al aporte
     */
    private String codigoSucursal;
    
    private String estadoAporteAportante;
    
    /**
     * Método constructor
     * 
     * @param aporteGeneral
     */
    public DetalleDatosAportanteDTO(AporteGeneral aporteGeneral) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        this.setTipoAportante(aporteGeneral.getTipoSolicitante());
        this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
        this.setValorAporte(aporteGeneral.getValorTotalAporteObligatorio() != null ? aporteGeneral.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
        this.setValorInteresMora(aporteGeneral.getValorInteresesMora());
        this.setFechaRecaudo(aporteGeneral.getFechaRecaudo()!=null ? format.format(aporteGeneral.getFechaRecaudo()):null);
        this.setMetodoRecaudo(aporteGeneral.getModalidadRecaudoAporte());
        this.setEstadoAportante(aporteGeneral.getEstadoAportante());
        this.setPagadorPorTerceros(aporteGeneral.getPagadorPorTerceros());
        this.setIdRegistroGeneral(aporteGeneral.getIdRegistroGeneral());
    }
    
    /**
     * Método constructor
     * 
     * @param aporteGeneral
     */
    public DetalleDatosAportanteDTO(AporteGeneral aporteGeneral, String codigoSucursal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        this.setTipoAportante(aporteGeneral.getTipoSolicitante());
        this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
        this.setValorAporte(aporteGeneral.getValorTotalAporteObligatorio() != null ? aporteGeneral.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
        this.setValorInteresMora(aporteGeneral.getValorInteresesMora());
        this.setFechaRecaudo(aporteGeneral.getFechaRecaudo()!=null ? format.format(aporteGeneral.getFechaRecaudo()):null);
        this.setMetodoRecaudo(aporteGeneral.getModalidadRecaudoAporte());
        this.setEstadoAportante(aporteGeneral.getEstadoAportante());
        this.setPagadorPorTerceros(aporteGeneral.getPagadorPorTerceros());
        this.setIdRegistroGeneral(aporteGeneral.getIdRegistroGeneral());
        
        this.setCodigoSucursal(codigoSucursal);
    }

    
    public DetalleDatosAportanteDTO(String tipoAportante, String periodoAporte, String valorAporte, String valorInteresMora, String fechaRecaudo, String metodoRecaudo, String estadoAportante, 
    		String pagadorPorTerceros, String idRegistroGeneral, String codigoSucursal, String diaHabilVencimientoAporte, String estadoAporteAportante) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        this.setTipoAportante(tipoAportante != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante) : null);
        this.setPeriodoAporte(periodoAporte);
        this.setValorAporte(valorAporte != null ? BigDecimal.valueOf(Double.parseDouble(valorAporte)) : BigDecimal.ZERO);
        this.setValorInteresMora(valorInteresMora != null ? BigDecimal.valueOf(Double.parseDouble(valorInteresMora)) : BigDecimal.ZERO);
        this.setFechaRecaudo(fechaRecaudo);
        this.setMetodoRecaudo(metodoRecaudo != null ? ModalidadRecaudoAporteEnum.valueOf(metodoRecaudo) : null);
        this.setEstadoAportante(estadoAportante != null ? EstadoEmpleadorEnum.valueOf(estadoAportante) : null);
        this.setPagadorPorTerceros((pagadorPorTerceros != null && pagadorPorTerceros.equals("1")) ? true : false);
        this.setIdRegistroGeneral(idRegistroGeneral != null ? Long.parseLong(idRegistroGeneral) : null);
        this.setCodigoSucursal(codigoSucursal);
        this.setDiaHabilVencimientoAporte(diaHabilVencimientoAporte != null ? Short.parseShort(diaHabilVencimientoAporte) : null);
        this.setEstadoAporteAportante(estadoAporteAportante);        
    }
    
    /**
     * 
     */
    public DetalleDatosAportanteDTO() {
    }

    /**
     * @return the tipoAportante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * @param tipoAportante
     *        the tipoAportante to set
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }
    
    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte
     *        the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the valorAporte
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * @param valorAporte
     *        the valorAporte to set
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    /**
     * @return the valorInteresMora
     */
    public BigDecimal getValorInteresMora() {
        return valorInteresMora;
    }

    /**
     * @param valorInteresMora
     *        the valorInteresMora to set
     */
    public void setValorInteresMora(BigDecimal valorInteresMora) {
        this.valorInteresMora = valorInteresMora;
    }

    /**
     * @return the fechaRecaudo
     */
    public String getFechaRecaudo() {
        return fechaRecaudo;
    }

    /**
     * @param fechaRecaudo
     *        the fechaRecaudo to set
     */
    public void setFechaRecaudo(String fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }

    /**
     * @return the metodoRecaudo
     */
    public ModalidadRecaudoAporteEnum getMetodoRecaudo() {
        return metodoRecaudo;
    }

    /**
     * @param metodoRecaudo
     *        the metodoRecaudo to set
     */
    public void setMetodoRecaudo(ModalidadRecaudoAporteEnum metodoRecaudo) {
        this.metodoRecaudo = metodoRecaudo;
    }

    /**
     * @return the estadoAportante
     */
    public EstadoEmpleadorEnum getEstadoAportante() {
        return estadoAportante;
    }

    /**
     * @param estadoAportante
     *        the estadoAportante to set
     */
    public void setEstadoAportante(EstadoEmpleadorEnum estadoAportante) {
        this.estadoAportante = estadoAportante;
    }

    /**
     * @return the pagadorPorTerceros
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
    }

    /**
     * @param pagadorPorTerceros
     *        the pagadorPorTerceros to set
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
    }

    /**
     * @return the claseAportante
     */
    public ClaseAportanteEnum getClaseAportante() {
        return claseAportante;
    }

    /**
     * @param claseAportante
     *        the claseAportante to set
     */
    public void setClaseAportante(ClaseAportanteEnum claseAportante) {
        this.claseAportante = claseAportante;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral
     *        the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }
    
    /**
     * @return the diaHabilVencimientoAporte
     */
    public Short getDiaHabilVencimientoAporte() {
        return diaHabilVencimientoAporte;
    }

    /**
     * @param diaHabilVencimientoAporte the diaHabilVencimientoAporte to set
     */
    public void setDiaHabilVencimientoAporte(Short diaHabilVencimientoAporte) {
        this.diaHabilVencimientoAporte = diaHabilVencimientoAporte;
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
	 * @return the estadoAporteAportante
	 */
	public String getEstadoAporteAportante() {
		return estadoAporteAportante;
	}

	/**
	 * @param estadoAporteAportante the estadoAporteAportante to set
	 */
	public void setEstadoAporteAportante(String estadoAporteAportante) {
		this.estadoAporteAportante = estadoAporteAportante;
	}
	
	
}
