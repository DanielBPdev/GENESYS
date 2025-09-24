package com.asopagos.pila.dto;

import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;

/**
 * <b>Descripcion:</b> DTO que extiende la funcionalidad del DetailedErrorDTO del componente FileProcessor<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero Echeverry</a>
 */

public class ErrorDetalladoValidadorDTO extends DetailedErrorDTO {
    private static final long serialVersionUID = 1L;
    
    /** ID del campo del error */
    private String idCampoError;

    /** Tipo de error reportado */
    private TipoErrorValidacionEnum tipoError;
    
    /** Bloque de validación en el que se genera el error */
    private BloqueValidacionEnum bloque;
    
    /** Código del error presentado */
    private String codigoError;
    
    /** ID de índice de planilla */
    private Long idIndicePlanilla;
    
    /** Tipo de archivo que se referencia */
    private TipoArchivoPilaEnum tipoArchivo;
    
    /** Valor del campo en archivo */
    private String valorCampo;
    
    /** ID de registro tipo 2 al cual corresponde el error (Bloque 4) */
    private Long idRegTipo2;

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
     * @return the codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
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
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the idCampoError
     */
    public String getIdCampoError() {
        return idCampoError;
    }

    /**
     * @param idCampoError the idCampoError to set
     */
    public void setIdCampoError(String idCampoError) {
        this.idCampoError = idCampoError;
    }

    /**
     * @return the valorCampo
     */
    public String getValorCampo() {
        return valorCampo;
    }

    /**
     * @param valorCampo the valorCampo to set
     */
    public void setValorCampo(String valorCampo) {
        this.valorCampo = valorCampo;
    }

    /**
     * @return the idRegTipo2
     */
    public Long getIdRegTipo2() {
        return idRegTipo2;
    }

    /**
     * @param idRegTipo2 the idRegTipo2 to set
     */
    public void setIdRegTipo2(Long idRegTipo2) {
        this.idRegTipo2 = idRegTipo2;
    }


    @Override
    public String toString() {
        return "{" +
            " idCampoError='" + getIdCampoError() + "'" +
            ", tipoError='" + getTipoError() + "'" +
            ", bloque='" + getBloque() + "'" +
            ", codigoError='" + getCodigoError() + "'" +
            ", idIndicePlanilla='" + getIdIndicePlanilla() + "'" +
            ", tipoArchivo='" + getTipoArchivo() + "'" +
            ", valorCampo='" + getValorCampo() + "'" +
            ", idRegTipo2='" + getIdRegTipo2() + "'" +
            "}";
    }

}
