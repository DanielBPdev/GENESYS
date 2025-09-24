package com.asopagos.pila.dto;

import java.util.List;

import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;

/**
 * DTO para el paso de mensajes de respuesta de parte de los servicios PILA
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
public class RespuestaServicioDTO {
	/**
	 * Nombre del archivo
	 * */
	private String fileName;
	
	/**
	 * ID del documento en ECM
	 * */
	private String idDocumento;
	
	/**
	 * Mensaje de respuesta
	 * */
	private String mensajeRespuesta;
	
	/**
	 * Número de planilla
	 * */
	private Long idIndicePlanilla;
	
	/**
	 * Estado de proceso de archivo PILA de referencia
	 * */
	private EstadoProcesoArchivoEnum estado;
	
	/**
	 * Bloque de validación en el que se presenta error
	 * */
	private BloqueValidacionEnum bloque;
	
	/**
	 * Tipo de error encontrado
	 * */
	private TipoErrorValidacionEnum tipoError;
	
	/**
	 * Indicador de archivo de reproceso
	 * */
	private Boolean esReproceso = Boolean.FALSE;
	
	/**
	 * Objeto índice de planilla creado
	 * */
	private Object indice;
    
    /** 
     * Listado de índices OI asociados a un indice OF 
     * */
    private List<IndicePlanilla> indicesOIenOF;
	

	/**
	 * @return the mensajeRespuesta
	 */
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	/**
	 * @param mensajeRespuesta the mensajeRespuesta to set
	 */
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the idDocumento
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the estado
	 */
	public EstadoProcesoArchivoEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoProcesoArchivoEnum estado) {
		this.estado = estado;
	}

	/**
	 * @return the idIndicePlanilla
	 */
	public Long getIdIndicePlanilla() {
		return idIndicePlanilla;
	}

	/**
	 * @param idIndicePlanilla the idIndicePlanilla to set
	 */
	public void setIdIndicePlanilla(Long idIndicePlanilla) {
		this.idIndicePlanilla = idIndicePlanilla;
	}

	/**
	 * @return the bloque
	 */
	public BloqueValidacionEnum getBloque() {
		return bloque;
	}

	/**
	 * @param bloque the bloque to set
	 */
	public void setBloque(BloqueValidacionEnum bloque) {
		this.bloque = bloque;
	}

	/**
	 * @return the tipoError
	 */
	public TipoErrorValidacionEnum getTipoError() {
		return tipoError;
	}

	/**
	 * @param tipoError the tipoError to set
	 */
	public void setTipoError(TipoErrorValidacionEnum tipoError) {
		this.tipoError = tipoError;
	}

    /**
     * @return the esReproceso
     */
    public Boolean getEsReproceso() {
        return esReproceso;
    }

    /**
     * @param esReproceso the esReproceso to set
     */
    public void setEsReproceso(Boolean esReproceso) {
        this.esReproceso = esReproceso;
    }

    /**
     * @return the indice
     */
    public Object getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Object indice) {
        this.indice = indice;
    }

	/**
	 * @return the indicesOIenOF
	 */
	public List<IndicePlanilla> getIndicesOIenOF() {
		return indicesOIenOF;
	}

	/**
	 * @param indicesOIenOF the indicesOIenOF to set
	 */
	public void setIndicesOIenOF(List<IndicePlanilla> indicesOIenOF) {
		this.indicesOIenOF = indicesOIenOF;
	}	

	@Override
	public String toString() {
		return "{" +
			" fileName='" + getFileName() + "'" +
			", idDocumento='" + getIdDocumento() + "'" +
			", mensajeRespuesta='" + getMensajeRespuesta() + "'" +
			", idIndicePlanilla='" + getIdIndicePlanilla() + "'" +
			", estado='" + getEstado() + "'" +
			", bloque='" + getBloque() + "'" +
			", tipoError='" + getTipoError() + "'" +
			", indice='" + getIndice() + "'" +
			", indicesOIenOF='" + getIndicesOIenOF() + "'" +
			"}";
	}

}
