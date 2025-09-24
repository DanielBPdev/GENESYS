package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.ActaAsignacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudAsignacion;

/**
 * 
 * Contiene la información del Acta de Asignacion FOVIS - Procesos FOVIS 3.2.3
 * 
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class ActaAsignacionFOVISModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 3347595361492121695L;

    /**
     * Identificador único, llave primaria
     */
    private Long idActaAsignacion;

    /**
     * Identificador de la solicitud de asignacion
     */
    private Long idSolicitudAsignacion;

    /**
     * Asociación de los datos de la solicitud de asignación
     * en el caso que sea necesario para presentar los datos
     */
    private SolicitudAsignacionFOVISModeloDTO solicitudAsignacion;

    /**
     * Fecha de creacion acta de asignacion fovis
     */
    private Long fechaActaAsignacionFovis;

    /**
     * Numero de resolucion del acta de aprobacion
     */
    private String numeroResolucion;

    /**
     * Ano de resolucion del acta de aprobacion
     */
    private String anoResolucion;

    /**
     * Fecha de resolucion de acta de asignación
     */
    private Long fechaResolucion;

    /**
     * Numero de oficio
     */
    private String numeroOficio;

    /**
     * Fecha de resolucion de acta de asignación
     */
    private Long fechaOficio;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable1;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable1;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable2;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable2;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable3;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable3;

    /**
     * Fecha de confirmacion del valor por area contable
     */
    private Long fechaConfirmacion;

    /**
     * Fecha de corte asociada al valor disponible informado
     */
    private Long fechaCorte;

    /**
     * Fecha de inicio de vigencia de los subsidios asignados
     */
    private Long fechaInicioVigencia;

    /**
     * Fecha de vencimiento vigencia de los subsidios asignados
     */
    private Long fechaFinVigencia;

    /**
     * Identificador del documento generado para el acta
     */
    private String idDocumento;
    /**
     * Identificador del documento consolidado con el acta de asignacion y las cartas por hogar
     */
    private String idDocumentoConsolidado;

    /**
     * Fecha de publicación
     */
    private Long fechaPublicacion;

    /**
     * Constructor vacio
     */
    public ActaAsignacionFOVISModeloDTO() {
    }

    /**
     * Constructor que recibe el entity del acta de asignación y el de la solicitud de asignación.
     * @param actaAsignacionFOVIS
     *        acta de asignación.
     * @param solicitudAsignacion
     *        Solicitud de asignación.
     */
    public ActaAsignacionFOVISModeloDTO(ActaAsignacionFOVIS actaAsignacionFOVIS, SolicitudAsignacion solicitudAsignacion) {
        convertToDTO(actaAsignacionFOVIS);
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacionFOVISModeloDTO = new SolicitudAsignacionFOVISModeloDTO();
        solicitudAsignacionFOVISModeloDTO.convertToDTO(solicitudAsignacion);
        this.setSolicitudAsignacion(solicitudAsignacionFOVISModeloDTO);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return ResultadosAsignacionFOVIS
     */
    public ActaAsignacionFOVIS convertToEntity() {
        ActaAsignacionFOVIS actaAsignacionFOVIS = new ActaAsignacionFOVIS();

        if (this.getAnoResolucion() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(Long.parseLong(this.getAnoResolucion()));
            actaAsignacionFOVIS.setAnoResolucion(String.valueOf(cal.get(Calendar.YEAR)));
        }

        actaAsignacionFOVIS.setCargoResponsable1(this.getCargoResponsable1());
        actaAsignacionFOVIS.setCargoResponsable2(this.getCargoResponsable2());
        actaAsignacionFOVIS.setCargoResponsable3(this.getCargoResponsable3());
        actaAsignacionFOVIS.setFechaActaAsignacionFovis(
                this.getFechaActaAsignacionFovis() != null ? new Date(this.getFechaActaAsignacionFovis()) : null);
        actaAsignacionFOVIS.setFechaConfirmacion(this.getFechaConfirmacion() != null ? new Date(this.getFechaConfirmacion()) : null);
        actaAsignacionFOVIS.setFechaCorte(this.getFechaCorte() != null ? new Date(this.getFechaCorte()) : null);
        actaAsignacionFOVIS.setFechaFinVigencia(this.getFechaFinVigencia() != null ? new Date(this.getFechaFinVigencia()) : null);
        actaAsignacionFOVIS.setFechaInicioVigencia(this.getFechaInicioVigencia() != null ? new Date(this.getFechaInicioVigencia()) : null);
        actaAsignacionFOVIS.setFechaOficio(this.getFechaOficio() != null ? new Date(this.getFechaOficio()) : null);
        actaAsignacionFOVIS.setFechaResolucion(this.getFechaResolucion() != null ? new Date(this.getFechaResolucion()) : null);
        actaAsignacionFOVIS.setIdActaAsignacion(this.getIdActaAsignacion());
        actaAsignacionFOVIS.setIdDocumento(this.getIdDocumento());
        actaAsignacionFOVIS.setIdDocumentoConsolidado(this.getIdDocumentoConsolidado());
        actaAsignacionFOVIS.setNombreResponsable1(this.getNombreResponsable1());
        actaAsignacionFOVIS.setNombreResponsable2(this.getNombreResponsable2());
        actaAsignacionFOVIS.setNombreResponsable3(this.getNombreResponsable3());
        actaAsignacionFOVIS.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        actaAsignacionFOVIS.setFechaPublicacion(this.getFechaPublicacion() != null ? new Date(this.getFechaPublicacion()) : null);
        actaAsignacionFOVIS.setNumeroResolucion(this.getNumeroResolucion());
        actaAsignacionFOVIS.setNumeroOficio(this.getNumeroOficio());
        return actaAsignacionFOVIS;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param ResultadosAsignacionFOVIS
     */
    public void convertToDTO(ActaAsignacionFOVIS actaAsignacionFOVIS) {
        this.setAnoResolucion(actaAsignacionFOVIS.getAnoResolucion());
        this.setCargoResponsable1(actaAsignacionFOVIS.getCargoResponsable1());
        this.setCargoResponsable2(actaAsignacionFOVIS.getCargoResponsable2());
        this.setCargoResponsable3(actaAsignacionFOVIS.getCargoResponsable3());
        this.setFechaActaAsignacionFovis(actaAsignacionFOVIS.getFechaActaAsignacionFovis() != null
                ? actaAsignacionFOVIS.getFechaActaAsignacionFovis().getTime() : null);
        this.setFechaConfirmacion(
                actaAsignacionFOVIS.getFechaConfirmacion() != null ? actaAsignacionFOVIS.getFechaConfirmacion().getTime() : null);
        this.setFechaCorte(actaAsignacionFOVIS.getFechaCorte() != null ? actaAsignacionFOVIS.getFechaCorte().getTime() : null);
        this.setFechaFinVigencia(
                actaAsignacionFOVIS.getFechaFinVigencia() != null ? actaAsignacionFOVIS.getFechaFinVigencia().getTime() : null);
        this.setFechaInicioVigencia(
                actaAsignacionFOVIS.getFechaInicioVigencia() != null ? actaAsignacionFOVIS.getFechaInicioVigencia().getTime() : null);
        this.setFechaOficio(actaAsignacionFOVIS.getFechaOficio() != null ? actaAsignacionFOVIS.getFechaOficio().getTime() : null);
        this.setFechaResolucion(
                actaAsignacionFOVIS.getFechaResolucion() != null ? actaAsignacionFOVIS.getFechaResolucion().getTime() : null);
        this.setNombreResponsable1(actaAsignacionFOVIS.getNombreResponsable1());
        this.setNombreResponsable2(actaAsignacionFOVIS.getNombreResponsable2());
        this.setNombreResponsable3(actaAsignacionFOVIS.getNombreResponsable3());
        this.setIdActaAsignacion(actaAsignacionFOVIS.getIdActaAsignacion());
        this.setNumeroResolucion(actaAsignacionFOVIS.getNumeroResolucion());
        this.setIdDocumento(actaAsignacionFOVIS.getIdDocumento());
        this.setIdDocumentoConsolidado(actaAsignacionFOVIS.getIdDocumentoConsolidado());
        this.setNumeroOficio(actaAsignacionFOVIS.getNumeroOficio());
        this.setIdSolicitudAsignacion(actaAsignacionFOVIS.getIdSolicitudAsignacion());
        this.setFechaPublicacion(
                actaAsignacionFOVIS.getFechaPublicacion() != null ? actaAsignacionFOVIS.getFechaPublicacion().getTime() : null);
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * 
     * @param persona
     *        resultadosAsignacionFOVIS consultada.
     */
    public ActaAsignacionFOVIS copyDTOToEntity(ActaAsignacionFOVIS actaAsignacionFOVIS) {

        if (this.getAnoResolucion() != null) {
            actaAsignacionFOVIS.setAnoResolucion(this.getAnoResolucion());
        }
        if (this.getCargoResponsable1() != null) {
            actaAsignacionFOVIS.setCargoResponsable1(this.getCargoResponsable1());
        }
        if (this.getCargoResponsable2() != null) {
            actaAsignacionFOVIS.setCargoResponsable1(this.getCargoResponsable2());
        }
        if (this.getCargoResponsable3() != null) {
            actaAsignacionFOVIS.setCargoResponsable1(this.getCargoResponsable3());
        }
        if (this.getFechaConfirmacion() != null) {
            actaAsignacionFOVIS.setFechaConfirmacion(this.getFechaConfirmacion() != null ? new Date(this.getFechaConfirmacion()) : null);
        }

        if (this.getFechaActaAsignacionFovis() != null) {
            actaAsignacionFOVIS.setFechaActaAsignacionFovis(
                    this.getFechaActaAsignacionFovis() != null ? new Date(this.getFechaActaAsignacionFovis()) : null);
        }

        if (this.getFechaCorte() != null) {
            actaAsignacionFOVIS.setFechaCorte(this.getFechaCorte() != null ? new Date(this.getFechaCorte()) : null);
        }

        if (this.getFechaFinVigencia() != null) {
            actaAsignacionFOVIS.setFechaFinVigencia(this.getFechaFinVigencia() != null ? new Date(this.getFechaFinVigencia()) : null);
        }

        if (this.getFechaInicioVigencia() != null) {
            actaAsignacionFOVIS.setFechaInicioVigencia(this.getFechaFinVigencia() != null ? new Date(this.getFechaFinVigencia()) : null);
        }

        if (this.getFechaOficio() != null) {
            actaAsignacionFOVIS.setFechaOficio(this.getFechaOficio() != null ? new Date(this.getFechaOficio()) : null);
        }

        if (this.getIdActaAsignacion() != null) {
            actaAsignacionFOVIS.setIdActaAsignacion(this.getIdActaAsignacion());
        }

        if (this.getFechaResolucion() != null) {
            actaAsignacionFOVIS.setFechaResolucion(this.getFechaResolucion() != null ? new Date(this.getFechaResolucion()) : null);
        }

        if (this.getIdDocumento() != null) {
            actaAsignacionFOVIS.setIdDocumento(this.getIdDocumento());
        }

        if (this.getNombreResponsable1() != null) {
            actaAsignacionFOVIS.setNombreResponsable1(this.getNombreResponsable1());
        }

        if (this.getNombreResponsable2() != null) {
            actaAsignacionFOVIS.setNombreResponsable1(this.getNombreResponsable2());
        }

        if (this.getNombreResponsable3() != null) {
            actaAsignacionFOVIS.setNombreResponsable1(this.getNombreResponsable3());
        }

        if (this.getNumeroOficio() != null) {
            actaAsignacionFOVIS.setNumeroOficio(this.getNumeroOficio());
        }

        if (this.getNumeroResolucion() != null) {
            actaAsignacionFOVIS.setNumeroResolucion(this.getNumeroResolucion());
        }
        if (this.getIdSolicitudAsignacion() != null) {
            actaAsignacionFOVIS.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        }

        if (this.getIdDocumentoConsolidado() != null) {
            actaAsignacionFOVIS.setIdDocumentoConsolidado(this.getIdDocumentoConsolidado());
        }

        if (this.getFechaPublicacion() != null) {
            actaAsignacionFOVIS.setFechaPublicacion(this.getFechaPublicacion() != null ? new Date(this.getFechaPublicacion()) : null);
        }

        return actaAsignacionFOVIS;
    }

    /**
     * @return the idActaAsignacion
     */
    public Long getIdActaAsignacion() {
        return idActaAsignacion;
    }

    /**
     * @param idActaAsignacion
     *        the idActaAsignacion to set
     */
    public void setIdActaAsignacion(Long idActaAsignacion) {
        this.idActaAsignacion = idActaAsignacion;
    }

    /**
     * @return the numeroResolucion
     */
    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    /**
     * @param numeroResolucion
     *        the numeroResolucion to set
     */
    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    /**
     * @return the anoResolucion
     */
    public String getAnoResolucion() {
        return anoResolucion;
    }

    /**
     * @param anoResolucion
     *        the anoResolucion to set
     */
    public void setAnoResolucion(String anoResolucion) {
        this.anoResolucion = anoResolucion;
    }

    /**
     * @return the fechaResolucion
     */
    public Long getFechaResolucion() {
        return fechaResolucion;
    }

    /**
     * @param fechaResolucion
     *        the fechaResolucion to set
     */
    public void setFechaResolucion(Long fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    /**
     * @return the numeroOficio
     */
    public String getNumeroOficio() {
        return numeroOficio;
    }

    /**
     * @param numeroOficio
     *        the numeroOficio to set
     */
    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    /**
     * @return the fechaOficio
     */
    public Long getFechaOficio() {
        return fechaOficio;
    }

    /**
     * @param fechaOficio
     *        the fechaOficio to set
     */
    public void setFechaOficio(Long fechaOficio) {
        this.fechaOficio = fechaOficio;
    }

    /**
     * @return the nombreResponsable1
     */
    public String getNombreResponsable1() {
        return nombreResponsable1;
    }

    /**
     * @param nombreResponsable1
     *        the nombreResponsable1 to set
     */
    public void setNombreResponsable1(String nombreResponsable1) {
        this.nombreResponsable1 = nombreResponsable1;
    }

    /**
     * @return the cargoResponsable1
     */
    public String getCargoResponsable1() {
        return cargoResponsable1;
    }

    /**
     * @param cargoResponsable1
     *        the cargoResponsable1 to set
     */
    public void setCargoResponsable1(String cargoResponsable1) {
        this.cargoResponsable1 = cargoResponsable1;
    }

    /**
     * @return the nombreResponsable2
     */
    public String getNombreResponsable2() {
        return nombreResponsable2;
    }

    /**
     * @param nombreResponsable2
     *        the nombreResponsable2 to set
     */
    public void setNombreResponsable2(String nombreResponsable2) {
        this.nombreResponsable2 = nombreResponsable2;
    }

    /**
     * @return the cargoResponsable2
     */
    public String getCargoResponsable2() {
        return cargoResponsable2;
    }

    /**
     * @param cargoResponsable2
     *        the cargoResponsable2 to set
     */
    public void setCargoResponsable2(String cargoResponsable2) {
        this.cargoResponsable2 = cargoResponsable2;
    }

    /**
     * @return the nombreResponsable3
     */
    public String getNombreResponsable3() {
        return nombreResponsable3;
    }

    /**
     * @param nombreResponsable3
     *        the nombreResponsable3 to set
     */
    public void setNombreResponsable3(String nombreResponsable3) {
        this.nombreResponsable3 = nombreResponsable3;
    }

    /**
     * @return the cargoResponsable3
     */
    public String getCargoResponsable3() {
        return cargoResponsable3;
    }

    /**
     * @param cargoResponsable3
     *        the cargoResponsable3 to set
     */
    public void setCargoResponsable3(String cargoResponsable3) {
        this.cargoResponsable3 = cargoResponsable3;
    }

    /**
     * @return the fechaConfirmacion
     */
    public Long getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    /**
     * @param fechaConfirmacion
     *        the fechaConfirmacion to set
     */
    public void setFechaConfirmacion(Long fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    /**
     * @return the fechaCorte
     */
    public Long getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte
     *        the fechaCorte to set
     */
    public void setFechaCorte(Long fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the fechaInicioVigencia
     */
    public Long getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    /**
     * @param fechaInicioVigencia
     *        the fechaInicioVigencia to set
     */
    public void setFechaInicioVigencia(Long fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    /**
     * @return the fechaFinVigencia
     */
    public Long getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    /**
     * @param fechaFinVigencia
     *        the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Long fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    /**
     * @return the idSolicitudAsignacion
     */
    public Long getIdSolicitudAsignacion() {
        return idSolicitudAsignacion;
    }

    /**
     * @param idSolicitudAsignacion
     *        the idSolicitudAsignacion to set
     */
    public void setIdSolicitudAsignacion(Long idSolicitudAsignacion) {
        this.idSolicitudAsignacion = idSolicitudAsignacion;
    }

    /**
     * @return the idDocumentoConsolidado
     */
    public String getIdDocumentoConsolidado() {
        return idDocumentoConsolidado;
    }

    /**
     * @param idDocumentoConsolidado
     *        the idDocumentoConsolidado to set
     */
    public void setIdDocumentoConsolidado(String idDocumentoConsolidado) {
        this.idDocumentoConsolidado = idDocumentoConsolidado;
    }

    /**
     * @param idDocumento
     *        the idDocumento to set
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * @return the idDocumento
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * @return the fechaActaAsignacionFovis
     */
    public Long getFechaActaAsignacionFovis() {
        return fechaActaAsignacionFovis;
    }

    /**
     * @param fechaActaAsignacionFovis
     *        the fechaActaAsignacionFovis to set
     */
    public void setFechaActaAsignacionFovis(Long fechaActaAsignacionFovis) {
        this.fechaActaAsignacionFovis = fechaActaAsignacionFovis;
    }

    /**
     * @return the solicitudAsignacion
     */
    public SolicitudAsignacionFOVISModeloDTO getSolicitudAsignacion() {
        return solicitudAsignacion;
    }

    /**
     * @param solicitudAsignacion
     *        the solicitudAsignacion to set
     */
    public void setSolicitudAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        this.solicitudAsignacion = solicitudAsignacion;
    }

    /**
     * @return the fechaPublicacion
     */
    public Long getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion
     *        the fechaPublicacion to set
     */
    public void setFechaPublicacion(Long fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

}
