package com.asopagos.entidaddescuento.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.CausalAnulacionArchivoDescuentoEnum;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ArchivoEntidadDescuentoSubsidioPignorado;

/**
 * <b>Descripción:</b> DTO que contiene los atributos de trazabilidad para un archivo de descuento
 * <br>
 * HU 311-432<br>
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@XmlRootElement
public class ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de la trazabilidad correspondiente al archivo de descuentos
     */
    private Long idArchivoEntidadDescuentoSubsidioMonetario;

    /**
     * Fecha de recepción del archivo de descuentos
     */
    @NotNull
    private Date fechaRecepcion;

    /**
     * Fecha de cargue del archivo de descuentos
     */
    private Date fechaCargue;

    /**
     * Identificador del archivo de descuento en el ECM
     */
    private String codigoIdentificacionECM;

    /**
     * Identificador de la entidad de descuento propietaria del archivo
     */
    @NotNull
    private Long idEntidadDescuento;

    /**
     * Estado de la carga del archivo de descuentos
     */
    private EstadoCargaArchivoDescuentoEnum estadoCarga;

    /**
     * Causal de anulacion del archivo de descuento
     */
    private CausalAnulacionArchivoDescuentoEnum causalAnulacion;

    /**
     * Nombre del archivo de descuentos
     */
    @NotNull
    private String nombre;

    /**
     * Numero de radicacion de la solicitud de liquidación que pignora los valores del archivo
     */
    private String numeroRadicacion;

    /**
     * Metodo que convierte una Entidad a un DTO.
     * @param archivoDescuento
     *        entidad con la información del archivo de descuento
     */
    public void convertToDTO(ArchivoEntidadDescuentoSubsidioPignorado archivoDescuento) {

        this.setIdArchivoEntidadDescuentoSubsidioMonetario(archivoDescuento.getIdArchivoEntidadDescuentoSubsidioMonetario());
        this.setIdEntidadDescuento(archivoDescuento.getIdEntidadDescuento());
        this.setCodigoIdentificacionECM(archivoDescuento.getCodigoIdentificacionECM());
        this.setEstadoCarga(archivoDescuento.getEstadoCarga());
        this.setCausalAnulacion(archivoDescuento.getCausalAnulacion());
        this.setFechaCargue(archivoDescuento.getFechaCargue());
        this.setFechaRecepcion(archivoDescuento.getFechaRecepcion());
        this.setNombre(archivoDescuento.getNombre());
    }

    /**
     * 
     * Metodo que convierte el DTO a la entidad
     * 
     * @return entidad correspondiente a la información de trazabilidad para el archivo de descuento
     */
    public ArchivoEntidadDescuentoSubsidioPignorado convertToEntity() {

        ArchivoEntidadDescuentoSubsidioPignorado infoTrazabilidad = new ArchivoEntidadDescuentoSubsidioPignorado();

        infoTrazabilidad.setIdArchivoEntidadDescuentoSubsidioMonetario(this.getIdArchivoEntidadDescuentoSubsidioMonetario());
        infoTrazabilidad.setIdEntidadDescuento(this.getIdEntidadDescuento());
        infoTrazabilidad.setCodigoIdentificacionECM(this.getCodigoIdentificacionECM());
        infoTrazabilidad.setEstadoCarga(this.getEstadoCarga());
        infoTrazabilidad.setCausalAnulacion(this.getCausalAnulacion());
        infoTrazabilidad.setFechaCargue(this.getFechaCargue());
        infoTrazabilidad.setFechaRecepcion(this.getFechaRecepcion());
        infoTrazabilidad.setNombre(this.getNombre());

        return infoTrazabilidad;
    }

    /**
     * @return the idArchivoEntidadDescuentoSubsidioMonetario
     */
    public Long getIdArchivoEntidadDescuentoSubsidioMonetario() {
        return idArchivoEntidadDescuentoSubsidioMonetario;
    }

    /**
     * @param idArchivoEntidadDescuentoSubsidioMonetario
     *        the idArchivoEntidadDescuentoSubsidioMonetario to set
     */
    public void setIdArchivoEntidadDescuentoSubsidioMonetario(Long idArchivoEntidadDescuentoSubsidioMonetario) {
        this.idArchivoEntidadDescuentoSubsidioMonetario = idArchivoEntidadDescuentoSubsidioMonetario;
    }

    /**
     * @return the fechaRecepcion
     */
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * @param fechaRecepcion
     *        the fechaRecepcion to set
     */
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * @return the fechaCargue
     */
    public Date getFechaCargue() {
        return fechaCargue;
    }

    /**
     * @param fechaCargue
     *        the fechaCargue to set
     */
    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    /**
     * @return the codigoIdentificacionECM
     */
    public String getCodigoIdentificacionECM() {
        return codigoIdentificacionECM;
    }

    /**
     * @param codigoIdentificacionECM
     *        the codigoIdentificacionECM to set
     */
    public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
        this.codigoIdentificacionECM = codigoIdentificacionECM;
    }

    /**
     * @return the idEntidadDescuento
     */
    public Long getIdEntidadDescuento() {
        return idEntidadDescuento;
    }

    /**
     * @param idEntidadDescuento
     *        the idEntidadDescuento to set
     */
    public void setIdEntidadDescuento(Long idEntidadDescuento) {
        this.idEntidadDescuento = idEntidadDescuento;
    }

    /**
     * @return the estadoCarga
     */
    public EstadoCargaArchivoDescuentoEnum getEstadoCarga() {
        return estadoCarga;
    }

    /**
     * @param estadoCarga
     *        the estadoCarga to set
     */
    public void setEstadoCarga(EstadoCargaArchivoDescuentoEnum estadoCarga) {
        this.estadoCarga = estadoCarga;
    }

    /**
     * @return the causalAnulacion
     */
    public CausalAnulacionArchivoDescuentoEnum getCausalAnulacion() {
        return causalAnulacion;
    }

    /**
     * @param causalAnulacion
     *        the causalAnulacion to set
     */
    public void setCausalAnulacion(CausalAnulacionArchivoDescuentoEnum causalAnulacion) {
        this.causalAnulacion = causalAnulacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO{" +
                "idArchivoEntidadDescuentoSubsidioMonetario=" + idArchivoEntidadDescuentoSubsidioMonetario +
                ", fechaRecepcion=" + fechaRecepcion +
                ", fechaCargue=" + fechaCargue +
                ", codigoIdentificacionECM='" + codigoIdentificacionECM + '\'' +
                ", idEntidadDescuento=" + idEntidadDescuento +
                ", estadoCarga=" + estadoCarga +
                ", causalAnulacion=" + causalAnulacion +
                ", nombre='" + nombre + '\'' +
                ", numeroRadicacion='" + numeroRadicacion + '\'' +
                '}';
    }
}
