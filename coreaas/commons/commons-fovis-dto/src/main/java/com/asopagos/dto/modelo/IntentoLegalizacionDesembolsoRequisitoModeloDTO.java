package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolsoRequisito;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene las propiedades de acceso a la entidad IntentoLegalizacionDesembolsoRequisito <br/>
 * <b>Módulo:</b> Asopagos - HU 3.2.4-053 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co"> alquintero</a>
 */
public class IntentoLegalizacionDesembolsoRequisitoModeloDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4535730564664180811L;

	/**
     * Código identificador de llave primaria del intento de legalización y desembolso requisito
     */
    private Long idIntentoLegalizacionDesembolsoRequisito;

    /**
     * Referencia el intento de legalización y desembolso asociada al requisito
     */
    private Long idIntentoLegalizacionDesembolso;

    /**
     * Referencia el requisito que falló en el intento de legalización y desembolso
     */
    private Long idRequisito;

    public IntentoLegalizacionDesembolsoRequisitoModeloDTO() {
    }

    public IntentoLegalizacionDesembolsoRequisitoModeloDTO(IntentoLegalizacionDesembolsoRequisito intentoLegalizacionDesembolsoRequisito) {
        copyEntityToDTO(intentoLegalizacionDesembolsoRequisito);
    }

    public IntentoLegalizacionDesembolsoRequisitoModeloDTO(Long intentoLegalizacionDesembolso, Long idRequisito) {
        this.setIdIntentoLegalizacionDesembolso(intentoLegalizacionDesembolso);
        this.setIdRequisito(idRequisito);
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return El Entity convertido
     */
    public IntentoLegalizacionDesembolsoRequisito convertToEntity() {
        IntentoLegalizacionDesembolsoRequisito intentoLegalizacionDesembolsoRequisito = new IntentoLegalizacionDesembolsoRequisito();
        intentoLegalizacionDesembolsoRequisito
                .setIdIntentoLegalizacionDesembolsoRequisito(this.getIdIntentoLegalizacionDesembolsoRequisito());
        IntentoLegalizacionDesembolso intentoLegalizacionDesembolso = new IntentoLegalizacionDesembolso();
        intentoLegalizacionDesembolso.setIdIntentoLegalizacionDesembolso(this.getIdIntentoLegalizacionDesembolso());
        intentoLegalizacionDesembolsoRequisito.setIntentoLegalizacionDesembolso(intentoLegalizacionDesembolso);
        intentoLegalizacionDesembolsoRequisito.setIdRequisito(this.getIdRequisito());
        return intentoLegalizacionDesembolsoRequisito;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param intentoLegalizacionDesembolsoRequisito
     * @return El Entity con las propiedades modificadas.
     */
    public IntentoLegalizacionDesembolsoRequisito copyDTOToEntity(
            IntentoLegalizacionDesembolsoRequisito intentoLegalizacionDesembolsoRequisito) {
        if (this.getIdIntentoLegalizacionDesembolsoRequisito() != null) {
            intentoLegalizacionDesembolsoRequisito
                    .setIdIntentoLegalizacionDesembolsoRequisito(this.getIdIntentoLegalizacionDesembolsoRequisito());
        }
        if (this.getIdIntentoLegalizacionDesembolso() != null) {
            IntentoLegalizacionDesembolso intentoLegalizacionDesembolso = new IntentoLegalizacionDesembolso();
            intentoLegalizacionDesembolso.setIdIntentoLegalizacionDesembolso(this.getIdIntentoLegalizacionDesembolso());
            intentoLegalizacionDesembolsoRequisito.setIntentoLegalizacionDesembolso(intentoLegalizacionDesembolso);
        }
        if (this.getIdRequisito() != null) {
            intentoLegalizacionDesembolsoRequisito.setIdRequisito(this.getIdRequisito());
        }
        return intentoLegalizacionDesembolsoRequisito;
    }

    /**
     * Copia las propiedades del entity que llega por parámetro al actual DTO.
     * @param intentoLegalizacionDesembolsoRequisito
     */
    public void copyEntityToDTO(IntentoLegalizacionDesembolsoRequisito intentoLegalizacionDesembolsoRequisito) {
        if (intentoLegalizacionDesembolsoRequisito.getIdIntentoLegalizacionDesembolsoRequisito() != null) {
            this.setIdIntentoLegalizacionDesembolsoRequisito(
                    intentoLegalizacionDesembolsoRequisito.getIdIntentoLegalizacionDesembolsoRequisito());
        }
        if (intentoLegalizacionDesembolsoRequisito.getIntentoLegalizacionDesembolso() != null
                && intentoLegalizacionDesembolsoRequisito.getIntentoLegalizacionDesembolso().getIdIntentoLegalizacionDesembolso() != null) {
            this.setIdIntentoLegalizacionDesembolso(
                    intentoLegalizacionDesembolsoRequisito.getIntentoLegalizacionDesembolso().getIdIntentoLegalizacionDesembolso());
        }
        if (intentoLegalizacionDesembolsoRequisito.getIdRequisito() != null) {
            this.setIdRequisito(intentoLegalizacionDesembolsoRequisito.getIdRequisito());
        }
    }

    /**
     * @return the idIntentoLegalizacionDesembolsoRequisito
     */
    public Long getIdIntentoLegalizacionDesembolsoRequisito() {
        return idIntentoLegalizacionDesembolsoRequisito;
    }

    /**
     * @param idIntentoLegalizacionDesembolsoRequisito
     *        the idIntentoLegalizacionDesembolsoRequisito to set
     */
    public void setIdIntentoLegalizacionDesembolsoRequisito(Long idIntentoLegalizacionDesembolsoRequisito) {
        this.idIntentoLegalizacionDesembolsoRequisito = idIntentoLegalizacionDesembolsoRequisito;
    }

    /**
     * @return the idIntentoLegalizacionDesembolso
     */
    public Long getIdIntentoLegalizacionDesembolso() {
        return idIntentoLegalizacionDesembolso;
	}

	/**
     * @param idIntentoLegalizacionDesembolso
     *        the idIntentoLegalizacionDesembolso to set
     */
    public void setIdIntentoLegalizacionDesembolso(Long idIntentoLegalizacionDesembolso) {
        this.idIntentoLegalizacionDesembolso = idIntentoLegalizacionDesembolso;
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
