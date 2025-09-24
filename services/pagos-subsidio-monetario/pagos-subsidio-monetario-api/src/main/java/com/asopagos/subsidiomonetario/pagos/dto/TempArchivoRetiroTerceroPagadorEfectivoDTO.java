package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.subsidiomonetario.pagos.ConvenioTerceroPagador;
import com.asopagos.entidades.transversal.core.TempArchivoRetiroTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.core.ReultadoValidacionCampoEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoConvenioEnum;

/**
 * <b>Descripción:</b> DTO que contiene los datos necesarios para gestionar un convenio del tercero pagador.
 * <b>Módulo:</b> Asopagos - HU-31-210<br/>
 * 
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class TempArchivoRetiroTerceroPagadorEfectivoDTO implements Serializable {

    private static final long serialVersionUID = 4617551990731504954L;

  private Long idTempArchivoRetiroTerceroPagadorEfectivo;
   
	private Long idConvenio;
    
	private Integer linea;

	private String tipoIdentificacionAdmin;
	
	private String numeroIdentificacionAdmin;

	private String idTransaccion;

	private Double valorTransaccion;

	private Date fechaHoraTransaccion;

	private String codigoDepartamento;

	private String codigoMunicipio;
	
	private String nombreCampo;	
	
	private ReultadoValidacionCampoEnum resultado;	
		
	private String motivo;
	
	private Long sitioCobro;
	private Long archivoRetiroTerceroPagadorEfectivo;

    /**
     * Constructor vacio de la clase.
     */
    public TempArchivoRetiroTerceroPagadorEfectivoDTO() {
    }

    /**
     * Constructor que inicializa el convenio a partir de la información de la
     * entidad del convenio y la empresa.
     * 
     * @param convenio
     *        entidad que contiene los datos del convenio.
     * @param empresa
     *        entidad que contiene los datos del convenio relacionados con una empresa.
     * @param dsc
     *        entidad que contiene el documento de soporte relacionado al convenio
     * @param ubicacion
     *        entidad que contiene los datos de la ubicación relacionada con el convenio.
     */
    public TempArchivoRetiroTerceroPagadorEfectivoDTO(TempArchivoRetiroTerceroPagadorEfectivo t) {
       
        this.setCodigoDepartamento(t.getCodigoDepartamento());
        this.setCodigoMunicipio(t.getCodigoMunicipio());
        this.setFechaHoraTransaccion(t.getFechaHoraTransaccion());
        this.setIdConvenio(t.getIdConvenio());
        this.setIdTempArchivoRetiroTerceroPagadorEfectivo(t.getIdTempArchivoRetiroTerceroPagadorEfectivo());
        this.setIdTransaccion(t.getIdTransaccion());
        this.setLinea(t.getLinea());
        this.setMotivo(t.getMotivo());
        this.setNombreCampo(t.getNombreCampo());
        this.setNumeroIdentificacionAdmin(t.getNumeroIdentificacionAdmin());
        this.setResultado(t.getResultado());
        this.setTipoIdentificacionAdmin(t.getTipoIdentificacionAdmin());
        this.setValorTransaccion(t.getValorTransaccion());
        this.setSitioCobro(t.getSitioCobro());
        this.setArchivoRetiroTerceroPagadorEfectivo(t.getArchivoRetiroTerceroPagadorEfectivo());
    }
    
    /**
     * Metodo que convierte el DTO de convenio en entidad.
     * @return entidad del convenio del tercero pagador
     */
    public TempArchivoRetiroTerceroPagadorEfectivo convertToTempArchivoRetiroTerceroPagadorEfectivoEntity() {

    	TempArchivoRetiroTerceroPagadorEfectivo t = new TempArchivoRetiroTerceroPagadorEfectivo();

    	 t.setCodigoDepartamento(this.getCodigoDepartamento());
         t.setCodigoMunicipio(this.getCodigoMunicipio());
         t.setFechaHoraTransaccion(this.getFechaHoraTransaccion());
         t.setIdConvenio(this.getIdConvenio());
         //t.setIdTempArchivoRetiroTerceroPagadorEfectivo(this.getIdTempArchivoRetiroTerceroPagadorEfectivo());
         t.setIdTransaccion(this.getIdTransaccion());
         t.setLinea(this.getLinea());
         t.setMotivo(this.getMotivo());
         t.setNombreCampo(this.getNombreCampo());
         t.setNumeroIdentificacionAdmin(this.getNumeroIdentificacionAdmin());
         t.setResultado(this.getResultado());
         t.setTipoIdentificacionAdmin(this.getTipoIdentificacionAdmin());
         t.setValorTransaccion(this.getValorTransaccion());
         t.setSitioCobro(this.getSitioCobro());
         t.setArchivoRetiroTerceroPagadorEfectivo(this.getArchivoRetiroTerceroPagadorEfectivo());
         t.setIdTempArchivoRetiroTerceroPagadorEfectivo(this.getIdTempArchivoRetiroTerceroPagadorEfectivo());

        return t;
    }   

	public Long getIdTempArchivoRetiroTerceroPagadorEfectivo() {
		return idTempArchivoRetiroTerceroPagadorEfectivo;
	}

	public void setIdTempArchivoRetiroTerceroPagadorEfectivo(Long idTempArchivoRetiroTerceroPagadorEfectivo) {
		this.idTempArchivoRetiroTerceroPagadorEfectivo = idTempArchivoRetiroTerceroPagadorEfectivo;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public String getTipoIdentificacionAdmin() {
		return tipoIdentificacionAdmin;
	}

	public void setTipoIdentificacionAdmin(String tipoIdentificacionAdmin) {
		this.tipoIdentificacionAdmin = tipoIdentificacionAdmin;
	}

	public String getNumeroIdentificacionAdmin() {
		return numeroIdentificacionAdmin;
	}

	public void setNumeroIdentificacionAdmin(String numeroIdentificacionAdmin) {
		this.numeroIdentificacionAdmin = numeroIdentificacionAdmin;
	}

	public String getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Double getValorTransaccion() {
		return valorTransaccion;
	}

	public void setValorTransaccion(Double valorTransaccion) {
		this.valorTransaccion = valorTransaccion;
	}

	public Date getFechaHoraTransaccion() {
		return fechaHoraTransaccion;
	}

	public void setFechaHoraTransaccion(Date fechaHoraTransaccion) {
		this.fechaHoraTransaccion = fechaHoraTransaccion;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public ReultadoValidacionCampoEnum getResultado() {
		return resultado;
	}

	public void setResultado(ReultadoValidacionCampoEnum resultado) {
		this.resultado = resultado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Long getSitioCobro() {
		return sitioCobro;
	}

	public void setSitioCobro(Long sitioCobro) {
		this.sitioCobro = sitioCobro;
	}

	public Long getArchivoRetiroTerceroPagadorEfectivo() {
		return archivoRetiroTerceroPagadorEfectivo;
	}

	public void setArchivoRetiroTerceroPagadorEfectivo(Long archivoRetiroTerceroPagadorEfectivo) {
		this.archivoRetiroTerceroPagadorEfectivo = archivoRetiroTerceroPagadorEfectivo;
	}

   
}
