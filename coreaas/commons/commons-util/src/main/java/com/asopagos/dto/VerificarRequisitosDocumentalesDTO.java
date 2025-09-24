package com.asopagos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripción:</b> DTO que transporta los de ingreso de un Afiliado
 * 
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
@XmlRootElement
public class VerificarRequisitosDocumentalesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long idSolicitudGlobal;

    @NotNull
    private ClasificacionEnum tipoClasificacion;

    @NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
    private ListaChequeoDTO listaChequeo;

    @NotNull
    private Boolean cumpleRequisitosDocumentales;

    @NotNull
    private FormatoEntregaDocumentoEnum documentosFisicos;

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the tipoClasificacion
     */
    public ClasificacionEnum getTipoClasificacion() {
        return tipoClasificacion;
    }

    /**
     * @param tipoClasificacion
     *        the tipoClasificacion to set
     */
    public void setTipoClasificacion(ClasificacionEnum tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    /**
     * @return the listaChequeo
     */
    public ListaChequeoDTO getListaChequeo() {
        return listaChequeo;
    }

    /**
     * @param listaChequeo
     *        the listaChequeo to set
     */
    public void setListaChequeo(ListaChequeoDTO listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

    /**
     * @return the cumpleRequisitosDocumentales
     */
    public Boolean getCumpleRequisitosDocumentales() {
        return cumpleRequisitosDocumentales;
    }

    /**
     * @param cumpleRequisitosDocumentales
     *        the cumpleRequisitosDocumentales to set
     */
    public void setCumpleRequisitosDocumentales(Boolean cumpleRequisitosDocumentales) {
        this.cumpleRequisitosDocumentales = cumpleRequisitosDocumentales;
    }

    /**
     * @return the documentosFisicos
     */
    public FormatoEntregaDocumentoEnum getDocumentosFisicos() {
        return documentosFisicos;
    }

    /**
     * @param documentosFisicos
     *        the documentosFisicos to set
     */
    public void setDocumentosFisicos(FormatoEntregaDocumentoEnum documentosFisicos) {
        this.documentosFisicos = documentosFisicos;
    }

}
