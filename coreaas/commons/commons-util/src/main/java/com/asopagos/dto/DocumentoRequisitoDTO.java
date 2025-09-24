package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.TipoDocumentoRequisitoEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la información de un documento de la lista de chequeo<br/>
 * <b>Módulo:</b> Asopagos - HU TRA<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> jocorrea</a>
 */
public class DocumentoRequisitoDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 5765349321714466807L;

    /**
     * Identificador ECM del documento
     */
    private String identificadorDocumento;

    /**
     * Identificador del requisito
     */
    private Long idRequisito;

    /**
     * Descripcion del requisito asociado
     */
    private String descripcionRequisito;

    /**
     * Nombre dado al documento
     */
    private TipoDocumentoRequisitoEnum tipoDocumento;

    /**
     * Constructor por defecto
     */
    public DocumentoRequisitoDTO() {
        super();
    }

    /**
     * Constructor usado en consultas
     * @param identificadorDocumento
     *        Identificador ECM del documento
     * @param idRequisito
     *        Identificador requisito
     * @param descripcionRequisito
     *        Descripción del requisito
     * @param tipoDocumento
     *        Tipo de documento requisto
     */
    public DocumentoRequisitoDTO(String identificadorDocumento, Long idRequisito, String descripcionRequisito, String tipoDocumento) {
        super();
        this.identificadorDocumento = identificadorDocumento;
        this.idRequisito = idRequisito;
        this.descripcionRequisito = descripcionRequisito;
        if (tipoDocumento != null) {
            this.tipoDocumento = TipoDocumentoRequisitoEnum.valueOf(tipoDocumento);
        }
    }

    /**
     * @return the identificadorDocumento
     */
    public String getIdentificadorDocumento() {
        return identificadorDocumento;
    }

    /**
     * @param identificadorDocumento
     *        the identificadorDocumento to set
     */
    public void setIdentificadorDocumento(String identificadorDocumento) {
        this.identificadorDocumento = identificadorDocumento;
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

    /**
     * @return the descripcionRequisito
     */
    public String getDescripcionRequisito() {
        return descripcionRequisito;
    }

    /**
     * @param descripcionRequisito
     *        the descripcionRequisito to set
     */
    public void setDescripcionRequisito(String descripcionRequisito) {
        this.descripcionRequisito = descripcionRequisito;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumentoRequisitoEnum getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *        the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumentoRequisitoEnum tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
