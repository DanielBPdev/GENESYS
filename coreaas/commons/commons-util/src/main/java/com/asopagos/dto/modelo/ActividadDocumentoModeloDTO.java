package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.ActividadDocumento;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.cartera.TipoDocumentoAdjuntoCarteraEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU 209<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class ActividadDocumentoModeloDTO extends DocumentoSoporteModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -231996696618927854L;

	/**
	 * Identificador de actividad de documento.
	 */
	private Long idActividadDocumento;

	/**
	 * Identificador de la actividad de cartera
	 */
	private Long idActividadCartera;

	/**
	 * Identificador del ecm
	 */
	private String idEcm;

	/**
	 * Atributo que representa el tipo de documento adjunto
	 */
    private TipoDocumentoAdjuntoCarteraEnum tipoDocumentoCartera;
	
	/**
	 * Constructor de la clase
	 */
	public ActividadDocumentoModeloDTO() {
	}

	
    /**
     * @param idActividadDocumento
     * @param idActividadCartera
     * @param idEcm
     * @param tipoDocumentoCartera
     */
    public ActividadDocumentoModeloDTO(ActividadDocumento actividadDocumento) {
        convertToDTO(actividadDocumento);
    }


    /**
	 * Método que obtiene el DTO de la superclase
	 * 
	 * @return Objeto <code>DocumentoSoporteModeloDTO</code> equivalente
	 */
	public DocumentoSoporteModeloDTO obtenerDocumentoSoporteDTO() {
		DocumentoSoporteModeloDTO documentoDTO = new DocumentoSoporteModeloDTO();
		documentoDTO.setDescripcionComentarios(this.getDescripcionComentarios());
		documentoDTO.setFechaHoraCargue(this.getFechaHoraCargue());
		documentoDTO.setIdDocumentoSoporte(this.getIdDocumentoSoporte());
		documentoDTO.setIdentificacionDocumento(this.getIdentificacionDocumento());
		documentoDTO.setNombre(this.getNombre());
		documentoDTO.setTipoDocumento(this.getTipoDocumento());
		documentoDTO.setVersionDocumento(this.getVersionDocumento());
		return documentoDTO;
	}

	/**
	 * Metodo que sirve para convertir de entidad a DTO
	 */
	public void convertToDTO(ActividadDocumento actividadDocumento) {
		if (actividadDocumento.getDocumentoSoporte() != null) {
			super.convertToDTO(actividadDocumento.getDocumentoSoporte());
		}
		this.setIdActividadDocumento(actividadDocumento.getIdActividadDocumento());
		this.setIdActividadCartera(actividadDocumento.getIdActividadCartera());
		this.setIdEcm(actividadDocumento.getIdEcm());
		this.setTipoDocumentoCartera(actividadDocumento.getTipoDocumento());
	}

	/**
	 * Metodo que sirve para convertir de DTO a entidad
	 */
	public ActividadDocumento convertToActividadDocumentoEntity() {
		ActividadDocumento actividadDocumento = new ActividadDocumento();
		actividadDocumento.setIdActividadDocumento(this.idActividadDocumento);
		actividadDocumento.setIdActividadCartera(this.idActividadCartera);		
		DocumentoSoporte documentoSoporte = super.convertToEntity();
		actividadDocumento.setDocumentoSoporte(documentoSoporte);
		actividadDocumento.setIdEcm(documentoSoporte.getIdentificacionDocumento());
		actividadDocumento.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO.equals(documentoSoporte.getTipoDocumento())?
		        TipoDocumentoAdjuntoCarteraEnum.OTROS:TipoDocumentoAdjuntoCarteraEnum.valueOf(documentoSoporte.getTipoDocumento().name()));
		return actividadDocumento;

	}

	/**
	 * Obtiene el valor de idActividadDocumento
	 * 
	 * @return El valor de idActividadDocumento
	 */
	public Long getIdActividadDocumento() {
		return idActividadDocumento;
	}

	/**
	 * Establece el valor de idActividadDocumento
	 * 
	 * @param idActividadDocumento
	 *            El valor de idActividadDocumento por asignar
	 */
	public void setIdActividadDocumento(Long idActividadDocumento) {
		this.idActividadDocumento = idActividadDocumento;
	}

	/**
	 * Obtiene el valor de idActividadCartera
	 * 
	 * @return El valor de idActividadCartera
	 */
	public Long getIdActividadCartera() {
		return idActividadCartera;
	}

	/**
	 * Establece el valor de idActividadCartera
	 * 
	 * @param idActividadCartera
	 *            El valor de idActividadCartera por asignar
	 */
	public void setIdActividadCartera(Long idActividadCartera) {
		this.idActividadCartera = idActividadCartera;
	}

    /**
     * @return the idEcm
     */
    public String getIdEcm() {
        return idEcm;
    }

    /**
     * @param idEcm the idEcm to set
     */
    public void setIdEcm(String idEcm) {
        this.idEcm = idEcm;
    }

    /**
     * @return the tipoDocumentoCartera
     */
    public TipoDocumentoAdjuntoCarteraEnum getTipoDocumentoCartera() {
        return tipoDocumentoCartera;
    }

    /**
     * @param tipoDocumentoCartera the tipoDocumentoCartera to set
     */
    public void setTipoDocumentoCartera(TipoDocumentoAdjuntoCarteraEnum tipoDocumentoCartera) {
        this.tipoDocumentoCartera = tipoDocumentoCartera;
    }

}
