package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.core.GrupoRequisito;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <b>Descripción:</b> DTO para transmisión de datos de la lista de chequeo
 * <b>Historia de Usuario:</b> HU-TRA-091 Verificar información solicitud y
 * registrar resultado
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemChequeoDTO implements Serializable {

    /**
     * Código identificador de llave primaria del Item chequeo DTO
     */
	private Long idTemChequeo;

	/**
     * Referencia a la solicitud global asociada al item chequeo DTO
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idSolicitudGlobal;

	/**
     * Referencia al requisito asociado al item chequeo DTO
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idRequisito;

	/**
	 * Desripción del nombre del requisito asociado al Item Chequeo DTO 
	 */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private String nombreRequisito;

	/**
     * Descripción que indica si el requisito es estándar y debe solicitarse en primera instancia o 
     * en instancias posteriores durante el diligenciamiento de lista de chequeo
	 */
	private TipoRequisitoEnum tipoRequisito;

	/**
     * Id que identifica al documento asoaciado al item chequeo DTO
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private String identificadorDocumento;

	/**
     * Versión del documento que esta asociado al Item Chequeo DTO
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Short versionDocumento;

	/**
     * Descripción los estados la obligatoriedad de los requisitos 
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private EstadoRequisitoTipoSolicitanteEnum estadoRequisito;

    /**
     * Indicador S/N si el item chequeo cumple con el requisito [S=Si N=No]
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Boolean cumpleRequisito;

	/**
     * Descripción del tipo de formatos de entrega de documentos
     */
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private FormatoEntregaDocumentoEnum formatoEntregaDocumento;

	/**
     * Descripción de los comentarios asociadas al item chequeo DTO 
     */
	private String comentarios;

	/**
     * Indicador S/N si el item chequeo cumple con los requisitos del back [S=Si N=No]
     */
	private Boolean cumpleRequisitoBack;

	/**
     * Descripción de los comentarios del back asociadas al Item Chequeo DTO 
     */
	private String comentariosBack;
	
	/**
     * Descripción del texto de ayuda asociada al item chequeo DTO 
     */
	private String textoAyuda;
	
	/**
     * Id que identifica al documento previo asoaciado al item chequeo si tiene los permisos
     */
	private String identificadorDocumentoPrevio;
	
	/**
	 * Lista de los grupos de un requisito para el item chequeo 
	 */
	private List<GrupoRequisito> grupoRequisitos;
	
	/**
	 * Identificador del Requisito caja clasificación asociado al item chequeo
	 */
	private Long idRequisitoCajaClasificacion;

	/**
     * Fecha de la recepción de los documentos registrada en la lista de chequeo del afiliado principal
     */
    private Long fechaRecepcionDocumentos;
	
	/**
	 * Constructor de la clase
	 */
	public ItemChequeoDTO() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param idRequisito
	 * @param nombreRequisito
	 * @param tipoRequisito
	 * @param identificadorDocumento
	 * @param versionDocumento
	 * @param estadoRequisito
	 * @param cumpleRequisito
	 * @param formatoEntregaDocumento
	 * @param comentarios
	 * @param cumpleRequisitoBack
	 * @param comentariosBack
	 * @param textoAyuda
	 * @param identificadorDocumentoPrevio
	 */
	public ItemChequeoDTO(Long idRequisito, String nombreRequisito, TipoRequisitoEnum tipoRequisito,
            String identificadorDocumento, Short versionDocumento, EstadoRequisitoTipoSolicitanteEnum estadoRequisito,
            Boolean cumpleRequisito, FormatoEntregaDocumentoEnum formatoEntregaDocumento,
            String comentarios, Boolean cumpleRequisitoBack, String comentariosBack, String textoAyuda,
            Long idRequisitoCajaClasficacion, Date fechaRecepcionDocumento) {
        this.idRequisito = idRequisito;
        this.nombreRequisito = nombreRequisito;
        this.tipoRequisito = tipoRequisito;
        this.identificadorDocumento = identificadorDocumento;
        this.versionDocumento = versionDocumento;
        this.estadoRequisito = estadoRequisito;
        this.cumpleRequisito = cumpleRequisito;
        this.formatoEntregaDocumento = formatoEntregaDocumento;
        this.comentarios = comentarios;
        this.cumpleRequisitoBack = cumpleRequisitoBack;
        this.comentariosBack = comentariosBack;
        this.textoAyuda = textoAyuda;
        this.idRequisitoCajaClasificacion = idRequisitoCajaClasficacion;
        if (fechaRecepcionDocumento!= null){
            this.fechaRecepcionDocumentos = fechaRecepcionDocumento.getTime();    
        }
    }

	public static ItemChequeoDTO convertItemChequeoToDTO(ItemChequeo itemChequeo) {
		ItemChequeoDTO dto = new ItemChequeoDTO();
		dto.setIdTemChequeo(itemChequeo.getIdItemChequeo());
		dto.setComentarios(itemChequeo.getComentarios());
		dto.setComentariosBack(itemChequeo.getComentariosBack());
		dto.setCumpleRequisito(itemChequeo.getCumpleRequisito());
		dto.setCumpleRequisitoBack(itemChequeo.getCumpleRequisitoBack());
		dto.setEstadoRequisito(itemChequeo.getEstadoRequisito());
		dto.setFormatoEntregaDocumento(itemChequeo.getFormatoEntregaDocumento());
		dto.setIdentificadorDocumento(itemChequeo.getIdentificadorDocumento());
		if (itemChequeo.getSolicitudGlobal() != null) {
			dto.setIdSolicitudGlobal(itemChequeo.getSolicitudGlobal().getIdSolicitud());
		}
		if (itemChequeo.getRequisito() != null) {
			dto.setIdRequisito(itemChequeo.getRequisito().getIdRequisito());
			dto.setNombreRequisito(itemChequeo.getRequisito().getDescripcion());
		}
		dto.setVersionDocumento(itemChequeo.getVersionDocumento());
		return dto;
	}

	/**
	 * @return the idRequisito
	 */
	public Long getIdRequisito() {
		return idRequisito;
	}

	/**
	 * @param idRequisito
	 *            the idRequisito to set
	 */
	public void setIdRequisito(Long idRequisito) {
		this.idRequisito = idRequisito;
	}

	/**
	 * @return the identificadorDocumento
	 */
	public String getIdentificadorDocumento() {
		return identificadorDocumento;
	}

	/**
	 * @param identificadorDocumento
	 *            the identificadorDocumento to set
	 */
	public void setIdentificadorDocumento(String identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
	}

	/**
	 * @return the versionDocumento
	 */
	public Short getVersionDocumento() {
		return versionDocumento;
	}

	/**
	 * @param versionDocumento
	 *            the versionDocumento to set
	 */
	public void setVersionDocumento(Short versionDocumento) {
		this.versionDocumento = versionDocumento;
	}

	/**
	 * @return the estadoRequisito
	 */
	public EstadoRequisitoTipoSolicitanteEnum getEstadoRequisito() {
		return estadoRequisito;
	}

	/**
	 * @param estadoRequisito
	 *            the estadoRequisito to set
	 */
	public void setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum estadoRequisito) {
		this.estadoRequisito = estadoRequisito;
	}

	/**
	 * @return the cumpleRequisito
	 */
	public Boolean getCumpleRequisito() {
		return cumpleRequisito;
	}

	/**
	 * @param cumpleRequisito
	 *            the cumpleRequisito to set
	 */
	public void setCumpleRequisito(Boolean cumpleRequisito) {
		this.cumpleRequisito = cumpleRequisito;
	}

	/**
	 * @return the formatoEntregaDocumento
	 */
	public FormatoEntregaDocumentoEnum getFormatoEntregaDocumento() {
		return formatoEntregaDocumento;
	}

	/**
	 * @param formatoEntregaDocumento
	 *            the formatoEntregaDocumento to set
	 */
	public void setFormatoEntregaDocumento(FormatoEntregaDocumentoEnum formatoEntregaDocumento) {
		this.formatoEntregaDocumento = formatoEntregaDocumento;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the cumpleRequisitoBack
	 */
	public Boolean getCumpleRequisitoBack() {
		return cumpleRequisitoBack;
	}

	/**
	 * @param cumpleRequisitoBack
	 *            the cumpleRequisitoBack to set
	 */
	public void setCumpleRequisitoBack(Boolean cumpleRequisitoBack) {
		this.cumpleRequisitoBack = cumpleRequisitoBack;
	}

	/**
	 * @return the comentariosBack
	 */
	public String getComentariosBack() {
		return comentariosBack;
	}

	/**
	 * @param comentariosBack
	 *            the comentariosBack to set
	 */
	public void setComentariosBack(String comentariosBack) {
		this.comentariosBack = comentariosBack;
	}

	/**
	 * @return the nombreRequisito
	 */
	public String getNombreRequisito() {
		return nombreRequisito;
	}

	/**
	 * @param nombreRequisito
	 *            the nombreRequisito to set
	 */
	public void setNombreRequisito(String nombreRequisito) {
		this.nombreRequisito = nombreRequisito;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal
	 *            the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

	/**
	 * @return the tipoRequisito
	 */
	public TipoRequisitoEnum getTipoRequisito() {
		return tipoRequisito;
	}

	/**
	 * @param tipoRequisito
	 *            the tipoRequisito to set
	 */
	public void setTipoRequisito(TipoRequisitoEnum tipoRequisito) {
		this.tipoRequisito = tipoRequisito;
	}

	/**
	 * @return the idTemChequeo
	 */
	public Long getIdTemChequeo() {
		return idTemChequeo;
	}

	/**
	 * @param idTemChequeo
	 *            the idTemChequeo to set
	 */
	public void setIdTemChequeo(Long idTemChequeo) {
		this.idTemChequeo = idTemChequeo;
	}

    /**
     * @return the textoAyuda
     */
    public String getTextoAyuda() {
        return textoAyuda;
    }

    /**
     * @param textoAyuda the textoAyuda to set
     */
    public void setTextoAyuda(String textoAyuda) {
        this.textoAyuda = textoAyuda;
    }

    /**
     * @return the identificadorDocumentoPrevio
     */
    public String getIdentificadorDocumentoPrevio() {
        return identificadorDocumentoPrevio;
    }

    /**
     * @param identificadorDocumentoPrevio the identificadorDocumentoPrevio to set
     */
    public void setIdentificadorDocumentoPrevio(String identificadorDocumentoPrevio) {
        this.identificadorDocumentoPrevio = identificadorDocumentoPrevio;
    }

    /**
     * @return the grupoRequisitos
     */
    public List<GrupoRequisito> getGrupoRequisitos() {
        return grupoRequisitos;
    }

    /**
     * @param grupoRequisitos the grupoRequisitos to set
     */
    public void setGrupoRequisitos(List<GrupoRequisito> grupoRequisitos) {
        this.grupoRequisitos = grupoRequisitos;
    }

    /**
     * @return the idRequisitoCajaClasificacion
     */
    public Long getIdRequisitoCajaClasificacion() {
        return idRequisitoCajaClasificacion;
    }

    /**
     * @param idRequisitoCajaClasificacion the idRequisitoCajaClasificacion to set
     */
    public void setIdRequisitoCajaClasificacion(Long idRequisitoCajaClasificacion) {
        this.idRequisitoCajaClasificacion = idRequisitoCajaClasificacion;
    }

    /**
     * @return the fechaRecepcionDocumentos
     */
    public Long getFechaRecepcionDocumentos() {
        return fechaRecepcionDocumentos;
    }

    /**
     * @param fechaRecepcionDocumentos the fechaRecepcionDocumentos to set
     */
    public void setFechaRecepcionDocumentos(Long fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }
    
}
