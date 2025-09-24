package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.IntentoPostulacion;
import com.asopagos.entidades.ccf.fovis.IntentoPostulacionRequisito;

public class IntentoPostulacionRequisitoModeloDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4535730564664180811L;

	/**
     * Código identificador de llave primaria del intento de afiliación requisito
     */
    private Long idIntentoPostulacionRequisito;

    /**
     * Referencia el intento de afiliación asociada al requisito
     */
    private Long idIntentoPostulacion;

    /**
     * Referencia el requisito que falló en el intento de afiliación
     */
    private Long idRequisito;

    public IntentoPostulacionRequisitoModeloDTO() {
    }

    public IntentoPostulacionRequisitoModeloDTO(IntentoPostulacionRequisito intentoPostulacionRequisito) {
        copyEntityToDTO(intentoPostulacionRequisito);
    }

    public IntentoPostulacionRequisitoModeloDTO(Long intentoPostulacion, Long idRequisito) {
        this.setIdIntentoPostulacion(intentoPostulacion);
        this.setIdRequisito(idRequisito);
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return El Entity convertido
     */
    public IntentoPostulacionRequisito convertToEntity() {
        IntentoPostulacionRequisito intentoPostulacionRequisito = new IntentoPostulacionRequisito();
        intentoPostulacionRequisito.setIdIntentoPostulacionRequisito(this.getIdIntentoPostulacionRequisito());
        IntentoPostulacion intentoPostulacion = new IntentoPostulacion();
        intentoPostulacion.setIdIntentoPostulacion(this.getIdIntentoPostulacion());
        intentoPostulacionRequisito.setIntentoPostulacion(intentoPostulacion);
        intentoPostulacionRequisito.setIdRequisito(this.getIdRequisito());
        return intentoPostulacionRequisito;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param intentoPostulacionRequisito
     * @return El Entity con las propiedades modificadas.
     */
    public IntentoPostulacionRequisito copyDTOToEntity(IntentoPostulacionRequisito intentoPostulacionRequisito) {
        if (this.getIdIntentoPostulacionRequisito() != null) {
            intentoPostulacionRequisito.setIdIntentoPostulacionRequisito(this.getIdIntentoPostulacionRequisito());
        }
        if (this.getIdIntentoPostulacion() != null) {
        	IntentoPostulacion intentoPostulacion = new IntentoPostulacion();
        	intentoPostulacion.setIdIntentoPostulacion(this.getIdIntentoPostulacion());
            intentoPostulacionRequisito.setIntentoPostulacion(intentoPostulacion);
        }
        if (this.getIdRequisito() != null) {
            intentoPostulacionRequisito.setIdRequisito(this.getIdRequisito());
        }
        return intentoPostulacionRequisito;
    }

    /**
     * Copia las propiedades del entity que llega por parámetro al actual DTO.
     * @param intentoPostulacionRequisito
     */
    public void copyEntityToDTO(IntentoPostulacionRequisito intentoPostulacionRequisito) {
        if (intentoPostulacionRequisito.getIdIntentoPostulacionRequisito() != null) {
            this.setIdIntentoPostulacionRequisito(intentoPostulacionRequisito.getIdIntentoPostulacionRequisito());
        }
        if (intentoPostulacionRequisito.getIntentoPostulacion() != null && 
        		intentoPostulacionRequisito.getIntentoPostulacion().getIdIntentoPostulacion() != null) {
            this.setIdIntentoPostulacion(intentoPostulacionRequisito.getIntentoPostulacion().getIdIntentoPostulacion());
        }
        if (intentoPostulacionRequisito.getIdRequisito() != null) {
            this.setIdRequisito(intentoPostulacionRequisito.getIdRequisito());
        }
    }

    /**
     * @return the idIntentoPostulacionRequisito
     */
    public Long getIdIntentoPostulacionRequisito() {
        return idIntentoPostulacionRequisito;
    }

    /**
     * @param idIntentoPostulacionRequisito
     *        the idIntentoPostulacionRequisito to set
     */
    public void setIdIntentoPostulacionRequisito(Long idIntentoPostulacionRequisito) {
        this.idIntentoPostulacionRequisito = idIntentoPostulacionRequisito;
    }

    /**
	 * @return the idIntentoPostulacion
	 */
	public Long getIdIntentoPostulacion() {
		return idIntentoPostulacion;
	}

	/**
	 * @param idIntentoPostulacion the idIntentoPostulacion to set
	 */
	public void setIdIntentoPostulacion(Long idIntentoPostulacion) {
		this.idIntentoPostulacion = idIntentoPostulacion;
	}

	/**
     * @return the idRequisito
     */
    public Long getIdRequisito() {
        return idRequisito;
    }

    /**
     * @param idRequisito
     *        the idRequisito to set
     */
    public void setIdRequisito(Long idRequisito) {
        this.idRequisito = idRequisito;
    }

}
