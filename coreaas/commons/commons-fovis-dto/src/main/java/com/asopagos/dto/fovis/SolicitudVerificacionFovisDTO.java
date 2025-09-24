package com.asopagos.dto.fovis;

import java.io.Serializable;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;

public class SolicitudVerificacionFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4902691401837328562L;
    /**
     * Identificador solicitud global
     */
    private Long idSolicitud;
    /**
     * Identificador de la instancia de proceso BPM.
     */
    private Long idInstanciaProceso;
    /**
     * Datos de la solicitud de Verificación Fovis
     */
    private SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO;
    /**
     * Objeto que trae todos los datos de la postulación
     */
    private SolicitudPostulacionFOVISDTO datosPostulacionFovis;
    /**
     * Identificador del documento adjunto de control interno.
     */
    private String idDocumentoControlInterno;
    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the solicitudVerificacionFovisModeloDTO
     */
    public SolicitudVerificacionFovisModeloDTO getSolicitudVerificacionFovisModeloDTO() {
        return solicitudVerificacionFovisModeloDTO;
    }

    /**
     * @param solicitudVerificacionFovisModeloDTO
     *        the solicitudVerificacionFovisModeloDTO to set
     */
    public void setSolicitudVerificacionFovisModeloDTO(SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO) {
        this.solicitudVerificacionFovisModeloDTO = solicitudVerificacionFovisModeloDTO;
    }

    /**
     * @return the datosPostulacionFovis
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacionFovis() {
        return datosPostulacionFovis;
    }

    /**
     * @param datosPostulacionFovis
     *        the datosPostulacionFovis to set
     */
    public void setDatosPostulacionFovis(SolicitudPostulacionFOVISDTO datosPostulacionFovis) {
        this.datosPostulacionFovis = datosPostulacionFovis;
    }

    /**
     * @return the idDocumentoControlInterno
     */
    public String getIdDocumentoControlInterno() {
        return idDocumentoControlInterno;
    }

    /**
     * @param idDocumentoControlInterno
     *        the idDocumentoControlInterno to set
     */
    public void setIdDocumentoControlInterno(String idDocumentoControlInterno) {
        this.idDocumentoControlInterno = idDocumentoControlInterno;
    }

}
